package factory.graphics;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import factory.client.*;
import factory.*;

public class LanePanel extends GraphicPanel implements ActionListener{
	
	public LanePanel(JFrame LM) {
		super(485);
		isLaneManager = true;
		
		WIDTH = 450;
		
		if (LM instanceof Client)
			am = (Client)LM;
		// Parts robot client
		// Add 8 nests
		/*nests = new ArrayList<GraphicNest>();	
		for(int i = 0; i < 8; i++)
		{
			GraphicNest newNest = new GraphicNest(35,i*80+50,0,0,0,0,75,75,"Images/nest3x3.png");
			Random randomGen = new Random();
			for(int j = 0; j < randomGen.nextInt(5)+4; j++)
				newNest.addItem(new GraphicItem(20,20,"Images/eyesItem.png"));
			nests.add(newNest);
		}
		lane = new GraphicLaneManager [4];
		for (int i = 0; i < lane.length; i++)
			lane[i] = new GraphicLaneManager(35, 160*i + 50, i, this);*/
		
		(new Timer(delay, this)).start();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setVisible(true);
	}
	
	/*public void cameraFlash(int nestIndex) {
		flashCounter = 10;
		flashFeederIndex = nestIndex;
	}
	
	public void loadLane(int laneNum, GraphicBin bin) {
		lane[laneNum].setBin(bin);
	}
	
	public void feedLane(int laneNum){ //FEEDS THE LANE! Lane 0-7
		///Testing for quick feed
		lane[(laneNum) / 2].bin = new GraphicBin(new Part("eyes"));
		lane[(laneNum) / 2].binExist = true;
		//end Test/
		if(lane[(laneNum) / 2].binExists && lane[(laneNum) / 2].bin.getBinItems().size() > 0){
			lane[(laneNum) / 2].laneStart = true;
			lane[(laneNum) / 2].divergeUp = ((laneNum) % 2 == 0);
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
	
	public void purgeFeeder(int feederNum){ // takes in lane 0 - 3
		lane[(feederNum)].bin = null;
		lane[(feederNum)].binExists = false;
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
	
	public void moveLanes() {
		for (int i = 0; i < lane.length; i++)
			lane[i].moveLane();
	}*/
	
	public void actionPerformed(ActionEvent arg0) {
		moveLanes();
		
		repaint();
	}
	
	public static void main(String args[]) {
		JFrame f = new JFrame();
		LanePanel lp = new LanePanel(f);
		f.add(lp);
		f.setVisible(true);
		f.pack();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//For testing
		Scanner kbr = new Scanner(System.in);
		String command;
		do {
			command = kbr.nextLine();
			switch (command.charAt(0)) {
			case '`': System.exit(0); break;
			}
		} while(command.charAt(0) != '`');
	}
}
