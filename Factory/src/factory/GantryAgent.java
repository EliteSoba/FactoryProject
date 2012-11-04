package factory;

import java.util.ArrayList;
import agent.Agent;
import java.util.*;
import factory.interfaces.*;

public class GantryAgent {
	   ArrayList<MyBin> myBins = new ArrayList<MyBin>();   
	   BinConfig binConfig;
	   
	   enum MyBinState { NEEDED, DELIVERED}

	   class MyBin {
	      
	      MyBinState state;
	      Part pt;
	      Feeder fdr;

	      public MyBin(Part part, Feeder feeder){
	            this.state = MyBinState.NEEDED;
	            this.fdr = feeder;
	            this.pt = part;
	      }
	   }
	   
	   
	   
	   
	// *** MESSAGES ***
	   
	   public void msgFeederNeedsPart(Part part, Feeder feeder) {
		   myBins.add(new MyBin(part, feeder));
		}

		public void msgChangeGantryBinConfig(BinConfig binConfig) {
		   this.binConfig = binConfig;
		}
	   
	   
	// *** SCHEDULER ***
	   
	   public boolean pickAndExecuteAnAction() {

		   for(MyBin b: myBins){
			   if(b.state == MyBinState.NEEDED){
				   goFetchTheRequestedBin(b);
			   }
		   }
			return false;
	   }
	   
	// *** ACTIONS ***
	   private void goFetchTheRequestedBin(MyBin b) {
		   DoPickupPurgeBin(); //animation message
		   DoRefillPurgeBin(binConfig.get(b.pt));
		   DoBringRequestedBin(binConfig.get(b.pt, b.fdr));
		   b.fdr.msgHereIsPart(b.pt);
		   b.state = MyBinState.DELIVERED;
		}
}
