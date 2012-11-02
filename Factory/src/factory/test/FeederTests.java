package factory.test;

import factory.FeederAgent;
import junit.framework.TestCase;

public class FeederTests extends TestCase{
	
	FeederAgent feeder;
	/**
	 * Sets up the standard configuration for easy testing.
	 */
	@Override
	protected void setUp() throws Exception {
		feeder = new FeederAgent("Feeder1");
	}
	
	/**
	 *  This test creates a feeder and tests its preconditions.
	 * 
	 */
	public void testPreconditions() {
		
		// Makes sure there aren't any parts in the feeder initially.
		assertEquals(feeder.requestedParts.size(),0);
		
		
		
	}
}
