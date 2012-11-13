package factory.test.mock;

import factory.Kit;
import factory.interfaces.Conveyor;
import factory.interfaces.KitRobot;

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


}
