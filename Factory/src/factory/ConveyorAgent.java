package factory;

import java.util.concurrent.Semaphore;

import factory.graphics.FrameKitAssemblyManager;
import factory.interfaces.*;
import agent.Agent;

public class ConveyorAgent extends Agent implements Conveyor {
	////Data	
	enum ConveyorState { NO_ACTION, KR_WANTS_EMPTY_KIT, GETTING_EMPTY_KIT, EXPORTING };
	
	public KitRobot kit_robot;
	public ConveyorController conveyor_controller;
	public FrameKitAssemblyManager server;
	
	Semaphore animation = new Semaphore(0);
	
	Kit on_conveyor;  //Supposed to represent what is on the ConveyorAgent
	
	ConveyorState state = ConveyorState.NO_ACTION;
	
	/** Public Constructor **/
	public ConveyorAgent(FrameKitAssemblyManager server) {
		super();
		this.server = server;
	}
	
	////Messages
	public void msgAnimationDone(){
		debug("Received msgAnimationDone() from server");
		animation.release();
	}
	
	public void msgHeresEmptyKit(Kit k) {
		debug("Received msgHeresEmptyKit() from the ConveyorController");
		on_conveyor = k;
	   	stateChanged();
	}

	public void msgNeedEmptyKit(KitRobot kr) {
		debug("Received msgNeedEmptyKit() from the KitRobot");
		state = ConveyorState.KR_WANTS_EMPTY_KIT;
		stateChanged();
	}

	public void msgExportKit(KitRobot kr, Kit k) {
		debug("Received msgExportKit() from the KitRobot for the Kit " + k);
		on_conveyor = k;
		state = ConveyorState.EXPORTING;
		stateChanged();
	}
	
	////Scheduler
	protected boolean pickAndExecuteAnAction() {
		if (state.equals(ConveyorState.EXPORTING)) { 
			state = ConveyorState.NO_ACTION;
			exportKit();
			return true;
		}

		if (state.equals(ConveyorState.KR_WANTS_EMPTY_KIT) || state.equals(ConveyorState.GETTING_EMPTY_KIT) && on_conveyor != null) {
			tellKitRobotAboutEmptyKit();
			return true;
		}

		if (state.equals(ConveyorState.KR_WANTS_EMPTY_KIT) && on_conveyor == null) {
			requestEmptyKit();
			return true;
		}
		
		return false;
	}
	
	////Actions
	private void tellKitRobotAboutEmptyKit() {
		debug("Telling The KitRobot About the Empty Kit");
		kit_robot.msgEmptyKitOnConveyor();
		state = ConveyorState.NO_ACTION;
	}

	private void requestEmptyKit() {
		debug("Requesting Empty Kit From the ConveyorController");
	    conveyor_controller.msgConveyorWantsEmptyKit(this);
	    state = ConveyorState.GETTING_EMPTY_KIT;
	    stateChanged();
	}

	private void exportKit() {
		debug("Exporting Kit");
		//server.exportKit(); //Animation for moving the kit out of the cell on the conveyor
		
		try {
			debug("Waiting on the server to finish the animation of exporting Kit");
			animation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		debug("Export Animation Completed");
	    conveyor_controller.msgKitExported(this, on_conveyor);
		on_conveyor = null;
		stateChanged();
	}

	////Misc / Hacks
	public void setConveyorController(ConveyorController cc) {
		conveyor_controller = cc;
	}
	
	public void setKitRobot(KitRobot kr) {
		kit_robot = kr;
	}
}

