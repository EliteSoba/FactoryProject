package factory.graphics;

import java.awt.*;

import javax.swing.*;

import factory.Kit.KitState;

public class GraphicFactoryProductionManager extends JFrame {
	
	FactoryProductionPanel panel;
	ControlPanel cp;
	
	public GraphicFactoryProductionManager() {
		panel = new FactoryProductionPanel(this);
		this.add(panel, BorderLayout.CENTER);
		cp = new ControlPanel(this);
		this.add(cp, BorderLayout.LINE_END);
	}
	
	public void moveEmptyKitToSlot(int slot){
		panel.moveEmptyKitToSlot(slot);
	}
	
	public void sendNewEmptyKit() {
		System.out.println("New Empty Kit Requested!");
		//Adds a Kit into the factory
		panel.newEmptyKit();
	}
	
	public void takePicture(){
		panel.takePictureOfInspectionSlot();
	}
	
	public void moveKitFromInspectionToConveyor() {
		panel.moveKitFromInspectionToConveyor();
	}
	
	public void exportKit() {
		//Sends a Kit out of the factory
		panel.exportKit();
	}
	
	public void kitToCheck(int slot) {
		if(slot == 0){
			System.out.println("Kit at topSlot is compete!");
			//stand.topSlot.kit.state = KitState.COMPLETE;
			//stand.stateChanged();
		}
		else {
			System.out.println("Kit at bottomSlot is compete!");
			//stand.bottomSlot.kit.state = KitState.COMPLETE;
			//stand.stateChanged();
		}
		//GKAM.checkKit(slot);
	}
	
	public void moveKitFromSlotToInspection(int slot){
		panel.moveKitToInspection(slot);
	}
	public void dumpKit() {
		panel.dumpKitAtInspection();
	}
	
	public void newEmptyKitAtConveyor(){
		System.out.println("New Empty Kit Arrived!");
		//Should call conveyorController.msgAnimationDone() when animation is complete
		//conveyorController.msgAnimationDone();
	}
	
	public void moveEmptyKitToSlotDone() {
		System.out.println("Kit sent to Kitting Station!");
		//kitRobot.msgAnimationDone();
	}
	
	public void moveKitToInspectionDone() {
		System.out.println("Kit sent to Inspection Station!");
		//kitRobot.msgAnimationDone();
	}
	
	public void takePictureOfInspectionSlotDone() {
		System.out.println("Picture taken!");
		//vision.msgAnimationDone();
	}
	
	public void dumpKitAtInspectionDone() {
		System.out.println("Kit has been dumped!");
		//kitRobot.msgAnimationDone();
	}
	
	public void moveKitFromInspectionToConveyorDone() {
		System.out.println("Kit sent to Conveyor Belt!");
		//kitRobot.msgAnimationDone();
	}
	
	public void exportKitDone() {
		System.out.println("Kit has left the building!");
		//kitRobot.msgAnimationDone();
	}
	
	public void moveRobotToNest(int nestNum, int partNum)
	{
		panel.movePartsRobotToNest(nestNum, partNum);
	}
	
	public void moveRobotToStation()
	{
		panel.movePartsRobotToStation(0);
	}
	
	public void popItemToKit(int target) {
		panel.partsRobotPopItemToCurrentKit(target);
	}
	
	public void moveRobotToCenter(){
		panel.movePartsRobotToCenter();
	}
	
	public void feedLane(int laneNum){ //FEEDS THE LANE! Lane 1-8, NOT 0-7
		panel.feedLane(laneNum);
	}
	
	public void startLane(int laneNum){
		panel.startLane(laneNum);
	}
	
	public void switchLane(int laneNum){
		panel.switchLane(laneNum);
	}
	
	public void switchFeederLane(int feederNum){
		panel.switchFeederLane(feederNum);
	}
	
	public void purgeFeeder(int feederNum){
		panel.purgeFeeder(feederNum);
	}
	
	public void purgeTopLane(int feederNum){
		panel.purgeTopLane(feederNum);
	}
	
	public void purgeBottomLane(int feederNum){
		panel.purgeBottomLane(feederNum);
	}
	
	public void jamTopLane(int feederNum){
		panel.jamTopLane(feederNum);
	}
	
	public void unjamTopLane(int feederNum){
		panel.unjamTopLane(feederNum);
	}
	
	public void jamBottomLane(int feederNum){
		panel.jamBottomLane(feederNum);
	}
	
	public void unjamBottomLane(int feederNum){
		panel.unjamBottomLane(feederNum);
	}
	
	public void getBin(String partName)
	{
		panel.moveGantryRobotToPickup(partName);
	}
	
	public void moveGantryToFeeder1Pickup()
	{
		panel.moveGantryRobotToFeederForPickup(0);
	}

	public void moveGantryToFeeder1Dropoff()
	{
		panel.moveGantryRobotToFeederForDropoff(0);
	}
	
	public void takePictureFeeder1()
	{
		panel.cameraFlash(0);
	}
	
	//Lane Manager Messages
	public void feedLaneDone(int laneNum) {
		System.out.println("Lane " + (laneNum + 1) + " has finished feeding.");
		//kitRobot.msgAnimationDone();
	}
	
	public static void main(String args[]) {
		//Implements this JFrame
		GraphicFactoryProductionManager FKAM = new GraphicFactoryProductionManager();
		FKAM.setVisible(true);
		FKAM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//FKAM.setSize(800, 720);
		FKAM.pack();
	}

}