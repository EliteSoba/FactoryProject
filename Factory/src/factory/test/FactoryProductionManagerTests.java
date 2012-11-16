package factory.test;

import factory.Part;
import factory.managers.FactoryProductionManager;
import factory.masterControl.MasterControl;
import junit.framework.TestCase;

public class FactoryProductionManagerTests extends TestCase {
	MasterControl mc;
	FactoryProductionManager fpm;
	Part p0;
	
	protected void setUp() throws Exception {
		mc = new MasterControl(true); // debug mode
		fpm = new FactoryProductionManager();
		fpm.connect(); // connects the client to the server 
		
		
		p0 = new Part("Ear");
		
	}
	
	public void testSingleFeederFunction() {
		
		// request a part for the lane
		mc.f0.msgLaneNeedsPart(p0, mc.l0t);
		
		mc.f0.pickAndExecuteAnAction(); // call agent's scheduler
		
		mc.f0.msgHereAreParts(p0);
		
		// should call DoSwitchLane() and then DoStartFeeding()
		
	}
}
