package factory.test;

import junit.framework.TestCase;

import org.junit.Test;
import factory.test.mock.*;

import factory.StandAgent;
import factory.StandAgent.MySlot;
import factory.StandAgent.MySlotState;
import factory.StandAgent.StandAgentState;

public class StandTests extends TestCase {

	StandAgent stand = new StandAgent();
	MockVision visionMock = new MockVision(null);
	MockKitRobot kitRobotMock = new MockKitRobot(null);
	MockPartsRobot partsRobotMock = new MockPartsRobot(null);
	MockConveyor conveyorMock = new MockConveyor(null);
	
	

	@Test
	public void testStandAgent() {
		
		stand.vision = visionMock;
		stand.kitRobot = kitRobotMock;
		stand.partsRobot = partsRobotMock;
		
		// Test that vision was saved
		assertEquals(stand.vision,visionMock);
		// Test that kitRobot was saved
		assertEquals(stand.kitRobot,kitRobotMock);
		// Test that partsRobot was saved
		assertEquals(stand.partsRobot,partsRobotMock);
	
		

		// Test that the state is FREE
		assertEquals(stand.state, StandAgentState.FREE);
	

		// Test that the topSlot was saved correctly with an empty kit and state to empty
		assertEquals(stand.topSlot.name, "topSlot");
		assertEquals(stand.topSlot.kit, null);
		assertEquals(stand.topSlot.state,MySlotState.EMPTY);

		// Test that the bottomSlot was saved correctly with an empty kit and state to empty
		assertEquals(stand.bottomSlot.name, "bottomSlot");
		assertEquals(stand.bottomSlot.kit, null);
		assertEquals(stand.bottomSlot.state,MySlotState.EMPTY);

		// Test that the inspectionSlot was saved correctly with an empty kit and state to empty
		assertEquals(stand.inspectionSlot.name, "inspectionSlot");
		assertEquals(stand.inspectionSlot.kit, null);
		assertEquals(stand.inspectionSlot.state,MySlotState.EMPTY);
		
		// Test that nothing gets executed if pickAndExecuteAnAction gets called
		assertEquals(stand.pickAndExecuteAnAction() , true);
	}


	@Test
	public void testMsgPartsRobotNoLongerUsingStand() {
		stand.vision = visionMock;
		stand.kitRobot = kitRobotMock;
		stand.partsRobot = partsRobotMock;
		
		stand.state = StandAgentState.PARTS_ROBOT;
		
		stand.msgPartsRobotNoLongerUsingStand();
		stand.needToClearStand = true;
		
		//state of the stand should be free
		assertEquals(StandAgentState.FREE, stand.state);
		
		stand.pickAndExecuteAnAction();
		
		//testing if kit robot can deliver an empty kit
		assertEquals(StandAgentState.KIT_ROBOT, stand.state);
		
		//stand needToClearStand should be false
		assertFalse(stand.needToClearStand);
		
		
	}

	@Test
	public void testMsgPartRobotWantsToPlaceParts() {
		stand.vision = visionMock;
		stand.kitRobot = kitRobotMock;
		stand.partsRobot = partsRobotMock;
		
		stand.msgPartRobotWantsToPlaceParts();
		
		
		//partsRobotWantsToDeliverParts should be true
		assertTrue(stand.partsRobotWantsToDeliverParts);
		
		stand.pickAndExecuteAnAction();
		
		//partsRobotWantsToDeliverParts should be true
		assertTrue(stand.partsRobotWantsToDeliverParts);
		
		//state should be FREE
		assertEquals(StandAgentState.FREE, stand.state);
	}
	
	@Test
	public void testMsgPartRobotWantsToPlacePartsWithEmptySlots() {

		stand.vision = visionMock;
		stand.kitRobot = kitRobotMock;
		stand.partsRobot = partsRobotMock;
		
		// Test that nothing gets executed if the Parts Robot wants to delivery parts but the slots are empty
		stand.msgPartRobotWantsToPlaceParts();
		assertEquals(stand.pickAndExecuteAnAction() , true);
		
		
		//assertEquals(stand.pickAndExecuteAnAction() , false);
	}

	@Test
	public void testMsgKitRobotNoLongerUsingStand() {
		stand.vision = visionMock;
		stand.kitRobot = kitRobotMock;
		stand.partsRobot = partsRobotMock;
		
		stand.state = StandAgentState.KIT_ROBOT;
		
		stand.msgKitRobotNoLongerUsingStand();
		
		//State should be FREE
		assertEquals(StandAgentState.FREE, stand.state);
		stand.needToClearStand = true;
		
		//state of the stand should be free
		assertEquals(StandAgentState.FREE, stand.state);
		
		stand.pickAndExecuteAnAction();
		
		//testing if kit robot can deliver an empty kit
		assertEquals(StandAgentState.KIT_ROBOT, stand.state);
		
		//stand needToClearStand should be false
		assertFalse(stand.needToClearStand);
		
		
	}

}
