package factory.test.mock;
import factory.interfaces.*;

public class MockKitRobot extends MockAgent implements KitRobot {
	
	public EventLog log = new EventLog();
	
	public MockKitRobot(String name) {
		super(name);
	}

	@Override
	public void msgComeMoveKitToInspectionSlot(String pos) {
		log.add(new LoggedEvent("received msgComeMoveKitToInspectionSlot() from the Stand for the slot "+pos));
	}

	@Override
	public void msgStandClear() {
		log.add(new LoggedEvent("received msgStandClear() from the Stand"));
	}

	@Override
	public void msgNeedEmptyKitAtSlot(String pos) {
		log.add(new LoggedEvent("received msgNeedEmptyKitAtSlot() from the Stand for the slot "+pos));
	}

	@Override
	public void msgEmptyKitOnConveyor() {
		log.add(new LoggedEvent("received msgEmptyKitOnConveyor() from the Conveyor"));
	}

	@Override
	public void msgComeProcessAnalyzedKitAtInspectionSlot() {
		log.add(new LoggedEvent("received msgComeProcessAnalyzedKitAtInspectionSlot() from the Stand"));
	}

	@Override
	public void msgClearTheStandOff() {
		log.add(new LoggedEvent("received msgClearTheStandOff() from the Stand"));
	}

}
