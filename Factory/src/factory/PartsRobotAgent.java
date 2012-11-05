package factory;

import factory.interfaces.*;
import agent.Agent;

public class PartsRobotAgent extends Agent implements PartsRobot {
	////Data

	////Messages
	
	////Scheduler
	protected boolean pickAndExecuteAnAction() {
		
		return false;
	}

	@Override
	public void msgBuildKitAtSlot(String slot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgDeliverKitParts() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgMakeKit(KitConfig kitConfig) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgClearLineOfSight(Nest nestOne, Nest nestTwo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgPictureTaken(Nest nestOne, Nest nestTwo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgHereArePartCoordiantes(Part part, Coordinate coordinateTwo) {
		// TODO Auto-generated method stub
		
	}
	
	////Actions

}

