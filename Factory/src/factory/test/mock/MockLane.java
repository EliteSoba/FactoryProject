package factory.test.mock;

import factory.Part;
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
		log.add(new LoggedEvent("msgDumpNest()"));
	}

	@Override
	public void msgPurge() {
		log.add(new LoggedEvent("msgPurge()"));
	}

	@Override
	public void setNest(Nest n) {
		myNest = n;
	}

	@Override
	public void msgNestWasDumped() {
		log.add(new LoggedEvent("msgNestWasDumped()"));
	}

	@Override
	public void msgNestNeedsPart(Part pt) {
		log.add(new LoggedEvent("msgNestNeedsPart()"));
	}

	@Override
	public void msgNestHasStabilized() {
		log.add(new LoggedEvent("msgNestHasStabilized()"));
	}

}
