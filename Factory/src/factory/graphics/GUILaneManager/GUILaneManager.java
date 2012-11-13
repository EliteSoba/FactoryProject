//Minh La

package factory.graphics.GUILaneManager;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import factory.Part;

//Parts dumped into bins and fed down the lanes

public class GUILaneManager{
	
	//Image Icons
	ImageIcon lane1Icon, lane2Icon;
	ImageIcon divergeLaneIcon;
	ImageIcon nest1Icon, nest2Icon;
	ImageIcon feederIcon;
	GUIGraphicBin bin;
	
	//Bin coordinates
	int feederX,feederY;
	
	//Items
	ArrayList <GUIGraphicItem> lane1Items;
	ArrayList <GUIGraphicItem> lane2Items;
	//ArrayList <GraphicItem> nest1Items;
	//ArrayList <GraphicItem> nest2Items;
	//Nest nest1, nest2;
	ArrayList <Boolean> lane1QueueTaken;			//The queue
	ArrayList <Boolean> lane2QueueTaken;			//The queue
	
	//variables
	int vX, vY;		//velocities
	int lane_xPos, lane_yPos;	//lane relative position
	boolean laneStart;			//default = false
	boolean divergeUp;			//true - up, false - down
	int timerCount;				//counter to periodic add item to lane
	int binItemCount;			//current item in bin to dump
	int vibrationCount;			//every 2 paint, it'll vibrate
	int laneManagerID;					//lane Manager Number
	//boolean placedBin;			//true is bin is added to feeder
	boolean feederOn;			//Feeder on/off
	boolean binExist;			
	boolean feederHasItems;		//Check to see if feeder has item to. If does, then paint the image
	boolean lane1PurgeOn;
	boolean lane2PurgeOn;
	
	GUILaneGraphicPanel graphicPanel;
	
	public GUILaneManager(int laneX,int laneY, int ID, GUILaneGraphicPanel gp){
		lane_xPos = laneX; lane_yPos = laneY;					//MODIFY to change Lane position
		laneManagerID = ID;
		graphicPanel = gp;
		bin = new GUIGraphicBin(new Part("eyes"));
		//declaration of variables
		lane1Items = new ArrayList<GUIGraphicItem>();
		lane2Items = new ArrayList<GUIGraphicItem>();
		//nest1Items = new ArrayList<GraphicItem>();
		//nest2Items = new ArrayList<GraphicItem>();
		
		//nest1 = new Nest(lane_xPos,lane_yPos,0,0,0,0,75,75,"Images/nest3x3.png");
		//nest2 = new Nest(lane_xPos,lane_yPos + 80,0,0,0,0,75,75,"Images/nest3x3.png");
		
		lane1QueueTaken = new ArrayList<Boolean>();
		lane2QueueTaken = new ArrayList<Boolean>();
		vX = -8; vY = 8;
		laneStart = false;
		divergeUp = false;
		feederOn = false;
		binExist = false;
		feederHasItems = false;
		lane1PurgeOn = false;		//Nest purge is off unless turned on
		lane2PurgeOn = false;		//Nest purge is off unless turned on
		timerCount = 1; binItemCount = 0; vibrationCount = 0;
		
		//Location of bin to appear. x is fixed
		feederX = lane_xPos + 250; feederY = lane_yPos + 15;
		
		/*
		//Declaration of items in bin's location
		for(int i = 0;i < bin.getBinItems().size() ;i = i + 2){
			for(int j = 0; j < bin.getBinItems().size() / 4;j++){
				bin.getBinItems()[i + j].setX(feederX + 10 + j * 20);
				bin.getBinItems()[i + j].setY(feederY + 10 + (i/2) * 20);
			}
		}
		*/
		//Declaration of variables
		lane1Icon = new ImageIcon("Images/lane.png");
		lane2Icon = new ImageIcon("Images/lane.png");
		divergeLaneIcon = new ImageIcon("Images/divergeLane.png");
		nest1Icon = new ImageIcon("Images/nest3x3.png");
		nest2Icon = new ImageIcon("Images/nest3x3.png");
		feederIcon = new ImageIcon("Images/feeder.png");

	
	}	
	
