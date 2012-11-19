package factory.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;



import org.junit.Test;

import factory.Bin;
import factory.FCSAgent;
import factory.KitConfig;
import factory.Part;
import factory.interfaces.PartsRobot;
import factory.test.mock.*;
import factory.BinConfig;
import factory.FCSAgent.KitProductionState;;



public class FCSTests extends TestCase{

	/**
	 * This test tests if the fcs can get an order and successfully send a message to the parts robot.
	 */
	public void testMsgProduceKit()
	{

		
		KitConfig kitConfig = new KitConfig();
		KitProductionState stateFinished = KitProductionState.FINISHED;
		KitProductionState statePending = KitProductionState.PENDING;
		KitProductionState stateProducing = KitProductionState.PRODUCING;
		MockGantry gantry = new MockGantry("gantry");
		MockPartsRobot partsRobot = new MockPartsRobot("parts robot");
		FCSAgent fcs = new FCSAgent(gantry, partsRobot, null);
		fcs.kitRecipes.put("typeA", kitConfig);
		
		//create message for FCS.  Should eventually call this.partsRobot.msgMakeKit(mkc.kitConfig);
		fcs.msgProduceKit(5, "typeA");
		
		//tests to see if the kit config state is PENDING
		assertEquals("FCS state should be PENDING, but instead is: " + fcs.state 
				, statePending, fcs.state);
		
		//test to see that the parts robot has not been given a message before the scheduler is called.
		//Its log should be empty.
		assertEquals(
				"Mock parts robot should have an empty event log before the FCSAgent scheduler is called. Instead, the mock parts robot event log reads: "
						+ partsRobot.log.toString(), 0, partsRobot.log.size());
		
		//test to see if parts robot added a kitConfig to its list.  The list shouldn't be empty after msgProduceKit()
		assertFalse("Parts robot should have a kitConfig to its list of kit configs, but it is empty", 
					fcs.orders.isEmpty());
		
		//call the fcs scheduler
		fcs.pickAndExecuteAnAction();
		
		//tests to see if the parts robot log got the message from the FCS
		assertTrue("Parts Robot should have gotten a message to make a kit from the FCS.", partsRobot.log
				.containsString("msgMakeKit"));
		
		//tests to see if the kit config state changed from PENDING to PRODUCING
		assertEquals("FCS state should be changed from PENDING to PRODUCING, but instead is: " + fcs.state 
				, stateProducing, fcs.state);
		
		System.out.println("==================================================");
		System.out.println("===================TEST BREAK=====================");
		System.out.println("==================================================");
	}

