package factory.test.mock;

import java.util.TimerTask;

import factory.Kit;
import factory.interfaces.Feeder;
import factory.interfaces.Nest;
import factory.interfaces.Vision;

public class MockVision extends MockAgent implements Vision {
	
	public EventLog log = new EventLog();

	public MockVision(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void msgMyNestsReadyForPicture(Nest nest, Nest nest2, Feeder feeder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgAnalyzeKitAtInspection(Kit kit) {
		// TODO Auto-generated method stub

	}
	public void msgVisionClearForPictureInNests(Nest nest1, Nest nest2) {
		this.log.add(new LoggedEvent("msgVisionClearForPictureInNests("+nest1.getNestName()+","+nest2.getNestName()+") received from the PartsRobot")); 

	}

}
