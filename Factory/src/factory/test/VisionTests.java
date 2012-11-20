package factory.test;

import static org.junit.Assert.*;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;



import org.junit.Test;

import factory.Bin;
import factory.FCSAgent;
import factory.KitConfig;
import factory.Part;
import factory.StandAgent;
import factory.VisionAgent;
import factory.VisionAgent.KitPicRequestState;
import factory.interfaces.PartsRobot;
import factory.test.mock.*;
import factory.BinConfig;
import factory.FCSAgent.KitProductionState;;

public class VisionTests extends TestCase{

	public void testMsgInspectKitStand(){
		
		MockPartsRobot partsRobot = new MockPartsRobot("parts robot");
		MockStand stand = new MockStand("stand");		
		VisionAgent vision = new VisionAgent(partsRobot, stand, null );
		
		KitPicRequestState needToInspect = KitPicRequestState.NEED_TO_INSPECT;
		KitPicRequestState inspected = KitPicRequestState.INSPECTED;
		
		vision.inspectKitStand();
		
		//check to see if vision added a kitPic request
		assertEquals("The size of kitPicRequests should be 1, but instead it is: " + vision.kitPicRequests.size()
				, 1, vision.kitPicRequests.size());
		
		//check state of first kitPicRequest
		assertEquals("The state of the 1st kitPicRequest should be NEED_TO_INSPECT, but instead it is: "
				+ vision.kitPicRequests.get(0).state, needToInspect, vision.kitPicRequests.get(0).state);
		
		//call the scheduler
		vision.pickAndExecuteAnAction();
		
		
	}

}
