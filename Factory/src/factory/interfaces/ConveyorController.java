package factory.interfaces;

import factory.Kit;

public interface ConveyorController {
	
	public void msgConveyorWantsEmptyKit();
	
	public void msgKitExported(Kit k);
	
}
