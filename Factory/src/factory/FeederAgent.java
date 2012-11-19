package factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import agent.Agent;
import factory.interfaces.Feeder;
import factory.interfaces.Gantry;
import factory.interfaces.Lane;
import factory.interfaces.Nest;
import factory.interfaces.Vision;
import factory.masterControl.MasterControl;
import factory.test.mock.EventLog;
import factory.test.mock.LoggedEvent;



public class FeederAgent extends Agent implements Feeder {

	/** DATA **/
	
	//agents
	public Vision vision;
	public Gantry gantry;
	
	private final static int kNUM_PARTS_FED = 15;
	private final static int kOK_TO_PURGE_TIME = 7;
	public int feederNumber;
	public List<MyPartRequest> requestedParts = Collections.synchronizedList(new ArrayList<MyPartRequest>());
	
	public boolean myNestsHaveBeenChecked = false;
	public Part currentPart;
	Bin dispenserBin; // need to use this
	
	//list of lanes per feeder
	public MyLane topLane;
	public MyLane bottomLane;

	//list of timers to indicate when it's OK to feed, when the feeder is empty, and when the part is resettled
	public Timer okayToPurgeTimer = new Timer();
	public Timer feederEmptyTimer = new Timer();
	public Timer partResettleTimer = new Timer();
	public boolean feederHasABinUnderneath = false; // no bin underneath the feeder initially

	//enum for the feeder state
	public enum FeederState { EMPTY, WAITING_FOR_PARTS, CONTAINS_PARTS, OK_TO_PURGE, SHOULD_START_FEEDING, IS_FEEDING }
	public FeederState state = FeederState.EMPTY;
	
	//enum for the diverter state
	public enum DiverterState { FEEDING_TOP, FEEDING_BOTTOM }
	public DiverterState diverter = DiverterState.FEEDING_BOTTOM;
	
	//enum for part request state
	public enum MyPartRequestState { NEEDED, ASKED_GANTRY, DELIVERED, PROCESSING }
	
	//enum for picture state
	public enum PictureState {UNSTABLE, STABLE, TOLD_VISION_TO_TAKE_PICTURE }
	
	//enum for lane state
	public enum MyLaneState {EMPTY, PURGING, CONTAINS_PARTS, BAD_NEST, 
		TOLD_NEST_TO_DUMP, NEST_SUCCESSFULLY_DUMPED}
	
	//enum for jam state
	public enum JamState {MIGHT_BE_JAMMED, TOLD_TO_INCREASE_AMPLITUDE, 
		AMPLITUDE_WAS_INCREASED, NOT_JAMMED }

	public EventLog log = new EventLog();


	public class MyPartRequest {
		public Part pt;
		public MyPartRequestState state;
		public Lane lane;

		// alternative constructor #1
		public MyPartRequest(Part part, Lane lane){
			this.state = MyPartRequestState.NEEDED;
			this.lane = lane;
			this.pt = part;
		}


		// alternative constructor #2
		public MyPartRequest(Part p, Lane l, MyPartRequestState s) {
			this.pt = p;
			this.lane = l;
			this.state = s;
		}
	}



	public class MyLane {

		public Lane lane;
		public MyLaneState state = MyLaneState.EMPTY;
		public PictureState picState = PictureState.UNSTABLE; // initially it should be empty. essentially unstbale
		public JamState jamState;
		public Part part;

		public MyLane(Lane lane){
			this.state = MyLaneState.EMPTY;
			this.lane = lane;
			this.jamState = JamState.NOT_JAMMED;
		}
	}


	public FeederAgent(String nameStr,int slot, Lane top, Lane bottom, Gantry g, Vision v, MasterControl mc) {
		super(mc);
		this.topLane = new MyLane(top);
		this.bottomLane = new MyLane(bottom);
		this.gantry = g;
		this.vision = v;
		this.feederNumber = slot;
	}


	/** MESSAGES **/
	
	/**
	 *  The Lane sends this message to the Feeder notifying 
	 *  him that his nest has become stable.
	 */ 
	public void msgNestHasStabilized(Lane lane) {
		myNestsHaveBeenChecked = false;
		
		// figure out which lane sent the message
		if(topLane.lane == lane)
		{
			debug("My TOP lane has stabilized and so it's ready for a picture.");
			topLane.picState = PictureState.STABLE;
		}
		else if(bottomLane.lane == lane)
		{
			debug("My BOTTOM lane has stabilized and so it's ready for a picture.");
			bottomLane.picState = PictureState.STABLE;
		}
		
		// One of the nests has stabilized, let's check
		// to see if the nests are ready for a picture:
		areMyNestsReadyForAPicture();
		
		stateChanged();
	}