	/**
	 * This test adds 2 kit orders to the fcs, then tests if the fcs correctly creates the kits, and moves on to the next
	 * order once the order is complete
	 */
	public void testAddingAndFinishingKitConfig(){
		
		//this test tests the fcs robot handling incoming orders.  It takes in 2 initial orders, and simulates
		//the factory producing a kit.
		KitProductionState stateFinished = KitProductionState.FINISHED;
		KitProductionState statePending = KitProductionState.PENDING;
		KitProductionState stateProducing = KitProductionState.PRODUCING;
		KitConfig kit1 = new KitConfig();
		KitConfig kit2 = new KitConfig();
		FCSAgent fcs = new FCSAgent(null);
		
		fcs.kitRecipes.clear();
		
		fcs.kitRecipes.put("typeA", kit1);
		fcs.kitRecipes.put("typeB", kit2);
		fcs.partsRobot = new MockPartsRobot("name");
		
		//test to see if fcs has 2 recipe types.
		assertEquals("FCS should have 2 recipes of kits, but instead has: " + fcs.kitRecipes.size()
				, 2, fcs.kitRecipes.size());
		
		fcs.msgProduceKit(2, "typeA");
		fcs.msgProduceKit(2, "typeB");
		
		
		//does the FCS has 2 orders pending?
		assertEquals("FCS should have 2 orders in its list, but instead has: " + fcs.orders.size()
				, 2, fcs.orders.size());
		
		//is the number of kits exported 0?
		assertEquals("The kits exported should be 0, but instead is: " + fcs.kitsExportedCount
				, 0, fcs.kitsExportedCount);
		
		
		//the state of the FCS should be PENDING
		assertEquals("The FCS state should be PENDING, but instead is: " + fcs.state
				, statePending, fcs.state);
		
		//the first order in the list should be for typeA
		assertEquals("The first order in the order list should be for kitConfig typeA, but instead is: " 
				+ fcs.orders.peek().kitName, "typeA", fcs.orders.peek().kitName);
		
		//tell fcs to change states
		fcs.pickAndExecuteAnAction();
		
		//export a kit
		fcs.msgKitIsExported(null);
		
		//the state of the FCS should be PROUDUCING 
		assertEquals("The FCS state should be PRODUCING, but instead is: " + fcs.state
				, stateProducing, fcs.state);
		
		//is the number of kits exported 1?
		assertEquals("The kits exported should be 1, but instead is: " + fcs.kitsExportedCount
				, 1, fcs.kitsExportedCount);
		
		//the first order in the list should still be for typeA
		assertEquals("The first order in the order list should be for kitConfig typeA, but instead is: " 
				+ fcs.orders.peek().kitName, "typeA", fcs.orders.peek().kitName);
		
		//export a kit
		fcs.msgKitIsExported(null);
		
		//is the number of kits exported 2?
		assertEquals("The kits exported should be 2, but instead is: " + fcs.kitsExportedCount
				, 2, fcs.kitsExportedCount);
		
		assertEquals("The FCS state should be FINISHED, but instead is: " + fcs.state
				, stateFinished, fcs.state);
		
		//the first order in the list should still be for typeA
		assertEquals("The first order in the order list should be for kitConfig typeA, but instead is: " 
				+ fcs.orders.peek().kitName, "typeA", fcs.orders.peek().kitName);
		
		fcs.pickAndExecuteAnAction();
		
		//check the state of the fcs
		assertEquals("The FCS state should be PENDING, but instead is: " + fcs.state
				, statePending, fcs.state);
		
		//the first order in the list should now be for typeB
		assertEquals("The first order in the order list should be for kitConfig typeB, but instead is: " 
				+ fcs.orders.peek().kitName, "typeB", fcs.orders.peek().kitName);
		
		//FCS should have removed an order
		assertEquals("The FCS orders list should have 1 order, but instead has: " + fcs.orders.size()
				, 1, fcs.orders.size());
		
		fcs.pickAndExecuteAnAction();
		
		//check the FCS state (should be PRODUCING)
		assertEquals("The FCS state should be PRODUCING, but instead is: " + fcs.state
				, stateProducing, fcs.state);
		
		//is the number of kits exported 0?
		assertEquals("The kits exported should be 0, but instead is: " + fcs.kitsExportedCount
				, 0, fcs.kitsExportedCount);
		
		//the first order in the list should still be for typeB
		assertEquals("The first order in the order list should be for kitConfig typeB, but instead is: " 
				+ fcs.orders.peek().kitName, "typeB", fcs.orders.peek().kitName);
		
		fcs.msgKitIsExported(null);
		
		//check the FCS state (should be PRODUCING)
		assertEquals("The FCS state should be PRODUCING, but instead is: " + fcs.state
				, stateProducing, fcs.state);
		
		//is the number of kits exported 1?
		assertEquals("The kits exported should be 1, but instead is: " + fcs.kitsExportedCount
				, 1, fcs.kitsExportedCount);
		
		//the first order in the list should still be for typeB
		assertEquals("The first order in the order list should be for kitConfig typeB, but instead is: " 
				+ fcs.orders.peek().kitName, "typeB", fcs.orders.peek().kitName);
		
		fcs.msgKitIsExported(null);
		
		//is the number of kits exported 2?
		assertEquals("The kits exported should be 2, but instead is: " + fcs.kitsExportedCount
				, 2, fcs.kitsExportedCount);
		
		//the first order in the list should still be for typeB
		assertEquals("The first order in the order list should be for kitConfig typeB, but instead is: " 
				+ fcs.orders.peek().kitName, "typeB", fcs.orders.peek().kitName);
		
		//check the FCS state (should be PRODUCING)
		assertEquals("The FCS state should be FINISHED, but instead is: " + fcs.state
				, stateFinished, fcs.state);
		
		fcs.pickAndExecuteAnAction();
		
		//check the FCS state (should be PENDING)
		assertEquals("The FCS state should be PENDING, but instead is: " + fcs.state
				, statePending, fcs.state);
		
		//the FCS should have removed its last order because it finished it
		assertEquals("The orders list should have no orders in it, but instead, has: " + fcs.orders.size()
				, 0, fcs.orders.size());
		
		fcs.pickAndExecuteAnAction();
		
		//check the FCS state (should be PENDING)
		assertEquals("The FCS state should be PENDING because there are no orders left, but instead is: " + fcs.state
				, statePending, fcs.state);
		
		System.out.println("==================================================");
		System.out.println("===================TEST BREAK=====================");
		System.out.println("==================================================");
	}
	/**
	 * This test tests all the part methods, which include adding, editing, and deleting parts.
	 */
	//test if the parts robot can add/edit/remove a part type
	public void testPartTypeMethods(){
		
		FCSAgent fcs = new FCSAgent(null);
		Map<String, Part> partsList = new HashMap<String, Part>();
		partsList = createPartsList();
		fcs.partsList = partsList;
		
		//test to see if the partsList initialized properly
		assertEquals("The size of the parts list should currently be 6, but instead is: " + fcs.partsList.size()
				, 6, fcs.partsList.size());
		
		//test to see if things are mapping correctly
		assertEquals("The nose id should be 1, but instead is: " + fcs.partsList.get("nose").id
				, 1, fcs.partsList.get("nose").id);
		
		//test to see if things are mapping correctly
		assertEquals("The ears id should be 2, but instead is: " + fcs.partsList.get("ears").id
				, 2, fcs.partsList.get("ears").id);
		
		//add a part type
		fcs.addPartType("sword", 7, "jpg", 5, "sword of potato");
		
		//test to see if partsList updated
		assertEquals("The size of the parts list should currently be 7, but instead is: " + fcs.partsList.size()
				, 7, fcs.partsList.size());
		
		fcs.editPartType("hat", "nostril", 4, "jpg", 5, "hat of potato");
		
		//test to see if nose got edited
		assertFalse("The part type \"hat\" should not exist because it should have been updated, but it still exists",
				fcs.partsList.containsKey("hat"));
		
		//test to see if nostril is in the list
		assertTrue("The part type \"hat\" should have been edited to nostril, but the nostril doesn't exist",
				fcs.partsList.containsKey("nostril"));
		
		//test to so if nostril id is 1
		assertEquals("The id of the nostril should be 4, but instead is: " + fcs.partsList.get("nostril").id,
				4, fcs.partsList.get("nostril").id);
		
		fcs.removePartType("arm");
		
		//test to see if part was removed by counting size of map
		assertEquals("The size of the parts list should currently be 6, but instead is: " +fcs.partsList.size(),
				6, fcs.partsList.size());
		
		assertFalse("The part type \"arm\" should not exist anymore because it should have been removed",
				fcs.partsList.containsKey("arm"));
		
		System.out.println("==================================================");
		System.out.println("===================TEST BREAK=====================");
		System.out.println("==================================================");
		
	}
	
