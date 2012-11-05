package factory;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import agent.Agent;
import factory.interfaces.Feeder;
import factory.interfaces.Gantry;
import factory.interfaces.Lane;
import factory.interfaces.Nest;
import factory.interfaces.Vision;

public class FeederAgent extends Agent implements Feeder {

	/** DATA **/
	//private GraphicLaneManagerClient graphicLaneManagerClient;
	private String name;
	public int feederSlot;
	public ArrayList<MyPartRequest> requestedParts = new ArrayList<MyPartRequest>();   
	public MyLane topLane;
	public MyLane bottomLane;
	Vision vision;
	public DiverterState diverter; 
	public Gantry gantry;
	public Part currentPart;
	Bin dispenserBin; // need to use this
	public FeederState state = FeederState.EMPTY;
	public Timer okayToPurgeTimer = new Timer();
	public Timer feederEmptyTimer = new Timer();
	public Timer partResettleTimer = new Timer();

	public enum FeederState { EMPTY, WAITING_FOR_PARTS, CONTAINS_PARTS, OK_TO_PURGE, SHOULD_START_FEEDING }
	public enum DiverterState { FEEDING_TOP, FEEDING_BOTTOM }

	public enum MyPartRequestState { NEEDED, ASKED_GANTRY, DELIVERED }

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


	public enum MyLaneState {EMPTY, PURGING, CONTAINS_PARTS, BAD_NEST, 
		TOLD_NEST_TO_DUMP, NEST_SUCCESSFULLY_DUMPED}
	public enum JamState {MIGHT_BE_JAMMED, TOLD_TO_INCREASE_AMPLITUDE, 
		AMPLITUDE_WAS_INCREASED, NOT_JAMMED }

	public class MyLane {

		public Lane lane;
		public MyLaneState state; 
		public JamState jamState;
		public Part part;
		public boolean readyForPicture = false;

		public MyLane(Lane lane){
			this.state = MyLaneState.EMPTY;
			this.lane = lane;
			this.jamState = JamState.NOT_JAMMED;
		}
	}


	public FeederAgent(String nameStr,int slot) {
		super();
		this.name = nameStr;
		this.feederSlot = slot;
	}


	/** MESSAGES **/
	public void msgEmptyNest(Nest n) {
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
	}

	public void msgNestWasDumped(Lane la) {
		if (topLane.lane == la)
		{
			topLane.state = MyLaneState.NEST_SUCCESSFULLY_DUMPED;
		}
		else if (bottomLane.lane == la)
		{
			bottomLane.state = MyLaneState.NEST_SUCCESSFULLY_DUMPED;
		}
	}

	public void msgLaneNeedsPart(Part part, Lane lane) {
		requestedParts.add(new MyPartRequest(part, lane));
	}

	public void msgHereAreParts(Part part) {
		for (MyPartRequest pr : requestedParts) {
			if(pr.state == MyPartRequestState.ASKED_GANTRY && pr.pt == part) {
				pr.state = MyPartRequestState.DELIVERED;
			}
		}
	}

	public void msgBadNest(Nest n) {
		if (topLane.lane.getNest() == n) 
			topLane.state = MyLaneState.BAD_NEST;
		else if (bottomLane.lane.getNest() == n)
			bottomLane.state = MyLaneState.BAD_NEST;
	}


