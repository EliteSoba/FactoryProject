package factory.test;


import junit.framework.TestCase;
import factory.*;
import factory.test.mock.*;
import factory.KitRobotAgent.StandInfo;
import factory.KitRobotAgent.ConveyorStatus;

public class KitRobotTests extends TestCase {
	
	MockStand stand;
	MockConveyor conveyor;
	KitRobotAgent kitrobot;
	
	public void initializeAgents() {
		//Creating MockConveyor and MockStand
		stand = new MockStand("Stand");
		conveyor = new MockConveyor("Conveyor");
		
		//Creating KitRobot
		kitrobot = new KitRobotAgent();
		
		//kitrobot.setStand(stand);
		kitrobot.setConveyor(conveyor);
		kitrobot.setStand(stand);
	}
	
	@Test
	public void testNewKitRequest() {
		/**
		 * Test case for the KitRobotAgent getting a request for an empty kit while there is nothing on the Conveyor currently
		 */
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
		
		//Calling KitRobot Scheduler, KitRobot should now start trying to put the empty kit in the topSlot
		kitrobot.pickAndExecuteAnAction();
		
		//KitRobot should be done putting the empty kit in the topSlot now
	}
	
	@Test
	public void testMovingToInspectionSlot() {
		/**
		 * Test case for the KitRobotAgent getting a request to move a completed kit to the inspection slot for inspection
		 */
		initializeAgents();
	}
	
	@Test
	public void testKitBad() {
		/**
		 * Test case for the the kitRobot checking the inspectionSlot and finding that it failed inspection
		 */
		initializeAgents();
	}
	
	@Test
	public void testKitGood() {
		/**
		 * Test case for the kitRobot checking the inspectionSlot and finding that the kit passed inspection
		 */
		initializeAgents();
	}

}
