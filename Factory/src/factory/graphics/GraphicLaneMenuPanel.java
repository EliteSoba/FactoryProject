//Minh La

package factory.graphics;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;


public class GraphicLaneMenuPanel extends JPanel implements ActionListener {

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

