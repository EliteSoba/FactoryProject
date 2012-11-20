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
import factory.VisionAgent.PictureRequest;
import factory.VisionAgent.PictureRequestState;
import factory.interfaces.PartsRobot;
import factory.test.mock.*;
import factory.BinConfig;
import factory.FCSAgent.KitProductionState;;

public class VisionTests extends TestCase{

	public void testMsgInspectKitStand(){
		
		MockPartsRobot partsRobot = new MockPartsRobot("parts robot");
		MockStand stand = new MockStand("stand");		
		VisionAgent vision = new VisionAgent(partsRobot, stand, null );
		vision.isUnitTesting = true;
		
		KitPicRequestState needToInspect = KitPicRequestState.NEED_TO_INSPECT;
		KitPicRequestState inspected = KitPicRequestState.INSPECTED;
		
		//check to see if kitPicRequests is empty initially
		assertEquals("The size of kitPicRequests should be 0, but instead it is: " + vision.kitPicRequests.size()
				, 0, vision.kitPicRequests.size());
		
		//send message
		vision.msgAnalyzeKitAtInspection(null);
		
		//check to see if vision added a kitPic request
		assertEquals("The size of kitPicRequests should be 1, but instead it is: " + vision.kitPicRequests.size()
				, 1, vision.kitPicRequests.size());
		
		//check state of first kitPicRequest
		assertEquals("The state of the 1st kitPicRequest should be NEED_TO_INSPECT, but instead it is: "
				+ vision.kitPicRequests.get(0).state, needToInspect, vision.kitPicRequests.get(0).state);
		
		//stand should have no messages
		assertEquals("StandAgent should have have no messages yet, but has: " + stand.log.toString()
				, 0, stand.log.size());
		
		//call the scheduler
		vision.pickAndExecuteAnAction();
		
		//stand should have gotten a message
		assertTrue("StandAgent should have gotten a message of the results of the inspection.", stand.log
				.getLastLoggedEvent().getMessage().contains("msgResultsOfKitAtInspection"));
		
		//check state of first kitPicRequest
		assertEquals("The state of the 1st kitPicRequest should be INSPECTED, but instead it is: "
				+ vision.kitPicRequests.get(0).state, inspected, vision.kitPicRequests.get(0).state);
		
		
		
	}
	
	public void testMsgMyNestsReadyForPicture(){
		
		MockPartsRobot partsRobot = new MockPartsRobot("parts robot");
		MockStand stand = new MockStand("stand");		
		VisionAgent vision = new VisionAgent(partsRobot, stand, null );
		vision.isUnitTesting = true;
		
		PictureRequestState askedPartsRobot = PictureRequestState.ASKED_PARTS_ROBOT;
		
		//picRequests should be empty
		assertEquals("Vision agent should have no picRequests initially, but instead has: " + vision.picRequests.size()
				, 0, vision.picRequests.size());
		
		//send message
		vision.msgMyNestsReadyForPicture(null, null, null, null, null);
		
		//picRequests should have 1 pic request
		assertEquals("Vision agent should have 1 picRequests , but instead has: " + vision.picRequests.size()
				, 1, vision.picRequests.size());
		
		//call scheduler
		vision.pickAndExecuteAnAction();
		
		//check picRequest state
		assertEquals("PicRequest state should be ASKED_PARTS_ROBOT, but instead is: " + vision.picRequests.get(0).state
				, askedPartsRobot, vision.picRequests.get(0).state);
		
		//stand should have gotten a message
		assertTrue("partsRobot should have gotten a message from Vision.", partsRobot.log
				.getLastLoggedEvent().getMessage().contains("msgClearLineOfSight"));
		
	}

}
