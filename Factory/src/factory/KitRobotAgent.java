package factory;

import factory.interfaces.*;
import agent.Agent;

public class KitRobotAgent extends Agent implements KitRobot {

	/** DATA **/
	
	/** MESSAGES **/
	@Override
	public void msgGrabAndBringEmptyKitFromConveyor() {
		// TODO Auto-generated method stub
		
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

