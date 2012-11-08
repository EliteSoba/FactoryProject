package factory.test;
/*
import factory.LaneAgent;
import factory.LaneAgent.MyPart;
import factory.LaneAgent.MyPartState;
import factory.LaneAgent.NestState;
import factory.Part;
import factory.test.mock.LoggedEvent;
import factory.test.mock.MockFeeder;
import factory.test.mock.MockNest;
import junit.framework.TestCase;

public class LaneTests extends TestCase {
	LaneAgent lane;
	MockNest nest;
	MockFeeder feeder;
	Part p1;
	
	protected void setUp() throws Exception {
		lane = new LaneAgent();
		feeder = new MockFeeder("feeder");
		nest = new MockNest("nest");
		lane.setFeeder(feeder);
		lane.setNest(nest);
		
		p1 = new Part("p1");
	}

	public void testPreconditions() {
		assertFalse(lane == null);
		assertEquals(lane.myParts.size(),0);
		//assertEquals(lane.state,LaneState.NORMAL);
		assertEquals(lane.amplitude,5);
		assertEquals(lane.myNest,nest);
		assertEquals(lane.myFeeder,feeder);
		
	}
	
	public void testMsgIncreaseAmplitude() {
		lane.msgIncreaseAmplitude(); // send the message
		
		assertEquals(lane.nestState,NestState.NEEDS_TO_INCREASE_AMPLITUDE); // is the nest state set correctly in the message?
		
		lane.pickAndExecuteAnAction(); // call the scheduler
		
		assertEquals(lane.amplitude,10); // did the amplitude value actually increase?
		
		assertEquals(lane.nestState,NestState.NORMAL); // did the nest state change properly?
	}
	
	
	public void testMsgNestNeedsPart() {
		assertFalse(lane.myParts.contains(p1));
		lane.msgNestNeedsPart(p1);
		
		
		// was the part added to the myParts list:
		boolean myPartsContainsP1 = false;
		for (MyPart p : lane.myParts)
		{
			if (p.pt == p1)
				myPartsContainsP1 = true;
		}
		assertTrue(myPartsContainsP1); 
		
		
		assertEquals(lane.myParts.get(0).state,MyPartState.NEEDED); // does it have the correct initial state
		
		lane.pickAndExecuteAnAction(); // call the scheduler
		
		// Check to see if feeder receives appropriate message
    	assertTrue("Feeder should have been told msgLaneNeedsPart(). Event log: "
    			+ feeder.log.toString(), 
    			feeder.log.containsString("msgLaneNeedsPart()"));
    	
    	assertEquals(lane.myParts.get(0).state,MyPartState.REQUESTED); // does it have the correct state after the scheduler
	}
	
	public void testMsgDumpNest() {
		lane.msgDumpNest();
		
		assertEquals(lane.nestState,NestState.NEEDS_TO_DUMP); // does it have the correct inital state
		
		lane.pickAndExecuteAnAction(); // call the scheduler
		
		assertEquals(lane.nestState,NestState.WAITING_FOR_DUMP_CONFIRMATION); // does it have the correct state after the scheduler
		
		// Check to see if the nest receives the appropriate message
    	assertTrue("Nest should have been told msgDump(). Event log: "
    			+ nest.log.toString(), 
    			nest.log.containsString("msgDump()"));
	}
	
	public void testMsgNestWasDumped() {
		lane.msgNestWasDumped();
		
		assertEquals(lane.nestState,NestState.NEST_WAS_DUMPED); // does it have the correct inital state

		lane.pickAndExecuteAnAction(); // call the scheduler
		
		assertEquals(lane.nestState,NestState.NORMAL); // does it have the correct state after the scheduler
	
		// Check to see if the feeder receives the appropriate message
    	assertTrue("Feeder should have been told msgNestWasDumped(). Event log: "
    			+ feeder.log.toString(), 
    			feeder.log.containsString("msgNestWasDumped()"));
	
	}

}

*/










