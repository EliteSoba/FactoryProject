package factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import agent.Agent;
import java.util.*;

//import factory.KitConfig.MyPart;
import factory.interfaces.*;
import factory.masterControl.MasterControl;
import factory.test.mock.MockGantry;

public class FCSAgent extends Agent implements FCS{

	//queue of orders that is received from the panels
	public Queue<KitConfig> orders = new LinkedList<KitConfig>();
	
	//agents
	public PartsRobot partsRobot;
	Gantry gantry;
	
	//map to keep track of list of available parts
	public Map<String, Part> partsList = new HashMap<String, Part>();
	
	//map to keep track of list of available recipes
	public Map<String, KitConfig> kitRecipes = new HashMap<String, KitConfig>();

	//enum to keep track of the state of the factory
	public enum KitProductionState { PENDING, PRODUCING, FINISHED, PURGING }
	public KitProductionState state = KitProductionState.PENDING;

	//this keeps track of the number of kits exported.  Once it hits the quantity ordered, FCS tells
	//parts robot to start producing the next order
	public int kitsExportedCount = 0;

	//reference to the masterControl to communicate to server
	public MasterControl masterControl;

	//this is temporarily used for testing purposes.  Constructor will likely change.
	public FCSAgent(Gantry gantry, PartsRobot partsRobot, MasterControl mc) {
		super(mc);
		this.masterControl = mc; // for testing purposes
		this.gantry = gantry;
		this.partsRobot = partsRobot;
		//loadData();
		//		testImport();
	}
	public FCSAgent(MasterControl mc){
		super (mc);
		this.masterControl = mc;
		//loadData();
		//		testImport();
	}

	// *** MESSAGES ***


	/**
	 * This is the message the comes from the swing panel to tell the FCS to add an order to the factory
	 * @param quantity This is the number of kits that you want to produce
	 * @param kitName This is the name of the kit configuration that you want to produce.
	 */
	public void msgProduceKit(int quantity, String kitName) {
		System.out.println("IT WORKS!");
		KitConfig recipe = new KitConfig();
		recipe = kitRecipes.get(kitName);
		recipe.quantity = quantity;
		recipe.kitName = kitName;
		orders.add(recipe);
		stateChanged();
	}

	/**
	 * This is a message from the kitRobot stating that a kit just got exported.  This message checks to see if the factory
	 * created enough kits to move on to the next order.
	 * @param kit This is the kit that was just exported
	 */
	public void msgKitIsExported(Kit kit){
		kitsExportedCount++;
		if(kitsExportedCount >= orders.peek().quantity){
			this.state = KitProductionState.FINISHED;
		}
		//send message to manager to say that kit is exported
		stateChanged();
	}


	// *** SCHEDULER ***
	public boolean pickAndExecuteAnAction() {


		//if the order is finished, start producing next kit
		if (this.state == KitProductionState.FINISHED){
			startProducingNextKit();
			return true;
		}

		//if there is a kit pending and the order list isn't empty, then send an order to parts robot
		if (this.state == KitProductionState.PENDING && !orders.isEmpty()){
			sendKitConfigToPartRobot();
			return true;
		}
		return false;
	}

	// *** ACTIONS ***

	/**
	 * Passes down the new Kit Configuration to the PartsRobot Agent
	 */
	private void sendKitConfigToPartRobot() { 

		/**THIS IS TEMPORARY, //TODO for now send message to gantry instead of parts robot to test swing->agent->animation**/
		//		this.gantry.msgFeederNeedsPart(partsList.get("Shoe"), this.masterControl.f0); //comment this out for unit testing
		this.partsRobot.msgMakeKit(orders.peek()); //TODO uncomment this for v1 implementation
		this.state = KitProductionState.PRODUCING;
		kitsExportedCount = 0;
		stateChanged();
	}

	/**
	 * This starts producing the next kit by removing the finished order out of the queue and changing the state of the FCS
	 */
	private void startProducingNextKit(){
		orders.remove();
		this.state = KitProductionState.PENDING;
		stateChanged();
	}

	// *** OTHER METHODS ***
	/**
	 * This method adds a Part to the FCS Agent partsList.  The FCSAgent needs to have a list of parts so that when
	 * a new kitConfiguration (or recipe) is sent in, the FCS can simply find the parts in its list and make the recipe
	 * within the FCSAgent class.
	 * @param name The name of the new Part.
	 * @param id The ID of the new Part.
	 * @param imagePath The image path for the part.
	 * @param nestStabilizationTime The nest stabilization time of the part.
	 * @param description The description of what the part is.
	 */
	public void addPartType(String name, int id, String imagePath, int nestStabilizationTime, String description){
		Part part = new Part();
		part.name = name;
		part.nestStabilizationTime = nestStabilizationTime;
		part.description = description;
		part.id = id;
		part.imagePath = imagePath;
		partsList.put(name, part);
	}

