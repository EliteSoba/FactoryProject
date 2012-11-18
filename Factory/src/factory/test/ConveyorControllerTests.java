package factory.test;

import org.junit.Test;

import junit.framework.TestCase;
import factory.*;
import factory.test.mock.*;

public class ConveyorControllerTests extends TestCase {
	
	MockConveyor conveyor;
	
	public void initializeAgents() {
		
	}
	
	@Test
	public void testNewKitRequest() {
		//Setting Up Agents and Mocks
		MockConveyor conveyor = new MockConveyor("Conveyor");
		ConveyorControllerAgent conveyorController = new ConveyorControllerAgent();
		conveyorController.setConveyor(conveyor);
		
		conveyorController.msgConveyorWantsEmptyKit();
		
		conveyorController.pickAndExecuteAnAction();
		
		conveyorController.msgAnimationDone();
	}

}
