package factory.test;

import factory.FeederAgent;
import factory.FeederAgent.DiverterState;
import factory.FeederAgent.FeederState;
import factory.FeederAgent.MyLane;
import factory.FeederAgent.MyLaneState;
import factory.FeederAgent.MyPartRequest;
import factory.FeederAgent.MyPartRequestState;
import factory.Part;
import factory.interfaces.Lane;
import factory.interfaces.Nest;
import factory.test.mock.EventLog;
import factory.test.mock.LoggedEvent;
import factory.test.mock.MockGantry;
import factory.test.mock.MockLane;
import factory.test.mock.MockNest;
import junit.framework.TestCase;

public class FeederTests extends TestCase{
	
	MyPartRequest mpr;
	FeederAgent feeder;
	MockLane top, bottom;
	MockGantry gantry;
	Part p1,p2,p3,p4,p5,p6;
	
	/**
	 * Sets up the standard configuration for easy testing.
	 */
	@Override
	protected void setUp() throws Exception {
		top = new MockLane("top");
		bottom = new MockLane("bottom");
		gantry = new MockGantry("gantry");
		
		feeder = new FeederAgent("Feeder",0, top, bottom, gantry, null,null,true);
		feeder.diverter = DiverterState.FEEDING_BOTTOM; // initial setting to test the switching of the lane diverter
		
		
		p1 = new Part("eye",001,"desc","imgPath",2);
		p2 = new Part("shoe",002,"desc","imgPath",3);
		p3 = new Part("sword",003,"desc","imgPath",3);
		p4 = new Part("arm",004,"desc","imgPath",3);
		p5 = new Part("hat",005,"desc","imgPath",3);
		p6 = new Part("tentacle",006,"desc","imgPath",3);

	}
	
	/**
	 *  This test creates a feeder and tests its preconditions.
	 * 
	 */
	public void testPreconditions() {
		// Makes sure there aren't any parts in the feeder initially.
		assertEquals(feeder.requestedParts.size(),0);
		assertTrue(feeder.requestedParts != null);
		assertEquals(feeder.currentPart,null);
		assertEquals(feeder.topLane.part,null);
		assertEquals(feeder.bottomLane.part,null);
		assertEquals(feeder.feederNumber,0);
		
	}
	
