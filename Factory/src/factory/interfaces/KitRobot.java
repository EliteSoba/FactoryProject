package factory.interfaces;

public interface KitRobot {
	public void msgComeMoveKitToInspectionSlot(String pos);
	public void msgStandClear();
	public void msgNeedEmptyKitAtSlot(String pos);
	public void msgEmptyKitOnConveyor();
	//public void msgInspectionAreaStatus(int status);
	public void msgComeProcessAnalyzedKitAtInspectionSlot();
	
}
