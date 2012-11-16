package factory.test;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Test;
import factory.*;
import factory.test.mock.*;

public class GantryTests extends TestCase{

	public void testMsgFeederNeedsPart() {
		
		GantryAgent gantry = new GantryAgent(null);
		Part part = new Part("Noses");
		MockFeeder feeder = new MockFeeder(null);
		gantry.msgFeederNeedsPart(part, feeder);
		
		//Test to see if gantry got a bin request.  myBins list should be 1
		assertEquals("Gantry should have one bin in myBins list.  Instead, it has: " + gantry.myBins.size() + " bins.",
				1, gantry.myBins.size());
		
		//test to see that the feeder agent has not been given a message before the scheduler is called.
		//Its log should be empty.
		assertEquals(
				"MockFeeder should have an empty event log before the GantryAgent scheduler is called. Instead, the mock gantry event log reads: "
						+ feeder.log.toString(), 0, feeder.log.size());
		
		gantry.pickAndExecuteAnAction();
		
		//feeder should get the message here.
		assertTrue("Feeder should have gotten a message to change bin config.", feeder.log
				.containsString("msgHereAreParts"));
	}
	
	public void testMultiplePartsRequestOneFeeder() {
		GantryAgent gantry = new GantryAgent(null);
		Part p1 = new Part("Noses");
		Part p2 = new Part("Ears");
		MockFeeder feeder = new MockFeeder(null);
		
		gantry.msgFeederNeedsPart(p1, feeder);
		
		assertEquals("Gantry should have one bin in myBins list.  Instead, it has: " + gantry.myBins.size() + " bins.",
				1, gantry.myBins.size());
		
		gantry.msgFeederNeedsPart(p2, feeder);
		
		//sees if the gantry got the message.  Should check to see that there are 2 bins in the myBins list
		assertEquals("Gantry should have two bins in myBins list.  Instead, it has: " + gantry.myBins.size() + " bins.",
				2, gantry.myBins.size());
		
		
		//test to see that the feeder agent has not been given a message before the scheduler is called.
		//Its log should be empty.
		assertEquals(
				"MockFeeder should have an empty event log before the GantryAgent scheduler is called. Instead, the mock gantry event log reads: "
						+ feeder.log.toString(), 0, feeder.log.size());
		
		gantry.pickAndExecuteAnAction();
		
		//feeder should have gotten message from the gantry
		assertTrue("Feeder should have gotten message from the gantry", feeder.log.getLastLoggedEvent()
				.getMessage().contains("msgHereAreParts"));
		
		//gantry should have given the part "nose"
		assertTrue("Feeder should have given noses", feeder.log.getLastLoggedEvent()
				.getMessage().contains("Noses"));
		
		
		assertEquals("Gantry should still have 2 bins \"this will change\"", 2, gantry.myBins.size());
		
		gantry.pickAndExecuteAnAction();
		
		assertTrue("Feeder should have gotten message from the gantry", feeder.log.getLastLoggedEvent().getMessage()
				.contains("msgHereAreParts"));
		
		//gantry should have given the part "Ears"
		assertTrue("Feeder should have given ears", feeder.log.getLastLoggedEvent()
				.getMessage().contains("Ears"));
		
		//check to see if the latest message was only for ears, and not noses
		assertFalse("Feeder should have given ears", feeder.log.getLastLoggedEvent()
				.getMessage().contains("Noses"));
		
	}
	
	public void testMultiplePartsRequestTwoFeeder() {
		GantryAgent gantry = new GantryAgent(null);
		Part p1 = new Part("Noses");
		Part p2 = new Part("Ears");
		MockFeeder f1 = new MockFeeder("Feeder1");
		MockFeeder f2 = new MockFeeder("Feeder2");
		
		gantry.msgFeederNeedsPart(p1, f1);
		
		//sees if the gantry got the message.  Should see that there is 1 bin in the myBins list
		assertEquals("Gantry should have one bin in myBins list.  Instead, it has: " + gantry.myBins.size() + " bins.",
				1, gantry.myBins.size());
		
		gantry.msgFeederNeedsPart(p2, f2);
		
		//sees if the gantry got the message.  Should see that there are 2 bins in the myBins list
		assertEquals("Gantry should have two bins in myBins list.  Instead, it has: " + gantry.myBins.size() + " bins.",
				2, gantry.myBins.size());
		
		
		//test to see that the feeder agent has not been given a message before the scheduler is called.
		//Its log should be empty.
		assertEquals(
				"MockFeeder 1 should have an empty event log before the GantryAgent scheduler is called. Instead, the mock gantry event log reads: "
						+ f1.log.toString(), 0, f1.log.size());
		
		//test to see that the feeder agent has not been given a message before the scheduler is called.
		//Its log should be empty.
		assertEquals(
				"MockFeeder 2 should have an empty event log before the GantryAgent scheduler is called. Instead, the mock gantry event log reads: "
						+ f2.log.toString(), 0, f2.log.size());
		
		gantry.pickAndExecuteAnAction();
		
		//feeder should have gotten message from the gantry
		assertTrue("Feeder 1 should have gotten message from the gantry", f1.log.getLastLoggedEvent()
				.getMessage().contains("msgHereAreParts"));
		
		//gantry should have given the part "nose"
		assertTrue("Feeder 1 should have given noses", f1.log.getLastLoggedEvent()
				.getMessage().contains("Noses"));
		
		//gantry should have given the part to feeder 1, not 2; therefore, feeder 2 log should still be empty
		assertEquals("Feeder 2 log should still be empty since the message should have been given to feeder 1; however, feeder 2 got message: "
						+ f2.log.toString(), 0, f2.log.size());
		
		
		//this will change later depending if i want to remove the bins from mybins or not
		assertEquals("Gantry should still have 2 bins \"this will change\"", 2, gantry.myBins.size());
		
		gantry.pickAndExecuteAnAction();
		
		//gantry should have given a message to feeder 2
		assertTrue("Feeder 2 should have gotten message from the gantry", f2.log.getLastLoggedEvent().getMessage()
				.contains("msgHereAreParts"));
		
		//gantry should have given the part "Ears" to feeder 2 
		assertTrue("Feeder 2 should have gotten ears", f2.log.getLastLoggedEvent()
				.getMessage().contains("Ears"));
		
		//gantry should NOT have given the part "Ears" to feeder 1
		assertFalse("Feeder 1 should NOT have gotten ears", f1.log.getLastLoggedEvent()
				.getMessage().contains("Ears"));
		
	}

}

