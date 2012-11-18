package factory.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;

import factory.KitConfig;
import factory.Part;
import factory.PartsRobotAgent;
import factory.PartsRobotAgent.KitConfigState;
import factory.PartsRobotAgent.MyNest;
import factory.PartsRobotAgent.NestState;
import factory.PartsRobotAgent.PartsRobotPositions;
import factory.PartsRobotAgent.StandState;

import factory.interfaces.Nest;
import factory.test.mock.*;

public class PartsRobotTests extends TestCase {

	PartsRobotAgent partsRobotAgent;
	MockVision vision;
	MockFCS FCS;
	MockStand stand;
	
	MockNest nestZero;
	MockNest nestOne;
	MockNest nestTwo;
	MockNest nestThree;
	MockNest nestFour;
	MockNest nestFive;
	MockNest nestSix;
	MockNest nestSeven;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		// Instantiate Mocks
		vision = new MockVision("MockVision");
		FCS = new MockFCS("MockFCS");
		stand = new MockStand("MockStand");

		nestZero = new MockNest("MockNestZero");
		nestOne = new MockNest("MockNestOne");
		nestTwo = new MockNest("MockNestTwo");
		nestThree = new MockNest("MockNestThree");
		nestFour = new MockNest("MockNestFour");
		nestFive = new MockNest("MockNestFive");
		nestSix = new MockNest("MockNestSix");
		nestSeven = new MockNest("MockNestSeven");
		
		List<Nest> nests = Collections.synchronizedList(new ArrayList<Nest>());

		for(int i = 0; i < 8; i++){
			nests.add(new MockNest("MockNest" + i));
		}
		
