package factory;

import java.util.ArrayList;
import java.util.List;

import agent.Agent;
import java.util.*;
import java.util.concurrent.Semaphore;


import factory.Kit.KitState;
import factory.interfaces.*;
import factory.masterControl.MasterControl;

public class VisionAgent extends Agent implements Vision {

	public enum kitPictureRequeststate { NEED_TO_INSPECT, INSPECTED }
	public enum PictureRequestState { NESTS_READY, ANALYZED, ASKED_PARTS_ROBOT, PARTS_ROBOT_CLEAR, PICTURE_TAKEN }

	public ArrayList<PictureRequest> pictureRequests = new ArrayList<PictureRequest>(); 
	public ArrayList<KitPicRequest> kitPictureRequests = new ArrayList<KitPicRequest>();     
	
	public PartsRobot partsRobot;
	public Stand stand;
	public Random r = new Random();
	
	public Semaphore pictureAllowed = new Semaphore(1);
	
	public ArrayList<Nest> nests;
	
	public Feeder feeder_zero;
	public Feeder feeder_one;
	public Feeder feeder_two;
	public Feeder feeder_three;
	
	public VisionAgent(PartsRobot partsRobot, Stand stand, MasterControl mc){
		super(mc);
		this.partsRobot = partsRobot;
		this.stand = stand;
	}

	public class KitPicRequest {

		public kitPictureRequeststate state;
		public boolean forceFail = false;

		public KitPicRequest(boolean forceFail) { 
			state = kitPictureRequeststate.NEED_TO_INSPECT;
			this.forceFail = forceFail;
		}
	}

	public class PictureRequest {

		public Nest nestOne;
		public Nest nestTwo;
		public int nestOneState = 0;
		public int nestTwoState = 0;
		public PictureRequestState state;
		public Feeder feeder;

		public PictureRequest(Nest nestOne, Nest nestTwo, Feeder feeder){
			this.state = PictureRequestState.NESTS_READY;
			this.nestOne = nestOne;
			this.nestTwo = nestTwo;
			this.feeder = feeder;
		}
	} 




/** ================================================================================ **/
/** 									MESSAGES 									 **/
/** ================================================================================ **/

	public void msgNewNestConfig(ArrayList<Nest> nests){
		this.nests = nests;
	}

	public void msgMyNestsReadyForPicture(Nest nestOne, Nest nestTwo, Feeder feeder) {
		if(nestOne.getPart() != null && nestTwo.getPart() !=null){
			debug("msgMyNestsReadyForPicture("+nestOne.getPart().name+","+nestTwo.getPart().name+")");

		}
		else {
			debug("msgMyNestsReadyForPicture(null,null)");

		}
		pictureRequests.add(new PictureRequest(nestOne, nestTwo, feeder));
		this.stateChanged();
	}

	public void msgVisionClearForPictureInNests(Nest nestOne, Nest nestTwo) {
		for( PictureRequest pr: pictureRequests) {
			if(pr.nestOne == nestOne && pr.nestTwo == nestTwo){
				pr.state = PictureRequestState.PARTS_ROBOT_CLEAR;
			}
		}
		this.stateChanged();
	}
	
	/**
	 * This is a message from the swing panel to force the nest to have no good parts
	 * @param nestNum The number of the nest that has no good parts
	 */
	public void msgNoGoodPartsFound(int nestNum){
		if (nestNum == 0 || nestNum == 1){
			feeder_zero.msgBadNest(nestNum % 2);
		}
		if (nestNum == 2 || nestNum == 3){
			feeder_one.msgBadNest(nestNum % 2);
		}
		if (nestNum == 4 || nestNum == 5){
			feeder_two.msgBadNest(nestNum % 2);
		}
		if (nestNum == 6 || nestNum == 7){
			feeder_three.msgBadNest(nestNum % 2);
		}
	}
	
	/**
	 * This is a message from the swing panel to force the lane to be jammed
	 * @param nestNum The number of the nest whose lane is jammed
	 */
	public void msgLaneJammed(int nestNum){
		if (nestNum == 0 || nestNum == 1){
			feeder_zero.msgEmptyNest(nestNum % 2);
		}
		if (nestNum == 2 || nestNum == 3){
			feeder_one.msgEmptyNest(nestNum % 2);
		}
		if (nestNum == 4 || nestNum == 5){
			feeder_two.msgEmptyNest(nestNum % 2);
		}
		if (nestNum == 6 || nestNum == 7){
			feeder_three.msgEmptyNest(nestNum % 2);
		}
	}
	
	/**
	 * This is a message from the swing panel to force the diverter to be slow
	 * @param feederNum The number of the feeder whose diverter is slow
	 */
	public void msgSlowDiverter(int feederNum){
		if (feederNum == 0){
			feeder0.msgEmptyNest(feederNum);
		}
		if (feederNum == 1){
			feeder1.msgEmptyNest(feederNum);
		}
		if (feederNum == 2){
			feeder2.msgEmptyNest(feederNum);
		}
		if (feederNum == 3){
			feeder3.msgEmptyNest(feederNum);
		}
	}


