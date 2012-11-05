package factory.test;

import factory.FeederAgent;
import factory.FeederAgent.MyPartRequest;
import factory.GantryAgent;
import factory.LaneAgent;
import factory.NestAgent;
import factory.Part;
import factory.LaneAgent.MyPartState;
import factory.NestAgent.MyPart;
import junit.framework.TestCase;

public class FeederManagementTests extends TestCase {
	FeederAgent feeder;
	LaneAgent top, bottom;
	NestAgent n0,n1,n2,n3;
	GantryAgent gantry;
	Part p1;

	/**
	 * Sets up the standard configuration for easy testing.
	 */
	@Override
	protected void setUp() throws Exception {
		feeder = new FeederAgent("feeder",0);
		top = new LaneAgent();
		bottom = new LaneAgent();
		gantry = new GantryAgent();

		n0 = new NestAgent();
		n1 = new NestAgent();
		n2 = new NestAgent();
		n3 = new NestAgent();
		p1 = new Part("Circle");


		n0.setLane(top);
		top.setNest(n0);
		top.setFeeder(feeder);
		feeder.setUpLanes(top, bottom);
		feeder.setGantry(gantry);

	}
	
	public void testPreconditions() {
		//test relevant preconditions
		assertEquals(top.myParts.size(),0); 
		assertEquals(gantry.myBins.size(),0);

	}


	public void testSingleRequestForANewPart() {

		n0.msgYouNeedPart(p1); // PartsRobotAgent sends this message to the nest
		n0.pickAndExecuteAnAction(); // call nest's scheduler, nest should message the lane msgNestNeedsPart


		top.pickAndExecuteAnAction(); // lane should msgLaneNeedsPart -> feeder 
		assertEquals(top.myParts.size(),1); // was the part added to the lane's myParts list
		
		
		feeder.pickAndExecuteAnAction(); // feeder should msgFeederNeeds -> gantry 
    	assertEquals(feeder.state,FeederAgent.FeederState.WAITING_FOR_PARTS);

//msgFeederNeedsPart
		gantry.pickAndExecuteAnAction(); // gantry should msgHereAreParts -> feeder 
		assertEquals(gantry.myBins.size(),1); // was the mybin (request for a part) added to the gantry's list

		
		feeder.pickAndExecuteAnAction(); // feeder should have p1 as its current parts
//		assertEquals(feeder.currentPart,p1);

	}

}




