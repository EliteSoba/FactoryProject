package factory.test;


import java.util.Timer;
import java.util.TimerTask;

import org.junit.Test;

import junit.framework.TestCase;
import factory.*;
import factory.test.mock.*;
import factory.ConveyorControllerAgent.Conveyor_State;
import factory.Kit.KitState;
import factory.KitRobotAgent.StandInfo;
import factory.KitRobotAgent.ConveyorStatus;
import factory.StandAgent.MySlotState;
import factory.StandAgent.StandAgentState;

public class KitRobotTests extends TestCase {
	
	StandAgent stand;
	MockConveyor conveyor;
	KitRobotAgent kitrobot;
	
	public void initializeAgents() {
		//Creating MockConveyor and MockStand
		stand = new StandAgent();
		conveyor = new MockConveyor("Conveyor");
		
		//Creating KitRobot
		kitrobot = new KitRobotAgent();
		
		//kitrobot.setStand(stand);
		kitrobot.setConveyor(conveyor);
		kitrobot.setStand(stand);
	}
	
	@Test
	public void testNewKitRequest() {
		// Test case for the KitRobotAgent getting a request for an empty kit while there is nothing on the Conveyor currently
		 
		initializeAgents();
		
		//Messaging the KitRobot about an empty slot in the Stand
		kitrobot.msgNeedEmptyKitAtSlot("topSlot");
		
		assertTrue("Should have the NEED_EMPTY_TOP StandInfo in the actions, but does not.", kitrobot.actions.contains(StandInfo.NEED_EMPTY_TOP));
		
		assertEquals("Should now have 1 action in actions, instead the size of actions is "+kitrobot.actions.size()+".", kitrobot.actions.size(), 1);
		
		assertEquals("Mock Conveyor should have an empty event log before the KitRobot scheduler is called.  Instead, the mock Conveyor event log reads: "+conveyor.log.toString(), 0, conveyor.log.size());
		
		assertEquals("Conveyor_state in the KitRobotAgent should be ConveyorStatus.EMPTY, instead it is: "+kitrobot.conveyor_state, kitrobot.conveyor_state, ConveyorStatus.EMPTY);
		
		//Calling KitRobot Scheduler
		kitrobot.pickAndExecuteAnAction();
		
		assertEquals("Conveyor_State should now be ConveyorStatus.GETTING_KIT, instead it is "+kitrobot.conveyor_state, kitrobot.conveyor_state, ConveyorStatus.GETTING_KIT);
		
		kitrobot.msgEmptyKitOnConveyor(); //Simulating the empty kit coming to the conveyor
		
		assertEquals("Should have a ConveryoStatus of ConveyorStatus.EMPTY_KIT, instead it is "+kitrobot.conveyor_state, kitrobot.conveyor_state, ConveyorStatus.EMPTY_KIT);
		
		//Calling KitRobot Scheduler, KitRobot should now start trying to put the empty kit in the topSlot
		kitrobot.pickAndExecuteAnAction();
		
		//KitRobot should be done putting the empty kit in the topSlot now
		assertEquals("Kit robot should not be holding anything, instead it thinks it is holding "+kitrobot.holding, kitrobot.holding, null);
		assertEquals("Should have a ConveyorStatus of ConveyorStatus.EMPTY instead it is "+kitrobot.conveyor_state, kitrobot.conveyor_state, ConveyorStatus.EMPTY);
	}
	
	@Test
	public void testMovingToInspectionSlot() {
		// Test case for the KitRobotAgent getting a request to move a completed kit to the inspection slot for inspection
		 
		initializeAgents();
		assertEquals("Should now have 0 actions in actions, instead the size of actions is "+kitrobot.actions.size()+".", kitrobot.actions.size(), 0);
		kitrobot.msgComeMoveKitToInspectionSlot("topSlot");
		assertEquals("Should now have 1 action in actions, instead the size of actions is "+kitrobot.actions.size()+".", kitrobot.actions.size(), 1);
		assertTrue("Should have the NEED_INSPECTION_TOP StandInfo in the actions, but does not.", kitrobot.actions.contains(StandInfo.NEED_INSPECTION_TOP));
		kitrobot.pickAndExecuteAnAction();
		//Should have moved the kit now.
	}
	
