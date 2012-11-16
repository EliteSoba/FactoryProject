package factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import factory.Kit.KitState;
import factory.StandAgent.MySlotState;
import factory.interfaces.*;
import factory.masterControl.MasterControl;
import agent.*;

public class KitRobotAgent extends Agent implements KitRobot {
	////Data
	public Stand stand;
	public Conveyor conveyor;

	String name;
	
	Kit holding = null;
	Semaphore standApproval = new Semaphore(0); //Semaphore for waiting for stand
	int inspectionAreaClear = 1; //0 = not clear, 1 = clear, -1 = need to find out	starting at empty
	public ConveyorStatus conveyor_state = ConveyorStatus.EMPTY;
	public List<StandInfo> actions = Collections.synchronizedList(new ArrayList<StandInfo>());

	public enum ConveyorStatus {EMPTY, GETTING_KIT, EMPTY_KIT, COMPLETED_KIT};
	public enum StandInfo { NEED_EMPTY_TOP, NEED_EMPTY_BOTTOM, NEED_INSPECTION_TOP, NEED_INSPECTION_BOTTOM, INSPECTION_SLOT_DONE, KIT_GOOD, KIT_BAD, CLEAR_OFF_UNFINISHED_KITS };
	
	//Constructor used for UnitTesting only
	public KitRobotAgent() {
		super(null);
	}
	
	public KitRobotAgent(MasterControl mc, Conveyor c) {
		super(mc);
		this.conveyor = c;
	}
	
	////Messages
	public void msgStandClear() {
		debug("Received msgStandClear() from Stand");
		standApproval.release();
	}
	
	public void msgClearTheStandOff() {
		/**
		 * Telling the KitRobot to dump any Kits that are not yet completed.
		 * the kits that are are already in need of inspection or in the inspectionSlot will not be disturbed
		 */
		if (!actions.contains(StandInfo.CLEAR_OFF_UNFINISHED_KITS)) {
			actions.add(StandInfo.CLEAR_OFF_UNFINISHED_KITS);
		}
	}
	
	public void msgNeedEmptyKitAtSlot(String pos) {
		debug("Received msgNeedEmptyKitAtSlot() from the Stand for "+ pos);
		if (pos.equals("topSlot")) {
			if (!actions.contains(StandInfo.NEED_EMPTY_TOP)) {
				actions.add(StandInfo.NEED_EMPTY_TOP);
			}
		} else if (pos.equals("bottomSlot")) {
			if (!actions.contains(StandInfo.NEED_EMPTY_BOTTOM)) {
				actions.add(StandInfo.NEED_EMPTY_BOTTOM);
			}
		}
		stateChanged();
	}
	
	public void msgComeMoveKitToInspectionSlot(String pos) {
		debug("Received msgComeMoveKitToInspectionSlot() From Stand for " + pos);
		if (pos.equals("topSlot")) {
			if (!actions.contains(StandInfo.NEED_INSPECTION_TOP)) {
				actions.add(StandInfo.NEED_INSPECTION_TOP);
			}
		} else if (pos.equals("bottomSlot")) {
			if (!actions.contains(StandInfo.NEED_INSPECTION_BOTTOM)) {
				actions.add(StandInfo.NEED_INSPECTION_BOTTOM);
			}
		}
		stateChanged();
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
		stateChanged();
		
	}
	
	
	////Scheduler
	public boolean pickAndExecuteAnAction() {	

		synchronized(actions){
			if (actions.contains(StandInfo.CLEAR_OFF_UNFINISHED_KITS)) {
				clearOffStand();
				return true;
			}
			
			if (actions.contains(StandInfo.KIT_BAD)) {
				dumpKit();
				return true;
			}
			
			if (actions.contains(StandInfo.KIT_GOOD) && conveyor_state.equals(ConveyorStatus.EMPTY)) {
				exportKit();
				return true;
			}
			
			if (actions.contains(StandInfo.INSPECTION_SLOT_DONE)) {
				processKitAtInspection(); 
				return true;
			}
			
			if (actions.contains(StandInfo.NEED_INSPECTION_TOP) && (inspectionAreaClear == 1)) {
				actions.remove(StandInfo.NEED_INSPECTION_TOP);
				moveToInspectionSpot("topSlot");
				return true;
			}
			
			if (actions.contains(StandInfo.NEED_INSPECTION_BOTTOM) && (inspectionAreaClear == 1)) {
				actions.remove(StandInfo.NEED_INSPECTION_BOTTOM);
				moveToInspectionSpot("bottomSlot");
				return true;
			}
			
			if ((actions.contains(StandInfo.NEED_EMPTY_TOP) || actions.contains(StandInfo.NEED_EMPTY_BOTTOM)) && conveyor_state.equals(ConveyorStatus.EMPTY)) {
				requestEmptyKit();
				return true;
			}
			
			if (actions.contains(StandInfo.NEED_EMPTY_TOP) && conveyor_state.equals(ConveyorStatus.EMPTY_KIT)) {
				actions.remove(StandInfo.NEED_EMPTY_TOP);
				putEmptyKitOnStand("topSlot");
				return true;
			}
			
			if (actions.contains(StandInfo.NEED_EMPTY_BOTTOM) && conveyor_state.equals(ConveyorStatus.EMPTY_KIT)) {
				actions.remove(StandInfo.NEED_EMPTY_BOTTOM);
				putEmptyKitOnStand("bottomSlot");
				return true;
			}
		}
		
		return false;
	}
	
