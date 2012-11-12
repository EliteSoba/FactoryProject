package factory.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;



import org.junit.Test;

import factory.Bin;
import factory.FCSAgent;
import factory.KitConfig;
import factory.test.mock.*;
import factory.BinConfig;



public class FCSTests extends TestCase{

//	MockGantry mockGantry;
//	MockPartsRobotAgent partsRobot;
//	FCSAgent FCSagent;
//	BinConfig binConfig;
//	boolean passBinConfigurationToGantry;
	
	/**tests the msgInitialize().  The initialize msg should send the bin config information to a gantry agent **/
	public void testMsgInitialize(){
		
		HashMap<Bin, Integer> binList = new HashMap<Bin, Integer>();
		BinConfig binConfig = new BinConfig(binList);
		MockGantry gantry = new MockGantry("gantry");
		MockPartsRobotAgent partsRobot = new MockPartsRobotAgent("parts robot");
		FCSAgent fcs = new FCSAgent(gantry, partsRobot);
		
		//create message for FCS.  Should eventually call gantry.msgChangeGantryBinConfig(this.binConfig);
		fcs.msgInitialize(binConfig);
		
		//passBinConfigurationToGantry should be true
		assertTrue("passBinConfigurationToGantry should be true after calling msgInitialize.  Instead, it is: "
				+ fcs.passBinConfigurationToGantry, fcs.passBinConfigurationToGantry);
		
		//test to see that the gantry robot has not been given a message before the scheduler is called.
		//Its log should be empty.
		assertEquals(
				"Mock Gantry should have an empty event log before the FCSAgent scheduler is called. Instead, the mock gantry event log reads: "
						+ gantry.log.toString(), 0, gantry.log.size());
		
		//call the fcs scheduler
		fcs.pickAndExecuteAnAction();
		
		//tests to see if the gantry log got the message
		assertTrue("Gantry should have gotten a message to change bin config.", gantry.log
				.containsString("msgChangeGantryBinConfig"));
		
		//passBinConfigurationToGantry should be false after calling the scheduler. 
		assertFalse("passBinConfigurationToGantry should be false after calling the scheduler.  Instead, it is: "
				+ fcs.passBinConfigurationToGantry, fcs.passBinConfigurationToGantry);
		
		//only one message should be sent to the gantry
		assertEquals(
				"Only 1 message should have been sent to the gantry. Event log: "
						+ gantry.log.toString(), 1, gantry.log.size());
		
	}
	
	public void testMsgProduceKit()
	{
		
		KitConfig kitConfig = new KitConfig();
		MockGantry gantry = new MockGantry("gantry");
		MockPartsRobotAgent partsRobot = new MockPartsRobotAgent("parts robot");
		FCSAgent fcs = new FCSAgent(gantry, partsRobot);
		
		//create message for FCS.  Should eventually call this.partsRobot.msgMakeKit(mkc.kitConfig);
		fcs.msgProduceKit(kitConfig);
		
		//test to see that the parts robot has not been given a message before the scheduler is called.
		//Its log should be empty.
		assertEquals(
				"Mock parts robot should have an empty event log before the FCSAgent scheduler is called. Instead, the mock parts robot event log reads: "
						+ partsRobot.log.toString(), 0, partsRobot.log.size());
		
		//test to see if parts robot added a kitConfig to its list.  The list shouldn't be empty after msgProduceKit()
		assertFalse("Parts robot should have a kitConfig to its list of kit configs, but it is empty", 
					fcs.myKitConfigs.isEmpty());
		
		//call the fcs scheduler
		fcs.pickAndExecuteAnAction();
		
		//tests to see if the parts robot log got the message from the FCS
		assertTrue("Parts Robot should have gotten a message to make a kit from the FCS.", partsRobot.log
				.containsString("msgMakeKit"));
		
		//tests to see if the kit config state changed from PENDING to PRODUCING
//		assertTrue("Parts Robot should have gotten a message to make a kit.", );
		
		
	}

	
	
	
	
}
