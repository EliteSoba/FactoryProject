package factory;

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
import factory.test.mock.MockPartsRobotAgent;

//test commit/push
public class FCSAgent extends Agent implements FCS{
	//change this to orders class later
	 public Queue<KitConfig> myKitConfigs = new LinkedList<KitConfig>(); //added this queue for kits
//	 List<MyKitConfig> myKitConfigs = new ArrayList<MyKitConfig>();
	   PartsRobot partsRobot;
	   Gantry gantry;
	   BinConfig binConfig; //no need for this the way CS200 is designing the bins?
	   public boolean passBinConfigurationToGantry = false;
	   public boolean freeToMakeKit = true;
//	   public Queue<Map> myKitConfigs = new LinkedList<Map>();
	   public List<Part> partsList = new ArrayList<Part>();
	   public Map<String, KitConfig> kitRecipes = new HashMap<String, KitConfig>();
	   
	   enum KitProductionState { PENDING, PRODUCING, FINISHED, PURGING }
	   KitProductionState state = KitProductionState.PENDING;

	   
	   //this is temporarily used for testing purposes.  Constructor will likely change.
	   public FCSAgent(Gantry gantry, PartsRobot partsRobot, MasterControl mc) {
		   super(mc);
		this.gantry = gantry;
		this.partsRobot = partsRobot;
	}

	// *** MESSAGES ***
	   
	public void msgInitialize(BinConfig binConfig) {
		   this.binConfig = binConfig;
		   this.passBinConfigurationToGantry = true;
		   stateChanged();
		}
	   
		//msg from Panel to produce a kit
	   public void msgProduceKit(String name, int quantity) {
		   KitConfig recipe = kitRecipes.get(name);
		   recipe.quantity = quantity;
		   myKitConfigs.add(recipe);
		   stateChanged();
		}
	   
	   //msg from KitRobot that says kit is finished. ADDED THIS
	   public void kitIsFinished(){
		   this.state = KitProductionState.FINISHED;
		   stateChanged();
	   }
	   
	   
	// *** SCHEDULER ***
	   public boolean pickAndExecuteAnAction() {
		   if (this.passBinConfigurationToGantry == true){
			   changeGantryBinConfig();
			   return true;
		   }
		   
		   //added this in the scheduler
		   if (this.state == KitProductionState.FINISHED){
			   startProducingNextKit();
			   return true;
		   }

		   if (this.state == KitProductionState.PENDING && !myKitConfigs.isEmpty()){ //added freeToMakeKit
			   sendKitConfigToPartRobot();
			   return true;
		   }
		   return false;
	   }

	// *** ACTIONS ***
	   /**
	    * Passes down the new configuration to the Gantry
	    */
	   private void changeGantryBinConfig(){
	      gantry.msgChangeGantryBinConfig(this.binConfig);
	      this.passBinConfigurationToGantry = false;
	      stateChanged();
	   }

	   /**
	    * Passes down the new Kit Configuration to the PartsRobot Agent
	    */
	   private void sendKitConfigToPartRobot() { 
	      this.partsRobot.msgMakeKit(myKitConfigs.peek());
	      this.state = KitProductionState.PRODUCING;
	      stateChanged();
	   }
	   
	   //added this action
	   private void startProducingNextKit(){
		   myKitConfigs.remove();
		   this.state = KitProductionState.PENDING;
		   stateChanged();
	   }
	   
	   
	// *** OTHER METHODS ***
	   public void addPartType(String name, double nestStabilizationTime, String description, int id, String imagePath){
		   Part part = new Part();
		   part.name = name;
		   part.nestStabilizationTime = nestStabilizationTime;
		   part.description = description;
		   part.id = id;
		   part.imagePath = imagePath;
		   partsList.add(part);
	   }
	   
	   public void addKitRecipe(String name, List<String> kitConfig){
		   KitConfig temp = new KitConfig();
		   if (kitConfig.size() != 0){
			   for (String p: kitConfig){
				   for (int i=0; i < partsList.size(); i++){
					   if (partsList.get(i).name.equals(p)){
						   temp.listOfParts.add(partsList.get(i));
						   break;
					   }
				   }
			   }
		   }
		   kitRecipes.put(name, temp);
	   }	   
}