	/**
	 *  The Lane sends this message to the Feeder notifying him that his nest has become unstable.
	 */ 
	public void msgNestHasDeStabilized(Lane lane) {
		myNestsHaveBeenChecked = false;
		
		// figure out which lane sent the message
		if(topLane.lane == lane)
		{
			debug("Uhoh, my TOP lane has destabilized!");
			topLane.picState = PictureState.UNSTABLE;
		}
		else if(bottomLane.lane == lane)
		{
			debug("Uhoh, my BOTTOM lane has destabilized!");
			bottomLane.picState = PictureState.UNSTABLE;
		}
		
		stateChanged();
	}
	
	
	/**
	 *  The vision sends this message notifying 
	 *  the Feeder that it had a empty nest. 
	 *  v.2
	 * */
	public void msgEmptyNest(Nest n) {
		debug("received msgEmptyNest()");
		if (topLane.lane.getNest() == n) 
		{
			topLane.lane.msgIncreaseAmplitude();
			topLane.jamState = JamState.MIGHT_BE_JAMMED;
		}
		else if (bottomLane.lane.getNest() == n) 
		{
			bottomLane.lane.msgIncreaseAmplitude();
			bottomLane.jamState = JamState.MIGHT_BE_JAMMED;
		}
		stateChanged();
	}

	
	/** One of the Feeder's lanes sends this message to the
	 *  Feeder telling him that his nest was sucessfully dumped.
	 */
	public void msgNestWasDumped(Lane la) {
		String laneStr = "Top Lane";
		if (bottomLane.lane == la)
			laneStr = "Bottom Lane";

		debug("received msgNestWasDumped("+ laneStr + ")");
		if (topLane.lane == la)
		{
			topLane.state = MyLaneState.NEST_SUCCESSFULLY_DUMPED;
		}
		else if (bottomLane.lane == la)
		{
			bottomLane.state = MyLaneState.NEST_SUCCESSFULLY_DUMPED;
		}
		stateChanged();
	}

	
	/** One of the Feeder's lanes sends this message to the Feeder when he wants a part. 
	 *  It is the end of a chain of messages originally sent by the PartsRobot.
	 */
	public void msgLaneNeedsPart(Part part, Lane lane) {
		if (lane != null && part != null)
		{
			requestedParts.add(new MyPartRequest(part, lane)); // add this part request to the list of part requests
		}
		stateChanged();
	}

	
	/** The Gantry sends this message when he is 
	 *  delivering parts to the Feeder. */
	public void msgHereAreParts(Part part) {
		debug("received msgHereAreParts("+part.name+")");

		feederHasABinUnderneath = true; // should never be false again

		synchronized(requestedParts)
		{
			for (MyPartRequest pr : requestedParts) {
				if(pr.state == MyPartRequestState.ASKED_GANTRY && pr.pt.id == part.id) {
					pr.state = MyPartRequestState.DELIVERED;
				}
			}
		}
		stateChanged();
	}

	/**
	 *  The vision sends this message notifying 
	 *  the Feeder that it had a bad nest. 
	 *  v.2
	 * */
	public void msgBadNest(Nest n) {
		debug("received msgBadNest()");
		if (topLane.lane.getNest() == n) 
			topLane.state = MyLaneState.BAD_NEST;
		else if (bottomLane.lane.getNest() == n)
			bottomLane.state = MyLaneState.BAD_NEST;
		stateChanged();
	}