	/**
	 * This method edits an already existing part type.  If the part type doesn't exist, then the part list won't update.
	 * @param oldPartName Name of the part you want to edit.
	 * @param newPartName The new name you want to give the part.
	 * @param id The new ID you want to give the part.
	 * @param imagePath The new image path you want to give the part.
	 * @param nestStabilizationTime The new nest stabilization time for the part.
	 * @param description The new description of the part.
	 */
	public void editPartType(String oldPartName, String newPartName, int id, String imagePath, int nestStabilizationTime, String description){
		if(partsList.containsKey(oldPartName)){
			partsList.remove(oldPartName);
			addPartType(newPartName, id, description, nestStabilizationTime, imagePath);
		}
	}
	//edit part
	//old part, new part, new id, new imagepath, new stabilizaitontime, new description


	/**
	 * This method removes a part from the FCSAgent
	 * @param partname The name of the part you want to remove.
	 */
	public void removePartType(String partname){
		if (partsList.containsKey(partname))
			partsList.remove(partname);
	}

	/**
	 * This adds a kit configuration to the FCS Agent so that the FCS Agent can add orders by drawing from a map of kit
	 * configurations.  The parameters p1, p2, etc... represent the name of the parts you want to add.  If there is an
	 * empty slot (that is, you don't need 8 parts for a recipe), pass a parameter (without quotes) named "None" in p1, p2, 
	 * etc..
	 * @param configurationName The name of the new kit configuration you want to add.
	 * @param p1 The name of part 1.
	 * @param p2 The name of part 2.
	 * @param p3 The name of part 3.
	 * @param p4 The name of part 4.
	 * @param p5 The name of part 5.
	 * @param p6 The name of part 6.
	 * @param p7 The name of part 7.
	 * @param p8 The name of part 8.
	 */
	public void addKitRecipe(String configurationName, String p1, String p2, String p3, String p4, String p5, String p6, 
			String p7, String p8){

		KitConfig config = new KitConfig();
		List<String> partsRequired = new ArrayList<String>();
		partsRequired.add(p1);
		partsRequired.add(p2);
		partsRequired.add(p3);
		partsRequired.add(p4);
		partsRequired.add(p5);
		partsRequired.add(p6);
		partsRequired.add(p7);
		partsRequired.add(p8);
		for (String p: partsRequired){
			if(!p.equals("None")){
				Part part = new Part();
				part = partsList.get(p);
				config.listOfParts.add(part);
				config.kitName = configurationName;
			}
		}
		kitRecipes.put(configurationName, config);
	}
	/**
	 * This edits a kit configuration that already exists within the FCS Agent.
	 * @param oldKitName The name of the kit configuration you want to edit.
	 * @param newKitName The new name you are giving to the kit configuration.
	 * @param p1 The name of part 1.
	 * @param p2 The name of part 2.
	 * @param p3 The name of part 3.
	 * @param p4 The name of part 4.
	 * @param p5 The name of part 5.
	 * @param p6 The name of part 6.
	 * @param p7 The name of part 7.
	 * @param p8 The name of part 8.
	 */
	public void editKitRecipe(String oldKitName, String newKitName, String p1, String p2, String p3, String p4, String p5, String p6, 
			String p7, String p8){

		kitRecipes.remove(oldKitName);
		addKitRecipe(newKitName,  p1, p2, p3, p4, p5, p6, p7, p8);
	}
	//edit kit
	//old kit name, new kit name, String p1, String p2....

	/**
	 * This removes a kit configuration from the FCS Agent.
	 * @param kitName The name of the kit you want to remove.
	 */
	public void removeKitRecipe(String kitName){
		kitRecipes.remove(kitName);
	}

	/**
	 * This is a method to test whether or not the FCS was able to load the list of parts and kit configurations
	 * from the text file.
	 */
	public void testImport() {
		System.out.println("Recipes" + kitRecipes.keySet());
		System.out.println("Parts" + partsList.keySet());
		System.out.println("LIST OF PARTS FOR RECIPE KIT2");
		for (int i=0; i < kitRecipes.get("Kit2").listOfParts.size(); i++){
			System.out.println(kitRecipes.get("Kit2").listOfParts.get(i).name);
		}
	}

	// Load Data - remember to import the file - FOR EVERYONE
	/**
	 * This function loads the data of existing parts and kit configurations from a text file.
	 */
	public void loadData(){
		FileInputStream f;
		ObjectInputStream o;
		try{    // loads previously saved player data
			f = new FileInputStream("InitialData/initialParts.ser");
			o = new ObjectInputStream(f);
			partsList = (HashMap<String,Part>) o.readObject();
			System.out.println("Good");
			o.close();
		}catch(IOException e){
			e.printStackTrace();
		} catch(ClassNotFoundException c){
			c.printStackTrace();
		}
		try{    // loads previously saved player data
			f = new FileInputStream("InitialData/initialKitConfigs.ser");
			o = new ObjectInputStream(f);
			kitRecipes = (HashMap<String,KitConfig>) o.readObject();
			System.out.println("Good");
			o.close();
		}catch(IOException e){
			e.printStackTrace();
		}catch(ClassNotFoundException c){
			c.printStackTrace();
		}
	}

}