	public void setBin(GUIGraphicBin bin){
		this.bin = bin;
	}
	
	public GUIGraphicBin getBin(){
		return bin;
	}
	
	public void paintLane(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		
		
		g2.drawImage(feederIcon.getImage(), lane_xPos+250, lane_yPos+15, null);
		g2.drawImage(bin.getBinImage().getImage(),lane_xPos+265, feederY+10, null);
		/*
		if(placedBin){
			g2.drawImage(bin.getBinImage().getImage(), lane_xPos+325, lane_yPos+30, null);
			if(binItemCount == 0){
			}
			if(binItemCount == 3){
				placedBin = false;
				feederHasItems = true;
				binItemCount = 0;
			}
			binItemCount++;
		}
		*/
		if(binExist){
			g2.drawImage(bin.getBinImage().getImage(),lane_xPos+265, feederY+10, null);
			//g2.drawImage(bin.getBinImage(), lane_xPos+265, feederY+10, null);
			if(laneStart){
	
				if(feederOn){
					if(timerCount % 10 == 0){		//Put an item on lane on a timed interval
						if(bin.getBinItems().size() > 0){
							bin.getBinItems().get(0).setX(lane_xPos + 220);
							bin.getBinItems().get(0).setY(lane_yPos + 70);
							if(divergeUp){
								bin.getBinItems().get(0).setVY(-8);
								bin.getBinItems().get(0).setDivergeUp(false);
							}
							else{
								bin.getBinItems().get(0).setVY(8);
								bin.getBinItems().get(0).setDivergeUp(true);
							}
							//System.out.println(bin.getBinItems()[currentItemCount].getVY() + " vY = " + vY);
							bin.getBinItems().get(0).setVX(0);
							if(divergeUp)
								lane1Items.add(bin.getBinItems().get(0));
							else
								lane2Items.add(bin.getBinItems().get(0));
							bin.getBinItems().remove(0);
							if(bin.getBinItems().size() == 0)
								feederOn = false;
						}/*
						else{//bin should be empty. remove items from bin
							for(int i = 0;i<bin.getBinItems().size();i++){
								bin.getBinItems().remove(0);
							}
						}*/
					}
				}
				
				processLane();
				
			}
			timerCount++;
		}
		else{
			processLane();
		}
		//g2.drawImage(nest1.getImage(),lane_xPos,lane_yPos,null);
		//g2.drawImage(nest2.getImage(),lane_xPos,lane_yPos + 80,null);
		//g2.drawImage(nest1Icon.getImage(), lane_xPos, lane_yPos, null);
		//g2.drawImage(nest2Icon.getImage(), lane_xPos, lane_yPos+80, null);
		g2.drawImage(lane1Icon.getImage(), lane_xPos+75, lane_yPos+20, null);
		g2.drawImage(lane2Icon.getImage(), lane_xPos+75, lane_yPos+100, null);
		g2.drawImage(divergeLaneIcon.getImage(), lane_xPos+210, lane_yPos+20, null);
		/*for(int i = 0;i<bin.getBinItems().size();i++)
			bin.getBinItems().get(i).getImageIcon().paintIcon(this,g2,bin.getBinItems().get(i).getX(),bin.getBinItems().get(i).getY());
		*/
		for(int i = 0;i<lane1Items.size();i++)
			lane1Items.get(i).paint(g2);
		for(int i = 0;i<lane2Items.size();i++)
			lane2Items.get(i).paint(g2);
//		for(int i = 0;i<nest1.getSize();i++)
//			nest1.items.get(i).paint(g2);
//		for(int i = 0;i<nest2.getSize();i++)
//			nest2.items.get(i).paint(g2);
//	
		vibrationCount++;
	} // END Paint function
		