	//Actions
	public void processKitAtInspection() {
		//action for checking the inspected kit and then doing the correct action.
		debug("Executing processKitAtInspection()");
		
		if (stand.getSlotKit("inspectionSlot").state.equals(KitState.PASSED_INSPECTION)) {
			//kit passed inspection
			if (!actions.contains(StandInfo.KIT_GOOD) && !actions.contains(StandInfo.KIT_BAD)) {
				actions.add(StandInfo.KIT_GOOD);
				actions.remove(StandInfo.INSPECTION_SLOT_DONE);
				stand.msgKitRobotNoLongerUsingStand();
			}
		} else {
			//kit failed inspection
			if (!actions.contains(StandInfo.KIT_GOOD) && !actions.contains(StandInfo.KIT_BAD)) {
				actions.add(StandInfo.KIT_BAD);
				actions.remove(StandInfo.INSPECTION_SLOT_DONE);
			}
		}
		stateChanged();
	}
	
	public void dumpKit() {
		//action for dumping a bad kit
		debug("KitRobot dumping bad kit");
		//server.dumpKit();

		// Wait until the animation is done
		try {
			debug("Waiting on the server to finish the animation dumpKit()");
			animation.acquire();
			debug("Animation dumpKit() was completed");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		holding = null;
		stand.setSlotKit("inspectionSlot", null);
		stand.setSlotState("inspectionSlot", MySlotState.EMPTY);
		actions.remove(StandInfo.KIT_BAD);
		stand.msgKitRobotNoLongerUsingStand();
		
		stateChanged();
	}
	
	public void exportKit() {
		//action for exporting a good kit
		debug("KitRobot picking up kit from inspection to export");
		
		stand.msgKitRobotWantsToExportKit();
		
		try {
			debug("Waiting for the stand to tell KitRobot it is clear to go");
			standApproval.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		holding = stand.getSlotKit("inspectionSlot");
		//server.exportKit(); //This should be the call for the animation for hte KitRobot to take the Kit at the inspection slot and put it on the conveyor
		
		// Wait until the animation is done
		try {
			debug("Waiting on the server to finish the animation outKit()");
			animation.acquire();
			debug("Animation outKit() was completed");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Animation is now done, kit is on the conveyor
		conveyor.msgExportKit(holding);

		stand.setSlotKit("inspectionSlot", null);
		stand.setSlotState("inspectionSlot", MySlotState.EMPTY);
		actions.remove(StandInfo.KIT_GOOD);
		stand.msgKitRobotNoLongerUsingStand();
		
		stateChanged();
	}
	
	public void putEmptyKitOnStand(String pos) {
		//method for putting an empty kit on the stand
		
		debug("Executing putEmptyKitOnStand "+ pos);
		
		
		stand.msgEmptyKitIsHereAndWantToDeliver();
		
		try {
			debug("Waiting for the stand to tell KitRobot it is clear to go");
			standApproval.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Here the stand gave the kit robot clearance
		//Can assume that hte kit robot has exclusive access to the stand here

		//new kit is picked up by the kit robot from the conveyor
		holding = new Kit();
		conveyor_state = ConveyorStatus.EMPTY;
		conveyor.setAtConveyor(null);
		
		//Here do the animation for picking up the kit from the conveyor
		if(pos.equals("topSlot")) {
			//server.moveEmptyKitToSlot(0); //Animation for hte kit robot to go to the conveyor and pick up the kit
		}  else if (pos.equals("bottomSlot")) {
			//server.moveEmptyKitToSlot(1); //Animation for hte kit robot to go to the conveyor and pick up the kit
		}

		try {
			debug("Kit robot is taking empty kit from conveyor");
			animation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		debug("Kit robot is taking empty kit from conveyor");
		
		stand.setSlotKit(pos, this.holding);
		stand.setSlotState(pos, MySlotState.EMPTY_KIT_JUST_PLACED);
		
		holding = null;
		stand.msgKitRobotNoLongerUsingStand();
	
		stateChanged();
	}
	
	public void clearOffStand() {
		/**
		 * KitRobot will dump any Kits that are not yet completed.
		 * the kits that are are already in need of inspection or in the inspectionSlot will not be disturbed
		 */
		//assuming that exclusive access to the stand was given to the KitRobot at this time.
		if (stand.getSlotKit("topSlot") != null) {
			if (stand.getSlotKit("topSlot").state.equals(KitState.INCOMPLETE)) {
				//do animation of dumping topSlot
				
				// Wait until the animation is done
				try {
					debug("Waiting on the server to finish the animation of dumping the bottom kit");
					animation.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				stand.setSlotKit("topSlot", null);
				stand.setSlotState("topSlot", MySlotState.EMPTY);
			}
		}
		if (stand.getSlotKit("bottomSlot") != null) {
			if (stand.getSlotKit("bottomSlot").state.equals(KitState.INCOMPLETE)) {
				//do animation of dumping bottomSlot
				
				// Wait until the animation is done
				try {
					debug("Waiting on the server to finish the animation of dumping the bottom kit");
					animation.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				stand.setSlotKit("bottomSlot", null);
				stand.setSlotState("bottomSlot", MySlotState.EMPTY);
			}
		}
		
		actions.remove(StandInfo.CLEAR_OFF_UNFINISHED_KITS);
		stand.msgKitRobotNoLongerUsingStand();

		stateChanged();
	}
	
	public void requestEmptyKit() {
		//method for asking for a new kit from the conveyor
		debug("Requesting an Empty Kit from the Conveyor");
		conveyor.msgNeedEmptyKit();
		conveyor_state = ConveyorStatus.GETTING_KIT;
		
		stateChanged();
	}
	
	public void moveToInspectionSpot(String pos) {
		//method for KitRobot moving a kit to the inspection slot
		//Can assume that has exclusive access to the Stand during this
		debug("Executing moveToInspectionSpot for the" + pos);
		int slot;
		if (pos.equals("topSlot")) {
			slot = 0;
		} else {
			slot = 1;
		}
		//server.moveKitFromSlotToInspection(slot);
		
		// Wait until the animation is done
		try {
			debug("Waiting on the server to finish the animation moveEmptyKitToSlot()");
			animation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		debug("Animation moveKitFromSlotToInspection() was completed");
		
		stand.setSlotKit("inspectionSlot", stand.getSlotKit(pos));
		stand.setSlotKit(pos, null);
		stand.setSlotState("inspectionSlot", MySlotState.KIT_JUST_PLACED_AT_INSPECTION);
		stand.setSlotState(pos, MySlotState.EMPTY);
		
		stand.msgKitRobotNoLongerUsingStand();
		
		stateChanged();
	}
	
	//Hacks / MISC
	public void setStand(Stand s) {
		this.stand = s;
	}
	public void setConveyor(Conveyor c) {
		this.conveyor = c;
	}
}
