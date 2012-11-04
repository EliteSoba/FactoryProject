package factory;

import java.util.*;
import factory.interfaces.*;
import agent.Agent;

public class ConveyorControllerAgent extends Agent implements ConveyorController {
	////Data	
	Conveyor conveyor;
	List<Kit> exported_kits = new ArrayList<Kit>();
	enum Conveyor_State { WANTS_EMPTY_KIT, EMPTY_KIT_SENT, NO_ACTION };
	Conveyor_State conveyor_state = Conveyor_State.NO_ACTION;
	
	public ConveyorControllerAgent() {
		
	}
	////Messages
	public void msgConveyorWantsEmptyKit(Conveyor c) {
		if (!conveyor_state.equals(Conveyor_State.WANTS_EMPTY_KIT) && !conveyor_state.equals(Conveyor_State.EMPTY_KIT_SENT)) {
			conveyor_state = Conveyor_State.WANTS_EMPTY_KIT;
			stateChanged();
		}
	}
	
	public void msgKitExported(Conveyor c, Kit k) {
		exported_kits.add(k);
		stateChanged();
	}
	
	////Scheduler
	protected boolean pickAndExecuteAnAction() {
		if (conveyor_state.equals(Conveyor_State.WANTS_EMPTY_KIT)) {
			conveyor_state = Conveyor_State.EMPTY_KIT_SENT;
			sendEmptyKit();
		}
		return false;
	}
	
	////Actions
	private void sendEmptyKit() {
		//Creates a random time that it will take for the empty kit to make it to the kitting cell
		
		conveyor_state = Conveyor_State.NO_ACTION;
	}

}

