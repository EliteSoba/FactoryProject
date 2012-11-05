package factory.test;

import factory.FeederAgent;
import factory.GantryAgent;
import factory.LaneAgent;
import factory.NestAgent;
import factory.Part;
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
	
	
	public void testRequestNewPart() {
		// some initial setup:
		
		n0.msgYouNeedPart(p1); // PartsRobotAgent sends this message to the nest
		
		n0.pickAndExecuteAnAction(); // call nest's scheduler, nest should message the lane msgNestNeedsPart
		
		top.pickAndExecuteAnAction(); // call lane's scheduler, lane should message feeder msgLaneNeedsPart
		
		feeder.pickAndExecuteAnAction(); // call feeder's scheduler, it should message gantry msgFeederNeeds
		
		
	}
	
}




