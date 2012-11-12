package factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import factory.Kit.KitState;
import factory.StandAgent.MySlotState;
import factory.graphics.FrameKitAssemblyManager;
import factory.interfaces.*;
import agent.*;

public class KitRobotAgent extends Agent implements KitRobot {
	////Data
	StandAgent stand;
	Conveyor conveyor;
	FrameKitAssemblyManager server;
	
	Kit holding = null;
	Semaphore standApproval = new Semaphore(0); //Semaphore for waiting for stand
	Semaphore animation = new Semaphore(0); //Semaphore for animation
	int inspectionAreaClear = 1; //0 = not clear, 1 = clear, -1 = need to find out	starting at empty
	ConveyorStatus conveyor_state;
	List<StandInfo> actions = Collections.synchronizedList(new ArrayList<StandInfo>());

	enum ConveyorStatus {EMPTY, GETTING_KIT, EMPTY_KIT, COMPLETED_KIT};
	enum StandInfo {NEED_EMPTY_TOP, NEED_EMPTY_BOTTOM, NEED_INSPECTION_TOP, NEED_INSPECTION_BOTTOM, INSPECTION_SLOT_DONE, KIT_GOOD, KIT_BAD };
	
	public KitRobotAgent(StandAgent stand, FrameKitAssemblyManager server, ConveyorAgent conveyor){
		this.conveyor = conveyor;
		this.stand = stand;
		this.server = server;
	}
	
	////Messages
	public void msgDeliverEmptyKit() {
		debug("Received msgDeliverEmptyKit() from Stand");
		standApproval.release();
	}
	
	public void msgAnimationDone(){
		debug("Received msgAnimationDone() from server");
		animation.release();
	}
	
	public void msgNeedEmptyKitAtSlot(String pos) {
		debug("Received msgNeedEmptyKitAtSlot() from the Stand for "+ pos);
		if (pos.equals("TopSlot")) {
			if (!actions.contains(StandInfo.NEED_EMPTY_TOP)) {
				actions.add(StandInfo.NEED_EMPTY_TOP);
				stateChanged();
			}
		} else if (pos.equals("BottomSlot")) {
			if (!actions.contains(StandInfo.NEED_EMPTY_BOTTOM)) {
				actions.add(StandInfo.NEED_EMPTY_BOTTOM);
				stateChanged();
			}
		}
	}
	
	public void msgComeMoveKitToInspectionSlot(String pos) {
		debug("Received msgComeMoveKitToInspectionSlot() From Stand for " + pos);
		if (pos.equals("TopSlot")) {
			if (!actions.contains(StandInfo.NEED_INSPECTION_TOP)) {
				actions.add(StandInfo.NEED_INSPECTION_TOP);
				stateChanged();
			}
		} else if (pos.equals("BottomSlot")) {
			if (!actions.contains(StandInfo.NEED_INSPECTION_BOTTOM)) {
				actions.add(StandInfo.NEED_INSPECTION_BOTTOM);
				stateChanged();
			}
		}
	}
		
	public void msgEmptyKitOnConveyor() {
		debug("Received msgEmptyKitOnConveyor() from the Conveyor");
		conveyor_state = ConveyorStatus.EMPTY_KIT;
		stateChanged();
	}
	
	public void msgInspectionAreaStatus(int status) {
		debug("Received msgInspectionAreaStatus() from the Stand with a status of "+status);
		if (status < 2 && status >= 0) {
			inspectionAreaClear = status;
			stateChanged();
		}
	}
	
	public void msgComeProcessAnalyzedKitAtInspectionSlot() {
		debug("Received msgComeProcessAnalayzedKitAtInspectionSlot from Stand");
		if (!actions.contains(StandInfo.INSPECTION_SLOT_DONE)) {
			actions.add(StandInfo.INSPECTION_SLOT_DONE);
		}
	}
	
	
	////Scheduler
	protected boolean pickAndExecuteAnAction() {	

		if (actions.contains(StandInfo.KIT_BAD)) {
			actions.remove(StandInfo.KIT_BAD);
			dumpKit();
			return true;
		}
		
		if (actions.contains(StandInfo.KIT_GOOD) && conveyor_state.equals(ConveyorStatus.EMPTY)) {
			actions.remove(StandInfo.KIT_GOOD);
			exportKit();
			return true;
		}
		
		if (actions.contains(StandInfo.INSPECTION_SLOT_DONE)) {
			actions.remove(StandInfo.INSPECTION_SLOT_DONE);
			processKitAtInspection(); 
			return true;
		}
		
		if (actions.contains(StandInfo.NEED_INSPECTION_TOP) && (inspectionAreaClear == 1)) {
			actions.remove(StandInfo.NEED_INSPECTION_TOP);
			moveToInspectionSpot("TopSlot");
			return true;
		}
		
		if (actions.contains(StandInfo.NEED_INSPECTION_BOTTOM) && (inspectionAreaClear == 1)) {
			actions.remove(StandInfo.NEED_INSPECTION_BOTTOM);
			moveToInspectionSpot("BottomSlot");
			return true;
		}
		
		if (actions.contains(StandInfo.NEED_EMPTY_TOP) && conveyor_state.equals(ConveyorStatus.EMPTY)) {
			requestEmptyKit();
			return true;
		}
		
		if (actions.contains(StandInfo.NEED_EMPTY_BOTTOM) && conveyor_state.equals(ConveyorStatus.EMPTY)) {
			requestEmptyKit();
			return true;
		}
		
		if (actions.contains(StandInfo.NEED_EMPTY_TOP) && conveyor_state.equals(ConveyorStatus.EMPTY_KIT)) {
			actions.remove(StandInfo.NEED_EMPTY_TOP);
			putEmptyKitOnStand("TopSlot");
			return true;
		}
		
		if (actions.contains(StandInfo.NEED_EMPTY_BOTTOM) && conveyor_state.equals(ConveyorStatus.EMPTY_KIT)) {
			actions.remove(StandInfo.NEED_EMPTY_BOTTOM);
			putEmptyKitOnStand("BottomSlot");
			return true;
		}
		
		return false;
	}
	
