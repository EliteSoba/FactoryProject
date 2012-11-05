//Minh La

package factory.graphics;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GraphicLaneGraphicPanel extends JPanel{
	
	GraphicLaneManager lane;
	GraphicBin bin;
	boolean feederHasItems;
	
	public GraphicLaneGraphicPanel(GraphicLaneManager lane, GraphicBin bin){
		this.lane = lane;
		this.bin = bin;
		//itemsIcon = new ImageIcon("./src/image/" + bin.partName + "Stack.png");
		feederHasItems = false;

		this.setPreferredSize(new Dimension(700,200));
		this.setVisible(true);
	}

	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		

		lane.feeder.paintIcon(this, g2, lane.lane_xPos + 280, lane.lane_yPos + 15);

		if(lane.placedBin){
			bin = lane.bin;
			lane.bin.getBinImage().paintIcon(this, g2, lane.lane_xPos + 430, lane.lane_yPos + 30);
			if(lane.binItemCount == 0){
				bin.binItemsStackImage.paintIcon(this,g2,lane.lane_xPos + 440,lane.lane_yPos + 40);
			}
			if(lane.binItemCount == 3){

				bin.binItemsStackImage.paintIcon(this,g2,lane.feederX + 10,lane.feederY + 10);
				/**ORIGINAL be4 infinity parts
				 * for(int i = 0;i < bin.getBinItems().size() ;i = i + (bin.getBinItems().size() / 4)){
					for(int j = 0; j < bin.getBinItems().size() / 4;j++){
						bin.getBinItems().get(i+j).setX(lane.feederX + 10 + j * 20);
						bin.getBinItems().get(i+j).setY(lane.feederY + 10 + (i/(bin.getBinItems().size() / 4)) * 20);
					}
				}
				**/
				lane.placedBin = false;
				feederHasItems = true;
				lane.binItemCount = 0;
			}
			lane.binItemCount++;
		}
		
		if(lane.binExist){
			if(feederHasItems)
				bin.binItemsStackImage.paintIcon(this,g2,lane.feederX + 10,lane.feederY + 10);
			
			if(lane.laneStart){
	
				if(lane.feederOn){
					if(lane.timerCount % 10 == 0){		//Put an item on lane on a timed interval
						if(lane.currentItemCount < lane.bin.getBinItems().size()){
							lane.bin.getBinItems().get(lane.currentItemCount).setX(lane.lane_xPos + 250);
							lane.bin.getBinItems().get(lane.currentItemCount).setY(lane.lane_yPos + 70);
							if(lane.divergeUp){
								lane.bin.getBinItems().get(lane.currentItemCount).setvY(-8);
								lane.bin.getBinItems().get(lane.currentItemCount).divergeUp = false;
							}
							else{
								lane.bin.getBinItems().get(lane.currentItemCount).setvY(8);
								lane.bin.getBinItems().get(lane.currentItemCount).divergeUp = true;
							}
							//System.out.println(bin.getBinItems()[currentItemCount].getvY() + " vY = " + vY);
							lane.bin.getBinItems().get(lane.currentItemCount).setvX(0);
							if(lane.divergeUp)
								lane.lane1Items.add(lane.bin.getBinItems().get(lane.currentItemCount));
							else
								lane.lane2Items.add(lane.bin.getBinItems().get(lane.currentItemCount));
							lane.currentItemCount++;
						}
						else{//bin should be empty. remove items from bin
							for(int i = 0;i<lane.bin.getBinItems().size();i++){
								lane.bin.getBinItems().remove(0);
							}
						}
					}
				}
				
				processLane();
				
					//lane.bin.getBinItems()[i].getImageIcon().paintIcon(this,g2,bin.getBinItems()[i].getX(),lane.bin.getBinItems()[i].getY());
			}
			lane.timerCount++;
		}
		else{
			processLane();
		}
		lane.nest1.paintIcon(this, g2, lane.lane_xPos, lane.lane_yPos);
		lane.nest2.paintIcon(this, g2, lane.lane_xPos, lane.lane_yPos + 80);
		lane.lane1.paintIcon(this,g2,lane.lane_xPos + 40,lane.lane_yPos + 20);
		lane.lane2.paintIcon(this,g2,lane.lane_xPos + 40,lane.lane_yPos + 100);
		lane.divergeLane.paintIcon(this,g2,lane.lane_xPos + 240,lane.lane_yPos + 20);
		/*for(int i = 0;i<lane.bin.getBinItems().size();i++)
			lane.bin.getBinItems().get(i).getImageIcon().paintIcon(this,g2,lane.bin.getBinItems().get(i).getX(),lane.bin.getBinItems().get(i).getY());
		*/
		for(int i = 0;i<lane.lane1Items.size();i++)
			lane.lane1Items.get(i).getImageIcon().paintIcon(this,g2,lane.lane1Items.get(i).getX(),lane.lane1Items.get(i).getY());
		for(int i = 0;i<lane.lane2Items.size();i++)
			lane.lane2Items.get(i).getImageIcon().paintIcon(this,g2,lane.lane2Items.get(i).getX(),lane.lane2Items.get(i).getY());
		for(int i = 0;i<lane.nest1Items.size();i++)
			lane.nest1Items.get(i).getImageIcon().paintIcon(this,g2,lane.nest1Items.get(i).getX(),lane.nest1Items.get(i).getY());
		for(int i = 0;i<lane.nest2Items.size();i++)
			lane.nest2Items.get(i).getImageIcon().paintIcon(this,g2,lane.nest2Items.get(i).getX(),lane.nest2Items.get(i).getY());
	
	} // END Paint function
		
	public void processLane(){
		for(int i = 0;i<lane.lane1Items.size();i++){
			lane.lane1Items.get(i).setX(lane.lane1Items.get(i).getX() + lane.lane1Items.get(i).getvX());
			lane.lane1Items.get(i).setY(lane.lane1Items.get(i).getY() + lane.lane1Items.get(i).getvY());
			//Lane items move vertically
			if(lane.lane1Items.get(i).getvY() == lane.vY || lane.lane1Items.get(i).getvY() == -(lane.vY) ){
				lane.lane1Items.get(i).setStepY(lane.lane1Items.get(i).getStepY() - 1);
				if(lane.lane1Items.get(i).getStepY() == 0){
					lane.lane1Items.get(i).setvY(0);
					lane.lane1Items.get(i).setvX(lane.vX);
				}
			}
			//if nest is not empty, continue moving horizontally
			/*if(lane.nestItems.size() < 8 && lane.lane1Items.get(i).getvY() == 0){
				lane.lane1Items.get(i).setvX(lane.vX);
			}*/
			//Lane items move horizontally
			if(lane.lane1Items.get(i).getvX() == lane.vX){
				lane.lane1Items.get(i).setStepX(lane.lane1Items.get(i).getStepX() - 1);
				//System.out.println(" nest" + lane.nestItems.size());
					if(lane.nest1Items.size() >= 8){
						if(lane.lane1Items.get(i).getStepX() == lane.lane1QueueTaken.size() + 1){
							//System.out.println(" " + lane.laneQueueTaken.size());
							//Queue is full, delete crashing Items
							if(lane.lane1QueueTaken.size() > lane.lane1Items.get(i).stepXSize - 3){
								//System.out.print(" " + lane.laneQueueTaken.size());
								lane.lane1Items.remove(i);
								i--;
							}
							else{
								//System.out.print(" " + lane.nestItems.size());
								//System.out.println(" zero");
								lane.lane1Items.get(i).setvX(0);
								lane.lane1QueueTaken.add(new Boolean(true));
							}
						}
					}
					else if(lane.lane1Items.get(i).getStepX() == 0){
						lane.lane1Items.get(i).setvY(0);
						lane.lane1Items.get(i).setvX(0);
						lane.lane1Items.get(i).setX(lane.lane_xPos + 20 * (int)(lane.nest1Items.size() / 4));
						boolean testDiverge = !lane.lane1Items.get(i).divergeUp;
						lane.lane1Items.get(i).setY(lane.lane_yPos + 20 * (lane.nest1Items.size() % 4) + 80 * ((testDiverge)?0:1));
						lane.nest1Items.add(lane.lane1Items.get(i));
						if(lane.lane1QueueTaken.size() > 0)
							lane.lane1QueueTaken.remove(0);
						lane.lane1Items.remove(i);
						i--;
					}
			}
			//lane.bin.getBinItems()[i].getImageIcon().paintIcon(this,g2,bin.getBinItems()[i].getX(),lane.bin.getBinItems()[i].getY());
		}
		for(int i = 0;i<lane.lane2Items.size();i++){
			lane.lane2Items.get(i).setX(lane.lane2Items.get(i).getX() + lane.lane2Items.get(i).getvX());
			lane.lane2Items.get(i).setY(lane.lane2Items.get(i).getY() + lane.lane2Items.get(i).getvY());
			//Lane items move vertically
			if(lane.lane2Items.get(i).getvY() == lane.vY || lane.lane2Items.get(i).getvY() == -(lane.vY) ){
				lane.lane2Items.get(i).setStepY(lane.lane2Items.get(i).getStepY() - 1);
				if(lane.lane2Items.get(i).getStepY() == 0){
					lane.lane2Items.get(i).setvY(0);
					lane.lane2Items.get(i).setvX(lane.vX);
				}
			}
			//if nest is not empty, continue moving horizontally
			/*if(lane.nestItems.size() < 8 && lane.lane2Items.get(i).getvY() == 0){
				lane.lane2Items.get(i).setvX(lane.vX);
			}*/
			//Lane items move horizontally
			if(lane.lane2Items.get(i).getvX() == lane.vX){
				lane.lane2Items.get(i).setStepX(lane.lane2Items.get(i).getStepX() - 1);
				//System.out.println(" nest" + lane.nestItems.size());
				if(lane.nest2Items.size() >= 8){
					if(lane.lane2Items.get(i).getStepX() == lane.lane2QueueTaken.size() + 1){
						//System.out.println(" " + lane.laneQueueTaken.size());
						//Queue is full, delete crashing Items
						if(lane.lane2QueueTaken.size() > lane.lane2Items.get(i).stepXSize - 3){
							//System.out.print(" " + lane.laneQueueTaken.size());
							lane.lane2Items.remove(i);
							i--;
						}
						else{
							//System.out.print(" " + lane.nestItems.size());
							//System.out.println(" zero");
							lane.lane2Items.get(i).setvX(0);
							lane.lane2QueueTaken.add(new Boolean(true));
						}
					}
				}
				else if(lane.lane2Items.get(i).getStepX() == 0){
					lane.lane2Items.get(i).setvY(0);
					lane.lane2Items.get(i).setvX(0);
					lane.lane2Items.get(i).setX(lane.lane_xPos + 20 * (int)(lane.nest2Items.size() / 4));
					boolean testDiverge = !lane.lane2Items.get(i).divergeUp;
					lane.lane2Items.get(i).setY(lane.lane_yPos + 20 * (lane.nest2Items.size() % 4) + 80 * ((testDiverge)?0:1));
					lane.nest2Items.add(lane.lane2Items.get(i));
					if(lane.lane2QueueTaken.size() > 0)
						lane.lane2QueueTaken.remove(0);
					lane.lane2Items.remove(i);
					i--;
				}
			}
		}
	}
}
	
	