	/** SCHEDULER **/
	@Override
	public boolean pickAndExecuteAnAction() {

		if (state == FeederState.EMPTY || state == FeederState.OK_TO_PURGE)
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

		for (MyPartRequest p : requestedParts) 
		{
			if (p.state == MyPartRequestState.DELIVERED)
			{
				processFeederParts(p);
				return true;
			}
		}

		if (state == FeederState.SHOULD_START_FEEDING) 
		{ 
			// Which lane should it feed to?
			if (topLane.part == currentPart && topLane.state == MyLaneState.EMPTY)
			{
				StartFeeding();
				return true;
			}
			else if (bottomLane.part == currentPart && bottomLane.state == MyLaneState.EMPTY)
			{
				StartFeeding();
				return true;
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
	private void nestWasDumped(MyLane la) {
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
	}

	private void dumpNest(MyLane la) {
		DoStopFeeding();
		la.state = MyLaneState.TOLD_NEST_TO_DUMP;
		la.lane.msgDumpNest();
	}

	private void askGantryForPart(MyPartRequest partRequested) { 
		
		if (purgeIfNecessary(partRequested) || this.state == FeederState.EMPTY) 
		{
			state = FeederState.WAITING_FOR_PARTS;
			partRequested.state = MyPartRequestState.ASKED_GANTRY;
			gantry.msgFeederNeeds(partRequested.pt, this);
		}
	}
	
	private boolean purgeIfNecessary(MyPartRequest partRequested) { 
		boolean purging = false;
		
		// Check if Feeder needs to be purged
		if (this.currentPart != partRequested.pt && state == FeederState.OK_TO_PURGE)
		{
			purging = true;
			purgeFeeder();	
		}

		// Check if lane needs to be purged
		if (topLane.lane == partRequested.lane && topLane.part != null && state == FeederState.OK_TO_PURGE)
		{
			purging = true;
			purgeLane(topLane);
		}

		if (bottomLane.lane == partRequested.lane && bottomLane.part != null && state == FeederState.OK_TO_PURGE)
		{
			purging = true;
			purgeLane(bottomLane);
		}
				
		return purging;
	}

	private void purgeFeeder(){
		DoStopFeeding();
		DoPurgeFeeder();
		currentPart = null;
	}

	private void purgeLane(MyLane myLane){
		myLane.lane.msgPurge();
		myLane.state = MyLaneState.PURGING;
	}

	// Old way was using a DELETED state, and was looking at all the requestedParts
	/*private void processFeederParts(){
		MyPartRequest rq = null;
		for (MyPartRequest p : requestedParts) {
			if( p.state == MyPartRequestState.DELETED){ 
				rq = p;
				break;
			}
		}

		requestedParts.remove(rq);
		currentPart = rq.pt;

		if (bottomLane.lane == rq.lane){
			bottomLane.part = rq.pt;
		}
		else {
			topLane.part = rq.pt;
		}
		state = FeederState.SHOULD_START_FEEDING;
	}
	*/
	
	private void processFeederParts(MyPartRequest mpr){
		requestedParts.remove(mpr);
		currentPart = mpr.pt;

		if (bottomLane.lane == mpr.lane){
			bottomLane.part = mpr.pt;
		}
		else {
			topLane.part = mpr.pt;
		}
		
		state = FeederState.SHOULD_START_FEEDING;
	}

	private void StartFeeding(){
		final MyLane currentLane;
		if(currentPart == topLane.part)
			currentLane = topLane;
		else 
			currentLane = bottomLane;
		// Switch Diverter
		if (currentLane == topLane && diverter == DiverterState.FEEDING_BOTTOM) {
			DoSwitchLane();   // Animation to switch lane
			diverter = DiverterState.FEEDING_TOP;
		}
		else if (currentLane == bottomLane && diverter == DiverterState.FEEDING_TOP) {
			DoSwitchLane();   // Animation to switch lane
			diverter = DiverterState.FEEDING_BOTTOM;
		}

		
		okayToPurgeTimer.schedule(new TimerTask(){
			public void run() {
				state = FeederState.OK_TO_PURGE;
			}
		},10000); // okay to purge after 10 seconds of feeding

		feederEmptyTimer.schedule(new TimerTask(){
			public void run(){		    
				state = FeederState.OK_TO_PURGE;
				partResettleTimer.schedule(new TimerTask() {
					public void run() {

						currentLane.readyForPicture = true;

						if (bottomLane.readyForPicture == true && topLane.readyForPicture == true)
						{
							vision.msgMyNestsReadyForPicture(topLane.lane.getNest(), bottomLane.lane.getNest(), this);
							topLane.readyForPicture = false;
							bottomLane.readyForPicture = false;
						}
					}
				}, 3000); // 3 seconds to resettle in the nest
			}
		}, (long) currentPart.averageDelayTime); // time it takes the part to move down the lane

		//	Timer.new(30000, { state = FeederState.OK_TO_PURGE; });
		//		Timer.new(currentPart.averageDelayTime,{vision.msgMyNestsReadyForPicture(topLane.lane.getNest(), bottomLane.lane.getNest(), this) });

		DoStartFeeding(currentPart);

	}
	
	/** This method returns the name of the FeederAgent **/
	public String getName() {
		return name;
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
	
	/** Set the connection the graphic lane manager client. **/
//	public void setGraphicLaneManagerClient(GraphicLaneManagerClient glmc) {
//		this.graphicLaneManagerClient = glmc;
//	}

	/** This method adds a MyPartRequest to the requestedParts list. 
	 *  It is used for testing purposes only.
	 */
	public void addMyPartRequest(Part p, Lane l, MyPartRequestState s) {
		requestedParts.add(new MyPartRequest(p,l,s));
	}

	/** ANIMATIONS **/
	private void DoStartFeeding(Part part) {
		print("Feeder " + feederSlot + " started feeding.");
//		graphicLaneManagerClient.doStartFeeding(feederSlot,part);
	}

	private void DoStopFeeding() {
		print("stopped feeding.");
//		graphicLaneManagerClient.doStopFeeding(feederSlot);
	}
	
	private void DoPurgeFeeder() {
		print("purging feeder.");
//		graphicLaneManagerClient.doPurgeFeeder(feederSlot);
	}

	private void DoSwitchLane() {
		print("switching lane");
//		graphicLaneManagerClient.doSwitchLane(feederSlot);
	}
	
	private void DoContinueFeeding(Part part) {
		print("continued feeding.");
		// worry about this later
	}


}







