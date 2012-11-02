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
	public ArrayList<MyPartRequest> requestedParts = new ArrayList<MyPartRequest>();   
	MyLane topLane;
	MyLane bottomLane;
	Vision vision;
	DiverterState diverter; 
	Gantry gantry;
	Part currentPart;
	Bin dispenserBin;
	FeederState state;
	Timer feederEmptyTimer = new Timer();
	Timer partResettleTimer = new Timer();
	private String name;

	enum FeederState { EMPTY, WAITING_FOR_PARTS, CONTAINS_PARTS, OK_TO_PURGE, SHOULD_START_FEEDING }
	enum DiverterState { FEEDING_TOP, FEEDING_BOTTOM }

	enum MyPartRequestState { NEEDED, ASKED_GANTRY, DELIVERED, DELETED }

	class MyPartRequest {
		Part pt;
		MyPartRequestState state;
		Lane lane;

		public MyPartRequest(Part part, Lane lane){
			this.state = MyPartRequestState.NEEDED;
			this.lane = lane;
			this.pt = part;
		}
	}


	enum MyLaneState {EMPTY, PURGING, CONTAINS_PARTS, BAD_NEST, 
		TOLD_NEST_TO_DUMP, NEST_SUCCESSFULLY_DUMPED}
	enum JamState {MIGHT_BE_JAMMED, TOLD_TO_INCREASE_AMPLITUDE, 
		AMPLITUDE_WAS_INCREASED, IS_NO_LONGER_JAMMED }

	class MyLane {

		Lane lane;
		MyLaneState state; 
		JamState jamState;
		Part part;
		protected boolean readyForPicture = false;

		public MyLane(Lane lane){
			this.state = MyLaneState.EMPTY;
			this.lane = lane;
		}
	}


	public FeederAgent(String nameStr) {
		super();
		this.name = nameStr;
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
	protected boolean pickAndExecuteAnAction() {

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
				processFeederParts();
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
			backToNormalAfterNestDump(topLane);
			return true;
		}

		if (bottomLane.state == MyLaneState.NEST_SUCCESSFULLY_DUMPED)
		{
			backToNormalAfterNestDump(bottomLane);
			return true;
		}


		// If nothing else has been called, then return false!
		return false;
	}




	/** ACTIONS **/
	private void backToNormalAfterNestDump(MyLane la) {
		la.state = MyLaneState.CONTAINS_PARTS;
		DoStartFeeding();
	}

	private void dumpNest(MyLane la) {
		DoStopFeeding();
		la.state = MyLaneState.TOLD_NEST_TO_DUMP;
		la.lane.msgDumpNest();
	}

	private void askGantryForPart(MyPartRequest partRequested) { 
		this.purgeIfNecessary(partRequested);
		gantry.msgFeederNeeds(partRequested.pt, this);
		state = FeederState.WAITING_FOR_PARTS;
		partRequested.state = MyPartRequestState.ASKED_GANTRY;
	}

	private void purgeIfNecessary(MyPartRequest partRequested) { 

		// Check if Feeder needs to be purged
		if (this.currentPart != partRequested.pt && state != FeederState.EMPTY)
			purgeFeeder();

		// Check if lane needs to be purged
		if (topLane.lane == partRequested.lane && topLane.part != null)
			purgeLane(topLane);

		if (bottomLane.lane == partRequested.lane && bottomLane.part != null)
			purgeLane(bottomLane);
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

	private void processFeederParts(){
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

		DoStartFeeding();

	}


	/** ANIMATIONS **/
	private void DoStartFeeding() {
		print("started feeding.");
	}

	private void DoStopFeeding() {
		print("stopped feeding.");
	}


	private void DoPurgeFeeder() {
		print("purging feeder.");
	}

	private void DoSwitchLane() {
		print("switching lane");
	}

}