	/** SCHEDULER **/
	@Override
	public boolean pickAndExecuteAnAction() {

		// Determine if the Nests need to be checked to see if they are ready for a picture
		if (myNestsHaveBeenChecked == false)
		{
			return areMyNestsReadyForAPicture();
		}
		
		
		if (state == FeederState.EMPTY || state == FeederState.OK_TO_PURGE)
		{
			synchronized(requestedParts)
			{
				for (MyPartRequest p : requestedParts)
				{
					if (p.state == MyPartRequestState.NEEDED)
					{
						askGantryForPart(p);
						return true;
					}
				}
			}
		}

		if (state == FeederState.SHOULD_START_FEEDING) 
		{ 
			//			log.add(new LoggedEvent(
			//					"Trying to decide which lane to feed parts to."));

			// Which lane should it feed to?
			
			if (topLane.part != null)
			{
				if (topLane.part == currentPart) // topLane.part is set in the processFeederParts() method
				{
					if (topLane.state == MyLaneState.EMPTY || topLane.state == MyLaneState.CONTAINS_PARTS)
					{
						// check to see if the lane diverter needs to switch
						if (diverter == DiverterState.FEEDING_BOTTOM) {
							diverter = DiverterState.FEEDING_TOP;
							DoSwitchLane();   // Animation to switch lane
						}

						state = FeederState.IS_FEEDING; // we don't want to call this code an infinite number of times
						StartFeeding();
						return true;
					}
					return true;
				}
			} // note: bottomeLane.part should never be null

			if (bottomLane.part != null) // bottomLane.part is set in the processFeederParts() method
			{
				if (bottomLane.part == currentPart)
				{
					if (bottomLane.state == MyLaneState.EMPTY || bottomLane.state == MyLaneState.CONTAINS_PARTS)
					{
						// check to see if the lane diverter needs to switch
						if (diverter == DiverterState.FEEDING_TOP) {
							diverter = DiverterState.FEEDING_BOTTOM;
							DoSwitchLane();   // Animation to switch lane
						}

						state = FeederState.IS_FEEDING; // we don't want to call this code an infinite number of times
						StartFeeding();
						return true;
					}
					return true;
				}
			}
			return true;
		}

		synchronized(requestedParts)
		{
			for (MyPartRequest p : requestedParts) 
			{
				if (p.state == MyPartRequestState.DELIVERED)
				{
					processFeederParts(p);
					return true;
				}
			}
		}

		if (topLane.state == MyLaneState.BAD_NEST) 
		{
			dumpNest(topLane);
			return true;
		}

		if (bottomLane.state == MyLaneState.BAD_NEST)
		{
			dumpNest(bottomLane);
			return true;
		}

		if (topLane.state == MyLaneState.NEST_SUCCESSFULLY_DUMPED)
		{
			nestWasDumped(topLane);
			return true;
		}

		if (bottomLane.state == MyLaneState.NEST_SUCCESSFULLY_DUMPED)
		{
			nestWasDumped(bottomLane);
			return true;
		}


		// If nothing else has been called, then return false!
		return false;
	}




	/** ACTIONS **/
	private boolean areMyNestsReadyForAPicture() {
		myNestsHaveBeenChecked = true;
		
		// Determine if the Feeder's associated Nests are ready for a picture
		boolean bothNestsStable = topLane.picState == PictureState.STABLE && 
									bottomLane.picState == PictureState.STABLE;
		
		boolean topNestStableBottomWaitingForPicture = topLane.picState == PictureState.STABLE && 
										bottomLane.picState == PictureState.TOLD_VISION_TO_TAKE_PICTURE;
		
		boolean bottomNestStableTopWaitingForPicture = bottomLane.picState == PictureState.STABLE && 
				topLane.picState == PictureState.TOLD_VISION_TO_TAKE_PICTURE;
		
		// Now check if any of the above conditions are true
		if (bothNestsStable || topNestStableBottomWaitingForPicture || bottomNestStableTopWaitingForPicture)
		{
			topLane.picState = PictureState.TOLD_VISION_TO_TAKE_PICTURE;   // we have told the vision to take a picture, only send this once
			bottomLane.picState = PictureState.TOLD_VISION_TO_TAKE_PICTURE; // we have told the vision to take a picture, only send this once
			debug("Feeder sends msgMyNestsReadyForPicture() to vision.");
			vision.msgMyNestsReadyForPicture(topLane.lane.getNest(), bottomLane.lane.getNest(), this); // send the message to the vision
			myNestsHaveBeenChecked = true;
			stateChanged();
			return true;
		}
		
		stateChanged();
		return false;
	}
	private void nestWasDumped(MyLane la) {
		String laneStr = "Top Lane";
		if (bottomLane == la)
			laneStr = "Bottom Lane";

		debug("has dumped nest of lane " + laneStr + ".");
		if (this.state == FeederState.CONTAINS_PARTS)
		{
			// only if the feeder is actually feeding
			la.state = MyLaneState.CONTAINS_PARTS;
			DoContinueFeeding(currentPart);
		}
		else // the nestWasDumped from within a purge cycle
		{
			la.state = MyLaneState.PURGING;
		}
		stateChanged();
	}

	private void dumpNest(MyLane la) {
		// Print the correct lane name
		String laneStr = "Top Lane";
		if (bottomLane == la)
			laneStr = "Bottom Lane";

		debug("Go dump nest of lane "+ laneStr + ".");
		
		DoStopFeeding(); // stop feeding the parts into the lane
		la.state = MyLaneState.TOLD_NEST_TO_DUMP; // the lane has been told to dump its nest
		la.lane.msgDumpNest(); // tell the lane to dump its nest
		stateChanged();

	}

