package factory.graphics;

import java.awt.*;

import javax.swing.*;

import factory.Kit.KitState;

public class FactoryProductionManager extends JFrame {
	
	GraphicPanel panel;
	ControlPanel cp;
	
	public FactoryProductionManager() {
		panel = new GraphicPanel(this);
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
	
	public void exportKitDone() {
		System.out.println("Kit has left the building!");
		//kitRobot.msgAnimationDone();
	}
	
	public void moveRobotToNest1()
	{
		panel.moveRobotToNest(1);
	}
	
	public static void main(String args[]) {
		//Implements this JFrame
		FactoryProductionManager FKAM = new FactoryProductionManager();
		FKAM.setVisible(true);
		FKAM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//FKAM.setSize(800, 720);
		FKAM.pack();
	}

}
