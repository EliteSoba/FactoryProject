package factory.test.mock;

import java.util.TimerTask;

import factory.Kit;
import factory.interfaces.Nest;
import factory.interfaces.Vision;

public class MockVisionAgent extends MockAgent implements Vision {

	public MockVisionAgent(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void msgMyNestsReadyForPicture(Nest nest, Nest nest2,
			TimerTask timerTask) {
		// TODO Auto-generated method stub

	}

	@Override
	public void msgAnalyzeKitAtInspection(Kit kit) {
		// TODO Auto-generated method stub

	}

}
