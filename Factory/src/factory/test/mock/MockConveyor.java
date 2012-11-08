package factory.test.mock;

import factory.Kit;
import factory.interfaces.Conveyor;
import factory.interfaces.KitRobot;

public class MockConveyor extends MockAgent implements Conveyor {

	public MockConveyor(String name) {
		super(name);
		
	}

	@Override
	public void msgHeresEmptyKit(Kit k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgTakingKit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgPuttingKit(KitRobot kr, Kit k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgNeedEmptyKit(KitRobot kr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgExportKit(KitRobot kr) {
		// TODO Auto-generated method stub
		
	}

}
