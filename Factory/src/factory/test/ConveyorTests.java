package factory.test;

import org.junit.Test;

import junit.framework.TestCase;
import factory.*;
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
		
		conveyor.pickAndExecuteAnAction();
		
		conveyor.msgNeedEmptyKit(); //Sending the nneedEmptyKit 
		
		conveyor.pickAndExecuteAnAction();
		
		conveyor.msgHeresEmptyKit(new Kit()); //The New Kit Arrives on the conveyor
		
		conveyor.pickAndExecuteAnAction();
		
		//Should have told the kitrobot about the empty kit that is at the conveyor
		
	}

}
