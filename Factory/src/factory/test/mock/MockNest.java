package factory.test.mock;

import factory.interfaces.Nest;

public class MockNest extends MockAgent implements Nest {
	
	public MockNest(String name) {
		super(name);
	}

	public EventLog log = new EventLog();
	
	

}
