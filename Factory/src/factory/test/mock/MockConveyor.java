package factory.test.mock;

import factory.Kit;
import factory.interfaces.*;

public class MockConveyor extends MockAgent implements Conveyor {
	
	public EventLog log = new EventLog();
	
	public MockConveyor(String name) {
		super(name);
		
	}

	public void msgAnimationDone() {
		log.add(new LoggedEvent("received msgAnimationDone() from the graphics"));
		
	}

	public void msgHeresEmptyKit(Kit k) {
		log.add(new LoggedEvent("received msgHeresEmptyKit() from the ConveyorController"));
		
	}

	public void msgNeedEmptyKit() {
		log.add(new LoggedEvent("received msgNeedEmptyKit() from the KitRobot"));
		
	}

	public void msgExportKit(Kit k) {
		log.add(new LoggedEvent("received msgExportKit() from the KitRobot to export kit "+ k));
		
	}

	@Override
	public Kit getAtConveyor() {
		// TODO Auto-generated method stub
		log.add(new LoggedEvent("the Kit on the Conveyor was requested"));
		return null;
	}

	@Override
	public void setAtConveyor(Kit k) {
		log.add(new LoggedEvent("the Kit on the Conveyor was set to"+ k));
		
	}



}
