package factory.test.mock;

import factory.Part;
import factory.interfaces.Nest;

public class MockNest extends MockAgent implements Nest {
	
	String nestName;
	
	public MockNest(String name) {
		super(name);
		nestName = name;
	}

	public String getNestName() {
		return nestName;
	}
	
	public EventLog log = new EventLog();

	@Override
	public void msgDump() {
		log.add(new LoggedEvent("msgDump()")); 
	}

	@Override
	public void msgYouNeedPart(Part part) {
//		System.out.println("received msgYouNeedPart("+part.name+").");
		log.add(new LoggedEvent("msgYouNeedPart("+part.name+")")); 
	}

	public void msgPartsRobotGrabbingPartFromNest(int coordinate) {

	}

	@Override
	public Part getPart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPosition() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
