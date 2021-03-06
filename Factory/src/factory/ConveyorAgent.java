package factory;

import factory.interfaces.*;
import factory.masterControl.MasterControl;
import agent.Agent;

public class ConveyorAgent extends Agent implements Conveyor {
	////Data	
	public enum ConveyorState { NO_ACTION, KR_WANTS_EMPTY_KIT, GETTING_EMPTY_KIT, EXPORTING };
	
	public KitRobot kitRobot;
	public ConveyorController conveyorController;
	public FCS fcs;
	
	public Kit kitAtConveyor = null;  //Supposed to represent what is on the ConveyorAgent
	
	public ConveyorState state = ConveyorState.NO_ACTION;
	
	boolean isUnitTesting = false;
	
	//UnitTesting Constructor
	public ConveyorAgent() {
		super(null);
		isUnitTesting = true;
	}
	
	/** Public Constructor **/
	public ConveyorAgent(MasterControl mc, ConveyorController cc) {
		super(mc);
		conveyorController = cc;
	}
	
	/** MESSAGES **/
	
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
	public boolean pickAndExecuteAnAction() {
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
	    conveyorController.msgConveyorWantsEmptyKit();
	    state = ConveyorState.GETTING_EMPTY_KIT;
	    stateChanged();
	}

	private void exportKit() {
		debug("Exporting Kit");
		if (!isUnitTesting) {
			DoKitExportAnimation();
		}
		debug("Export Animation Completed");
	    fcs.msgKitIsExported(kitAtConveyor);
	    kitRobot.msgKitExported();
		kitAtConveyor = null;
		stateChanged();
	}

	////Animations
	private void DoKitExportAnimation() {
		debug("doing DoKitExport Animation");
		server.command(this,"ca kam cmd exportkitfromcell");
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	////Misc / Hacks
	public Kit getAtConveyor() {
		return kitAtConveyor;
	}
	
	public void setAtConveyor(Kit k) {
		this.kitAtConveyor = k;
	}
	
	public void setKitRobot(KitRobot kr) {
		this.kitRobot = kr;
	}
	public void setConveyorController(ConveyorController cc) {
		this.conveyorController = cc;
	}
	public void setFCS(FCS fcs) {
		this.fcs = fcs;
	}
}