	private void askGantryForPart(MyPartRequest partRequested) { 
		debug("asking gantry for part " + partRequested.pt.name + ".");

		// The feeder won't need to be purged:
		if (this.currentPart != null)
		{
			if (this.currentPart.id == partRequested.pt.id && state != FeederState.EMPTY)
			{
				debug("feeder doesn't need to purge.");
				state = FeederState.IS_FEEDING;
				partRequested.state = MyPartRequestState.DELIVERED;
			}
		}
		
		// The feeder MIGHT need to be purged (or it could just be empty):
		if (purgeIfNecessary(partRequested) || this.state == FeederState.EMPTY )
		{
			state = FeederState.WAITING_FOR_PARTS;
			partRequested.state = MyPartRequestState.ASKED_GANTRY;
			gantry.msgFeederNeedsPart(partRequested.pt, this);
		}

		stateChanged();
	}

	private boolean purgeIfNecessary(MyPartRequest partRequested) { 
		debug("purging if necessary.");
		log.add(new LoggedEvent(
				"Action purgeIfNecessary()"));

		boolean purging = false; // assume we won't be purging

		// Check if Feeder needs to be purged
		if (this.currentPart != partRequested.pt && state == FeederState.OK_TO_PURGE)
		{
			purging = true;
			purgeFeeder();	
		}

		// Check if lane needs to be purged
		// A BUG IS HERE
		if (topLane.lane == partRequested.lane && state == FeederState.OK_TO_PURGE)
		{
			if (topLane.part != null) // the topLane's part is initially null, which is always != to 
			{						  // the partRequested.pt, but we don't want to purge when the lane is empty!
				if (topLane.part.id != partRequested.pt.id)
				{
					purging = true;
					purgeLane(topLane);
				}
			}
		}

		if (bottomLane.lane == partRequested.lane && state == FeederState.OK_TO_PURGE)
		{
			if (bottomLane.part != null) // the bottomlane's part is initially null, which is always != to
			{						  	 // the partRequested.pt, but we don't want to purge when the lane is empty!
				if (bottomLane.part.id != partRequested.pt.id)
				{
					purging = true;
					purgeLane(bottomLane);
				}
			}
		}

		if (purging == true)
			debug("yep, purging.");
		else
			debug("nope, not purging.");

		return purging;
	}

	private void purgeFeeder(){

		DoStopFeeding(); // Stop sending parts into the lane(s)
		DoPurgeFeeder(); // Purge the feeder 

		currentPart = null; // and the feeder no longer has a part in it, so its current part is null

	}

	private void purgeLane(MyLane myLane){
		myLane.state = MyLaneState.PURGING; // The lane is now purging		
		
		// Call the purge lane animation for the appropriate lane
		if (myLane == topLane)
		{
			DoPurgeTopLane();
		}
		else if (myLane == bottomLane)
		{
			DoPurgeBottomLane();
		}

		myLane.state = MyLaneState.EMPTY; // we have received a message from the animation telling us that the lane has been purged

		myLane.lane.msgPurge(); // tell the lane to purge
		
	}



	private void processFeederParts(MyPartRequest mpr){
		debug("processing feeder parts of type "+mpr.pt.name);
		
		mpr.state = MyPartRequestState.PROCESSING; // the part request is being processed
		log.add(new LoggedEvent("Action ProcessFeederParts()"));
		
		// Remove the part request, it is no longer needed
		synchronized(requestedParts)
		{
			requestedParts.remove(mpr);
		}
		
		currentPart = mpr.pt; // Set the current part to be the requested part

		// Check to see if the request part is null (this would be an error)
		if (mpr.pt == null)
			debug("Mpr.pt IS NULL");

		// Set the target lane's part to be the requested part
		if (bottomLane.lane == mpr.lane){
			bottomLane.part = mpr.pt;
		}
		else {
			topLane.part = mpr.pt;
		}

		// The feeder should start feeding now
		state = FeederState.SHOULD_START_FEEDING;

		stateChanged();
	}

