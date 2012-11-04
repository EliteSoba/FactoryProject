package factory.test;

import factory.FeederAgent;
import factory.FeederAgent.MyLaneState;
import factory.interfaces.Lane;
import factory.test.mock.MockLane;
import factory.test.mock.MockNest;
import junit.framework.TestCase;

public class FeederTests extends TestCase{
	
	FeederAgent feeder;
	MockLane top, bottom;
	/**
	 * Sets up the standard configuration for easy testing.
	 */
	@Override
	protected void setUp() throws Exception {
		feeder = new FeederAgent("Feeder1");
		
		top = new MockLane("top");
		bottom = new MockLane("bottom");
		
		feeder.setUpLanes(top, bottom);
	}
	
	/**
	 *  This test creates a feeder and tests its preconditions.
	 * 
	 */
	public void testPreconditions() {
		
		// Makes sure there aren't any parts in the feeder initially.
		assertEquals(feeder.requestedParts.size(),0);
		System.out.println("feeder name = " + feeder.getName());

		assertEquals(feeder.getName(),"Feeder1");
		
	}
	
	/** 
	 * This test makes sure that the Feeder's lanes are getting set up properly.
	 */
	public void testSetUpLanes() {
		// These lanes were set up in the preconditions test
		assertEquals(feeder.topLane.lane.getName(),"top");
		assertEquals(feeder.bottomLane.lane.getName(),"bottom");
	}
	
	
	
	/** Test the MESSAGES **/
	
	public void testMsgEmptyNest(){
		// Some initial setup
		MockNest n = new MockNest("n");
		top.setNest(n);
		
		// send the message
		feeder.msgEmptyNest(n);
		
		
		// Check to see if lane receives appropriate message
    	assertTrue("Lane should have been told msgIncreaseAmplitude(). Event log: "
    			+ top.log.toString(), 
    			top.log.containsString("msgIncreaseAmplitude"));
    	
    	// Check to see if lane's jamstate gets set correctly
    	assertEquals(feeder.topLane.jamState,FeederAgent.JamState.MIGHT_BE_JAMMED);
    	
    	
    	
    	// Now check to make sure that the feeder's scheduler is working properly
    	feeder.pickAndExecuteAnAction();  
    	// FURTHER TESTING HERE FOR NON-NORMATIVE CASE
    	
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
		
		
		
		// scenario #2: the feeder dumps a nest because
		// it is trying to purge the lane
		feeder.state = FeederAgent.FeederState.OK_TO_PURGE; 

		// send the message
		feeder.msgNestWasDumped(top);

		// Check to see if the lane's state changes properly
		assertEquals(feeder.topLane.state,FeederAgent.MyLaneState.NEST_SUCCESSFULLY_DUMPED);

		// Make sure that the Feeder's scheduler is working correctly
		feeder.pickAndExecuteAnAction();
		assertEquals(feeder.topLane.state,FeederAgent.MyLaneState.PURGING);
				
	}
}









