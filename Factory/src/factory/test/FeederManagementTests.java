package factory.test;
/*
import factory.FeederAgent;
import factory.FeederAgent.DiverterState;
import factory.FeederAgent.FeederState;
import factory.FeederAgent.MyPartRequest;
import factory.FeederAgent.MyPartRequestState;
import factory.GantryAgent;
import factory.LaneAgent;
import factory.NestAgent;
import factory.Part;
import factory.LaneAgent.MyPartState;
import factory.NestAgent.MyPart;
import factory.test.mock.MockGantry;
import junit.framework.TestCase;

public class FeederManagementTests extends TestCase {
	FeederAgent feeder;
	LaneAgent top, bottom;
	NestAgent n0,n1,n2,n3;
	GantryAgent gantry;
	MockGantry mockGantry;
	Part p1,p2;

	/**
	 * Sets up the standard configuration for easy testing.
	 
	@Override
	protected void setUp() throws Exception {
		feeder = new FeederAgent("feeder",0);
		top = new LaneAgent();
		bottom = new LaneAgent();
		gantry = new GantryAgent();
		mockGantry = new MockGantry("mockGantry");

		n0 = new NestAgent();
		n1 = new NestAgent();
		n2 = new NestAgent();
		n3 = new NestAgent();
		p1 = new Part("Circle");
		p2 = new Part("Square");


		n0.setLane(top);
		top.setNest(n0);
		top.setFeeder(feeder);

		feeder.setUpLanes(top, bottom);
		feeder.setGantry(mockGantry); //for now
		feeder.diverter = DiverterState.FEEDING_BOTTOM;

	}
	
	public void testPreconditions() {
		//test relevant preconditions
		assertEquals(top.myParts.size(),0); 
		assertEquals(gantry.myBins.size(),0);

	}

	/* CASES TO TEST:
	* 1) Single request.
	* 2) Two different part requests, different lanes/
	* 3) Two different part requests, same lane.
	* 4) Two of the same kind of part, different lanes.
	* 5) Two of the same kind of part, same lane.
	
	
	public void testSingleRequestForANewPart() {

		n0.msgYouNeedPart(p1); // PartsRobotAgent sends this message to the nest
		n0.pickAndExecuteAnAction(); // call nest's scheduler, nest should message the lane msgNestNeedsPart


		top.pickAndExecuteAnAction(); // lane should msgLaneNeedsPart -> feeder 
		assertEquals(top.myParts.size(),1); // was the part added to the lane's myParts list
		
		
		feeder.pickAndExecuteAnAction(); // feeder should msgFeederNeeds -> gantry 
    	assertEquals(feeder.state,FeederAgent.FeederState.WAITING_FOR_PARTS);

    	
    	// We are going to use mock gantrys instead
		gantry.pickAndExecuteAnAction(); // gantry should msgHereAreParts -> feeder 
		assertEquals(gantry.myBins.size(),1); // was the mybin (request for a part) added to the gantry's list
		
    	
    	feeder.msgHereAreParts(p1);
    	MyPartRequest mpr = null;
    	for (MyPartRequest p : feeder.requestedParts) 
    	{
    		if (p.pt == p1)
    		{
    			mpr = p;
    			assertEquals(p.state,MyPartRequestState.DELIVERED); // is this mapping correctly to its part request?
    		}
    	}
    	
    	assertFalse(mpr == null);
    	
    	feeder.pickAndExecuteAnAction(); // feeder should have p1 as its current parts
		assertEquals(feeder.currentPart,p1);

		feeder.pickAndExecuteAnAction(); // feeder should process the part that it just received
		assertEquals(feeder.topLane.part,p1);
		assertEquals(feeder.state,FeederState.SHOULD_START_FEEDING);	
		assertEquals(feeder.diverter,DiverterState.FEEDING_TOP); // test to see if the diverter switched correctly
		assertFalse(feeder.requestedParts.contains(mpr));
		
		
	}
	
	public void testTwoDifferentRequestsForDifferentLanes() {
		testSingleRequestForANewPart(); // 1 part first
		
		// some initial set up
		n1.setLane(bottom);
		bottom.setNest(n1);
		bottom.setFeeder(feeder);

		
		n1.msgYouNeedPart(p2); // PartsRobotAgent sends this message to the nest
		n1.pickAndExecuteAnAction(); // call nest's scheduler, nest should message the lane msgNestNeedsPart

		// ... 
	}

}*/




