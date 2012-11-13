package factory.test.mock;

import factory.Kit;
import factory.interfaces.Conveyor;
import factory.interfaces.KitRobot;

public class MockConveyor extends MockAgent implements Conveyor {
	
	public EventLog log = new EventLog();
	
	public MockConveyor(String name) {
		super(name);
		
	}


}
