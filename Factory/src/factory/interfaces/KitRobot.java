package factory.interfaces;

public interface KitRobot {

	public void msgGrabAndBringEmptyKitFromConveyorToSlot(String slot);
	public void msgComeMoveKitToInspectionSlot(String slot);
	public void msgComeProcessAnalyzedKitAtInspectionSlot();
	public void msgInspectionResults(InspectionResults inspectionResults); //can't pass enum, change to string?
}