		// Instantiate PartRobotAgent
		partsRobotAgent = new PartsRobotAgent(null, FCS, vision, stand, nests);
	
	}

	/**
	 * Check that all passed variables are assigned correctly
	 */
	@Test
	public void testConstructor() {
		assertEquals(partsRobotAgent.server, null);
		assertEquals(partsRobotAgent.fcs, FCS);
		assertEquals(partsRobotAgent.vision, vision);
		assertEquals(partsRobotAgent.stand, stand);
		assertEquals(partsRobotAgent.standState, StandState.DOING_NOTHING);

		for(int i = 0; i < 8; i++){
			assertEquals(partsRobotAgent.nests.get(i).nest.getNestName() , "MockNest" + i);
			assertEquals(partsRobotAgent.nests.get(i).partCoordinate , -1);
			assertEquals(partsRobotAgent.nests.get(i).state , NestState.WAITING);
			assertEquals(partsRobotAgent.nests.get(i).part , null);
		}

		assertEquals(partsRobotAgent.currentKitConfiguration, null);
		assertEquals(partsRobotAgent.currentKitConfigurationState, KitConfigState.EMPTY);
		assertEquals(partsRobotAgent.position, PartsRobotPositions.CENTER);
		assertEquals(partsRobotAgent.topSlot, null);
		assertEquals(partsRobotAgent.bottomSlot, null);
		
	}

	/**
	 * At the beginning, if pickAndExecuteAnAction() is called, nothing should change
	 */
	@Test
	public void testSchedulerWithNoAction() {
		
		partsRobotAgent.pickAndExecuteAnAction();
		
		assertEquals(partsRobotAgent.server, null);
		assertEquals(partsRobotAgent.fcs, FCS);
		assertEquals(partsRobotAgent.vision, vision);
		assertEquals(partsRobotAgent.stand, stand);
		assertEquals(partsRobotAgent.standState, StandState.DOING_NOTHING);

		for(int i = 0; i < 8; i++){
			assertEquals(partsRobotAgent.nests.get(i).nest.getNestName() , "MockNest" + i);
			assertEquals(partsRobotAgent.nests.get(i).partCoordinate , -1);
			assertEquals(partsRobotAgent.nests.get(i).state , NestState.WAITING);
			assertEquals(partsRobotAgent.nests.get(i).part , null);
		}

		assertEquals(partsRobotAgent.currentKitConfiguration, null);
		assertEquals(partsRobotAgent.currentKitConfigurationState, KitConfigState.EMPTY);
		assertEquals(partsRobotAgent.position, PartsRobotPositions.CENTER);
		assertEquals(partsRobotAgent.topSlot, null);
		assertEquals(partsRobotAgent.bottomSlot, null);
		
	}

	/**
	 * Test passing the first configuration 
	 */
	@Test
	public void testFirstKitConfiguration() {
		
		KitConfig firstKit = new KitConfig();
		firstKit.listOfParts.add(new Part("Part1"));
		firstKit.listOfParts.add(new Part("Part2"));
		firstKit.listOfParts.add(new Part("Part3"));
		firstKit.listOfParts.add(new Part("Part4"));
		firstKit.listOfParts.add(new Part("Part5"));
		firstKit.listOfParts.add(new Part("Part6"));
		firstKit.listOfParts.add(new Part("Part7"));
		firstKit.listOfParts.add(new Part("Part8"));
		
		// Send the message that the FCS would send
		partsRobotAgent.msgMakeKit(firstKit);

		// The KitConfig should be stored in currentKitConfiguration
		assertEquals(partsRobotAgent.currentKitConfiguration, firstKit);
		
		// currentKitConfigurationState should be REQUESTED
		assertEquals(partsRobotAgent.currentKitConfigurationState, KitConfigState.REQUESTED);
	
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// The new Kit Configuration should be in production
		assertEquals(partsRobotAgent.currentKitConfigurationState, KitConfigState.PRODUCING);

		// Stand should have receive message to empty stand
		assertTrue(stand.log.containsString("msgClearStand() received from the PartsRobot"));
		
		// Slots should be empty
		assertEquals(partsRobotAgent.topSlot, null);
		assertEquals(partsRobotAgent.bottomSlot, null);
		
		// Nest one have part one
		for(int i = 0; i < 8; i++){
			assertEquals(partsRobotAgent.nests.get(i).nest.getNestName() , "MockNest" + i);
			assertEquals(partsRobotAgent.nests.get(i).partCoordinate , -1);
			assertEquals(partsRobotAgent.nests.get(i).state , NestState.WAITING);
			assertEquals(partsRobotAgent.nests.get(i).part , firstKit.listOfParts.get(i));
			assertTrue(((MockNest)partsRobotAgent.nests.get(i).nest).log.containsString("msgYouNeedPart"));
		}

	}
	
	/**
	 * Test passing the second configuration 
	 */
	@Test
	public void testSecondKitConfiguration() {
		
		KitConfig firstKit = new KitConfig();
		firstKit.listOfParts.add(new Part("Part1"));
		firstKit.listOfParts.add(new Part("Part2"));
		firstKit.listOfParts.add(new Part("Part3"));
		firstKit.listOfParts.add(new Part("Part4"));
		firstKit.listOfParts.add(new Part("Part5"));
		firstKit.listOfParts.add(new Part("Part6"));
		firstKit.listOfParts.add(new Part("Part7"));
		firstKit.listOfParts.add(new Part("Part8"));
		
		// Send the message that the FCS would send
		partsRobotAgent.msgMakeKit(firstKit);

		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		KitConfig secondKit = new KitConfig();
		secondKit.listOfParts.add(new Part("Part1"));
		secondKit.listOfParts.add(new Part("Part10"));
		secondKit.listOfParts.add(new Part("Part3"));
		secondKit.listOfParts.add(new Part("Part15"));
		secondKit.listOfParts.add(new Part("Part5"));
		secondKit.listOfParts.add(new Part("Part16"));
		secondKit.listOfParts.add(new Part("Part7"));
		secondKit.listOfParts.add(new Part("Part48"));
		
		// Send the message that the FCS would send
		partsRobotAgent.msgMakeKit(secondKit);

		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// The new Kit Configuration should be in production
		assertEquals(partsRobotAgent.currentKitConfigurationState, KitConfigState.PRODUCING);

		// Stand should have receive message to empty stand
		assertTrue(stand.log.containsString("msgClearStand() received from the PartsRobot"));
		
		// Slots should be empty
		assertEquals(partsRobotAgent.topSlot, null);
		assertEquals(partsRobotAgent.bottomSlot, null);
		
		// Nest one have part one
		for(int i = 0; i < 8; i++){
			assertEquals(partsRobotAgent.nests.get(i).nest.getNestName() , "MockNest" + i);
			assertEquals(partsRobotAgent.nests.get(i).partCoordinate , -1);
			assertEquals(partsRobotAgent.nests.get(i).state , NestState.WAITING);
			assertEquals(partsRobotAgent.nests.get(i).part , secondKit.listOfParts.get(i));
			// Only the ones that changed should be messaged	
			if(i == 1 || i == 3 || i == 4 || i == 7){
				assertTrue(((MockNest)partsRobotAgent.nests.get(i).nest).log.containsString("msgYouNeedPart"));
			}
		}

	}


	
}










