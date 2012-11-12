package factory;

import java.util.ArrayList;
import agent.Agent;
import java.util.*;
import factory.interfaces.*;
import factory.test.mock.MockGantry;
import factory.test.mock.MockPartsRobotAgent;

//test commit/push
public class FCSAgent extends Agent implements FCS{
	 public ArrayList<MyKitConfig> myKitConfigs = new ArrayList<MyKitConfig>();
	   PartsRobot partsRobot;
	   Gantry gantry;
	   BinConfig binConfig;
	   public Boolean passBinConfigurationToGantry = false;
	   
	   enum MyKitConfigState { PENDING, PRODUCING }

	   class MyKitConfig {
	      
	      KitConfig kitConfig;
	      MyKitConfigState state;

	      public MyKitConfig(KitConfig kitConfig){
	            this.state = MyKitConfigState.PENDING;
	            this.kitConfig = kitConfig;
	      }
	   }
	   
	   //this is temporarily used for testing purposes.  Constructor will likely change.
	   public FCSAgent(Gantry gantry, PartsRobot partsRobot) {
		this.gantry = gantry;
		this.partsRobot = partsRobot;
	}

	   
	// *** MESSAGES ***
	   

	public void msgInitialize(BinConfig binConfig) {
		   this.binConfig = binConfig;
		   this.passBinConfigurationToGantry = true;
		   stateChanged();
		}
	   
	   public void msgProduceKit(KitConfig kitConfig) {
		   myKitConfigs.add(new MyKitConfig(kitConfig));
		   stateChanged();
		}
	   
	   
	// *** SCHEDULER ***
	   public boolean pickAndExecuteAnAction() {
		   if (this.passBinConfigurationToGantry == true){
			   changeGantryBinConfig();
			   return true;
		   }
		   for(MyKitConfig mkc: myKitConfigs){
			   if (mkc.state == MyKitConfigState.PENDING){
				   sendKitConfigToPartRobot(mkc);
				   return true;
			   }
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
	   private void sendKitConfigToPartRobot(MyKitConfig mkc) { 
	      this.partsRobot.msgMakeKit(mkc.kitConfig);
	      mkc.state = MyKitConfigState.PRODUCING;
	      stateChanged();
	   }
	   
}
