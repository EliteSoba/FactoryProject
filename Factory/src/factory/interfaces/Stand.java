package factory.interfaces;

import factory.Kit;
import factory.Kit.KitState;
import factory.StandAgent.MySlotState;


public interface Stand {
	
	void msgResultsOfKitAtInspection(KitState results);
	void msgEmptyKitIsHereAndWantToDeliver();
	void msgKitRobotNoLongerUsingStand();
	void msgPartRobotWantsToPlaceParts();
	void msgKitRobotWantsToExportKit();
	
	public boolean setSlotKit(String slot, Kit k);
	public Kit getSlotKit(String slot);
	
	public boolean setSlotState(String slot, MySlotState state);
	public MySlotState getSlotState(String slot);

}
