package factory.interfaces;

import factory.Kit;
import factory.Kit.KitState;
import factory.StandAgent.MySlot;
import factory.StandAgent.MySlotState;


public interface Stand {
	
	void msgResultsOfKitAtInspection(KitState results);
	void msgEmptyKitIsHereAndWantToDeliver();
	void msgKitRobotNoLongerUsingStand();
	void msgPartsRobotNoLongerUsingStand();
	void msgPartRobotWantsToPlaceParts();
	void msgKitRobotWantsToExportKit();
	
	public void msgClearStand();
	
	public boolean setSlotKit(String slot, Kit k);
	public Kit getSlotKit(String slot);
	
	public boolean setSlotState(String slot, MySlotState state);
	public MySlotState getSlotState(String slot);
	
	public MySlot topSlot = null;
	public MySlot bottomSlot = null;
	public MySlot inspectionSlot = null;

}
