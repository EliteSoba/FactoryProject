package factory;

import java.util.ArrayList;

import agent.Agent;
import factory.interfaces.Feeder;
import factory.interfaces.Gantry;
import factory.interfaces.Lane;
import factory.interfaces.Nest;
import factory.interfaces.Vision;

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

		      enum MyPartRequestState { NEEDED, ASKED_GANTRY, DELIVERED }
		      
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

		      public MyLane(Lane lane){
		            this.state = MyLaneState.EMPTY;
		            this.lane = lane;
		      }
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
				}
			}
		}
		
		for (MyPartRequest p : requestedParts) 
		{
			if (p.state == MyPartRequestState.DELIVERED)
			{
				processFeederParts();
			}
		}
		
		
		
		if (state == FeederState.SHOULD_START_FEEDING) 
		{ 
			// Which lane should it feed to?
			if (topLane.part == currentPart && topLane.state == MyLaneState.EMPTY)
			{
				StartFeeding();
			}
			else if (bottomLane.part == currentPart && bottomLame.state = MyLaneState.EMPTY)
			{
				StartFeeding();
			}
		}

		if (topLane.state == MyLaneState.BAD_NEST) 
			dumpNest(topLane);

		if (bottomLane.state == MyLaneState.BAD_NEST)
			dumpNest(bottomLane);

		if (topLane.state == MyLaneState.NEST_SUCCESSFULLY_DUMPED)
			backToNormalAfterNestDump(topLane);

		if (bottomLane.state == MyLaneState.NEST_SUCCESSFULLY_DUMPED)
			backToNormalAfterNestDump(bottomLane);
		
		return false;
	}

	
}