	private void StartFeeding(){
		debug("action start feeding.");
		
		// Make a reference to the MyLane that must be fed
		MyLane currentLane = null;
		if (topLane.part != null)
		{
			if(currentPart.id == topLane.part.id)
			{
				currentLane = topLane;
			}
		}
		
		if (bottomLane.part != null)
		{
			if(currentPart.id == bottomLane.part.id)
			{
				currentLane = bottomLane;
			}
		}
		
		
		// This timer will run so that we don't automatically purge a
		// lane when a second request comes to the feeder (note: the 
		// feeder almost always has multiple requests coming in at once)
		okayToPurgeTimer.schedule(new TimerTask(){
			public void run() {
				state = FeederState.OK_TO_PURGE;
				DoStopFeeding(); // for v.0/v.1
				debug("it is now okay to purge this feeder.");
				stateChanged();
			}
		},(long) kOK_TO_PURGE_TIME * 1000); // okay to purge after this many seconds

		
		DoStartFeeding(currentPart);
		//currentLane.lane.msgFeedingParts(kNUM_PARTS_FED); // we feed a constant number of parts into the lane each time
		
		System.out.println("1.6");

		stateChanged();

		
		// NOT NEEDED:
//		feederEmptyTimer.schedule(new TimerTask(){
//			public void run(){	
//					/** */
//				partResettleTimer.schedule(new TimerTask() {
//					public void run() {
//						if (currentLane == topLane)
//							debug("My top lane is ready for a picture.");
//						else
//							debug("My bottom lane is ready for a picture.");
//
//						
//						currentLane.readyForPicture = true;
//
//						if (bottomLane.readyForPicture == true && topLane.readyForPicture == true)
//						{
//							System.out.println("1.0");
//							debug("both of my nests are ready for a picture.");
//							vision.msgMyNestsReadyForPicture(topLane.lane.getNest(), bottomLane.lane.getNest(), this);
//							topLane.readyForPicture = false;
//							bottomLane.readyForPicture = false;
//							System.out.println("1.1");
//						}
//						System.out.println("1.2");
//						stateChanged();
//						System.out.println("1.3");
//					}
//				}, 3000); // 3 seconds to resettle in the nest
//				System.out.println("1.4");
//			}
//		}, (long) kOK_TO_PURGE_TIME * 1000); // time it takes the part to move down the lane and fill a nest 
//		System.out.println("1.5");

		//	Timer.new(30000, { state = FeederState.OK_TO_PURGE; });
		//		Timer.new(currentPart.averageDelayTime,{vision.msgMyNestsReadyForPicture(topLane.lane.getNest(), bottomLane.lane.getNest(), this) });
		// need a timer so that we don't immediately purge the new ones
	}

	
	/** This method sets up the pointers to this Feeder's Lanes. **/
	public void setUpLanes(Lane top,Lane bottom) 
	{
		topLane = new MyLane(top);
		bottomLane = new MyLane(bottom);
	}

	/** This method connects the feeder to the gantry. **/
	public void setGantry(Gantry g)
	{
		this.gantry = g;
	}


	/** This method adds a MyPartRequest to the requestedParts list. 
	 *  It is used for testing purposes only.
	 */
	public void addMyPartRequest(Part p, Lane l, MyPartRequestState s) {
		requestedParts.add(new MyPartRequest(p,l,s));
	}

	/** ANIMATIONS 
	 * @throws InterruptedException **/
	private void DoStartFeeding(Part part) {
		server.command("fa fpm cmd startfeeding " + feederNumber);
	
		debug("Feeder " + feederNumber + " started feeding.");
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void DoStopFeeding() { 
		server.command("fa fpm cmd stopfeeding " + feederNumber);
		debug("stopped feeding.");
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void DoPurgeFeeder() {
		log.add(new LoggedEvent(
				"Animation DoPurgeFeeder()"));

		server.command("fa fpm cmd purgefeeder " + feederNumber); /** TODO: MULTI **/
		debug("purging feeder.");
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void DoSwitchLane() {
		log.add(new LoggedEvent(
				"Animation DoSwitchLane()"));

		server.command("fa fpm cmd switchlane " + feederNumber);
		debug("switching lane");
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void DoContinueFeeding(Part part) {
		debug("continued feeding.");
		// worry about this later (v.2)
	}

	private void DoPurgeTopLane() {
		log.add(new LoggedEvent(
				"Animation DoPurgeTopLane()"));

		server.command("fa fpm cmd purgetoplane " + feederNumber); /** TODO: MULTI **/
		debug("purging top lane");
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void DoPurgeBottomLane() {
		log.add(new LoggedEvent(
				"Animation DoPurgeBottomLane()"));

		server.command("fa fpm cmd purgebottomlane " + feederNumber); /** TODO: MULTI **/
		debug("purging bottom lane");
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	/** OTHER **/
	public boolean getFeederHasABinUnderneath() 
	{
		return feederHasABinUnderneath;
	}

	public int getFeederNumber()
	{
		return this.feederNumber;
	}

	




}