	public void processLane(){
		for(int i = 0;i<lane1Items.size();i++){
			lane1Items.get(i).setX(lane1Items.get(i).getX() + lane1Items.get(i).getVX());
			lane1Items.get(i).setY(lane1Items.get(i).getY() + lane1Items.get(i).getVY());
			//Lane items move vertically
			if(lane1Items.get(i).getVY() == vY || lane1Items.get(i).getVY() == -(vY) ){
				if(vibrationCount % 4 == 1){	//Vibration left and right every 2 paint calls
					if(i%2 == 0)
						lane1Items.get(i).setX(lane1Items.get(i).getX() - 2);
					else if(i%2 == 1)
						lane1Items.get(i).setX(lane1Items.get(i).getX() + 2);
				}
				else if(vibrationCount % 4 == 3){
					if(i%2 == 0)
						lane1Items.get(i).setX(lane1Items.get(i).getX() + 2);
					else if(i%2 == 1)
						lane1Items.get(i).setX(lane1Items.get(i).getX() - 2);
				}
				lane1Items.get(i).setStepY(lane1Items.get(i).getStepY() - 1);
				if(lane1Items.get(i).getStepY() == 0){
					lane1Items.get(i).setVY(0);
					lane1Items.get(i).setVX(vX);
				}
			}
			//if nest is not empty, continue moving horizontally
			/*if(lane.nestItems.size() < 8 && lane1Items.get(i).getVY() == 0){
				lane1Items.get(i).setvX(vX);
			}*/
			//Lane items move horizontally
			if(lane1Items.get(i).getVX() == vX){
				lane1Items.get(i).setStepX(lane1Items.get(i).getStepX() - 1);
				if(vibrationCount % 4 == 1){	//Vibration up and down every 2 paint calls
					if(i%2 == 0)
						lane1Items.get(i).setY(lane1Items.get(i).getY() - 2);
					else if(i%2 == 1)
						lane1Items.get(i).setY(lane1Items.get(i).getY() + 2);
				}
				else if(vibrationCount % 4 == 3){
					if(i%2 == 0)
						lane1Items.get(i).setY(lane1Items.get(i).getY() + 2);
					else if(i%2 == 1)
						lane1Items.get(i).setY(lane1Items.get(i).getY() - 2);
				}
				//System.out.println(" nest" + lane.nestItems.size());
				if(lane1PurgeOn){
					graphicPanel.getNest().get(laneManagerID * 2).clearItems();
					for(int j = 0; j <lane1Items.size();j++){
						lane1Items.get(j).setVX(vX);
					}
					if(lane1Items.get(i).getStepX() == 0){
						lane1Items.remove(i);
						i--;
					}
					if(lane1Items.size() == 0)
						lane1PurgeOn = false;
				}
				else if(graphicPanel.getNest().get(laneManagerID * 2).getSize() >= 9){
					if(lane1Items.get(i).getStepX() == lane1QueueTaken.size() + 1){
						//System.out.println(" " + lane.laneQueueTaken.size());
						//Queue is full, delete crashing Items
						if(lane1QueueTaken.size() > 17){ // To be changed according to size of lane
							//System.out.print(" " + lane.laneQueueTaken.size());
							lane1Items.remove(i);
							i--;
						}
						else{
							//System.out.print(" " + lane.nestItems.size());
							//System.out.println(" zero");
							lane1Items.get(i).setVX(0);
							lane1QueueTaken.add(new Boolean(true));
						}
					}
				}
				else if(lane1Items.get(i).getStepX() == 0){
					//TO BE CHANGED
					lane1Items.get(i).setVY(0);
					lane1Items.get(i).setVX(0);
					lane1Items.get(i).setX(lane_xPos + 3 + 25 * (int)(graphicPanel.getNest().get(laneManagerID * 2).getSize() / 3));
					boolean testDiverge = !lane1Items.get(i).getDivergeUp();
					lane1Items.get(i).setY(lane_yPos + 3 + 25 * (graphicPanel.getNest().get(laneManagerID * 2).getSize() % 3) + 80 * ((testDiverge)?0:1));
					graphicPanel.getNest().get(laneManagerID * 2).addItem(lane1Items.get(i));
					if(lane1QueueTaken.size() > 0)
						lane1QueueTaken.remove(0);
					lane1Items.remove(i);
					i--;
					/*
					lane1Items.get(i).setvY(0);
					lane1Items.get(i).setvX(0);
					lane1Items.get(i).setX(lane_xPos + 20 * (int)(nest1Items.size() / 4));
					boolean testDiverge = !lane1Items.get(i).divergeUp;
					lane1Items.get(i).setY(lane_yPos + 20 * (nest1Items.size() % 4) + 80 * ((testDiverge)?0:1));
					nest1Items.add(lane1Items.get(i));
					if(lane1QueueTaken.size() > 0)
						lane1QueueTaken.remove(0);
					lane1Items.remove(i);
					i--;
					*/
					
				}
			}
			else{
				if(lane1Items.get(i).getVY() == 0){	//In the queue
					if(vibrationCount % 4 == 1){	//Vibration up and down every 2 paint calls
						if(i%2 == 0)
							lane1Items.get(i).setY(lane1Items.get(i).getY() - 2);
						else if(i%2 == 1)
							lane1Items.get(i).setY(lane1Items.get(i).getY() + 2);
					}
					else if(vibrationCount % 4 == 3){
						if(i%2 == 0)
							lane1Items.get(i).setY(lane1Items.get(i).getY() + 2);
						else if(i%2 == 1)
							lane1Items.get(i).setY(lane1Items.get(i).getY() - 2);
					}
				}
			}
		}
		for(int i = 0;i<lane2Items.size();i++){
			lane2Items.get(i).setX(lane2Items.get(i).getX() + lane2Items.get(i).getVX());
			lane2Items.get(i).setY(lane2Items.get(i).getY() + lane2Items.get(i).getVY());
			//Lane items move vertically
			if(lane2Items.get(i).getVY() == vY || lane2Items.get(i).getVY() == -(vY) ){
				if(vibrationCount % 4 == 1){	//Vibration left and right every 2 paint calls
					if(i%2 == 0)
						lane2Items.get(i).setX(lane2Items.get(i).getX() - 2);
					else if(i%2 == 1)
						lane2Items.get(i).setX(lane2Items.get(i).getX() + 2);
				}
				else if(vibrationCount % 4 == 3){
					if(i%2 == 0)
						lane2Items.get(i).setX(lane2Items.get(i).getX() + 2);
					else if(i%2 == 1)
						lane2Items.get(i).setX(lane2Items.get(i).getX() - 2);
				}
				lane2Items.get(i).setStepY(lane2Items.get(i).getStepY() - 1);
				if(lane2Items.get(i).getStepY() == 0){
					lane2Items.get(i).setVY(0);
					lane2Items.get(i).setVX(vX);
				}
			}
			//if nest is not empty, continue moving horizontally
			/*if(lane.nestItems.size() < 8 && lane2Items.get(i).getVY() == 0){
				lane2Items.get(i).setvX(vX);
			}*/
			//Lane items move horizontally
			if(lane2Items.get(i).getVX() == vX){
				if(vibrationCount % 4 == 1){	//Vibration up and down every 2 paint calls
					if(i%2 == 0)
						lane2Items.get(i).setY(lane2Items.get(i).getY() - 2);
					else if(i%2 == 1)
						lane2Items.get(i).setY(lane2Items.get(i).getY() + 2);
				}
				else if(vibrationCount % 4 == 3){
					if(i%2 == 0)
						lane2Items.get(i).setY(lane2Items.get(i).getY() + 2);
					else if(i%2 == 1)
						lane2Items.get(i).setY(lane2Items.get(i).getY() - 2);
				}
				lane2Items.get(i).setStepX(lane2Items.get(i).getStepX() - 1);
				if(lane2PurgeOn){
					graphicPanel.getNest().get(laneManagerID * 2 + 1).clearItems();
					for(int j = 0; j <lane2Items.size();j++){
						lane2Items.get(j).setVX(vX);
					}
					if(lane2Items.get(i).getStepX() == 0){
						lane2Items.remove(i);
						i--;
					}
					if(lane2Items.size() == 0)
						lane2PurgeOn = false;
				}
				else if(graphicPanel.getNest().get(laneManagerID * 2 + 1).getSize() >= 9){
					if(lane2Items.get(i).getStepX() == lane2QueueTaken.size() + 1){
						//System.out.println(" " + lane.laneQueueTaken.size());
						//Queue is full, delete crashing Items
						if(lane2QueueTaken.size() > 17){
							//System.out.print(" " + lane.laneQueueTaken.size());
							lane2Items.remove(i);
							i--;
						}
						else{
							//System.out.print(" " + lane.nestItems.size());
							//System.out.println(" zero");
							lane2Items.get(i).setVX(0);
							lane2QueueTaken.add(new Boolean(true));
						}
					}
				}
				else if(lane2Items.get(i).getStepX() == 0){ // reaches Nest
					//remove from queue or lane item, add to nest
					lane2Items.get(i).setVY(0);
					lane2Items.get(i).setVX(0);
					lane2Items.get(i).setX(lane_xPos + 3 + 25 * (int)(graphicPanel.getNest().get(laneManagerID * 2 + 1).getSize() / 3));
					boolean testDiverge = !lane2Items.get(i).getDivergeUp();
					lane2Items.get(i).setY(lane_yPos + 3 + 25 * (graphicPanel.getNest().get(laneManagerID * 2 + 1).getSize() % 3) + 80 * ((testDiverge)?0:1));
					graphicPanel.getNest().get(laneManagerID * 2 + 1).addItem(lane2Items.get(i));
					if(lane2QueueTaken.size() > 0)
						lane2QueueTaken.remove(0);
					lane2Items.remove(i);
					i--;
				}
			}
			else{
				if(lane2Items.get(i).getVY() == 0){ // lane 2 queue stopped moving. needs to vibrate
					if(vibrationCount % 4 == 1){	//Vibration up and down every 2 paint calls
						if(i%2 == 0)
							lane2Items.get(i).setY(lane2Items.get(i).getY() - 2);
						else if(i%2 == 1)
							lane2Items.get(i).setY(lane2Items.get(i).getY() + 2);
					}
					else if(vibrationCount % 4 == 3){
						if(i%2 == 0)
							lane2Items.get(i).setY(lane2Items.get(i).getY() + 2);
						else if(i%2 == 1)
							lane2Items.get(i).setY(lane2Items.get(i).getY() - 2);
					}
				}
			}
		}
	}
	/*
	public void refreshNest(){
		for(int i = 0; i < nest1.getSize();i++){
			boolean testDiverge = !lane1Items.get(i).getDivergeUp();
			nest1.items.get(i).setX(lane_xPos + 3 + 25 * (int)(nest1.getSize() / 3));
			nest1.items.get(i).setY(lane_yPos + 3 + 25 * (nest1.getSize() % 3) + 80 * ((testDiverge)?0:1));
		}
		for(int i = 0; i < nest2.getSize();i++){
			boolean testDiverge = !lane2Items.get(i).getDivergeUp();
			nest2.items.get(i).setX(lane_xPos + 3 + 25 * (int)(nest2.getSize() / 3));
			nest2.items.get(i).setY(lane_yPos + 3 + 25 * (nest2.getSize() % 3) + 80 * ((testDiverge)?0:1));
		}
	}*/
}