	/* SCENARIO #1: The very first part fed into the Feeder.
	 * The Feeder is empty.
	 * The target lane is empty.
	 */
//	public void testScenario1() {
//		
//		// Test Preconditions:
//		assertTrue(feeder.currentPart == null); // the feeder is empty
//		assertTrue(feeder.topLane.part == null); // the target lane is empty
//		
//		// Run the scenario test:
//		feeder.msgLaneNeedsPart(p1, top);
//		feeder.pickAndExecuteAnAction(); // for checking the nests
//		feeder.pickAndExecuteAnAction();
//
//		// Test Postconditions:
//		assertTrue(feeder.state == FeederState.WAITING_FOR_PARTS);
//		for(MyPartRequest mpr : feeder.requestedParts)
//		{
//			if (mpr.pt == p1)
//			{
//				assertTrue(mpr.state == MyPartRequestState.ASKED_GANTRY);
//			}
//		}
//    	assertTrue("Gantry should receive msgFeederNeedsPart(...). Event log: "+ gantry.log.toString(),gantry.log.containsString("msgFeederNeedsPart(...)"));
//	}
	
	
	
//	else if (!FeederHasDesiredPart && (TargetLaneHasDesiredPart || TargetLaneIsEmpty))
//	{
//		purgeFeeder();  
//		state = FeederState.WAITING_FOR_PARTS;
//		partRequested.state = MyPartRequestState.ASKED_GANTRY;
//		gantry.msgFeederNeedsPart(partRequested.pt, this);
//	}
	
	
	/* SCENARIO #7: The Feeder has the right part and the target lane has the wrong part.
	 * The Feeder has the right part.
	 * The target lane has the wrong part.
	 * This is the only known bug right now in the FeederAgent.
	 */
	public void testScenario6() {
		// Set up test scenario
		feeder.msgLaneNeedsPart(p1, top);
		feeder.pickAndExecuteAnAction(); // for checking the nests
		feeder.pickAndExecuteAnAction();

		feeder.msgHereAreParts(p1);
		feeder.pickAndExecuteAnAction();
		feeder.pickAndExecuteAnAction();
		
		assertTrue(feeder.requestedParts.size() == 0);

		feeder.msgLaneNeedsPart(p2, bottom);
		feeder.pickAndExecuteAnAction(); // for checking the nests
		feeder.pickAndExecuteAnAction();
		feeder.pickAndExecuteAnAction();

		
		feeder.msgHereAreParts(p2);
		feeder.pickAndExecuteAnAction();
		feeder.pickAndExecuteAnAction();
		feeder.pickAndExecuteAnAction();

		
		assertTrue(feeder.requestedParts.size() == 0);
		assertTrue(feeder.topLane.part.id == p1.id);
		assertTrue(feeder.bottomLane.part.id == p2.id);
		assertTrue(feeder.currentPart.id == p2.id);
		
		feeder.msgLaneNeedsPart(p3, top); // the new part that is requested
		feeder.pickAndExecuteAnAction(); // for checking the nests
		feeder.pickAndExecuteAnAction();
		
		feeder.msgHereAreParts(p3);
		feeder.pickAndExecuteAnAction();
		feeder.pickAndExecuteAnAction();
		
		
		feeder.msgLaneNeedsPart(p3, bottom); 
		feeder.pickAndExecuteAnAction(); // for checking the nests
		feeder.pickAndExecuteAnAction();

		MyPartRequest requestedPart = null;
		for(MyPartRequest mpr : feeder.requestedParts)
		{
			if (mpr.pt == p3 && mpr.lane == bottom)
			{
				requestedPart = mpr;
			}
		}
		
		assertTrue(requestedPart != null);
		assertTrue(requestedPart.pt == p3);
		assertTrue(feeder.currentPart == p3);
		
		MyLane targetLane = null;
		if (feeder.topLane.lane == requestedPart.lane)
			targetLane = feeder.topLane;
		else if (feeder.bottomLane.lane == requestedPart.lane)
			targetLane = feeder.bottomLane;
		
		
		
		
		// Test Preconditions:
		assertTrue(feeder.currentPart.id == requestedPart.pt.id); // feeder has the right part
		assertTrue(targetLane.part.id != requestedPart.pt.id);    // lane has the wrong part
		
		assertTrue(feeder.currentPart.id == p3.id);  // more specific
		assertTrue(targetLane.part.id == p2.id); // more specific

		
		
		// Run the scenario test:
		feeder.pickAndExecuteAnAction(); // for checking the nests
		feeder.pickAndExecuteAnAction();
	
		feeder.msgHereAreParts(p3);
		feeder.pickAndExecuteAnAction();
		feeder.pickAndExecuteAnAction();
		
		// Test Postconditions:
		assertTrue(feeder.currentPart.id == p3.id);
		assertTrue(targetLane.part.id == p3.id);
		
		assertTrue(feeder.topLane.part.id == p3.id); // and just make sure this hasn't changed for any reason
		
	}
	
	
	/** 
	 * This test makes sure that the Feeder's lanes are getting set up properly.
	 **/
//	
//	public void testSetUpLanesAndGantry() {
//		// These lanes were set up in the preconditions test
////		assertEquals(feeder.topLane.lane.getName(),"top");
////		assertEquals(feeder.bottomLane.lane.getName(),"bottom");
////		
//		// make sure topLane's state is initialized correctly
//		assertEquals(feeder.topLane.state,FeederAgent.MyLaneState.EMPTY); 
//
//		//assertEquals(feeder.gantry.getName(),"gantry");
//
//	}
//	
//	
//	/** Test the MESSAGES  **/
//	
//	public void testMsgEmptyNest(){
//		// Some initial setup
//		MockNest n = new MockNest("n");
//		top.setNest(n);
//		
//		// send the message
//		feeder.msgEmptyNest(n);
//		
//		
//		// Check to see if lane receives appropriate message
//    	assertTrue("Lane should have been told msgIncreaseAmplitude(). Event log: "
//    			+ top.log.toString(), 
//    			top.log.containsString("msgIncreaseAmplitude()"));
//    	
//    	// Check to see if lane's jamstate gets set correctly
//    	assertEquals(feeder.topLane.jamState,FeederAgent.JamState.MIGHT_BE_JAMMED);
//    	
//    	
//    	
//    	// Now check to make sure that the feeder's scheduler is working properly
//    	feeder.pickAndExecuteAnAction();  
//    	// FURTHER TESTING HERE FOR THE NON-NORMATIVE CASE OF A PART BEING STUCK
//    	
//	}
//	
//	public void testMsgNestWasDumped() {
//		
//		// scenario #1: the feeder dumps a nest because
//		// the nest didn't have any good parts
//		feeder.state = FeederAgent.FeederState.CONTAINS_PARTS; 
//
//		// send the message
//		feeder.msgNestWasDumped(top);
//		
//		// Check to see if the lane's state changes properly
//		assertEquals(feeder.topLane.state,FeederAgent.MyLaneState.NEST_SUCCESSFULLY_DUMPED);
//
//		// Make sure that the Feeder's scheduler is working correctly
//		feeder.pickAndExecuteAnAction();
//		assertEquals(feeder.topLane.state,FeederAgent.MyLaneState.CONTAINS_PARTS);
//		
//		
//		
//		// scenario #2: the feeder dumps a nest because it is trying to purge the lane
//		feeder.state = FeederAgent.FeederState.OK_TO_PURGE; 
//
//		// send the message
//		feeder.msgNestWasDumped(top);
//
//		// Check to see if the lane's state changes properly
//		assertEquals(feeder.topLane.state,FeederAgent.MyLaneState.NEST_SUCCESSFULLY_DUMPED);
//
//		// Make sure that the Feeder's scheduler is working correctly
//		feeder.pickAndExecuteAnAction();
//		assertEquals(feeder.topLane.state,FeederAgent.MyLaneState.PURGING);
//
//					
//	}
//	
//	
//	public void testMsgLaneNeedsPart() {
//		// some initial setup
//		feeder.state = FeederAgent.FeederState.EMPTY;
//		
//		
//		// SCENARIO #1: The Feeder is initially empty and receives a request for parts.
//		// send the message
//		feeder.msgLaneNeedsPart(p1, top);
//		
//		// Initial Parts Request State
//		for(FeederAgent.MyPartRequest pr : feeder.requestedParts)
//		{
//			if (pr.pt == p1)
//			{
//				assertEquals(pr.state,FeederAgent.MyPartRequestState.NEEDED);
//			}
//		}
//		
//		
//		// Now make sure scheduler works
//		feeder.pickAndExecuteAnAction();
//		
//		// Check to see if gantry receives appropriate message
//    	assertTrue("Gantry should have been told msgFeederNeedsPart(...). Event log: "
//    			+ gantry.log.toString(), 
//    			gantry.log.containsString("msgFeederNeedsPart(...)"));
//    	
//    	// Check to see if the feeder's state gets set correctly
//    	assertEquals(feeder.state,FeederAgent.FeederState.WAITING_FOR_PARTS);
//		
//    	// Parts Request State after scheduler
//		for(FeederAgent.MyPartRequest pr : feeder.requestedParts)
//		{
//			if (pr.pt == p1)
//			{
//				mpr = pr;
//				assertEquals(pr.state,FeederAgent.MyPartRequestState.ASKED_GANTRY);
//			}
//		}
//
//    			
//    			
//
//    			
//    			
//		// SCENARIO #2: The feeder is IMMEDIATELY sent a new request for parts, which happens often, as the feeder has 2 lanes.
//    	Part p2 = new Part("p2");
//		feeder.msgLaneNeedsPart(p2, top);
//
//		// Initial Parts Request State
//		for(FeederAgent.MyPartRequest pr : feeder.requestedParts)
//		{
//			if (pr.pt == p2)
//			{
//				assertEquals(pr.state,FeederAgent.MyPartRequestState.NEEDED);
//			}
//		}
//
//		// Now make sure scheduler works
//		feeder.pickAndExecuteAnAction();
//
//		assertTrue("The gantry should not have received another message.",gantry.log.size() == 1);
//		
//		// Parts Request State after scheduler- basically this should not change 
//		// from NEEDED b/c the feeder isn't ready to purge, and is thus not ready to ask the gantry for this part
//		for(FeederAgent.MyPartRequest pr : feeder.requestedParts)
//		{
//			if (pr.pt == p2)
//			{
//				mpr = pr;
//				assertEquals(pr.state,FeederAgent.MyPartRequestState.NEEDED);
//			}
//		}
//		
//		
//		
//		
//		
//		
//		
//		// SCENARIO #3: The feeder is ready to purge now
//		// some initial setup:
//		feeder.state = FeederAgent.FeederState.OK_TO_PURGE;
//		//mpr.state = FeederAgent.MyPartRequestState.NEEDED; // I don't think this is necessary.
//		feeder.topLane.part = mpr.pt;
//		
//		
//		assertEquals(feeder.topLane.lane,mpr.lane);
//		assertTrue(feeder.topLane.part != null);
//		assertEquals(top,feeder.topLane.lane);
//
//		// call the scheduler
//		feeder.pickAndExecuteAnAction();
//		
//		// Check to see if the lane receives appropriate message
////    	assertTrue("Lane should have been told msgPurge(). Event log: "
////    			+ top.log.toString(), 
////    			top.log.containsString("msgPurge()"));
////    	
//    	// and check the lane's state
//    	assertEquals(feeder.topLane.state,FeederAgent.MyLaneState.PURGING);
//		
//	}
//	
//	
//	public void testMsgHereAreParts() {
//		// some initial setup
//		feeder.state = FeederAgent.FeederState.WAITING_FOR_PARTS;
//		feeder.addMyPartRequest(p1,top,FeederAgent.MyPartRequestState.ASKED_GANTRY);
//		
//		// PART #1: The feeder receives the parts he asked for from the gantry.
//		feeder.msgHereAreParts(p1);
//		
//		// The gantry has DELIVERED the part
//		for(FeederAgent.MyPartRequest pr : feeder.requestedParts)
//		{
//			if (pr.pt == p1)
//			{
//				mpr = pr; // set a reference to the MyPartRequest
//				assertEquals(pr.state,FeederAgent.MyPartRequestState.DELIVERED);
//			}
//		}
//		
//		// make sure the scheduler works
//		feeder.pickAndExecuteAnAction();
//		
//		// should remove the request from the list
//		assertFalse(feeder.requestedParts.contains(mpr));
//		
//		// the current part that is being fed into the lane
//		assertEquals(feeder.currentPart,mpr.pt);
//		
//		if (feeder.bottomLane.lane == mpr.lane){
//			assertEquals(feeder.bottomLane.part,mpr.pt);
//		}
//		else {
//			assertEquals(feeder.topLane.part,mpr.pt);
//		}
//		
//		// make sure the feeder's state is updating correctly
//		assertEquals(feeder.state,FeederAgent.FeederState.SHOULD_START_FEEDING);
//		
//		
//		
//		
//		// PART #2: The feeder receives the parts he asked for from the gantry.
//		feeder.diverter = DiverterState.FEEDING_BOTTOM; // to test the switching of the lane diverter
//		
//		feeder.pickAndExecuteAnAction();
//		
//		assertEquals(feeder.diverter,DiverterState.FEEDING_TOP); // switches to feed the top because that is the lane we are feeding
//		
//		
//		// PART #3: After the okayToPurgeTimer goes off, the feeder receives a request for new parts.
//		feeder.state = FeederState.OK_TO_PURGE; // this gets called when the okayToPurgeTimer goes off
//		
//	}
//	
//	
//	public void testMsgBadNest() {
//		
//		feeder.msgBadNest(top.getNest()); // send the msg
//		
//		assertEquals(feeder.topLane.state,MyLaneState.BAD_NEST); // check to see if initial state assignment works
//		
//		// check to see if the scheduler works
//		feeder.pickAndExecuteAnAction();
//		
//		// make sure the topLane receives the right message
//    	assertTrue("Lane should have been told msgDumpNest(). Event log: "
//    			+ top.log.toString(), 
//    			top.log.containsString("msgDumpNest()"));
//		
//    	// and that the lane's state changes properly
//		assertEquals(feeder.topLane.state,MyLaneState.TOLD_NEST_TO_DUMP); 
//				
//		
//	}
//	
//	
//	/** This test tests the following scenario:
//	 * 1) Parts of type A are loaded into the top lane. 
//	 * 2) Parts of type B are loaded into the bottom lane.
//	 * 3) Parts of type A are loaded into the top lane.
//	 *     - Neither lane should purge, only the feeder.
//	 *     - The lane diverter should switch and feed the Parts A into the top lane.
//	 */
//	
//	public void testLaneSwitchCase1() {
//		// some initial setup
////		feeder.state = FeederAgent.FeederState.WAITING_FOR_PARTS;
//		feeder.msgLaneNeedsPart(p1, top);
//		feeder.pickAndExecuteAnAction();
//		assertEquals(feeder.diverter,DiverterState.FEEDING_BOTTOM); // initially the diverter is feeding the bottom lane
//
//		
//		// #################################################
//		// (1) Parts of type A are loaded into the top lane.
//		// #################################################
//
//		feeder.msgHereAreParts(p1);
//		
//		// The gantry has DELIVERED the part
//		for(FeederAgent.MyPartRequest pr : feeder.requestedParts)
//		{
//			if (pr.pt == p1)
//			{
//				mpr = pr; // set a reference to the MyPartRequest
//				assertEquals(pr.state,FeederAgent.MyPartRequestState.DELIVERED);
//			}
//		}
//		
//		// make sure the scheduler works
//		feeder.pickAndExecuteAnAction();
//		
//		// should remove the request from the list
//		assertFalse(feeder.requestedParts.contains(mpr));
//		
//		// the current part that is being fed into the lane
//		assertEquals(feeder.currentPart,mpr.pt);
//
//		// make sure the top lane has registered the part correctly
//		assertEquals(feeder.topLane.part,mpr.pt);
//		
//		// make sure the feeder's state is updating correctly
//		assertEquals(feeder.state,FeederAgent.FeederState.SHOULD_START_FEEDING);
//		
//		// The feeder now acts on its SHOULD_START_FEEDING state change
//		feeder.pickAndExecuteAnAction();
//		
//		// diverter was feeding the bottom lane initially, see if it switched correctly
//		assertEquals(feeder.diverter,DiverterState.FEEDING_TOP); 
//			
//		// After the okayToPurgeTimer goes off, the feeder receives a request for new parts.
//		feeder.state = FeederState.OK_TO_PURGE; // this gets called when the okayToPurgeTimer goes off
//		
//		// Check the post-conditions of the 1st part load
//		assertEquals(feeder.topLane.part,p1);
//		assertEquals(feeder.currentPart,p1);
//		
//		
//		
//		// ####################################################
//		// (2) Parts of type B are loaded into the bottom lane.
//		// ####################################################
//		
//		feeder.msgLaneNeedsPart(p2, bottom);
//
//		feeder.pickAndExecuteAnAction();
//
//		// make sure the feeder's state is updating correctly
//		assertEquals(feeder.state,FeederAgent.FeederState.WAITING_FOR_PARTS);
//
//
////		assertEquals(feeder.bottomLane.part,p2);
//
//		// Figure out whats going on inside the purge if necessary method
////		assertTrue(feeder.bottomLane.lane == bottom);
////		assertTrue(feeder.bottomLane.part != feeder.currentPart);
//
//		
//		
//		feeder.msgHereAreParts(p2);
//
//		// The gantry has DELIVERED the part
//		for(FeederAgent.MyPartRequest pr : feeder.requestedParts)
//		{
//			if (pr.pt == p2)
//			{
//				mpr = pr; // set a reference to the MyPartRequest
//				assertEquals(pr.state,FeederAgent.MyPartRequestState.DELIVERED);
//			}
//		}
//
//		// make sure the scheduler works
//		feeder.pickAndExecuteAnAction();
//
//		// should remove the request from the list
//		assertFalse(feeder.requestedParts.contains(mpr));
//
//		// the current part that is being fed into the lane
//		assertEquals(feeder.currentPart,mpr.pt);
//		
//		// make sure the top lane has registered the part correctly
//		assertEquals(feeder.bottomLane.part,mpr.pt);
//
//		// make sure the feeder's state is updating correctly
//		assertEquals(feeder.state,FeederAgent.FeederState.SHOULD_START_FEEDING);
//
//		// The feeder acts on the SHOULD_START_FEEDING state change
//		feeder.pickAndExecuteAnAction();
//		
//		// the feeder should want to feed the bottomlane
//		assertEquals(feeder.currentPart,feeder.bottomLane.part);
//		
//		// Feed p2 to the bottom lane 
//		assertEquals(feeder.diverter,DiverterState.FEEDING_BOTTOM); 
//
//		
//		
//		// OK, so the diverter switching has worked for the first two lanes 
//		// when they are different parts for different lanes.
//
//		
//		
//		// Feeder is deciding if it should purge or not
//		assertTrue("Feeder should call the Action purgeIfNecessary(). Event log: "
//    			+ feeder.log.toString(), 
//    			feeder.log.containsString("Action purgeIfNecessary()"));
//
//		// Feeder has decided to purge
//		assertTrue("Feeder should call the Animation DoPurgeFeeder(). Event log: "
//    			+ feeder.log.toString(), 
//    			feeder.log.containsString("Animation DoPurgeFeeder()"));
//
//		// Feeder shouldn't purge either of its lanes
//		assertFalse("Feeder shouldn't call the Animation DoPurgeTopLane(). Event log: "
//				+ feeder.log.toString(), 
//				feeder.log.containsString("Animation DoPurgeTopLane()"));
//		assertFalse("Feeder shouldn't call the Animation DoPurgeBottomLane(). Event log: "
//				+ feeder.log.toString(), 
//				feeder.log.containsString("Animation DoPurgeBottomLane()"));
//		
//		// After the okayToPurgeTimer goes off, the feeder receives a request for new parts.
//		feeder.state = FeederState.OK_TO_PURGE; // this gets called when the okayToPurgeTimer goes off
//		
//
//		// Check the post-conditions of the 2nd part load
//		assertEquals(feeder.topLane.part,p1);
//		assertEquals(feeder.bottomLane.part,p2);
//		assertEquals(feeder.currentPart,p2);
//		assertEquals(feeder.diverter,DiverterState.FEEDING_BOTTOM); 
//
//		
//		
//		
//		
//		// #################################################
//		// (3) Parts of type A are loaded into the top lane.
//		// #################################################
//		feeder.msgLaneNeedsPart(p1, top);
//
//		feeder.pickAndExecuteAnAction();
//		
//		// make sure the feeder's state is updating correctly
//		assertEquals(feeder.state,FeederAgent.FeederState.WAITING_FOR_PARTS);
//
//		// Figure out whats going on inside the askGantyForPart method
//		assertFalse(feeder.currentPart == p1);
//		assertFalse(feeder.state == FeederState.IS_FEEDING);
//		
//		
//		assertEquals(feeder.topLane.part,p1);
//
//		// Figure out whats going on inside the purge if necessary method
//		assertTrue(feeder.topLane.lane == top);
//		assertTrue(feeder.topLane.part != feeder.currentPart); // WHERE IS CURRENTPART GETTING SET?
//		
//		//assertEquals(feeder.state,FeederState.OK_TO_PURGE);  // NOTE: We don't check this because it gets reset during the agent's thread.
//		
//		
//		
//		// Feeder shouldn't purge either of its lanes
//		assertFalse("Feeder shouldn't call the Animation DoPurgeTopLane(). Event log: "
//				+ feeder.log.toString(), 
//				feeder.log.containsString("Animation DoPurgeTopLane()"));
//		assertFalse("Feeder shouldn't call the Animation DoPurgeBottomLane(). Event log: "
//				+ feeder.log.toString(), 
//				feeder.log.containsString("Animation DoPurgeBottomLane()"));
//
//		// make sure the feeder's state is updating correctly
//		assertEquals(feeder.state,FeederAgent.FeederState.WAITING_FOR_PARTS);
//
//		feeder.msgHereAreParts(p1);
//		
//		feeder.log.clear();
//		
//		feeder.pickAndExecuteAnAction(); // puts the feeder in the state SHOULD_START_FEEDING
//		assertEquals(feeder.state,FeederState.SHOULD_START_FEEDING); 
//		
//		
//		feeder.pickAndExecuteAnAction(); // feeder should call the StartFeeding() action
//
//		
//		assertEquals(feeder.topLane.part,feeder.currentPart);
//		
//		//assertEquals(feeder.state,FeederState.IS_FEEDING); 
//
//		
//		// the PREVIOUS lane that was being fed
//		assertEquals(feeder.diverter,DiverterState.FEEDING_TOP); 
//
//		// see if the parts are matching up with the appropriate lane
//		assertEquals(feeder.currentPart,feeder.topLane.part);
//
//		// the top lane should contain parts
//		//assertEquals(feeder.topLane.state,MyLaneState.CONTAINS_PARTS);
//		
//		
//		assertTrue("Feeder should be doing Action ProcessFeederParts(). Event log: "
//    			+ feeder.log.toString(), 
//    			feeder.log.containsString("Action ProcessFeederParts()"));
//		
//		// MAKE SURE THE DIVERTER IS SWITCHING CORRECTLY
//		assertTrue("Feeder should be doing Animation DoSwitchLane(). Event log: "
//    			+ feeder.log.toString(), 
//    			feeder.log.containsString("Animation DoSwitchLane()"));
//	
//		
//	}
//	
//	/** This test tests the following scenario:
//	 * 1) Parts of type A are loaded into the top lane. 
//	 * 2) Parts of type B are loaded into the bottom lane.
//	 * 3) Parts of type C are loaded into the top lane.
//	 */
//	
//	public void testSendingVisionMessage1() {
////		feeder.msgLaneNeedsPart(p1,top); //eye to top
////		
////		feeder.pickAndExecuteAnAction();
////		
////		assertTrue(feeder.topLane.nestChecked == false);
////		
////		
////		
////		feeder.msgLaneNeedsPart(p2,bottom); //shoe to bottom
////
////		feeder.msgLaneNeedsPart(p3,top); //sword to top
//		
//	}
	
	
	
}









