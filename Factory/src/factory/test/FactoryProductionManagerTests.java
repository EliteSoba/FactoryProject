//package factory.test;
//
//import factory.*;
//import factory.managers.FactoryProductionManager;
//import factory.masterControl.MasterControl;
//import junit.framework.TestCase;
//
//public class FactoryProductionManagerTests extends TestCase {
//	MasterControl mc;
//	FactoryProductionManager fpm;
//	Part p0;
//	
//	protected void setUp() throws Exception {
//		fpm = new FactoryProductionManager();
//		System.out.println("1");
//		mc = new MasterControl(true); // debug mode
//		System.out.println("2");
//		System.out.println("3");
//		System.out.println("4");
//		p0 = new Part();
//		
//	}
//	
//	public void testSingleFeederFunction() {
//		System.out.println("started single feeder function test");
//		
//		// request a part for the lane
//		mc.f0.msgLaneNeedsPart(p0, mc.l0t);
//		
//		mc.f0.pickAndExecuteAnAction(); // call agent's scheduler
//		
//		mc.f0.msgHereAreParts(p0);
//		
//		// should call DoSwitchLane() and then DoStartFeeding()
//		
//	}
//}
