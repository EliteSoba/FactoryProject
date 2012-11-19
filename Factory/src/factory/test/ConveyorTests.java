package factory.test;

import org.junit.Test;

import junit.framework.TestCase;
import factory.*;
import factory.ConveyorAgent.ConveyorState;
import factory.test.mock.*;

public class ConveyorTests extends TestCase {
	ConveyorAgent conveyor;
	MockKitRobot kitRobot;
	MockConveyorController conveyorController;
	
	public void initializeAgents() {
		conveyor = new ConveyorAgent();
		kitRobot = new MockKitRobot("KitRobot");
		conveyorController = new MockConveyorController("ConveyorController");
		conveyor.setKitRobot(kitRobot);
		conveyor.setConveyorController(conveyorController);
	}
	
	@Test
	public void testNewKitRequest() {
		/**
		 * Test for the Conveyor getting a request for a new Kit from the KitRobot
		 */
		
		initializeAgents();
		assertEquals("kitAtConveyor is supposed to be initalized to be null, instead it is "+conveyor.kitAtConveyor, conveyor.kitAtConveyor, null);
		assertEquals("conveyorState should be NO_ACTION, instead it is "+conveyor.state, conveyor.state, ConveyorState.NO_ACTION);
		
		conveyor.pickAndExecuteAnAction();
		
		//nothing should have happend
		assertEquals("conveyorState should be NO_ACTION, instead it is "+conveyor.state, conveyor.state, ConveyorState.NO_ACTION);
		
		conveyor.msgNeedEmptyKit(); //Sending the nneedEmptyKit 
		
		assertEquals("ConveyorState should be KR_WANTS_EMPTY_KIT instead it is "+conveyor.state, conveyor.state, ConveyorState.KR_WANTS_EMPTY_KIT);
		
		conveyor.pickAndExecuteAnAction();
		
		assertEquals("ConveyorState shoudl be GETTING_EMPTY_KIT instead it is "+conveyor.state, conveyor.state, ConveyorState.GETTING_EMPTY_KIT);
		
		conveyor.msgHeresEmptyKit(new Kit()); //The New Kit Arrives on the conveyor
		
		assertTrue("Conveyor kitAtConveyor should now not be null, instead it is "+conveyor.kitAtConveyor, conveyor.kitAtConveyor instanceof Kit);
		
		conveyor.pickAndExecuteAnAction();
		//Should have told the kitrobot about the empty kit that is at the conveyor
		
		assertEquals("ConveyorState should now be ConveyorState.NO_ACTION, instead it is "+conveyor.state, conveyor.state, ConveyorState.NO_ACTION);
		
	}

}
