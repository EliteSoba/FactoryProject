package factory;

import java.util.concurrent.Semaphore;

import factory.interfaces.*;
import agent.Agent;

import factory.graphics.FrameKitAssemblyManager;

import factory.Kit.KitState;
import factory.StandAgent;
import factory.StandAgent.MySlotState;

enum KitRobotAgentState { DOING_NOTHING, NEEDS_TO_GRAB_EMPTY_KIT_AND_PLACE_IN_SLOT_ONE, 
	NEEDS_TO_GRAB_EMPTY_KIT_AND_PLACE_IN_SLOT_TWO, NEEDS_TO_MOVE_KIT_AT_SLOT_ONE_TO_INSPECTION_SLOT, 
	NEEDS_TO_MOVE_KIT_AT_SLOT_TWO_TO_INSPECTION_SLOT, NEEDS_TO_PROCESS_KIT_AT_INSPECTION_SLOT, 
	GRABING_EMPTY_KIT_AND_PLACING_IN_SLOT }

public class KitRobotAgent extends Agent implements KitRobot {

	/** DATA **/
	
	StandAgent stand;
	FrameKitAssemblyManager server;
	KitRobotAgentState state;
	
	Semaphore animation = new Semaphore(0);
	
	public KitRobotAgent(StandAgent stand, FrameKitAssemblyManager server){
		state = KitRobotAgentState.DOING_NOTHING;
		this.stand = stand;
		this.server = server;
	}
	
	/** MESSAGES **/
	
	/**
	 * Message sent from the StandAgent when we need to grab an empty kit and place it in a slot
	 */
	public void msgGrabAndBringEmptyKitFromConveyorToSlot(String slot) {
		debug("Received msgGrabAndBringEmptyKitFromConveyorToSlot("+slot+") from stand");
		if(slot == "topSlot"){
			state = KitRobotAgentState.NEEDS_TO_GRAB_EMPTY_KIT_AND_PLACE_IN_SLOT_ONE;
		}
		else if(slot == "bottomSlot"){
			state = KitRobotAgentState.NEEDS_TO_GRAB_EMPTY_KIT_AND_PLACE_IN_SLOT_TWO;
		}
		stateChanged();
	}

	/**
	 * Message from the server when the animation is done
	 */
	public void msgAnimationDone(){
		debug("Received msgAnimationDone() from server");
		animation.release();
	}
	
	/**
	 * Message received by the stand to move kit to inspection slot
	 */
	public void msgComeMoveKitToInspectionSlot(String slot) {
		debug("Received msgComeMoveKitToInspectionSlot("+slot+") from stand");
		if(slot == "topSlot"){
			state = KitRobotAgentState.NEEDS_TO_MOVE_KIT_AT_SLOT_ONE_TO_INSPECTION_SLOT;
		}
		else if(slot == "bottomSlot"){
			state = KitRobotAgentState.NEEDS_TO_MOVE_KIT_AT_SLOT_TWO_TO_INSPECTION_SLOT;
		}
		stateChanged();
	}

	public void msgComeProcessAnalyzedKitAtInspectionSlot() {
		debug("Received msgComeProcessAnalyzedKitAtInspectionSlot() from stand");
		state = KitRobotAgentState.NEEDS_TO_PROCESS_KIT_AT_INSPECTION_SLOT;
		stateChanged();
	}
	

	/** SCHEDULER **/
	protected boolean pickAndExecuteAnAction() {
		
		synchronized(state){

			/**
			 * If there is an empty kit at conveyor that needs to be placed at slot one or two
			 */
			if(state == KitRobotAgentState.NEEDS_TO_GRAB_EMPTY_KIT_AND_PLACE_IN_SLOT_ONE){
				DoGrabEmptyKitAndPlaceInSlot(0);
				return true;
			}
			if(state == KitRobotAgentState.NEEDS_TO_GRAB_EMPTY_KIT_AND_PLACE_IN_SLOT_TWO){
				DoGrabEmptyKitAndPlaceInSlot(1);
				return true;
			}

			/**
			 * If a kit needs to be moved to the inspection area
			 */
			if(state == KitRobotAgentState.NEEDS_TO_MOVE_KIT_AT_SLOT_ONE_TO_INSPECTION_SLOT){
				DoMoveKitAtSlotToInspectionSlot(0);
				return true;
			}
			if(state == KitRobotAgentState.NEEDS_TO_MOVE_KIT_AT_SLOT_TWO_TO_INSPECTION_SLOT){
				DoMoveKitAtSlotToInspectionSlot(1);
				return true;
			}
			
			if(state == KitRobotAgentState.NEEDS_TO_PROCESS_KIT_AT_INSPECTION_SLOT){
				DoProcessKitAtInspection();
				return true;
			}
			
		}
		return false;
	}
	
