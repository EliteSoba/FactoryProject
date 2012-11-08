package factory.test.mock;

import factory.Coordinate;
import factory.KitConfig;
import factory.Part;
import factory.interfaces.Nest;
import factory.interfaces.PartsRobot;

public class MockPartsRobotAgent extends MockAgent implements PartsRobot {

	public MockPartsRobotAgent(String name) {
		super(name);
		// TODO Auto-generated constructor stub
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

}
