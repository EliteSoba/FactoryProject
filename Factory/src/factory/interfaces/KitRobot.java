package factory.interfaces;

public interface KitRobot {

	public void msgGrabAndBringEmptyKitFromConveyor();
	public void msgComeMoveKitToInspectionSlot(String slot);
	public void msgComeProcessAnalyzedKitAtInspectionSlot();
}