	//the following message existed in the wiki, but the parameter is different.  It takes a timer rather than feeder
	//Yeah, that was my fault.  It is no longer needed.
	//	@Override
	//	public void msgMyNestsReadyForPicture(Nest nest, Nest nest2, TimerTask timerTask) {
	//		// TODO Auto-generated method stub
	//		
	//	}


	public void msgAnalyzeKitAtInspection(Kit kit) {
		debug("Received msgAnalyzeKitAtInspection() from the kit robot.");
		kitPictureRequests.add(new KitPicRequest(kit.forceFail));
		stateChanged();
	}


/** ================================================================================ **/
/** 									SCHEDULER 									 **/
/** ================================================================================ **/

	public boolean pickAndExecuteAnAction() {

		synchronized (pictureRequests) {
			for (PictureRequest pr : pictureRequests) {

				if (pr.state == PictureRequestState.PARTS_ROBOT_CLEAR) {
					Nest one = this.nests.get(this.nests.indexOf(pr.nestOne));
					Nest two = this.nests.get(this.nests.indexOf(pr.nestTwo));

					if (one.getPart().name == pr.nestOne.getPart().name
							&& two.getPart().name == pr.nestTwo.getPart().name) {
						takePicture(pr);
					}
					return true;
				}
			}
			for (PictureRequest pr : pictureRequests) {
				if (pr.state == PictureRequestState.NESTS_READY) {
					processPictureRequest(pr);
					return true;
				}
			}
		}
		for(KitPicRequest k: kitPictureRequests){
			if(k.state == kitPictureRequeststate.NEED_TO_INSPECT){
				inspectKit(k);
				return true;
			}
		}

		return false;
	}

/** ================================================================================ **/
/** 									ACTIONS 									 **/
/** ================================================================================ **/

	private void processPictureRequest(PictureRequest pr){

		pr.state = PictureRequestState.ANALYZED;
		
		pr.nestOneState = calculateNestState(pr.nestOne);
		pr.nestTwoState = calculateNestState(pr.nestTwo);
	}
	
	private void inspectKit(KitPicRequest k) {

		try{
			pictureAllowed.acquire();
		}
		catch(Exception ex){

		}

		DoAnimationTakePictureOfInstpectionSlot();
		
		if (k.forceFail == true)
			stand.msgResultsOfKitAtInspection(KitState.FAILED_INSPECTION);
		else
			stand.msgResultsOfKitAtInspection(KitState.PASSED_INSPECTION);
		/*
		int randomNum = r.nextInt(11);
		if(randomNum < 4)
			stand.msgResultsOfKitAtInspection(KitState.PASSED_INSPECTION);
		else
			stand.msgResultsOfKitAtInspection(KitState.PASSED_INSPECTION);
			*/

		pictureAllowed.release();

		k.state = kitPictureRequeststate.INSPECTED;
	}

	private void takePicture(PictureRequest pr){
		try{
			pictureAllowed.acquire();
			DoAnimationTakePictureOfNest(pr.nestOne);

			partsRobot.msgPictureTaken(pr.nestOne, pr.nestTwo);
			if(true) {
				partsRobot.msgHereArePartCoordinatesForNest(pr.nestOne, pr.nestOne.getPart(), r.nextInt(9));
			}

			if(true) {

				partsRobot.msgHereArePartCoordinatesForNest(pr.nestTwo, pr.nestTwo.getPart(), r.nextInt(9));
			}

			pictureRequests.remove(pr);
			pictureAllowed.release();
			stateChanged();
		}
		catch(Exception ex){}
	}

	private void checkLineOfSight(PictureRequest pr){
		debug("Executing checkLineOfSight()");
		partsRobot.msgClearLineOfSight(pr.nestOne, pr.nestTwo);
		pr.state = PictureRequestState.ASKED_PARTS_ROBOT;
	}

/** ================================================================================ **/
/** 									ANIMATIONS 									 **/
/** ================================================================================ **/
	
	private void DoAnimationTakePictureOfInstpectionSlot() {
		debug("Executing DoAnimationTakePictureOfInstpectionSlot()");
		server.command(this,"va kam cmd takepictureofinspection");
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


	}
	
	private void DoAnimationTakePictureOfNest(Nest nest) {

		debug("Executing DoAnimationTakePicture()");
		server.command(this,"va lm cmd takepictureofnest " + nest.getPosition()/2);
		try {
			animation.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

/** ================================================================================ **/
/** 									HELPERS 									 **/
/** ================================================================================ **/

	private int calculateNestState(Nest nest){
		
		// Check if the nest is not used
		if(!nest.isBeingUsed()) {
			return 0;
		}
		
		return 0;
	}
	
	public void setFeeder(Feeder feeder, int feederNum){
		if (feederNum == 0){
			this.feeder_zero = feeder;
		}
		if (feederNum == 1){
			this.feeder_one = feeder;
		}
		if (feederNum == 2){
			this.feeder_two = feeder;
		}
		if (feederNum == 3){
			this.feeder_three = feeder;
		}
	}

	public void setPartsRobot(PartsRobot pr) {
		this.partsRobot = pr;
	}


}