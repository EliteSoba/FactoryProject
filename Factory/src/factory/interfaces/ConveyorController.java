package factory.interfaces;

import factory.Kit;

public interface ConveyorController {
	
	public void msgAnimationDone();
	
	public void msgConveyorWantsEmptyKit();
	
	public void msgKitExported(Kit k);
	
}
