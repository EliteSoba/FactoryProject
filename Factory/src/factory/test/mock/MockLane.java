package factory.test.mock;

import factory.test.mock.LoggedEvent;
import factory.test.mock.EventLog;
import factory.interfaces.Lane;
import factory.interfaces.Nest;

public class MockLane extends MockAgent implements Lane {

	public EventLog log = new EventLog();
	public Nest myNest;
	
	public MockLane(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Nest getNest() {
		return myNest;
	}

	@Override
	public void msgIncreaseAmplitude() {
		log.add(new LoggedEvent("msgIncreaseAmplitude()")); 
	}

	@Override
	public void msgDumpNest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgPurge() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNest(Nest n) {
		myNest = n;
	}

}
