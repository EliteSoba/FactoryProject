package factory.test;

import junit.framework.TestCase;

import org.junit.Test;
import factory.test.mock.*;

import factory.StandAgent;
import factory.StandAgent.MySlotState;
import factory.StandAgent.StandAgentState;

public class StandTests extends TestCase {

	StandAgent stand;
	MockVision visionMock;
	MockKitRobot kitRobotMock;
	MockPartsRobot partsRobotMock;
	MockConveyor conveyorMock;
	

	@Test
	public void testStandAgent() {

		
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
		assertEquals(stand.pickAndExecuteAnAction() ,false);
	}

	@Test
	public void testMsgEmptyKitIsHereWithTopSlotFree() {
		fail("Not yet implemented");
	}

	@Test
	public void testMsgEmptyKitIsHereWithBottomSlotFree() {
		fail("Not yet implemented");
	}

	@Test
	public void testMsgEmptyKitIsHereWithNoSlotFree() {
		fail("Not yet implemented");
	}
	

	@Test
	public void testMsgHereIsAnEmptyKit() {
		fail("Not yet implemented");
	}

	@Test
	public void testMsgKitRobotNoLongerUsingStand() {
		fail("Not yet implemented");
	}

	@Test
	public void testMsgPartRobotWantsToPlaceParts() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testMsgPartRobotWantsToPlacePartsWithEmptySlots() {

		// Test that nothing gets executed if the Parts Robot wants to delivery parts but the slots are empty
		stand.msgPartRobotWantsToPlaceParts();
		assertEquals(stand.pickAndExecuteAnAction() , false);
	}

	@Test
	public void testMsgPartsRobotNoLongerUsingStand() {
		fail("Not yet implemented");
	}

	@Test
	public void testMsgResultsOfKitAtInspection() {
		fail("Not yet implemented");
	}

}
