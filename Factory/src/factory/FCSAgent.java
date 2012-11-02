package factory;

import java.util.ArrayList;

public class FCSAgent {
	 ArrayList<MyKitConfig> myKitConfigs = new ArrayList<MyKitConfig>();
	   PartsRobot partsRobot;
	   Gantry gantry;
	   BinConfig binConfig;
	   Boolean passBinConfigurationToGantry = false;
	   
	   enum MyKitConfigState { PENDING, PRODUCING }

	   class MyKitConfig {
	      
	      KitConfig kitConfig;
	      MyKitConfigState state;

	      public MyKitConfig(KitConfig kitConfig){
	            this.state = MyKitConfigState.PENDING;
	            this.kitConfig = kitConfig;
	      }
	   }
	   
	// *** MESSAGES ***
	   
	   public void msgInitialize(BinConfig binConfig) {
		   this.binConfig = binConfig;
		   this.passBinConfigurationToGantry = true;
		}
	   
	   public void msgProduceKit(KitConfig kitConfig) {
		   myKitConfigs.add(new MyKitConfig(kitConfig));
		}
	   
	   
	// *** SCHEDULER ***
	   
	   if (this.passBinConfigurationToGantry == true){
		   changeGantryBinConfig();
	   }
	   
	   for(MyKitConfig mkc: myKitConfigs){
		   if (mkc.state == MyKitConfigState.PENDING){
			   sendKitConfigToPartRobot(mkc);
		   }
	   }
	   
	// *** ACTIONS ***
	   
	   
}
