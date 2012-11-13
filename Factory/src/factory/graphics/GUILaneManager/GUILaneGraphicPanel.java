//Minh La

package factory.graphics.GUILaneManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import factory.Part;

public class GUILaneGraphicPanel extends JPanel implements ActionListener {
	
	// LANE MANAGER
	GUILaneManagerClient client;
	GUILaneManager [] lane;
	
	public static final int WIDTH = 1100, HEIGHT = 720;
	
	// PARTS MANAGER
	private ArrayList<GUINest> nests;
	
	GUILaneManagerClient laneClient;
	
	// GANTRY
	
	public GUILaneGraphicPanel(GUILaneManagerClient lc) {
		lane = new GUILaneManager [4];
		lane[0] = new GUILaneManager(510,50,0,this);
		lane[1] = new GUILaneManager(510,210,1,this);
		lane[2] = new GUILaneManager(510,370,2,this);
		lane[3] = new GUILaneManager(510,530,3,this);
		
		laneClient = lc;
		// Parts robot client
		// Add 8 nests
		nests = new ArrayList<GUINest>();	
		for(int i = 0; i < 8; i++)
		{
			GUINest newNest = new GUINest(510,i*80+50,0,0,0,0,75,75,"Images/nest3x3.png");
			Random randomGen = new Random();
			for(int j = 0; j < randomGen.nextInt(5)+4; j++)
				newNest.addItem(new GUIGraphicItem(20,20,"Images/eyesItem.png"));
			nests.add(newNest);
		}
		
		(new Timer(50, this)).start();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setVisible(true);
	}

	public void paint(Graphics g) {
		g.setColor(new Color(200, 200, 200));
		g.fillRect(0, 0, getWidth(), getHeight());
		if(lane[0] != null && lane[1] != null){
			lane[0].paintLane(g);
			lane[1].paintLane(g);
			lane[2].paintLane(g);
			lane[3].paintLane(g);
		}

		// Parts robot client
		// Draw the nests
		for(int i = 0; i < nests.size(); i++)
		{
			GUINest currentNest = nests.get(i);
			currentNest.paint(g);
		}
		
	}
	public GUILaneManager getLane(int index) {
		return lane[index];
	}
	
	public void cameraFlash()
	{
		
	}
	
	
	public void feedLane(int laneNum){ //FEEDS THE LANE! Lane 1-8, NOT 0-7
		/*//Testing for quick feed
		lane[(laneNum - 1) / 2].bin = new GraphicBin(new Part("eyes"));
		lane[(laneNum - 1) / 2].binExist = true;
		//end Test*/
		if(lane[(laneNum - 1) / 2].binExist && lane[(laneNum - 1) / 2].bin.getBinItems().size() > 0){
			lane[(laneNum - 1) / 2].laneStart = true;
			lane[(laneNum - 1) / 2].divergeUp = ((laneNum- 1) % 2 == 0);
			lane[(laneNum - 1) / 2].feederOn = true;
		}
		//System.out.println("bin size " + lane[(laneNum - 1) / 2].bin.getBinItems().size());
	}
	
	public void startLane(int laneNum){
		lane[(laneNum - 1) / 2].laneStart = true;
	}
	
	public void switchLane(int laneNum){
		lane[(laneNum - 1) / 2].divergeUp = !lane[(laneNum - 1) / 4].divergeUp;
		lane[(laneNum - 1) / 2].vY = -(lane[(laneNum - 1) / 4].vY);
	}
	
	public void stopLane(int laneNum){
		lane[(laneNum - 1) / 2].laneStart = false;
	}
	
	public void turnFeederOnLane(int laneNum){
		lane[(laneNum - 1) / 2].feederOn = true;
	}
	
	public void turnFeederOffLane(int laneNum){
		lane[(laneNum - 1) / 2].feederOn = false;
	}
	
	public void purgeFeederLane(int feederNum){ // takes in lane 1 - 4
		lane[(feederNum - 1)].bin = null;
		lane[(feederNum - 1)].binExist = false;
		lane[(feederNum - 1)].feederOn = false;
	}
	
	public void purgeLaneLane(int laneNum){
		if((laneNum - 1) % 2 == 0)
			lane[(laneNum - 1) / 2].lane1PurgeOn = true;
		else
			lane[(laneNum - 1) / 2].lane2PurgeOn = true;
		lane[(laneNum - 1) / 2].feederOn = false;
		lane[(laneNum - 1) / 2].laneStart = false;
	}
	
	public ArrayList<GUINest> getNest(){
		return nests;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		repaint();		
	}
}
	
	