	/**
	 * This test tests all the kitConfig methods, which include adding, editing, and deleting kitConfigs.
	 */
	public void testKitConfigMethods(){
		
		//set up the kitConfigs
		FCSAgent fcs = new FCSAgent(null);
		Map<String, KitConfig> kitConfigs = new HashMap<String, KitConfig>();
		Map<String, Part> partsList = new HashMap<String, Part>();
		partsList = createPartsList();
		fcs.partsList = partsList;
		KitConfig k1 = new KitConfig();
		k1.listOfParts.add(partsList.get("nose"));
		k1.listOfParts.add(partsList.get("ears"));
		k1.listOfParts.add(partsList.get("arm"));
		k1.listOfParts.add(partsList.get("arm"));
		fcs.kitRecipes.put("config1", k1);

		KitConfig k2 = new KitConfig();
		k2.listOfParts.add(partsList.get("hat"));
		k2.listOfParts.add(partsList.get("mouth"));
		k2.listOfParts.add(partsList.get("leg"));
		k2.listOfParts.add(partsList.get("leg"));
		fcs.kitRecipes.put("config2", k2);
		
		//there should be 2 kit configs in the kit config map
		assertEquals("There should be 2 kit configs in the kit config map, but instead there is: " + fcs.kitRecipes.size()
				, 2, fcs.kitRecipes.size());
		
		//config2 should have 4 parts in it
		assertEquals("There should be 4 parts in \"config2\", but instead there is: " + fcs.kitRecipes.get("config2").listOfParts.size()
				, 4, fcs.kitRecipes.get("config2").listOfParts.size());
		
		//edit a new kit recipe
		fcs.editKitRecipe("config2", "editConfig", "hat", "hat", "hat", "hat", "hat", "hat", "None", "None");
		
		//make sure config2 is gone.
		assertFalse("The fcs should have removed \"config2\", but it didn't"
				, fcs.kitRecipes.containsKey("config2"));
		
		//make sure config 2 name is edited to newConfig
		assertTrue("The fcs should have edited \"config2\" recipe to be called \"editConfig\", but it didn't"
				, fcs.kitRecipes.containsKey("editConfig"));
		
		//make sure no kit configs got deleted
		assertEquals("The size of the kit configs map should still be 2, but instead it is: " + fcs.kitRecipes.size()
				, 2, fcs.kitRecipes.size());
		
		//the number of parts in the edited list should be 6
		assertEquals("There should be 6 parts in \"config2\", but instead there is: " + fcs.kitRecipes.get("editConfig").listOfParts.size()
				, 6, fcs.kitRecipes.get("editConfig").listOfParts.size());
		
		//adding a new kit config
		fcs.addKitRecipe("newConfig", "arm", "arm", "arm", "arm", "arm", "None", "None", "None");
		
		//making sure there are now 3 kit configs
		assertEquals("There should be 3 kit configs in the fcs, but instead there is: " + fcs.kitRecipes.size()
				, 3, fcs.kitRecipes.size());
		
		//make sure kit recipe now contains the new config
		assertTrue("The fcs should contain the new configuration, but it doesn't"
				, fcs.kitRecipes.containsKey("newConfig"));
		
		//make sure there are 5 parts in the new kit config
		assertEquals("There should be 5 parts in the new kit configuration, but instead, there are: " + fcs.kitRecipes.get("newConfig").listOfParts.size()
				, 5, fcs.kitRecipes.get("newConfig").listOfParts.size());
		
		//test removing a kit recipe
		fcs.removeKitRecipe("config1");

		//make sure there are now only 2 recipes
		assertEquals("There should be only 2 kit configs in the fcs, but instead there are: " + fcs.kitRecipes.size()
				, 2, fcs.kitRecipes.size());
		
		//make sure the correct kit config got deleted
		assertFalse("kit config \"config1\" should not exist anymore, but it does"
				, fcs.kitRecipes.containsKey("config1"));
		
		System.out.println("==================================================");
		System.out.println("===================TEST BREAK=====================");
		System.out.println("==================================================");
	}

