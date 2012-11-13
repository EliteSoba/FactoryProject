package factory.interfaces;

public interface KitRobot {
	public void msgComeMoveKitToInspectionSlot(Stand stand, String pos);
	public void msgDeliverEmptyKit(Stand stand);
	public void msgAnimationDone();
	public void msgNeedEmptyKitAtSlot(Stand stand, String pos);
	public void msgEmptyKitOnConveyor(Conveyor conveyor);
	//public void msgInspectionAreaStatus(Stand stand, int status);
	public void msgComeProcessAnalyzedKitAtInspectionSlot(Stand stand);
	
	
}
