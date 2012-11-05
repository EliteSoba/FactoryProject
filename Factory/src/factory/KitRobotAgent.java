package factory;

import factory.interfaces.*;
import agent.Agent;

public class KitRobotAgent extends Agent implements KitRobot {

	/** DATA **/
	public enum KitRobotAgentState { DOING_NOTHING, NEEDS_TO_GRAB_EMPTY_KIT }
	
	
	/** MESSAGES **/
	@Override
	public void msgGrabAndBringEmptyKitFromConveyorToSlot(String slot) {
		
	}

	@Override
	public void msgComeMoveKitToInspectionSlot(String slot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgComeProcessAnalyzedKitAtInspectionSlot() {
		// TODO Auto-generated method stub
		
	}
	

	/** SCHEDULER **/
	protected boolean pickAndExecuteAnAction() {
		
		return false;
	}
	
	/** ACTIONS **/

	
	/** ANIMATIONS **/

}

