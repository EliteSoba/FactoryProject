package factory;

import java.util.concurrent.Semaphore;

import factory.graphics.FrameKitAssemblyManager;
import factory.interfaces.*;
import agent.Agent;

public class ConveyorAgent extends Agent implements Conveyor {
	////Data	
	enum ConveyorState { NO_ACTION, KR_WANTS_EMPTY_KIT, GETTING_EMPTY_KIT, EXPORTING };
	
	public KitRobot kitRobot;
	public ConveyorController conveyorController;
	public FrameKitAssemblyManager server;
	
	public Semaphore animation = new Semaphore(0);
	
	public Kit kitAtConveyor;  //Supposed to represent what is on the ConveyorAgent
	
	ConveyorState state = ConveyorState.NO_ACTION;
	
	/** Public Constructor **/
	public ConveyorAgent(FrameKitAssemblyManager server, KitRobot kr) {
		super();
		this.server = server;
		this.kitRobot = kr;
	}
	
	/** MESSAGES **/
	
	public void msgAnimationDone(){
		debug("Received msgAnimationDone() from server");
		animation.release();
	}
	
	public void msgHeresEmptyKit(Kit k) {
		debug("Received msgHeresEmptyKit() from the ConveyorController");
		kitAtConveyor = k;
	   	stateChanged();
	}

	public void msgNeedEmptyKit() {
		debug("Received msgNeedEmptyKit() from the KitRobot");
		this.state = ConveyorState.KR_WANTS_EMPTY_KIT;
		stateChanged();
	}

	public void msgExportKit(Kit k) {
		debug("Received msgExportKit() from the KitRobot for the Kit " + k);
		kitAtConveyor = k;
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

		if (state.equals(ConveyorState.KR_WANTS_EMPTY_KIT) && kitAtConveyor == null) {
			requestEmptyKit();
			return true;
		}
		
		if (state.equals(ConveyorState.KR_WANTS_EMPTY_KIT) || state.equals(ConveyorState.GETTING_EMPTY_KIT) && kitAtConveyor != null) {
			tellKitRobotAboutEmptyKit();
			return true;
		}

		return false;
	}
	
	////Actions
	private void tellKitRobotAboutEmptyKit() {
		debug("Telling The KitRobot About the Empty Kit");
		kitRobot.msgEmptyKitOnConveyor();
		state = ConveyorState.NO_ACTION;
	}

	private void requestEmptyKit() {
		debug("Requesting Empty Kit From the ConveyorController");
	    conveyorController.msgConveyorWantsEmptyKit(this);
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
	    conveyorController.msgKitExported(this, kitAtConveyor);
		kitAtConveyor = null;
		stateChanged();
	}

	////Misc / Hacks
	public void setConveyorController(ConveyorController cc) {
		conveyorController = cc;
	}
	
	public void setKitRobot(KitRobot kr) {
		kitRobot = kr;
	}
}