	@Test
	public void testKitBad() {
		// Test case for the the kitRobot checking the inspectionSlot and finding that it failed inspection
		 
		initializeAgents();
		stand.setSlotState("inspectionSlot", MySlotState.KIT_ANALYZED);

		Kit k = new Kit();
		k.state = KitState.FAILED_INSPECTION;
		stand.setSlotKit("inspectionSlot", k);
		
		kitrobot.msgComeProcessAnalyzedKitAtInspectionSlot();
		
		assertEquals("Should have an action of INSPECTION_SLOT_DONE instead it is "+kitrobot.actions.get(0), kitrobot.actions.get(0), StandInfo.INSPECTION_SLOT_DONE );
		assertEquals("Should now have 1 action in actions, instead the size of actions is "+kitrobot.actions.size()+".", kitrobot.actions.size(), 1);

		kitrobot.pickAndExecuteAnAction();
		kitrobot.pickAndExecuteAnAction(); //Dumping kit takes 2 schedulers to complete.
		
		assertEquals("Kit inspection slot should be null, instead it is "+ stand.getSlotKit("inspectionSlot"), stand.getSlotKit("inspectionSlot"), null);
		assertEquals("inspectionSlot slot state should be empty, instead it is "+ stand.getSlotState("inspectionSlot"), stand.getSlotState("inspectionSlot"), MySlotState.EMPTY);
		//Kit should be dumped
	}
	
	
	@Test
	public void testKitGood() {
		//Test case for the kitRobot checking the inspectionSlot and finding that the kit passed inspection, also while a new kit is currently incoming
		 
		initializeAgents();
		stand.setSlotState("inspectionSlot", MySlotState.KIT_ANALYZED);

		Kit k = new Kit();
		k.state = KitState.PASSED_INSPECTION;
		stand.setSlotKit("inspectionSlot", k);
		
		kitrobot.conveyor_state = ConveyorStatus.GETTING_KIT;
		kitrobot.msgComeProcessAnalyzedKitAtInspectionSlot();
		
		assertEquals("Should have an action of INSPECTION_SLOT_DONE instead it is "+kitrobot.actions.get(0), kitrobot.actions.get(0), StandInfo.INSPECTION_SLOT_DONE );
		assertEquals("Should now have 1 action in actions, instead the size of actions is "+kitrobot.actions.size()+".", kitrobot.actions.size(), 1);
		assertEquals("ConveyorStatus should be GETTING_KIT to simulate that the kitrobot has to wait before he can export the kit, instead ConveyorStatus is "+ kitrobot.conveyor_state, kitrobot.conveyor_state, ConveyorStatus.GETTING_KIT);
		
		kitrobot.pickAndExecuteAnAction();
		kitrobot.pickAndExecuteAnAction();
		//Make sure nothing happened until the conveyor is clear
		
		kitrobot.conveyor_state = ConveyorStatus.EMPTY;
		assertEquals("ConveyorStatus should be EMPTY instead ConveyorStatus is "+ kitrobot.conveyor_state, kitrobot.conveyor_state, ConveyorStatus.EMPTY);
		
		kitrobot.pickAndExecuteAnAction();
		
		assertEquals("Kit inspection slot should be null, instead it is "+ stand.getSlotKit("inspectionSlot"), stand.getSlotKit("inspectionSlot"), null);
		assertEquals("inspectionSlot slot state should be empty, instead it is "+ stand.getSlotState("inspectionSlot"), stand.getSlotState("inspectionSlot"), MySlotState.EMPTY);
		//Should have exported the kit by now

	}

}
