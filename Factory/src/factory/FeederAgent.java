package factory;

import java.util.ArrayList;

import agent.Agent;
import factory.interfaces.Feeder;

public class FeederAgent extends Agent implements Feeder {

	/** DATA **/
		   
		   ArrayList<MyPartRequest> requestedParts = new ArrayList<MyPartRequest>();   
		   MyLane topLane;
		   MyLane bottomLane;
		   Vision vision;
		   DiverterState diverter; 
		   Gantry gantry;
		   Part currentPart;
		   Bin dispenserBin;
		   FeederState state;

		   enum FeederState { EMPTY, WAITING_FOR_PARTS, CONTAINS_PARTS, OK_TO_PURGE, SHOULD_START_FEEDING }
		   enum DiverterState { FEEDING_TOP, FEEDING_BOTTOM }

		   class MyPartRequest {
		      enum MyPartRequestState { NEEDED, ASKED_GANTRY, DELIVERED }
		      Part pt;
		      MyPartRequestState state;
		      Lane lane;

		      public MyPart(PartType part, Lane lane){
		            this.state = MyPartState.NEEDED;
		            this.lane = lane;
		            this.pt = part;
		      }
		   }

		   class MyLane {
		      enum MyLaneState {EMPTY, PURGING, CONTAINS_PARTS, BAD_NEST, 
		      TOLD_NEST_TO_DUMP, NEST_SUCCESSFULLY_DUMPED}
		      enum JamState {MIGHT_BE_JAMMED, TOLD_TO_INCREASE_AMPLITUDE, 
		      AMPLITUDE_WAS_INCREASED, IS_NO_LONGER_JAMMED }
		      Lane lane;
		      MyLaneState state; 
		      JamState jamState;
		      Part

		      public MyLane(Lane lane){
		            this.state = MyLaneState.EMPTY;
		            this.lane = lane;
		      }
		   }

	/** MESSAGES **/
		   public void msgEmptyNest(Nest n) {
			   if (topLane.nest = n) 
			   {
				   topLane.msgIncreaseAmplitude();
				   topLane.jamState = MyLane.JamState.MIGHT_BE_JAMMED;
			   }
			   else if (bottomLane.nest = n) 
			   {
				   bottomLane.msgIncreaseAmplitude();
				   bottomLane.jamState = MyLane.JamState.MIGHT_BE_JAMMED;
			   }
		   }
		   public void msgNestWasDumped(Lane la) {
			   if there exists MyLane l such that l.lane = la
					   l.state = MyLane.MyLaneState.NEST_SUCCESSFULLY_DUMPED;
			   end
		   }
		   public void msgLaneNeedsPart(Part part, Lane lane) {
			   myParts.add(new MyPartRequestState(part, lane));
		   }
		   public void msgHereAreParts(Part part) {
			   for (requestedPart in requestedParts) {
				   if(requestedPart.state == MyPartState.ASKED_GANTRY && requestedPart.pt == part) {
					   requestedPart.state = MyPartState.DELIVERED;
				   }
			   }
		   }
		   public void msgBadNest(Nest n) {
			   if (topLane.nest = n) 
				   topLane.state = MyLane.MyLaneState.BAD_NEST;
			   else if (bottomLane.nest = n)
				   bottomLane.state = MyLane.MyLaneState.BAD_NEST;
		   }

		   
	/** Scheduler **/
	@Override
	protected boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

}
