//Minh La

package factory.graphics;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import factory.Bin;
import factory.BinConfig;
import factory.FeederAgent;
import factory.GantryAgent;
import factory.LaneAgent;
import factory.NestAgent;
import factory.Part;
import factory.FeederAgent.DiverterState;
import factory.VisionAgent;
import factory.test.mock.MockGantry;


public class GraphicLaneMenuPanel extends JPanel implements ActionListener {
	FeederAgent feeder;
	LaneAgent top, bottom;
	NestAgent n0,n1,n2,n3;
	GantryAgent gantry;
	VisionAgent vision;
	Part p0,p1,p2;


	protected void setUp() throws Exception {
		feeder = new FeederAgent("feeder",0);
		feeder.startThread();
		top = new LaneAgent();
		top.startThread();
		bottom = new LaneAgent();
		bottom.startThread();
		gantry = new GantryAgent();
		gantry.startThread();
		vision = new VisionAgent(null,null,null);
		vision.startThread();
		feeder.vision = vision;

		n0 = new NestAgent();
		n0.startThread();
		n1 = new NestAgent();
		n1.startThread();

		n2 = new NestAgent();
		n2.startThread();

		n3 = new NestAgent();
		n3.startThread();

		p0 = new Part("Triangle");
		p0.averageDelayTime = 15; // it takes 20 seconds for this part to go all the way down the lane and then 
								  // start stacking up.  after 20 seconds, the feeder should stop feeding.
		p1 = new Part("Circle");
		p1.averageDelayTime = 12; 
		p2 = new Part("Triangle");
		p2.averageDelayTime = 10;

		n0.setLane(top);
		top.setNest(n0);
		top.setFeeder(feeder);

		n1.setLane(bottom);
		bottom.setNest(n1);
		bottom.setFeeder(feeder);

		feeder.setUpLanes(top, bottom);
		feeder.setGantry(gantry); //for now
		feeder.diverter = DiverterState.FEEDING_BOTTOM;
		
		
		Bin bin0 = new Bin(p0);
		HashMap<Bin, Integer> binMap0 = new HashMap<Bin, Integer>(); 
		binMap0.put(bin0, 0);

		gantry.msgChangeGantryBinConfig(new BinConfig(binMap0));
		
		Bin bin1 = new Bin(p1);
		HashMap<Bin, Integer> binMap1 = new HashMap<Bin, Integer>(); 
		binMap1.put(bin1, 1);

		gantry.msgChangeGantryBinConfig(new BinConfig(binMap1));
	}


	GraphicLaneManager lane;
	GraphicBin bin;

	//Image Icon buttons
	JButton startLane1Button, startLane2Button, placeBinButton,dumpNestButton;

	public GraphicLaneMenuPanel(GraphicLaneManager lane, GraphicBin bin){
		this.lane = lane;
		this.bin = bin;

		startLane1Button = new JButton("Start Lane 1");
		startLane1Button.addActionListener(this);
		startLane2Button = new JButton("Start Lane 2");
		startLane2Button.addActionListener(this);
		placeBinButton = new JButton("Place Bin");
		placeBinButton.addActionListener(this);
		dumpNestButton = new JButton("Dump Nest");
		dumpNestButton.addActionListener(this);

		this.setPreferredSize(new Dimension(700,50));
		this.setVisible(true);
		this.add(placeBinButton);
		this.add(startLane1Button);
		this.add(startLane2Button);
		//this.add(dumpNestButton);

		/* CASES TO TEST:
		 * 1) Single request.
		 * 2) Two different part requests, different lanes
		 * 3) Two different part requests, same lane.
		 * 4) Two of the same kind of part, different lanes.
		 * 5) Two of the same kind of part, same lane.
		 */
		try {
			setUp();
		} catch (Exception e) {
			e.printStackTrace();
		}

		//singlePartRequest(); // TEST CASE #1
		//twoDifferentPartRequestsForDifferentLanes(); // TEST CASE #2
		//twoDifferentPartRequestsForSameLane(); // TEST CASE #3
		twoSamePartRequestsForDifferentLanes(); // TEST CASE #4
	}

	public void singlePartRequest() {
		Bin bin0 = new Bin(p0);
		HashMap<Bin, Integer> binMap = new HashMap<Bin, Integer>(); 
		binMap.put(bin0, 0);


		gantry.msgChangeGantryBinConfig(new BinConfig(binMap));
		n0.msgYouNeedPart(p0);
	}

	public void twoDifferentPartRequestsForDifferentLanes() {
		// First Part:
		Bin bin0 = new Bin(p0);
		HashMap<Bin, Integer> binMap0 = new HashMap<Bin, Integer>(); 
		binMap0.put(bin0, 0);

		gantry.msgChangeGantryBinConfig(new BinConfig(binMap0));
		n0.msgYouNeedPart(p0);

		// Second Part:
		Bin bin1 = new Bin(p1);
		HashMap<Bin, Integer> binMap1 = new HashMap<Bin, Integer>(); 
		binMap1.put(bin1, 1);

		gantry.msgChangeGantryBinConfig(new BinConfig(binMap1));
		n1.msgYouNeedPart(p1);

	}

	public void twoDifferentPartRequestsForSameLane() {
		// First Part:
		n0.msgYouNeedPart(p0);

		
		// Second Part:
		n0.msgYouNeedPart(p1);
	}
	
	public void twoSamePartRequestsForDifferentLanes() {
		// First Part:
		n0.msgYouNeedPart(p0);
		
		// Second Part:
		n1.msgYouNeedPart(p0); 
	}

	public void actionPerformed(ActionEvent ae){
		if(ae.getSource() == placeBinButton){
			if(!lane.placedBin){
				GraphicBin b = new GraphicBin();
				lane.bin = b;
				lane.currentItemCount = 0;
				lane.placedBin = true;
			}
		}
		else if(ae.getSource() == startLane1Button){
			//if(!lane.laneStart){
			lane.laneStart = true;
			lane.divergeUp = true;
			lane.vY = -8;
			//}
		}
		else if(ae.getSource() == startLane2Button){
			//if(!lane.laneStart){
			lane.laneStart = true;
			lane.divergeUp = false;
			lane.vY = 8;
			//}
		}

		else if(ae.getSource() == dumpNestButton){
			lane.nest1Items.clear();
		}
	}
}

