package factory.test.mock;

import factory.Kit.KitState;
import factory.interfaces.Stand;

public class MockStand extends MockAgent implements Stand {

	public MockStand(String name) {
		super(name);
	}
	
	@Override
	public void msgResultsOfKitAtInspection(KitState results) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgEmptyKitIsHereAndWantToDeliver() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgKitRobotNoLongerUsingStand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgPartRobotWantsToPlaceParts() {
		// TODO Auto-generated method stub
		
	}

}