	/**
	 * This tests to see if the FCS was succesfully able to import and load the data
	 */
	public void testImportListAndSwing(){
		
		
		FCSAgent fcs = new FCSAgent(null);
		
		assertTrue(true);
		
		fcs.loadData();
		fcs.testImport();
		
		System.out.println("==================================================");
		System.out.println("===================TEST BREAK=====================");
		System.out.println("==================================================");
	}
	
	
	public Map<String, Part> createPartsList(){
		 Map<String, Part> partsList = new HashMap<String, Part>();
		 Part p1 = new Part("nose", 1, "nose of potato", "jpg", 5);
		 Part p2 = new Part("ears", 2, "ears of potato", "jpg", 5);
		 Part p3 = new Part("arm", 3, "arm of potato", "jpg", 5);
		 Part p4 = new Part("hat", 4, "hat of potato", "jpg", 5);
		 Part p5 = new Part("leg", 5, "leg of potato", "jpg", 5);
		 Part p6 = new Part("mouth", 6, "mouth of potato", "jpg", 5);
		 partsList.put(p1.name, p1);
		 partsList.put(p2.name, p2);
		 partsList.put(p3.name, p3);
		 partsList.put(p4.name, p4);
		 partsList.put(p5.name, p5);
		 partsList.put(p6.name, p6);
		 
		 return partsList;
		
	}

	
	
	
	
}
