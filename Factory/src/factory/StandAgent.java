package factory;

import factory.interfaces.*;
import agent.Agent;
import factory.Kit;

public class StandAgent extends Agent implements Stand {
	
	/** DATA **/
	private enum StandAgentState { FREE, KIT_ROBOT, PARTS_ROBOT }
	private enum TemporaryEmptyKitHolderState { EMPTY, EMPTY_KIT }
	private enum MyConveyorState { EMPTY, HAS_EMPTY_KIT, FETCHING_EMPTY_KIT }
	public enum MySlotState { EMPTY, EMPTY_KIT_JUST_PLACED, BUILDING_KIT, MOVING_KIT_TO_INSPECTION, KIT_JUST_PLACED_AT_INSPECTION, ANALYZING_KIT, KIT_ANALYZED, PROCESSING_ANALYZED_KIT}

	private StandAgentState state;
	
	private MyConveyor conveyor;
	private PartsRobotAgent partsRobot;
	private Boolean partsRobotWantsToDeliverParts = false;
	private KitRobotAgent kitRobot;
	private VisionAgent vision;
	  
	private Kit temporaryEmptyKitHolder = null;
	private TemporaryEmptyKitHolderState temporaryEmptyKitHolderState = TemporaryEmptyKitHolderState.EMPTY;
		
	public MySlot topSlot;
	public MySlot bottomSlot;
	public MySlot inspectionSlot;

	public class MySlot {
		String name;
		Kit kit;
		MySlotState state;
	
		public MySlot(String name){
			this.state = MySlotState.EMPTY;
			this.name = name;
			this.kit = null;
		}
	}
	
	private class MyConveyor {
		ConveyorAgent conveyor;
		MyConveyorState state;

		public MyConveyor(ConveyorAgent conveyor){
			this.state = MyConveyorState.EMPTY;
			this.conveyor = conveyor;
		}
	}

	
	/**
	 * Constructor
	 * @param conveyor
	 * @param vision
	 * @param kitRobot
	 * @param partsRobot
	 */
	public StandAgent(ConveyorAgent conveyor, VisionAgent vision, KitRobotAgent kitRobot, PartsRobotAgent partsRobot){

		this.conveyor = new MyConveyor(conveyor);
		this.vision = vision;
		this.partsRobot = partsRobot;
		this.kitRobot = kitRobot;
		
		partsRobotWantsToDeliverParts = false;
		this.state = StandAgentState.FREE;
		
		topSlot = new MySlot("topSlot");
		bottomSlot = new MySlot("bottomSlot");
		inspectionSlot = new MySlot("inspectionSlot");
	}
	
	/** MESSAGES **/
	
	/**
	 * Message that is Received from the conveyor when it brought an empty kit
	 */
	public void msgEmptyKitIsHere() {
	   conveyor.state = MyConveyorState.HAS_EMPTY_KIT;
	   stateChanged();
	}
	
	/**
	 * Message that receives an empty kit from the KitRobot
	 * By design, the temporaryEmptyKitHolder should always be empty when this is called   
	 * We do not access the slots here because the KitRobot doesn't know where it goes and logic shouldn't be in messages
	 */
	public void msgHereIsAnEmptyKit(Kit kit) {
	   if(temporaryEmptyKitHolderState == TemporaryEmptyKitHolderState.EMPTY){    
	      temporaryEmptyKitHolder = kit;       
	      temporaryEmptyKitHolderState = TemporaryEmptyKitHolderState.EMPTY_KIT;
	      conveyor.state = MyConveyorState.EMPTY;
	      stateChanged();
	   }
	   else {
	      // Throw Exception
	   }
	}
	
	/**
	 * Message that is received from the KitRobot when it has moved out of the way
	 */
	public void msgKitRobotNoLongerUsingStand() {
	   if(state == StandAgentState.KIT_ROBOT){
	      state = StandAgentState.FREE;
	      stateChanged();
	   }
	   else {
	      // Throw Exception
	   }
	}
	
	/**
	 * Message received when the PartsRobot wants to deliver a part(s)
	 */
	public void msgPartRobotWantsToPlaceParts() {
	   partsRobotWantsToDeliverParts = true;
	   stateChanged();
	}
	
	/**
	 * Message that is received from the PartsRobot when it has moved out of the way  
	 */
	public void msgPartsRobotNoLongerUsingStand() {   
	   if(state == StandAgentState.PARTS_ROBOT){
	      state = StandAgentState.FREE;
	      stateChanged();
	   }
	   else {
	      // Throw Exception
	   }
	}
	
	/**
	 * Message that is received when the Vision has analyzed the results
	 */
	public void msgResultsOfKitAtInspection(KitState results) {   
	   inspectionSlot.kit.state = results;
	   inspectionSlot.state = MySlotState.KIT_ANALYZED;
	   stateChanged();
	}
	
