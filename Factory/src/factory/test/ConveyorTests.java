package factory.test;

import org.junit.Test;

import junit.framework.TestCase;
import factory.*;
import factory.test.mock.*;

public class ConveyorTests extends TestCase {
	
	@Test
	public void testNewKitRequest() {
		ConveyorAgent  conveyor = new ConveyorAgent();
		MockKitRobot kitRobot = new MockKitRobot("KitRobot");
		
	}

}
