package factory;

import java.util.ArrayList;
import agent.Agent;
import java.util.*;


import factory.interfaces.*;

public class VisionAgent extends Agent implements Vision {
	
	enum KitPicRequestState { NEED_TO_INSPECT, INSPECTED }
	enum InspectionResults { PASSED, FAILED }
	enum PictureRequestState { NESTS_READY, ASKED_PARTS_ROBOT, PARTS_ROBOT_CLEAR, PICTURE_TAKEN }
	
	ArrayList<PictureRequest> picRequests = new ArrayList<PictureRequest>(); 
	ArrayList<KitPicRequest> kitPicRequests = new ArrayList<KitPicRequest>();     
	KitRobot kitRobot;
	PartsRobot partsRobot;
	Random r = new Random();
	
	class KitPicRequest {
	      
	      KitPicRequestState state;
	      InspectionResults inspectionResults;

	      public KitPicRequest(KitPicRequestState kprs) { 
	    	  state = kprs;
	    	}
	   }

	class PictureRequest {
	      
	      Nest nestOne;
	      Nest nestTwo;
	      PictureRequestState state;
	      Feeder feeder;
	      Coordinate coordinateOne;
	      Coordinate coordinateTwo;

	      public PictureRequest(Nest nestOne, Nest nestTwo, Feeder feeder){
	            this.state = PictureRequestState.NESTS_READY;
	            this.nestOne = nestOne;
	            this.nestTwo = nestTwo;
	            this.feeder = feeder;
	      }
	   } 


	
	
	// *** MESSAGES ***
	public void inspectKitStand() {
		kitPicRequests.add(new KitPicRequest(KitPicRequestState.NEED_TO_INSPECT));
	}
	public void msgMyNestsReadyForPicture(Nest nestOne, Nest nestTwo, Feeder feeder) {
		picRequests.add(new PictureRequest(nestOne, nestTwo, feeder));
	}
		
	public void msgNestVisionClear(Nest nestOne, Nest nestTwo) {
		for( PictureRequest pr: picRequests) {
		   if(pr.nestOne == nestOne && pr.nestTwo == nestTwo){
		      pr.state = PictureRequestState.PARTS_ROBOT_CLEAR;
		   }
		}
	}
	// *** SCHEDULER ***
	public boolean pickAndExecuteAnAction() {
		
		for(PictureRequest pr: picRequests){
			if(pr.state == PictureRequestState.PARTS_ROBOT_CLEAR){
				takePicture(pr);
			}
		}
		
		for(PictureRequest pr: picRequests){
			if(pr.state == PictureRequestState.NESTS_READY){
				checkLineOfSight(pr);
			}
		}
		
		for(KitPicRequest k: kitPicRequests){
			if(k.state == KitPicRequestState.NEED_TO_INSPECT){
				takePicture(k);
			}
		}

		return false;
	}
		
	// *** ACTIONS ***
	private void inspectKit(KitPicRequest k) {
		   int randomNum = r.nextInt //Random.new(0,10);
		   if (randomNum == 0) {
		      k.inspectionResults = FAILED;
		   }
		   else {
		      k.inspectionResults = PASSED;
		   }
		   k.state = INSPECTED;
		   kitRobot.msgInspectionResults(k.inspectionResults);
		}
		private void takePicture(PictureRequest pr){
		   int randomNumberOne = Random.new(0,2);
		   int randomNumberTwo = Random.new(0,2);
		   DoTakePicture();
		   partsRobot.msgPictureTaken(pr.nestOne, pr.nestTwo);
		   if(randomNumberOne == 0) {
		      pr.coordinateOne = new Coordinate(); 
		      partsRobot.msgHereArePartCoordiantes(pr.nestOne.part, pr.coordinateOne);
		   }
		   else if(randomNumberOne == 1) {
		      pr.feeder.msgBadNest(pr.nestOne);
		   }
		   else if(randomNumberOne == 2) {
		      pr.feeder.msgEmptyNest(pr.nestOne);
		   }

		   if(randomNumberTwo == 0) {
		      pr.coordinateTwo = new Coordinate(); 
		      partsRobot.msgHereArePartCoordiantes(pr.nestTwo.part, pr.coordinateTwo);
		   }
		   else if(randomNumberTwo == 1) {
		      pr.feeder.msgBadNest(pr.nestTwo);
		   }
		   else if(randomNumberTwo == 2) {
		      pr.feeder.msgEmptyNest(pr.nestTwo);
		   }
		   picRequests.delete(pr);
		}

		private void checkLineOfSight(PictureRequest pr){
		   partsRobot.msgClearLineOfSight(pr.nestOne, pr.nestTwo);
		   pr.state = PictureRequestState.ASKED_PARTS_ROBOT;
		}
	
	
	
}



