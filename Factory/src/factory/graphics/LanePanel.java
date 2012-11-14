package factory.graphics;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import factory.client.*;

public class LanePanel extends GraphicPanel implements ActionListener{
	
	public static final int WIDTH = 500, HEIGHT = 720;
	
	public LanePanel(JFrame LM) {
		super();
		isLaneManager = true;
		
		if (LM instanceof Client)
			am = (Client)LM;
		// Parts robot client
		// Add 8 nests
		nests = new ArrayList<Nest>();	
		for(int i = 0; i < 8; i++)
		{
			Nest newNest = new Nest(50,i*80+50,0,0,0,0,75,75,"Images/nest3x3.png");
			Random randomGen = new Random();
			for(int j = 0; j < randomGen.nextInt(5)+4; j++)
				newNest.addItem(new GraphicItem(20,20,"Images/eyesItem.png"));
			nests.add(newNest);
		}
		lane = new GraphicLaneManager [4];
		for (int i = 0; i < lane.length; i++)
			lane[i] = new GraphicLaneManager(50, 160*i + 50, i, this);
		
		(new Timer(50, this)).start();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setVisible(true);
	}
	
	public void cameraFlash() {
		
	}
	
	public void feedLane(int laneNum){ //FEEDS THE LANE! Lane 0-7
		/*//Testing for quick feed
		lane[(laneNum) / 2].bin = new GraphicBin(new Part("eyes"));
		lane[(laneNum) / 2].binExist = true;
		//end Test*/
		if(lane[(laneNum) / 2].binExist && lane[(laneNum) / 2].bin.getBinItems().size() > 0){
			lane[(laneNum) / 2].laneStart = true;
			lane[(laneNum) / 2].divergeUp = ((laneNum- 1) % 2 == 0);
			lane[(laneNum) / 2].feederOn = true;
		}
		//System.out.println("bin size " + lane[(laneNum) / 2].bin.getBinItems().size());
	}
	
	public void startLane(int laneNum){
		lane[(laneNum) / 2].laneStart = true;
	}
	
	public void switchLane(int laneNum){
		lane[(laneNum) / 2].divergeUp = !lane[(laneNum) / 4].divergeUp;
		lane[(laneNum) / 2].vY = -(lane[(laneNum) / 4].vY);
	}
	
	public void switchFeederLane(int feederNum){
		// MINH, CAN YOU MAKE THIS LIKE THE FUNCTION ABOVE, BUT BASED ON THE FEEDER NUMBER?
		// thanks
	}
	
	public void stopLane(int laneNum){
		lane[(laneNum) / 2].laneStart = false;
	}
	
	public void turnFeederOnLane(int laneNum){
		lane[(laneNum) / 2].feederOn = true;
	}
	
	public void turnFeederOffLane(int laneNum){
		lane[(laneNum) / 2].feederOn = false;
	}
	
	public void turnFeederOn(int feederNum){
		lane[feederNum].feederOn = true;
	}

	public void turnFeederOff(int feederNum){
		lane[feederNum].feederOn = false;
	}
	
	public void purgeFeeder(int feederNum){ // takes in lane 0 - 4
		lane[(feederNum)].bin = null;
		lane[(feederNum)].binExist = false;
		lane[(feederNum)].feederOn = false;
	}
	
	public void purgeLane(int laneNum){
		if((laneNum) % 2 == 0)
			lane[(laneNum) / 2].lane1PurgeOn = true;
		else
			lane[(laneNum) / 2].lane2PurgeOn = true;
		lane[(laneNum) / 2].feederOn = false;
		lane[(laneNum) / 2].laneStart = false;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}
	
	public static void main(String args[]) {
		JFrame f = new JFrame();
		LanePanel lp = new LanePanel(f);
		f.add(lp);
		f.setVisible(true);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
