package factory;

import java.util.ArrayList;
import agent.Agent;
import java.util.*;


import factory.interfaces.*;
import factory.masterControl.MasterControl;
//test commit
public class GantryAgent extends Agent implements Gantry {
	public GantryAgent(MasterControl mc) {
		super(mc); // needed for the server 
		
	}

	public ArrayList<MyBin> myBins = new ArrayList<MyBin>();   
	BinConfig binConfig;

	enum MyBinState { NEEDED, DELIVERED}

	public class MyBin {

		public MyBinState state;
		public Part pt;
		public Feeder fdr;

		public MyBin(Part part, Feeder feeder){
			this.state = MyBinState.NEEDED;
			this.fdr = feeder;
			this.pt = part;
		}
	}

	// *** MESSAGES ***

	public void msgFeederNeedsPart(Part part, Feeder feeder) {
		myBins.add(new MyBin(part, feeder));
		stateChanged();
	}

	public void msgChangeGantryBinConfig(BinConfig binConfig) {
		this.binConfig = binConfig;
		stateChanged();
	}


	// *** SCHEDULER ***

	public boolean pickAndExecuteAnAction() {
		System.out.println("gantry scheduler called");
		for(MyBin b: myBins){
			if(b.state == MyBinState.NEEDED){
				goFetchTheRequestedBin(b);
				return true;
			}
		}
		return false;
	}


	// *** ACTIONS ***
	private void goFetchTheRequestedBin(MyBin b) {
		debug("goFetchTheRequestedBin");
		if (b.fdr.getFeederHasABinUnderneath() == true)
				DoPickupPurgeBin(b); //animation message
		
		System.out.println("1");
		DoGoGetNewBin(b);
		System.out.println("2");
		DoBringNewBin(b);
		System.out.println("3");

//		DoRefillPurgeBin(binConfig.binList.get(b.pt));
//		DoBringRequestedBin(binConfig.binList.get(b.pt),b.fdr,b.pt);
		
		b.fdr.msgHereAreParts(b.pt);
		b.state = MyBinState.DELIVERED; //is this needed?  Or do I just remove it per the line below?
		//myBins.remove(b);
		stateChanged();
	}


	private void DoPickupPurgeBin(MyBin b) {
		print("Picking up Purge Bin");
		server.command("ga fpm cmd pickuppurgebin " + b.fdr.getFeederNumber());
		try{
			animation.acquire();
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	
	private void DoGoGetNewBin(MyBin b) {
		print("Going to get new Bin");
		server.command("ga fpm cmd getnewbin " + b.pt.name);
		try{
			animation.acquire();
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
	}
	
	private void DoBringNewBin(MyBin b) {
		print("Bringing new Bin to the feeder");
		server.command("ga fpm cmd bringbin " + b.fdr.getFeederNumber());
		try{
			animation.acquire();
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
	}

	
	// *** OTHER METHODS ***
	/**
	 * This is a temporary message to test executing an animation from swing
	 */
	public void msgTestGetPart() {
		
	}



}




