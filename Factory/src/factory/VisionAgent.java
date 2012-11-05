package factory;

import java.util.ArrayList;
import java.util.TimerTask;

import agent.Agent;
import java.util.*;


import factory.interfaces.*;

public class VisionAgent extends Agent implements Vision {
	
	enum KitPicRequestState { NEED_TO_INSPECT, INSPECTED }
	public enum InspectionResults { PASSED, FAILED }
	enum PictureRequestState { NESTS_READY, ASKED_PARTS_ROBOT, PARTS_ROBOT_CLEAR, PICTURE_TAKEN }
	
	ArrayList<PictureRequest> picRequests = new ArrayList<PictureRequest>(); 
	ArrayList<KitPicRequest> kitPicRequests = new ArrayList<KitPicRequest>();     
	KitRobot kitRobot;
	PartsRobot partsRobot;
	Stand stand;
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
	
	
	//the following message existed in the wiki, but the parameter is different.  It takes a timer rather than feeder
	@Override
	public void msgMyNestsReadyForPicture(Nest nest, Nest nest2,
			TimerTask timerTask) {
		// TODO Auto-generated method stub
		
	}
	
	//this message was not in the wiki, but existed in the interface.  comes from stand agent
	@Override
	public void msgAnalyzeKitAtInspection(Kit kit) {
		// TODO Auto-generated method stub
		
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
				inspectKit(k);
			}
		}

		return false;
	}
		
	// *** ACTIONS ***
	private void inspectKit(KitPicRequest k) {
		   int randomNum = r.nextInt(11); //Random.new(0,10);
		   if (randomNum == 0) {
		      k.inspectionResults = InspectionResults.FAILED;
		   }
		   else {
		      k.inspectionResults = InspectionResults.PASSED;
		   }
		   k.state = KitPicRequestState.INSPECTED;
		   stand.msgInspectionResults(k.inspectionResults); //can't pass enum.  change to a string?
		}
		private void takePicture(PictureRequest pr){
		   int randomNumberOne = r.nextInt(3);
		   int randomNumberTwo = r.nextInt(3);
		   DoTakePicture();
		   partsRobot.msgPictureTaken(pr.nestOne, pr.nestTwo);
		   if(randomNumberOne == 0) {
		      pr.coordinateOne = new Coordinate(10, 10); //the parameters for coordinates don't mean anything in this case, or does it?
		      partsRobot.msgHereArePartCoordiantes(pr.nestOne.part, pr.coordinateOne);
		   }
		   else if(randomNumberOne == 1) {
		      pr.feeder.msgBadNest(pr.nestOne);
		   }
		   else if(randomNumberOne == 2) {
		      pr.feeder.msgEmptyNest(pr.nestOne);
		   }

		   if(randomNumberTwo == 0) {
		      pr.coordinateTwo = new Coordinate(10, 10); 
		      partsRobot.msgHereArePartCoordiantes(pr.nestTwo.part, pr.coordinateTwo);
		   }
		   else if(randomNumberTwo == 1) {
		      pr.feeder.msgBadNest(pr.nestTwo);
		   }
		   else if(randomNumberTwo == 2) {
		      pr.feeder.msgEmptyNest(pr.nestTwo);
		   }
		   picRequests.remove(pr);
		}

		private void DoTakePicture() {
			// TODO Auto-generated method stub
			
		}
		private void checkLineOfSight(PictureRequest pr){
		   partsRobot.msgClearLineOfSight(pr.nestOne, pr.nestTwo);
		   pr.state = PictureRequestState.ASKED_PARTS_ROBOT;
		}

	
	
	
}



