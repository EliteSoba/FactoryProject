package factory.test;

import factory.NestAgent;
import factory.LaneAgent.MyPartState;
import factory.NestAgent.MyPart;
import factory.NestAgent.NestState;
import factory.Part;
import factory.test.mock.MockLane;
import junit.framework.TestCase;

public class NestTests extends TestCase {
	NestAgent nest;
	MockLane lane;
	Part p1;

	protected void setUp() throws Exception {
		nest = new NestAgent(null);
		lane = new MockLane("lane");
		nest.setLane(lane);
		nest.startThread();
		p1 = new Part("p1");
	}

	public void testPreconditions() {
		assertFalse(nest == null);
		assertEquals(nest.myParts.size(),0);
		assertEquals(nest.myLane,lane);
		assertEquals(nest.nestState,NestState.NORMAL);
	}

	public void testMsgDump() {
		nest.msgDump();

		assertEquals(nest.nestState,NestState.NEEDS_TO_DUMP); // is initial state correct

		nest.pickAndExecuteAnAction(); // call the scheduler

		assertEquals(nest.nestState,NestState.NORMAL); // is state correct after scheduler

	}

	public void testMsgYouNeedPart() {
		nest.msgYouNeedPart(p1);

		// was the part added to the myParts list:
		boolean myPartsContainsP1 = false;
		MyPart mp = null;
		for (MyPart p : nest.myParts)
		{
			if (p.pt == p1)
			{
				myPartsContainsP1 = true;
				mp = p;
			}
		}
		assertTrue(myPartsContainsP1); 
		//assertEquals(mp.state,MyPartState.NEEDED); // is initial state of MyPart correct
		
		nest.pickAndExecuteAnAction(); // call the scheduler
		
		// Check to see if lane receives appropriate message
    	assertTrue("Lane should have been told msgNestNeedsPart(). Event log: "
    			+ lane.log.toString(), 
    			lane.log.containsString("msgNestNeedsPart()"));
    	
    	//assertEquals(mp.state,MyPartState.REQUESTED); // is state correct after scheduler
		
	}
	
	public void testMsgNestHasStabilized() {
		nest.msgNestHasStabilized();
		
		assertTrue(nest.nestState == NestState.HAS_STABILIZED);
		
		nest.pickAndExecuteAnAction();
		
		// Check to see if lane receives appropriate message
    	assertTrue("Lane should have been told msgNestNeedsPart(). Event log: "
    			+ lane.log.toString(), 
    			lane.log.containsString("msgNestHasStabilized()"));
    	
    	assertTrue(nest.nestState == NestState.NORMAL);

	}
	
	
}




