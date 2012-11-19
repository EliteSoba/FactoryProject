package factory.test;

import java.util.Timer;
import java.util.TimerTask;

import org.junit.Test;

import junit.framework.TestCase;
import factory.*;
import factory.ConveyorControllerAgent.Conveyor_State;
import factory.test.mock.*;

public class ConveyorControllerTests extends TestCase {
	
	MockConveyor conveyor;
	ConveyorControllerAgent conveyorController;
	
	public void initializeAgents() {
		conveyor = new MockConveyor("Conveyor");
		conveyorController = new ConveyorControllerAgent();
		conveyorController.setConveyor(conveyor);
	}
	
	@Test
	public void testNewKitRequest() {
		//Setting Up Agents and Mocks
		initializeAgents();
		assertEquals("Conveyor_state should be NO_ACTION at initialization, instead it is "+conveyorController.conveyor_state, conveyorController.conveyor_state, Conveyor_State.NO_ACTION);
		
		conveyorController.msgConveyorWantsEmptyKit();
		
		assertEquals("Conveyor_State should now be WANTS_EMPTY_KIT after getting msgConveyorWantsEmptyKit() from the kitrobot, instead conveyor_state is "+conveyorController.conveyor_state, conveyorController.conveyor_state, Conveyor_State.WANTS_EMPTY_KIT);
		
		conveyorController.pickAndExecuteAnAction();
		
		assertEquals("Conveyor_State should now be EMPTY_KIT_SENDING while it is sending the kit but before it gets there, instead conveyor_state is "+conveyorController.conveyor_state, conveyorController.conveyor_state, Conveyor_State.EMPTY_KIT_SENDING);
		
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				assertEquals("Conveyor_State should now be NO_ACTION, instead it is "+conveyorController.conveyor_state, conveyorController.conveyor_state, Conveyor_State.NO_ACTION);
			}
		}, 4000);
		
	}

}