	//Actions
	public void processKitAtInspection(){
		//action for checking the inspected kit and then doing the correct action.
		debug("Executing processKitAtInspection()");
		
		if (stand.inspectionSlot.kit.equals(KitState.PASSED_INSPECTION)) {
			//kit passed inspection
			if (!actions.contains(StandInfo.KIT_GOOD) && !actions.contains(StandInfo.KIT_BAD)) {
				actions.add(StandInfo.KIT_GOOD);
				stateChanged();
			}
		} else {
			//kit failed inspection
			if (!actions.contains(StandInfo.KIT_GOOD) && !actions.contains(StandInfo.KIT_BAD)) {
				actions.add(StandInfo.KIT_BAD);
				stateChanged();
			}
		}
	}
	
	public void dumpKit() {
		//action for dumping a bad kit
		debug("KitRobot dumping bad kit");
		server.dumpKit();

		// Wait until the animation is done
		try {
			debug("Waiting on the server to finish the animation dumpKit()");
			animation.acquire();
			debug("Animation dumpKit() was completed");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		holding = null;
		stand.inspectionSlot.kit = null;
		stand.inspectionSlot.state = MySlotState.EMPTY;
		stand.msgKitRobotNoLongerUsingStand();
	}
	
	public void exportKit() {
		//action for exporting a good kit
		debug("KitRobot picking up kit from inspection to export");
		holding = stand.inspectionSlot.kit;
		server.exportKit(); //This should be the call for the animation for hte KitRobot to take the Kit at the inspection slot and put it on the conveyor
		
		// Wait until the animation is done
		try {
			debug("Waiting on the server to finish the animation outKit()");
			animation.acquire();
			debug("Animation outKit() was completed");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Animation is now done, kit is on the conveyor
		conveyor.msgExportKit(this, holding);
	
		stand.inspectionSlot.kit = null;
		stand.inspectionSlot.state = MySlotState.EMPTY;
		stand.msgKitRobotNoLongerUsingStand();
	}
	
	public void putEmptyKitOnStand(String pos) {
		//method for putting an empty kit on the stand
		
		debug("Executing putEmptyKitOnStand "+ pos);
		
		//Here do the animation for picking up the kit from the conveyor
		if(pos == "topSlot")
			server.moveEmptyKitToSlot(0); //Animation for hte kit robot to go to the conveyor and pick up the kit
		else if (pos == "bottomSlot")
			server.moveEmptyKitToSlot(1); //Animation for hte kit robot to go to the conveyor and pick up the kit

		try {
			debug("Kit robot is taking empty kit from conveyor");
			animation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//new kit is picked up by the kit robot from the conveyor
		holding = new Kit();
		
		
		stand.msgEmptyKitIsHereAndWantToDeliver();
		
		try {
			debug("Waiting for the stand to tell KitRobot it is clear to go");
			standApproval.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Here the stand gave the kit robot clearance
		//Can assume that hte kit robot has exclusive access to the stand here
		
		if (pos.equals("TopSlot")) {
			server.moveEmptyKitToSlot(0); //Animation to put the kit in the top slot
		} else {
			server.moveEmptyKitToSlot(1); //Animation for kitRobot to place into bottom slot
		}
		
		try {
			debug("Kit robot is placing empty kit in "+pos);
			animation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if (pos.equals("TopSlot")) {
			//top slot
			stand.topSlot.kit = new Kit();
			stand.topSlot.state = MySlotState.EMPTY_KIT_JUST_PLACED;
		} else {
			//bottom slot
			stand.bottomSlot.kit = new Kit();
			stand.bottomSlot.state = MySlotState.EMPTY_KIT_JUST_PLACED;
		}
		
		holding = null;
		stand.msgKitRobotNoLongerUsingStand();
		
	}
	
	public void requestEmptyKit() {
		//method for asking for a new kit from the conveyor
		debug("requesting an Empty Kit from the Conveyor");
		 conveyor.msgNeedEmptyKit(this);
		 conveyor_state = ConveyorStatus.GETTING_KIT;
	}
	
	public void moveToInspectionSpot(String pos) {
		//method for KitRobot moving a kit to the inspection slot
		//Can assume that has exclusive access to the Stand during this
		debug("Executing moveToInspectionSpot for the" + pos);
		int slot;
		if (pos.equals("TopSlot")) {
			slot = 0;
		} else {
			slot = 1;
		}
		server.moveKitFromSlotToInspection(slot);
		
		// Wait until the animation is done
		try {
			debug("Waiting on the server to finish the animation moveEmptyKitToSlot()");
			animation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		debug("Animation moveKitFromSlotToInspection() was completed");
		if(slot == 0) {
			// Put an empty kit in the topSlot of the stand
			stand.inspectionSlot.kit = stand.topSlot.kit;
			stand.topSlot.kit = null;
			stand.inspectionSlot.state = MySlotState.KIT_JUST_PLACED_AT_INSPECTION;
			stand.topSlot.state = MySlotState.EMPTY;
		}
		else {
			// Put an empty kit in the bottomSlot of the stand
			stand.inspectionSlot.kit = stand.bottomSlot.kit;
			stand.bottomSlot.kit = null;
			stand.inspectionSlot.state = MySlotState.KIT_JUST_PLACED_AT_INSPECTION;
			stand.bottomSlot.state = MySlotState.EMPTY;
		}
		
		stand.msgKitRobotNoLongerUsingStand();
	}
}