	/** SCHEDULER **/
	protected boolean pickAndExecuteAnAction() {
		
		synchronized(state) {

			/**
			 * If there is a Kit in the Inspection Slot that hasn't been analyzed, then ask Vision to do so 
			 */
			if (inspectionSlot.state == MySlotState.KIT_ANALYZED) {
			   DoProcessAnalyzedKit();
			   return true;
			}
			/**
			 * If there is a Kit in the Inspection Slot that has been analyzed, then ask KitRobot to process it 
			 */
			if (inspectionSlot.state == MySlotState.KIT_JUST_PLACED_AT_INSPECTION) {
			   DoAskVisionToInspectKit();
			   return true;
			}
			/**
			 * If empty kit was delivered, place in appropiate bin and notify the PartsRobot to start building   
			 * No need to check for state to be free because it will not call the robots to move to the stand 
			 */
			if (state == StandAgentState.FREE && temporaryEmptyKitHolderState == TemporaryEmptyKitHolderState.EMPTY_KIT) {
			   DoProcessEmptyBinFromConveyor();
			   return true;
			}
			/**
			 * If there is a completed kit and the stand is not being used
			 */
			if (state == StandAgentState.FREE && topSlot.kit.state == KitState.COMPLETE && topSlot.state == MySlotState.BUILDING_KIT) {
			   DoTellKitRobotToMoveKitToInspectionSlot(topSlot);
			   return true;
			}
			if (state == StandAgentState.FREE && bottomSlot.kit.state == KitState.COMPLETE && bottomSlot.state == MySlotState.BUILDING_KIT) {
			   DoTellKitRobotToMoveKitToInspectionSlot(bottomSlot);
			   return true;
			}
			/**
			 * If there is an empty kit at the conveyor and there is a place to put it, ask the Kit Robot to fetch it
			 * as long as the stand is not being used.
			 */
			if (state == StandAgentState.FREE && conveyor.state == MyConveyorState.HAS_EMPTY_KIT 
					&& (topSlot.state == MySlotState.EMPTY || bottomSlot.state == MySlotState.EMPTY) 
					&& temporaryEmptyKitHolderState == TemporaryEmptyKitHolderState.EMPTY) {
			   DoFetchEmptyKitFromConveyor();
			   return true;
			}       
			/**
			 * If the stand is free and the partRobot wants to deliver parts
			 */
			if (state == StandAgentState.FREE && partsRobotWantsToDeliverParts == true) {
			   DoTellPartsRobotToDeliverParts();
			   return true;
			}
		}
		
		return false;
	}
	
	/** ACTIONS **/
	
	/**
	 * Method that tells the KitRobot to fetch the empty Kit
	 */
	private void DoFetchEmptyKitFromConveyor() {
	   kitRobot.msgGrabAndBringEmptyKitFromConveyor();
	   state = StandAgentState.KIT_ROBOT;
	   conveyor.state = MyConveyorState.FETCHING_EMPTY_KIT;
	}    
	/**
	 * Method that places the empty bin in the right slot
	 */
	private void DoProcessEmptyBinFromConveyor() {
	   if(temporaryEmptyKitHolderState == TemporaryEmptyKitHolderState.EMPTY_KIT) {
	      if (topSlot.state == MySlotState.EMPTY) {
	         topSlot.kit = temporaryEmptyKitHolder;
	         topSlot.state = MySlotState.EMPTY_KIT_JUST_PLACED;
	         DoTellPartsRobotToBuildKitAtSlot(topSlot);
	         //server.DoAnimationAddEmptyKitToSlot(topSlot.name); // Animation to add Empty Kit to slot
	      }
	      else if (bottomSlot.state == MySlotState.EMPTY) {
	         bottomSlot.kit = temporaryEmptyKitHolder;
	         bottomSlot.state = MySlotState.EMPTY_KIT_JUST_PLACED;
	         DoTellPartsRobotToBuildKitAtSlot(bottomSlot);
	         //server.DoAnimationAddEmptyKitToSlot(bottomSlot.name); // Animation to add Empty Kit to slot
	      }   
	      temporaryEmptyKitHolder = null;
	      temporaryEmptyKitHolderState = TemporaryEmptyKitHolderState.EMPTY;
	   }
	   else {
	      // Throw Exception
	   }
	}
	/**
	 * Method that tells the PartsRobot to build Kit
	 */
	private void DoTellPartsRobotToBuildKitAtSlot(MySlot slot) {
	   partsRobot.msgBuildKitAtSlot(slot.name);
	   slot.state = MySlotState.BUILDING_KIT;
	} 
	/**
	 * Method that tells the PartsRobot to deliver parts
	 */
	private void DoTellPartsRobotToDeliverParts() {
	   partsRobot.msgDeliverKitParts();   
	   state = StandAgentState.PARTS_ROBOT;                      
	}   
	/**
	 * Method that tells the KitRobot to move a Kit to the inspection slot
	 */
	private void DoTellKitRobotToMoveKitToInspectionSlot(MySlot slot) {
	   kitRobot.msgComeMoveKitToInspectionSlot(slot.name);
	   state = StandAgentState.KIT_ROBOT;
	   slot.state = MySlotState.MOVING_KIT_TO_INSPECTION;                   
	}
	/**
	 * Method that tells the Vision to take picture of the kit
	 */
	private void DoAskVisionToInspectKit() {
	   vision.msgAnalyzeKitAtInspection(inspectionSlot.kit);
	   inspectionSlot.state = MySlotState.ANALYZING_KIT;                   
	}   
	/**
	 * Method that tells the KitRobot to process the Kit
	 */
	private void DoProcessAnalyzedKit() {
	   kitRobot.msgComeProcessAnalyzedKitAtInspectionSlot();
	   state = StandAgentState.KIT_ROBOT;
	   inspectionSlot.state = MySlotState.PROCESSING_ANALYZED_KIT;                    
	}    

}


