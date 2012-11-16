package factory.test.mock;

import factory.Coordinate;
import factory.KitConfig;
import factory.Part;
import factory.interfaces.Nest;
import factory.interfaces.PartsRobot;

public class MockPartsRobot extends MockAgent implements PartsRobot {

	public MockPartsRobot(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public EventLog log = new EventLog();
	
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
		log.add(new LoggedEvent(
				"Received message msgMakeKit from the FCS"));
		
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
