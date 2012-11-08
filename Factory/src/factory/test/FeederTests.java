package factory.test;
/*
import factory.FeederAgent;
import factory.FeederAgent.DiverterState;
import factory.FeederAgent.FeederState;
import factory.FeederAgent.MyLaneState;
import factory.FeederAgent.MyPartRequest;
import factory.FeederAgent.MyPartRequestState;
import factory.Part;
import factory.interfaces.Lane;
import factory.interfaces.Nest;
import factory.test.mock.MockGantry;
import factory.test.mock.MockLane;
import factory.test.mock.MockNest;
import junit.framework.TestCase;

public class FeederTests extends TestCase{
	
	MyPartRequest mpr;
	FeederAgent feeder;
	MockLane top, bottom;
	MockGantry gantry;
	Part p1;
	/**
	 * Sets up the standard configuration for easy testing.
	 
	@Override
	protected void setUp() throws Exception {
		feeder = new FeederAgent("Feeder1",0);
		
		top = new MockLane("top");
		bottom = new MockLane("bottom");
		gantry = new MockGantry("gantry");
		p1 = new Part("p1");
		
		feeder.setGantry(gantry);
		feeder.setUpLanes(top, bottom);
	}
	
	/**
	 *  This test creates a feeder and tests its preconditions.
	 * 
	 
	public void testPreconditions() {
		// Makes sure there aren't any parts in the feeder initially.
		assertEquals(feeder.requestedParts.size(),0);
		assertEquals(feeder.feederSlot,0);
		System.out.println("feeder name = " + feeder.getName());

		assertEquals(feeder.getName(),"Feeder1");
		
	}
	
	/** 
	 * This test makes sure that the Feeder's lanes are getting set up properly.
	 
	public void testSetUpLanesAndGantry() {
		// These lanes were set up in the preconditions test
		assertEquals(feeder.topLane.lane.getName(),"top");
		assertEquals(feeder.bottomLane.lane.getName(),"bottom");
		
		// make sure topLane's state is initialized correctly
		assertEquals(feeder.topLane.state,FeederAgent.MyLaneState.EMPTY); 

		//assertEquals(feeder.gantry.getName(),"gantry");

	}
	
	
	
	/** Test the MESSAGES 
	
	public void testMsgEmptyNest(){
		// Some initial setup
		MockNest n = new MockNest("n");
		top.setNest(n);
		
		// send the message
		feeder.msgEmptyNest(n);
		
		
		// Check to see if lane receives appropriate message
    	assertTrue("Lane should have been told msgIncreaseAmplitude(). Event log: "
    			+ top.log.toString(), 
    			top.log.containsString("msgIncreaseAmplitude()"));
    	
    	// Check to see if lane's jamstate gets set correctly
    	assertEquals(feeder.topLane.jamState,FeederAgent.JamState.MIGHT_BE_JAMMED);
    	
    	
    	
    	// Now check to make sure that the feeder's scheduler is working properly
    	feeder.pickAndExecuteAnAction();  
    	// FURTHER TESTING HERE FOR THE NON-NORMATIVE CASE OF A PART BEING STUCK
    	
	}
	
	public void testMsgNestWasDumped() {
		
		// scenario #1: the feeder dumps a nest because
		// the nest didn't have any good parts
		feeder.state = FeederAgent.FeederState.CONTAINS_PARTS; 

		// send the message
		feeder.msgNestWasDumped(top);
		
		// Check to see if the lane's state changes properly
		assertEquals(feeder.topLane.state,FeederAgent.MyLaneState.NEST_SUCCESSFULLY_DUMPED);

		// Make sure that the Feeder's scheduler is working correctly
		feeder.pickAndExecuteAnAction();
		assertEquals(feeder.topLane.state,FeederAgent.MyLaneState.CONTAINS_PARTS);
		
		
		
		// scenario #2: the feeder dumps a nest because it is trying to purge the lane
		feeder.state = FeederAgent.FeederState.OK_TO_PURGE; 

		// send the message
		feeder.msgNestWasDumped(top);

		// Check to see if the lane's state changes properly
		assertEquals(feeder.topLane.state,FeederAgent.MyLaneState.NEST_SUCCESSFULLY_DUMPED);

		// Make sure that the Feeder's scheduler is working correctly
		feeder.pickAndExecuteAnAction();
		assertEquals(feeder.topLane.state,FeederAgent.MyLaneState.PURGING);

					
	}
	
	
	public void testMsgLaneNeedsPart() {
		// some initial setup
		feeder.state = FeederAgent.FeederState.EMPTY;
		
		
		// SCENARIO #1: The Feeder is initially empty and receives a request for parts.
		// send the message
		feeder.msgLaneNeedsPart(p1, top);
		
		// Initial Parts Request State
		for(FeederAgent.MyPartRequest pr : feeder.requestedParts)
		{
			if (pr.pt == p1)
			{
				assertEquals(pr.state,FeederAgent.MyPartRequestState.NEEDED);
			}
		}
		
		
		// Now make sure scheduler works
		feeder.pickAndExecuteAnAction();
		
		// Check to see if gantry receives appropriate message
    	assertTrue("Gantry should have been told msgFeederNeedsPart(...). Event log: "
    			+ gantry.log.toString(), 
    			gantry.log.containsString("msgFeederNeedsPart(...)"));
    	
    	// Check to see if the feeder's state gets set correctly
    	assertEquals(feeder.state,FeederAgent.FeederState.WAITING_FOR_PARTS);
		
    	// Parts Request State after scheduler
    			for(FeederAgent.MyPartRequest pr : feeder.requestedParts)
    			{
    				if (pr.pt == p1)
    				{
    					mpr = pr;
    					assertEquals(pr.state,FeederAgent.MyPartRequestState.ASKED_GANTRY);
    				}
    			}
    	
    			
    			

    			
    			
		// SCENARIO #2: The feeder is IMMEDIATELY sent a new request for parts, which happens often, as the feeder has 2 lanes.
    	Part p2 = new Part("p2");
		feeder.msgLaneNeedsPart(p2, top);

		// Initial Parts Request State
		for(FeederAgent.MyPartRequest pr : feeder.requestedParts)
		{
			if (pr.pt == p2)
			{
				assertEquals(pr.state,FeederAgent.MyPartRequestState.NEEDED);
			}
		}

		// Now make sure scheduler works
		feeder.pickAndExecuteAnAction();

		assertTrue("The gantry should not have received another message.",gantry.log.size() == 1);
		
		// Parts Request State after scheduler- basically this should not change 
		// from NEEDED b/c the feeder isn't ready to purge, and is thus not ready to ask the gantry for this part
		for(FeederAgent.MyPartRequest pr : feeder.requestedParts)
		{
			if (pr.pt == p2)
			{
				mpr = pr;
				assertEquals(pr.state,FeederAgent.MyPartRequestState.NEEDED);
			}
		}
		
		
		
		// SCENARIO #3: The feeder is ready to purge now
		// some initial setup:
		feeder.state = FeederAgent.FeederState.OK_TO_PURGE;
		mpr.state = FeederAgent.MyPartRequestState.NEEDED;
		feeder.topLane.part = mpr.pt;
		
		
		assertEquals(feeder.topLane.lane,mpr.lane);
		assertTrue(feeder.topLane.part != null);
		assertEquals(top,feeder.topLane.lane);

		// call the scheduler
		feeder.pickAndExecuteAnAction();
		
		// Check to see if the lane receives appropriate message
    	assertTrue("Lane should have been told msgPurge(). Event log: "
    			+ top.log.toString(), 
    			top.log.containsString("msgPurge()"));
    	
    	// and check the lane's state
    	assertEquals(feeder.topLane.state,FeederAgent.MyLaneState.PURGING);
		
		
	}
	
	
	public void testMsgHereAreParts() {
		// some initial setup
		feeder.state = FeederAgent.FeederState.WAITING_FOR_PARTS;
		feeder.addMyPartRequest(p1,top,FeederAgent.MyPartRequestState.ASKED_GANTRY);
		
		// PART #1: The feeder receives the parts he asked for from the gantry.
		feeder.msgHereAreParts(p1);
		
		// The gantry has DELIVERED the part
		for(FeederAgent.MyPartRequest pr : feeder.requestedParts)
		{
			if (pr.pt == p1)
			{
				mpr = pr; // set a reference to the MyPartRequest
				assertEquals(pr.state,FeederAgent.MyPartRequestState.DELIVERED);
			}
		}
		
		// make sure the scheduler works
		feeder.pickAndExecuteAnAction();
		
		// should remove the request from the list
		assertFalse(feeder.requestedParts.contains(mpr));
		
		// the current part that is being fed into the lane
		assertEquals(feeder.currentPart,mpr.pt);
		
		if (feeder.bottomLane.lane == mpr.lane){
			assertEquals(feeder.bottomLane.part,mpr.pt);
		}
		else {
			assertEquals(feeder.topLane.part,mpr.pt);
		}
		
		// make sure the feeder's state is updating correctly
		assertEquals(feeder.state,FeederAgent.FeederState.SHOULD_START_FEEDING);
		
		
		
		
		// PART #2: The feeder receives the parts he asked for from the gantry.
		feeder.diverter = DiverterState.FEEDING_BOTTOM; // to test the switching of the lane diverter
		
		feeder.pickAndExecuteAnAction();
		
		assertEquals(feeder.diverter,DiverterState.FEEDING_TOP); // switches to feed the top because that is the lane we are feeding
		
		
		// PART #3: After the okayToPurgeTimer goes off, the feeder receives a request for new parts.
		feeder.state = FeederState.OK_TO_PURGE; // this gets called when the okayToPurgeTimer goes off
		
	}
	
	
	public void testMsgBadNest() {
		
		feeder.msgBadNest(top.getNest()); // send the msg
		
		assertEquals(feeder.topLane.state,MyLaneState.BAD_NEST); // check to see if initial state assignment works
		
		// check to see if the scheduler works
		feeder.pickAndExecuteAnAction();
		
		// make sure the topLane receives the right message
    	assertTrue("Lane should have been told msgDumpNest(). Event log: "
    			+ top.log.toString(), 
    			top.log.containsString("msgDumpNest()"));
		
    	// and that the lane's state changes properly
		assertEquals(feeder.topLane.state,MyLaneState.TOLD_NEST_TO_DUMP); 
				
		
	}
	
	
}
*/








