package factory.test.mock;

import factory.Kit;
import factory.interfaces.ConveyorController;

public class MockConveyorController extends MockAgent implements ConveyorController {
	
	public EventLog log = new EventLog();
	
	public MockConveyorController(String name) {
		super(name);
	}

	public void msgAnimationDone() {
		log.add(new LoggedEvent("received msgAnimationDone() from the graphics"));
		
	}

	public void msgConveyorWantsEmptyKit() {
		log.add(new LoggedEvent("received msgConveyorWantsEmptyKit() from the Conveyor"));
		
	}

	@Override
	public void msgKitExported(Kit k) {
		log.add(new LoggedEvent("received msgKitExported() from the Conveyor for kit "+k));
		
	}
	
}