	/** ACTIONS **/

	public void DoGrabEmptyKitAndPlaceInSlot(int slot){
		debug("Executing DoGrabEmptyKitAndPlaceInSlot("+slot+")");
		
		// Tell server to do animation of moving empty kit from conveyor to the topSlot of the stand
		server.moveEmptyKitToSlot(slot);
		
		// Wait until the animation is done
		try {
			debug("Waiting on the server to finish the animation moveEmptyKitToSlot()");
			animation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		debug("Animation moveEmptyKitToSlot() was completed");

		if(slot == 0) {
			// Put an empty kit in the topSlot of the stand
			stand.topSlot.kit = new Kit();
			// Update the state of the topSlot of the stand
			stand.topSlot.state = MySlotState.EMPTY_KIT_JUST_PLACED;
		}
		else {
			// Put an empty kit in the topSlot of the stand
			stand.bottomSlot.kit = new Kit();
			// Update the state of the topSlot of the stand
			stand.bottomSlot.state = MySlotState.EMPTY_KIT_JUST_PLACED;
		}

		// Update the state of the Kit Robot
		this.state = KitRobotAgentState.DOING_NOTHING;

		stand.msgKitRobotNoLongerUsingStand();
	}
	
	public void DoMoveKitAtSlotToInspectionSlot(int slot){
		debug("Executing DoMoveKitAtSlotToInspectionSlot("+slot+")");
		
		// Tell server to do animation of moving empty kit from conveyor to the topSlot of the stand
		server.moveKitFromSlotToInspection(slot);

		// Wait until the animation is done
		try {
			debug("Waiting on the server to finish the animation moveEmptyKitToSlot()");
			animation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		debug("Animation moveKitFromSlotToInspection() was completed");

		if(slot == 0) {
			// Put an empty kit in the topSlot of the stand
			stand.inspectionSlot.kit = stand.topSlot.kit;
			stand.topSlot.kit = null;
			stand.inspectionSlot.state = MySlotState.KIT_JUST_PLACED_AT_INSPECTION;
			stand.topSlot.state = MySlotState.EMPTY;
		}
		else {
			// Put an empty kit in the bottomSlot of the stand
			stand.inspectionSlot.kit = stand.bottomSlot.kit;
			stand.bottomSlot.kit = null;
			stand.inspectionSlot.state = MySlotState.KIT_JUST_PLACED_AT_INSPECTION;
			stand.bottomSlot.state = MySlotState.EMPTY;
		}

		// Update the state of the Kit Robot
		this.state = KitRobotAgentState.DOING_NOTHING;
		
		stand.msgKitRobotNoLongerUsingStand();
	}
	
	public void DoProcessKitAtInspection(){
		debug("Executing DoProcessKitAtInspection()");
		
		if(stand.inspectionSlot.kit.state == KitState.PASSED_INSPECTION){
			server.outKit();
			
			// Wait until the animation is done
			try {
				debug("Waiting on the server to finish the animation outKit()");
				animation.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			debug("Animation outKit() was completed");
		}
		else {
			server.dumpKit();

			// Wait until the animation is done
			try {
				debug("Waiting on the server to finish the animation dumpKit()");
				animation.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			debug("Animation dumpKit() was completed");
		}
	
		
		stand.inspectionSlot.kit = null;
		stand.inspectionSlot.state = MySlotState.EMPTY;

		// Update the state of the Kit Robot
		this.state = KitRobotAgentState.DOING_NOTHING;
		
		stand.msgKitRobotNoLongerUsingStand();

	}

	/** ANIMATIONS **/

	
}

