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
import factory.PartsRobotAgent.NestState;
import factory.PartsRobotAgent.PartsRobotPositions;
import factory.PartsRobotAgent.SlotState;
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
		System.out.println("Running test testConstructor()");
		assertEquals(partsRobotAgent.server, null);
		assertEquals(partsRobotAgent.fcs, FCS);
		assertEquals(partsRobotAgent.vision, vision);
		assertEquals(partsRobotAgent.stand, stand);
		assertEquals(partsRobotAgent.standState, StandState.DOING_NOTHING);

		for(int i = 0; i < 8; i++){
			assertEquals(partsRobotAgent.nests.get(i).nest.getNestName() , "MockNest" + i);
			assertEquals(partsRobotAgent.nests.get(i).partCoordinate , -1);
			assertEquals(partsRobotAgent.nests.get(i).state , NestState.DOING_NOTHING);
			assertEquals(partsRobotAgent.nests.get(i).part , null);
		}

		assertEquals(partsRobotAgent.currentKitConfiguration, null);
		assertEquals(partsRobotAgent.currentKitConfigurationState, KitConfigState.EMPTY);
		assertEquals(partsRobotAgent.position, PartsRobotPositions.CENTER);
		assertEquals(partsRobotAgent.topSlot, null);
		assertEquals(partsRobotAgent.bottomSlot, null);
		assertEquals(partsRobotAgent.topSlotState, SlotState.EMPTY);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.EMPTY);

		assertEquals(partsRobotAgent.armOne, null);
		assertEquals(partsRobotAgent.armTwo, null);
		assertEquals(partsRobotAgent.armThree, null);
		assertEquals(partsRobotAgent.armFour, null);

	}

	/**
	 * At the beginning, if pickAndExecuteAnAction() is called, nothing should change
	 */
	@Test
	public void testSchedulerWithNoAction() {
		System.out.println("Running test testSchedulerWithNoAction()");
		
		partsRobotAgent.pickAndExecuteAnAction();
		
		assertEquals(partsRobotAgent.server, null);
		assertEquals(partsRobotAgent.fcs, FCS);
		assertEquals(partsRobotAgent.vision, vision);
		assertEquals(partsRobotAgent.stand, stand);
		assertEquals(partsRobotAgent.standState, StandState.DOING_NOTHING);

		for(int i = 0; i < 8; i++){
			assertEquals(partsRobotAgent.nests.get(i).nest.getNestName() , "MockNest" + i);
			assertEquals(partsRobotAgent.nests.get(i).partCoordinate , -1);
			assertEquals(partsRobotAgent.nests.get(i).state , NestState.DOING_NOTHING);
			assertEquals(partsRobotAgent.nests.get(i).part , null);
		}

		assertEquals(partsRobotAgent.currentKitConfiguration, null);
		assertEquals(partsRobotAgent.currentKitConfigurationState, KitConfigState.EMPTY);
		assertEquals(partsRobotAgent.position, PartsRobotPositions.CENTER);
		assertEquals(partsRobotAgent.topSlot, null);
		assertEquals(partsRobotAgent.bottomSlot, null);

		assertEquals(partsRobotAgent.topSlotState, SlotState.EMPTY);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.EMPTY);

		assertEquals(partsRobotAgent.armOne, null);
		assertEquals(partsRobotAgent.armTwo, null);
		assertEquals(partsRobotAgent.armThree, null);
		assertEquals(partsRobotAgent.armFour, null);
	}

	/**
	 * Test passing the first configuration 
	 */
	@Test
	public void testFirstKitConfiguration() {
		System.out.println("Running test testFirstKitConfiguration()");
		
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
		assertEquals(partsRobotAgent.topSlotState, SlotState.EMPTY);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.EMPTY);
		
		// Nest one have part one
		for(int i = 0; i < 8; i++){
			assertEquals(partsRobotAgent.nests.get(i).nest.getNestName() , "MockNest" + i);
			assertEquals(partsRobotAgent.nests.get(i).partCoordinate , -1);
			assertEquals(partsRobotAgent.nests.get(i).state , NestState.DOING_NOTHING);
			assertEquals(partsRobotAgent.nests.get(i).part , firstKit.listOfParts.get(i));
			assertTrue(((MockNest)partsRobotAgent.nests.get(i).nest).log.containsString("msgYouNeedPart"));
		}

		// Arms should be empty
		assertEquals(partsRobotAgent.armOne, null);
		assertEquals(partsRobotAgent.armTwo, null);
		assertEquals(partsRobotAgent.armThree, null);
		assertEquals(partsRobotAgent.armFour, null);
	}

	/**
	 * Test passing the second and third configurations
	 */
	@Test
	public void testSecondKitConfiguration() {
		System.out.println("Running test testSecondKitConfiguration()");
		
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
		assertEquals(partsRobotAgent.topSlotState, SlotState.EMPTY);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.EMPTY);
		
		// Nest one have part one
		for(int i = 0; i < 8; i++){
			assertEquals(partsRobotAgent.nests.get(i).nest.getNestName() , "MockNest" + i);
			assertEquals(partsRobotAgent.nests.get(i).partCoordinate , -1);
			assertEquals(partsRobotAgent.nests.get(i).state , NestState.DOING_NOTHING);
			// Only the ones that changed should be messages
			switch (i) {
	            case 0:  
					assertTrue(((MockNest)partsRobotAgent.nests.get(i).nest).log.size() == 1 );
	                break;
	            case 1:  
					assertTrue(((MockNest)partsRobotAgent.nests.get(i).nest).log.containsString("msgYouNeedPart(Part10)"));
	                break;
	            case 2:  
					assertTrue(((MockNest)partsRobotAgent.nests.get(i).nest).log.size() == 1 );
	                break;
	            case 3:  
					assertTrue(((MockNest)partsRobotAgent.nests.get(i).nest).log.containsString("msgYouNeedPart(Part15)"));
	                break;
	            case 4:  
					assertTrue(((MockNest)partsRobotAgent.nests.get(i).nest).log.size() == 1 );
	                break;
	            case 5:  
					assertTrue(((MockNest)partsRobotAgent.nests.get(i).nest).log.containsString("msgYouNeedPart(Part16)"));
	                break;
	            case 6:  
					assertTrue(((MockNest)partsRobotAgent.nests.get(i).nest).log.size() == 1 );
	                break;
	            case 7:  
					assertTrue(((MockNest)partsRobotAgent.nests.get(i).nest).log.containsString("msgYouNeedPart(Part48)"));
	                break;
	        }
		}

		// Arms should be empty
		assertEquals(partsRobotAgent.armOne, null);
		assertEquals(partsRobotAgent.armTwo, null);
		assertEquals(partsRobotAgent.armThree, null);
		assertEquals(partsRobotAgent.armFour, null);


		KitConfig thirdKit = new KitConfig();
		thirdKit.listOfParts.add(new Part("Part99"));
		thirdKit.listOfParts.add(new Part("Part98"));
		thirdKit.listOfParts.add(new Part("Part97"));
		thirdKit.listOfParts.add(new Part("Part96"));
		thirdKit.listOfParts.add(new Part("Part95"));
		thirdKit.listOfParts.add(new Part("Part94"));
		thirdKit.listOfParts.add(new Part("Part93"));
		thirdKit.listOfParts.add(new Part("Part92"));
		
		// Send the message that the FCS would send
		partsRobotAgent.msgMakeKit(thirdKit);

		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// The new Kit Configuration should be in production
		assertEquals(partsRobotAgent.currentKitConfigurationState, KitConfigState.PRODUCING);

		// Stand should have receive message to empty stand
		assertTrue(stand.log.containsString("msgClearStand() received from the PartsRobot"));
		
		// Slots should be empty
		assertEquals(partsRobotAgent.topSlot, null);
		assertEquals(partsRobotAgent.bottomSlot, null);
		assertEquals(partsRobotAgent.topSlotState, SlotState.EMPTY);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.EMPTY);
		
		// Nest one have part one
		for(int i = 0; i < 8; i++){
			assertEquals(partsRobotAgent.nests.get(i).nest.getNestName() , "MockNest" + i);
			assertEquals(partsRobotAgent.nests.get(i).partCoordinate , -1);
			assertEquals(partsRobotAgent.nests.get(i).state , NestState.DOING_NOTHING);
			// Both should be the same sin
			assertEquals(partsRobotAgent.nests.get(i).part , thirdKit.listOfParts.get(i));
		}

		// Arms should be empty
		assertEquals(partsRobotAgent.armOne, null);
		assertEquals(partsRobotAgent.armTwo, null);
		assertEquals(partsRobotAgent.armThree, null);
		assertEquals(partsRobotAgent.armFour, null);
	}

	/**
	 * Test when the Stand tells us to build kit at a particular slot
	 */
	@Test
	public void testBuildKitAtSlotWithNoKitConfig() {
		System.out.println("Running test testBuildKitAtSlotWithNoKitConfig()");

		assertEquals(partsRobotAgent.topSlot, null);
		assertEquals(partsRobotAgent.bottomSlot, null);
		assertEquals(partsRobotAgent.topSlotState, SlotState.EMPTY);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.EMPTY);
		
		// Send the message that the Stand would send
		partsRobotAgent.msgBuildKitAtSlot("topSlot");

		// The state should be at Requested
		assertEquals(partsRobotAgent.topSlotState, SlotState.BUILD_REQUESTED);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.EMPTY);
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();

		// Since the kitConfiguration is empty, the state should still be at Requested
		assertEquals(partsRobotAgent.topSlotState, SlotState.BUILD_REQUESTED);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.EMPTY);

		// Send the message that the Stand would send
		partsRobotAgent.msgBuildKitAtSlot("bottomSlot");

		// Since the kitConfiguration is empty, the state should still be at Requested
		assertEquals(partsRobotAgent.topSlotState, SlotState.BUILD_REQUESTED);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.BUILD_REQUESTED);

		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();

		// Since the kitConfiguration is empty, the state should still be at Requested
		assertEquals(partsRobotAgent.topSlotState, SlotState.BUILD_REQUESTED);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.BUILD_REQUESTED);
		
	
	}
	
	@Test
	public void testBuildKitAtSlotWithKitConfig() {
		System.out.println("Running test testBuildKitAtSlotWithKitConfig()");
		// Check initial conditions
		assertEquals(partsRobotAgent.topSlot, null);
		assertEquals(partsRobotAgent.bottomSlot, null);
		assertEquals(partsRobotAgent.topSlotState, SlotState.EMPTY);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.EMPTY);
		
		// Build KitConfig and send it
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
		
		// Send the message that the Stand would send
		partsRobotAgent.msgBuildKitAtSlot("topSlot");

		// The state should be at Requested
		assertEquals(partsRobotAgent.topSlotState, SlotState.BUILD_REQUESTED);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.EMPTY);
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();

		// Since the kitConfiguration is not empty, the state should be building
		assertEquals(partsRobotAgent.topSlotState, SlotState.BUILDING);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.EMPTY);

		// Send the message that the FCS would send
		partsRobotAgent.msgBuildKitAtSlot("bottomSlot");

		// The bottomSLot state should be at Requested
		assertEquals(partsRobotAgent.topSlotState, SlotState.BUILDING);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.BUILD_REQUESTED);
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();

		// Since the kitConfiguration is empty, the state should still be at Requested
		assertEquals(partsRobotAgent.topSlotState, SlotState.BUILDING);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.BUILDING);
		
	
	}
	
	@Test
	public void testBuildKitAtSlotWithNoKitConfigButLaterReceived() {
		System.out.println("Running test testBuildKitAtSlotWithNoKitConfigButLaterReceived()");

		assertEquals(partsRobotAgent.topSlot, null);
		assertEquals(partsRobotAgent.bottomSlot, null);
		assertEquals(partsRobotAgent.topSlotState, SlotState.EMPTY);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.EMPTY);
		
		// Send the message that the Stand would send
		partsRobotAgent.msgBuildKitAtSlot("topSlot");

		// The state should be at Requested
		assertEquals(partsRobotAgent.topSlotState, SlotState.BUILD_REQUESTED);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.EMPTY);
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();

		// Since the kitConfiguration is empty, the state should still be at Requested
		assertEquals(partsRobotAgent.topSlotState, SlotState.BUILD_REQUESTED);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.EMPTY);

		// Send the message that the Stand would send
		partsRobotAgent.msgBuildKitAtSlot("bottomSlot");

		// Since the kitConfiguration is empty, the state should still be at Requested
		assertEquals(partsRobotAgent.topSlotState, SlotState.BUILD_REQUESTED);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.BUILD_REQUESTED);

		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();

		// Since the kitConfiguration is empty, the state should still be at Requested
		assertEquals(partsRobotAgent.topSlotState, SlotState.BUILD_REQUESTED);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.BUILD_REQUESTED);
		
		// Build KitConfig and send it
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
		partsRobotAgent.pickAndExecuteAnAction();
		partsRobotAgent.pickAndExecuteAnAction();

		// Since the kitConfiguration is empty, the state should still be at Requested
		assertEquals(partsRobotAgent.topSlotState, SlotState.BUILDING);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.BUILDING);
	}
	
	/**
	 * Test passing the second configuration after the kits had started to be built
	 */
	@Test
	public void testSecondKitConfigurationAfterBuildingKits() {
		System.out.println("Running test testSecondKitConfigurationAfterBuildingKits()");
		
		// Initialize first configuration
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

		// Send the message that the Stand would send
		partsRobotAgent.msgBuildKitAtSlot("topSlot");
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Send the message that the Stand would send
		partsRobotAgent.msgBuildKitAtSlot("bottomSlot");
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		//Initialize second configuration
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
		assertEquals(partsRobotAgent.topSlotState, SlotState.EMPTY);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.EMPTY);

		// Arms should be empty
		assertEquals(partsRobotAgent.armOne, null);
		assertEquals(partsRobotAgent.armTwo, null);
		assertEquals(partsRobotAgent.armThree, null);
		assertEquals(partsRobotAgent.armFour, null);
	}

	/**
	 * Test that we 
	 */
	@Test
	public void testClearLineOfSightReceived() {
		System.out.println("Running test testClearLineOfSightReceived()");
		
		// Send the message that the Vision would send for nests 0,1
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(0).nest, partsRobotAgent.nests.get(1).nest);

		assertEquals(partsRobotAgent.nests.get(0).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(1).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(2).state, NestState.DOING_NOTHING);
		assertEquals(partsRobotAgent.nests.get(3).state, NestState.DOING_NOTHING);
		assertEquals(partsRobotAgent.nests.get(4).state, NestState.DOING_NOTHING);
		assertEquals(partsRobotAgent.nests.get(5).state, NestState.DOING_NOTHING);
		assertEquals(partsRobotAgent.nests.get(6).state, NestState.DOING_NOTHING);
		assertEquals(partsRobotAgent.nests.get(7).state, NestState.DOING_NOTHING);

		// Send the message that the Vision would send for nest 2,3
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(2).nest, partsRobotAgent.nests.get(3).nest);

		assertEquals(partsRobotAgent.nests.get(0).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(1).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(2).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(3).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(4).state, NestState.DOING_NOTHING);
		assertEquals(partsRobotAgent.nests.get(5).state, NestState.DOING_NOTHING);
		assertEquals(partsRobotAgent.nests.get(6).state, NestState.DOING_NOTHING);
		assertEquals(partsRobotAgent.nests.get(7).state, NestState.DOING_NOTHING);

		// Send the message that the Vision would send for nest 4,5
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(4).nest, partsRobotAgent.nests.get(5).nest);

		assertEquals(partsRobotAgent.nests.get(0).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(1).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(2).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(3).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(4).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(5).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(6).state, NestState.DOING_NOTHING);
		assertEquals(partsRobotAgent.nests.get(7).state, NestState.DOING_NOTHING);

		// Send the message that the Vision would send for nest 6,7
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(6).nest, partsRobotAgent.nests.get(7).nest);

		assertEquals(partsRobotAgent.nests.get(0).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(1).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(2).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(3).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(4).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(5).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(6).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(7).state, NestState.PICTURE_NEEDED);
		
	

	}

	/**
	 * Test that new kitConfig when picture is being requested 
	 */
	@Test
	public void testNewKitConfigWithPictureRequested() {
		System.out.println("Running test testNewKitConfigWithPictureRequested()");
		
		// Initialize first configuration
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

		// Send the message that the Vision would send for nests 0,1
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(0).nest, partsRobotAgent.nests.get(1).nest);
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(2).nest, partsRobotAgent.nests.get(3).nest);
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(4).nest, partsRobotAgent.nests.get(5).nest);
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(6).nest, partsRobotAgent.nests.get(7).nest);
		
		// Send the message that the FCS would send
		partsRobotAgent.msgMakeKit(firstKit);

		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// The new Kit Configuration should be in production
		assertEquals(partsRobotAgent.currentKitConfigurationState, KitConfigState.PRODUCING);

		// Stand should have receive message to empty stand
		assertTrue(stand.log.containsString("msgClearStand() received from the PartsRobot"));
		
		// Slots should be empty
		assertEquals(partsRobotAgent.topSlot, null);
		assertEquals(partsRobotAgent.bottomSlot, null);
		assertEquals(partsRobotAgent.topSlotState, SlotState.EMPTY);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.EMPTY);
		
		// Nest one have part one
		for(int i = 0; i < 8; i++){
			assertEquals(partsRobotAgent.nests.get(i).nest.getNestName() , "MockNest" + i);
			assertEquals(partsRobotAgent.nests.get(i).partCoordinate , -1);
			System.out.println(partsRobotAgent.nests.get(i).state);
			assertEquals(partsRobotAgent.nests.get(i).state , NestState.PICTURE_NEEDED);
			assertEquals(partsRobotAgent.nests.get(i).part , firstKit.listOfParts.get(i));
			assertTrue(((MockNest)partsRobotAgent.nests.get(i).nest).log.containsString("msgYouNeedPart"));
		}
			
		// Initialize first configuration
		KitConfig secondKit = new KitConfig();
		secondKit.listOfParts.add(new Part("Part11"));
		secondKit.listOfParts.add(new Part("Part12"));
		secondKit.listOfParts.add(new Part("Part13"));
		secondKit.listOfParts.add(new Part("Part14"));
		secondKit.listOfParts.add(new Part("Part15"));
		secondKit.listOfParts.add(new Part("Part16"));
		secondKit.listOfParts.add(new Part("Part17"));
		secondKit.listOfParts.add(new Part("Part18"));
		

		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();

		// Send the message that the Vision would send for nests 0,1
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(0).nest, partsRobotAgent.nests.get(1).nest);
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(2).nest, partsRobotAgent.nests.get(3).nest);
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(4).nest, partsRobotAgent.nests.get(5).nest);
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(6).nest, partsRobotAgent.nests.get(7).nest);
		
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
		assertEquals(partsRobotAgent.topSlotState, SlotState.EMPTY);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.EMPTY);
		
		// Nest one have part one
		for(int i1 = 0; i1 < 8; i1++){
			assertEquals(partsRobotAgent.nests.get(i1).nest.getNestName() , "MockNest" + i1);
			assertEquals(partsRobotAgent.nests.get(i1).partCoordinate , -1);
			System.out.println(partsRobotAgent.nests.get(i1).state);
			assertEquals(partsRobotAgent.nests.get(i1).state , NestState.DOING_NOTHING);
			assertEquals(partsRobotAgent.nests.get(i1).part , secondKit.listOfParts.get(i1));
			assertTrue(((MockNest)partsRobotAgent.nests.get(i1).nest).log.containsString("msgYouNeedPart"));
		}
	}


	/**
	 * Test that new kitConfig tells the Vision to Take picture when there is no overlap
	 */
	@Test
	public void testSightClearedForPictureWithNoOverlap() {
		System.out.println("Running test testSightClearedForPictureWithNoOverlap()");

		// Send the message that the Vision would send for nests 0,1
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(0).nest, partsRobotAgent.nests.get(1).nest);
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Stand should have receive message to empty stand
		assertTrue(vision.log.containsString("msgVisionClearForPictureInNests("+partsRobotAgent.nests.get(0).nest.getNestName()+","+partsRobotAgent.nests.get(1).nest.getNestName()+") received from the PartsRobot"));
		
		// Check that the states changed accordingly
		assertEquals(partsRobotAgent.nests.get(0).state, NestState.WAITING_ON_PICTURE);
		assertEquals(partsRobotAgent.nests.get(1).state, NestState.WAITING_ON_PICTURE);
		
		// Send the message that the Vision would send for nests 2,3
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(2).nest, partsRobotAgent.nests.get(3).nest);
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Stand should have receive message to empty stand
		assertTrue(vision.log.containsString("msgVisionClearForPictureInNests("+partsRobotAgent.nests.get(2).nest.getNestName()+","+partsRobotAgent.nests.get(3).nest.getNestName()+") received from the PartsRobot"));
		
		// Check that the states changed accordingly
		assertEquals(partsRobotAgent.nests.get(2).state, NestState.WAITING_ON_PICTURE);
		assertEquals(partsRobotAgent.nests.get(3).state, NestState.WAITING_ON_PICTURE);
		
		// Send the message that the Vision would send for nests 4,5
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(4).nest, partsRobotAgent.nests.get(5).nest);
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Stand should have receive message to empty stand
		assertTrue(vision.log.containsString("msgVisionClearForPictureInNests("+partsRobotAgent.nests.get(4).nest.getNestName()+","+partsRobotAgent.nests.get(5).nest.getNestName()+") received from the PartsRobot"));
		
		// Check that the states changed accordingly
		assertEquals(partsRobotAgent.nests.get(4).state, NestState.WAITING_ON_PICTURE);
		assertEquals(partsRobotAgent.nests.get(5).state, NestState.WAITING_ON_PICTURE);
		
		// Send the message that the Vision would send for nests 6,7
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(6).nest, partsRobotAgent.nests.get(7).nest);
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Stand should have receive message to empty stand
		assertTrue(vision.log.containsString("msgVisionClearForPictureInNests("+partsRobotAgent.nests.get(6).nest.getNestName()+","+partsRobotAgent.nests.get(7).nest.getNestName()+") received from the PartsRobot"));
		
		// Check that the states changed accordingly
		assertEquals(partsRobotAgent.nests.get(6).state, NestState.WAITING_ON_PICTURE);
		assertEquals(partsRobotAgent.nests.get(7).state, NestState.WAITING_ON_PICTURE);
		
	}

	/**
	 * Test that PartsRobot doesn't tell the Vision to Take picture when there is overlap
	 */
	@Test
	public void testSightClearedForPictureWithOverlapForNests0And1() {
		System.out.println("Running test testSightClearedForPictureWithOverlapForNests0And1()");

		// Update the position to create overlap
		partsRobotAgent.position = PartsRobotPositions.NEST_ZERO;
		
		// Send the message that the Vision would send for nests 0,1
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(0).nest, partsRobotAgent.nests.get(1).nest);
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Stand should have receive message to empty stand
		assertTrue(vision.log.size() == 0);
		
		// Check that the states changed accordingly
		assertEquals(partsRobotAgent.nests.get(0).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(1).state, NestState.PICTURE_NEEDED);

		// Move the Robot but still at overlap
		partsRobotAgent.position = PartsRobotPositions.NEST_ONE;
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Stand should have receive message to empty stand
		assertTrue(vision.log.size() == 0);
		
		// Check that the states changed accordingly
		assertEquals(partsRobotAgent.nests.get(0).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(1).state, NestState.PICTURE_NEEDED);		

		// Move the Robot but still at overlap
		partsRobotAgent.position = PartsRobotPositions.NEST_TWO;
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Stand should have receive message to empty stand
		assertTrue(vision.log.size() == 0);
		
		// Check that the states changed accordingly
		assertEquals(partsRobotAgent.nests.get(0).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(1).state, NestState.PICTURE_NEEDED);		
		
	
		// Move the Robot for NO overlap
		partsRobotAgent.position = PartsRobotPositions.CENTER;
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Stand should have receive message to empty stand
		assertTrue(vision.log.containsString("msgVisionClearForPictureInNests("+partsRobotAgent.nests.get(0).nest.getNestName()+","+partsRobotAgent.nests.get(1).nest.getNestName()+") received from the PartsRobot"));
		
		// Check that the states changed accordingly
		assertEquals(partsRobotAgent.nests.get(0).state, NestState.WAITING_ON_PICTURE);
		assertEquals(partsRobotAgent.nests.get(1).state, NestState.WAITING_ON_PICTURE);
		
	}
	@Test
	public void testSightClearedForPictureWithOverlapForNests2And3() {
		System.out.println("Running test testSightClearedForPictureWithOverlapForNests2And3()");

		// Update the position to create overlap
		partsRobotAgent.position = PartsRobotPositions.NEST_ONE;
		
		// Send the message that the Vision would send for nests 2,3
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(2).nest, partsRobotAgent.nests.get(3).nest);
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Stand should have receive message to empty stand
		assertTrue(vision.log.size() == 0);
		
		// Check that the states changed accordingly
		assertEquals(partsRobotAgent.nests.get(2).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(3).state, NestState.PICTURE_NEEDED);

		// Move the Robot but still at overlap
		partsRobotAgent.position = PartsRobotPositions.NEST_TWO;
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Stand should have receive message to empty stand
		assertTrue(vision.log.size() == 0);
		
		// Check that the states changed accordingly
		assertEquals(partsRobotAgent.nests.get(2).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(3).state, NestState.PICTURE_NEEDED);

		// Move the Robot but still at overlap
		partsRobotAgent.position = PartsRobotPositions.NEST_THREE;
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Stand should have receive message to empty stand
		assertTrue(vision.log.size() == 0);
		
		// Check that the states changed accordingly
		assertEquals(partsRobotAgent.nests.get(2).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(3).state, NestState.PICTURE_NEEDED);

		// Move the Robot but still at overlap
		partsRobotAgent.position = PartsRobotPositions.NEST_FOUR;
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Stand should have receive message to empty stand
		assertTrue(vision.log.size() == 0);
		
		// Check that the states changed accordingly
		assertEquals(partsRobotAgent.nests.get(2).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(3).state, NestState.PICTURE_NEEDED);
		
	
		// Move the Robot for NO overlap
		partsRobotAgent.position = PartsRobotPositions.CENTER;
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Stand should have receive message to empty stand
		assertTrue(vision.log.containsString("msgVisionClearForPictureInNests("+partsRobotAgent.nests.get(2).nest.getNestName()+","+partsRobotAgent.nests.get(3).nest.getNestName()+") received from the PartsRobot"));
		
		// Check that the states changed accordingly
		assertEquals(partsRobotAgent.nests.get(2).state, NestState.WAITING_ON_PICTURE);
		assertEquals(partsRobotAgent.nests.get(3).state, NestState.WAITING_ON_PICTURE);
		
	}
	@Test
	public void testSightClearedForPictureWithOverlapForNests4And5() {
		System.out.println("Running test testSightClearedForPictureWithOverlapForNests4And5()");


		// Update the position to create overlap
		partsRobotAgent.position = PartsRobotPositions.NEST_THREE;
		
		// Send the message that the Vision would send for nests 4,5
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(4).nest, partsRobotAgent.nests.get(5).nest);
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Stand should have receive message to empty stand
		assertTrue(vision.log.size() == 0);
		
		// Check that the states changed accordingly
		assertEquals(partsRobotAgent.nests.get(4).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(5).state, NestState.PICTURE_NEEDED);

		// Move the Robot but still at overlap
		partsRobotAgent.position = PartsRobotPositions.NEST_FOUR;
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Stand should have receive message to empty stand
		assertTrue(vision.log.size() == 0);
		
		// Check that the states changed accordingly
		assertEquals(partsRobotAgent.nests.get(4).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(5).state, NestState.PICTURE_NEEDED);

		// Move the Robot but still at overlap
		partsRobotAgent.position = PartsRobotPositions.NEST_FIVE;
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Stand should have receive message to empty stand
		assertTrue(vision.log.size() == 0);
		
		// Check that the states changed accordingly
		assertEquals(partsRobotAgent.nests.get(4).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(5).state, NestState.PICTURE_NEEDED);

		// Move the Robot but still at overlap
		partsRobotAgent.position = PartsRobotPositions.NEST_SIX;
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Stand should have receive message to empty stand
		assertTrue(vision.log.size() == 0);
		
		// Check that the states changed accordingly
		assertEquals(partsRobotAgent.nests.get(4).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(5).state, NestState.PICTURE_NEEDED);
		
	
		// Move the Robot for NO overlap
		partsRobotAgent.position = PartsRobotPositions.CENTER;
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Stand should have receive message to empty stand
		assertTrue(vision.log.containsString("msgVisionClearForPictureInNests("+partsRobotAgent.nests.get(4).nest.getNestName()+","+partsRobotAgent.nests.get(5).nest.getNestName()+") received from the PartsRobot"));
		
		// Check that the states changed accordingly
		assertEquals(partsRobotAgent.nests.get(4).state, NestState.WAITING_ON_PICTURE);
		assertEquals(partsRobotAgent.nests.get(5).state, NestState.WAITING_ON_PICTURE);
		
	}
	@Test
	public void testSightClearedForPictureWithOverlapForNests6And7() {
		System.out.println("Running test testSightClearedForPictureWithOverlapForNests6And7()");

		// Update the position to create overlap
		partsRobotAgent.position = PartsRobotPositions.NEST_FIVE;
		
		// Send the message that the Vision would send for nests 6,7
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(6).nest, partsRobotAgent.nests.get(7).nest);
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Stand should have receive message to empty stand
		assertTrue(vision.log.size() == 0);
		
		// Check that the states changed accordingly
		assertEquals(partsRobotAgent.nests.get(6).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(7).state, NestState.PICTURE_NEEDED);

		// Move the Robot but still at overlap
		partsRobotAgent.position = PartsRobotPositions.NEST_SIX;
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Stand should have receive message to empty stand
		assertTrue(vision.log.size() == 0);
		
		// Check that the states changed accordingly
		assertEquals(partsRobotAgent.nests.get(6).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(7).state, NestState.PICTURE_NEEDED);	

		// Move the Robot but still at overlap
		partsRobotAgent.position = PartsRobotPositions.NEST_SEVEN;
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Stand should have receive message to empty stand
		assertTrue(vision.log.size() == 0);
		
		// Check that the states changed accordingly
		assertEquals(partsRobotAgent.nests.get(6).state, NestState.PICTURE_NEEDED);
		assertEquals(partsRobotAgent.nests.get(7).state, NestState.PICTURE_NEEDED);
		
	
		// Move the Robot for NO overlap
		partsRobotAgent.position = PartsRobotPositions.CENTER;
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Stand should have receive message to empty stand
		assertTrue(vision.log.containsString("msgVisionClearForPictureInNests("+partsRobotAgent.nests.get(6).nest.getNestName()+","+partsRobotAgent.nests.get(7).nest.getNestName()+") received from the PartsRobot"));
		
		// Check that the states changed accordingly
		assertEquals(partsRobotAgent.nests.get(6).state, NestState.WAITING_ON_PICTURE);
		assertEquals(partsRobotAgent.nests.get(7).state, NestState.WAITING_ON_PICTURE);
		
	}
	
	/**
	 * Test that the picture was taken
	 */
	@Test
	public void testPictureTaken() {
		System.out.println("Running test testPictureTaken()");
		
		// Send the message that the Vision would send for nests 0,1
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(0).nest, partsRobotAgent.nests.get(1).nest);
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();

		// Send the message that the Vision would send for nests 0,1
		partsRobotAgent.msgPictureTaken(partsRobotAgent.nests.get(0).nest, partsRobotAgent.nests.get(1).nest);
		
		// Check that the state was updated
		assertEquals(partsRobotAgent.nests.get(0).state, NestState.DOING_NOTHING);
		assertEquals(partsRobotAgent.nests.get(1).state, NestState.DOING_NOTHING);
		
		// Send the message that the Vision would send for nests 2,3
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(2).nest, partsRobotAgent.nests.get(3).nest);
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();

		// Send the message that the Vision would send for nests 2,3
		partsRobotAgent.msgPictureTaken(partsRobotAgent.nests.get(2).nest, partsRobotAgent.nests.get(3).nest);
		
		// Check that the state was updated
		assertEquals(partsRobotAgent.nests.get(2).state, NestState.DOING_NOTHING);
		assertEquals(partsRobotAgent.nests.get(3).state, NestState.DOING_NOTHING);
		
		// Send the message that the Vision would send for nests 4,5
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(4).nest, partsRobotAgent.nests.get(5).nest);
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();

		// Send the message that the Vision would send for nests 4,5
		partsRobotAgent.msgPictureTaken(partsRobotAgent.nests.get(4).nest, partsRobotAgent.nests.get(5).nest);
		
		// Check that the state was updated
		assertEquals(partsRobotAgent.nests.get(4).state, NestState.DOING_NOTHING);
		assertEquals(partsRobotAgent.nests.get(5).state, NestState.DOING_NOTHING);
		
		// Send the message that the Vision would send for nests 6,7
		partsRobotAgent.msgClearLineOfSight(partsRobotAgent.nests.get(6).nest, partsRobotAgent.nests.get(7).nest);
		
		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();

		// Send the message that the Vision would send for nests 6,7
		partsRobotAgent.msgPictureTaken(partsRobotAgent.nests.get(6).nest, partsRobotAgent.nests.get(7).nest);
		
		// Check that the state was updated
		assertEquals(partsRobotAgent.nests.get(6).state, NestState.DOING_NOTHING);
		assertEquals(partsRobotAgent.nests.get(7).state, NestState.DOING_NOTHING);
	}

	
	/**
	 * Test that arms are empty after a new kit config comes in
	 */
	@Test
	public void testNewKitConfigWithArmsFull() {
		System.out.println("Running test testNewKitConfigWithArmsFull()");
		
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
		
		// Put Parts
		partsRobotAgent.armOne = new Part();
		partsRobotAgent.armTwo = new Part();
		partsRobotAgent.armThree = new Part();
		partsRobotAgent.armFour = new Part();
		

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
		assertEquals(partsRobotAgent.topSlotState, SlotState.EMPTY);
		assertEquals(partsRobotAgent.bottomSlotState, SlotState.EMPTY);
		
		// Nest one have part one
		for(int i = 0; i < 8; i++){
			assertEquals(partsRobotAgent.nests.get(i).nest.getNestName() , "MockNest" + i);
			assertEquals(partsRobotAgent.nests.get(i).partCoordinate , -1);
			assertEquals(partsRobotAgent.nests.get(i).state , NestState.DOING_NOTHING);
			assertEquals(partsRobotAgent.nests.get(i).part , firstKit.listOfParts.get(i));
			assertTrue(((MockNest)partsRobotAgent.nests.get(i).nest).log.containsString("msgYouNeedPart"));
		}

		// Arms should be empty
		assertEquals(partsRobotAgent.armOne, null);
		assertEquals(partsRobotAgent.armTwo, null);
		assertEquals(partsRobotAgent.armThree, null);
		assertEquals(partsRobotAgent.armFour, null);
	}

	
	/**
	 * Test that that the coordinates of a nest are stored and status updated
	 */
	@Test
	public void testCoordinateReceived() {
		System.out.println("Running test testCoordinateReceived()");

		for(int i = 0; i < 8; i ++){
			// Send the message that the Vision would send for nest i
			partsRobotAgent.msgHereArePartCoordiantesForNest(partsRobotAgent.nests.get(i).nest, partsRobotAgent.nests.get(i).part,  9);
			
			// Check that the state was updated
			assertEquals(partsRobotAgent.nests.get(i).state, NestState.PICK_UP_NEEDED);
			assertEquals(partsRobotAgent.nests.get(i).partCoordinate, 9);
		}
		
	}

	
	/**
	 * Test that that the coordinates of a nest are stored and status updated
	 */
	@Test
	public void testNewKitConfigurationAfterCoordinateReceived() {
		System.out.println("Running test testNewKitConfigurationAfterCoordinateReceived()");

		// Send part coordinate
		partsRobotAgent.msgHereArePartCoordiantesForNest(partsRobotAgent.nests.get(0).nest, partsRobotAgent.nests.get(0).part, 9);

		// Initialize first configuration
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

		//Initialize second configuration
		KitConfig secondKit = new KitConfig();
		secondKit.listOfParts.add(new Part("Part1"));
		secondKit.listOfParts.add(new Part("Part10"));
		secondKit.listOfParts.add(new Part("Part3"));
		secondKit.listOfParts.add(new Part("Part15"));
		secondKit.listOfParts.add(new Part("Part5"));
		secondKit.listOfParts.add(new Part("Part16"));
		secondKit.listOfParts.add(new Part("Part7"));
		secondKit.listOfParts.add(new Part("Part48"));

		// Send part coordinate
		partsRobotAgent.msgHereArePartCoordiantesForNest(partsRobotAgent.nests.get(0).nest, partsRobotAgent.nests.get(0).part, 9);
		partsRobotAgent.msgHereArePartCoordiantesForNest(partsRobotAgent.nests.get(1).nest, partsRobotAgent.nests.get(1).part, 9);

		// Send the message that the FCS would send
		partsRobotAgent.msgMakeKit(secondKit);

		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Nest one should still need to be picked up
		assertEquals(partsRobotAgent.nests.get(0).state, NestState.PICK_UP_NEEDED);
		assertEquals(partsRobotAgent.nests.get(0).partCoordinate, 9);
		
		// Nest one should not be needed since it got replaced
		assertEquals(partsRobotAgent.nests.get(1).state, NestState.DOING_NOTHING);
		assertEquals(partsRobotAgent.nests.get(1).partCoordinate, -1);
		

		KitConfig thirdKit = new KitConfig();
		thirdKit.listOfParts.add(new Part("Part99"));
		thirdKit.listOfParts.add(new Part("Part98"));
		thirdKit.listOfParts.add(new Part("Part97"));
		thirdKit.listOfParts.add(new Part("Part96"));
		thirdKit.listOfParts.add(new Part("Part95"));
		thirdKit.listOfParts.add(new Part("Part94"));
		thirdKit.listOfParts.add(new Part("Part93"));
		thirdKit.listOfParts.add(new Part("Part92"));

		// Send part coordinate
		partsRobotAgent.msgHereArePartCoordiantesForNest(partsRobotAgent.nests.get(0).nest, partsRobotAgent.nests.get(0).part, 9);
		partsRobotAgent.msgHereArePartCoordiantesForNest(partsRobotAgent.nests.get(1).nest, partsRobotAgent.nests.get(1).part, 9);
		
		// Send the message that the FCS would send
		partsRobotAgent.msgMakeKit(thirdKit);

		// Execute the Scheduler
		partsRobotAgent.pickAndExecuteAnAction();
		
		// Nest one have part one
		for(int i = 0; i < 8; i++){
			assertEquals(partsRobotAgent.nests.get(i).partCoordinate , -1);
			assertEquals(partsRobotAgent.nests.get(i).state , NestState.DOING_NOTHING);

		}

		
	}

	
}










