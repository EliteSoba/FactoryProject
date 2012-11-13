//Ben Mayeux and Stephanie Reagle
//CS 200

package factory.managers;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import factory.graphics.*;
import factory.client.Client;
import factory.graphics.GraphicLaneGraphicPanel;
import factory.graphics.GraphicLaneManagerClient;
import factory.swing.LaneManPanel;

public class LaneManager extends Client {
	private static final long serialVersionUID = 1L;


	public LaneManager(JPanel buttons) {
		
		super(Client.Type.LANEMANAGER, buttons, null); 
		setInterface();
	}
	
	public static void main(String[] args){
		LaneManPanel buttons = new LaneManPanel();
		//GraphicPanel animation = new GraphicPanel(null);
		LaneManager l = new LaneManager(buttons);
		buttons.setManager(l);
		
	}
	
	public void sendMessage(int lane, int setting, String message){
		String set = new String("");
		if (message == "power"){
			set = "lm lma lanepowertoggle "+ lane;
		}
		else if (message == "red"){
			set = "lm lma set lanevibration "+ lane + " " + setting;
		}
		else if (message == "yellow"){
			set = "lm lma set lanevibration "+ lane + " " + setting;
		}
		else if (message == "green"){
			set = "lm lma set lanevibration "+ lane + " " + setting;
		}
		sendCommand(set);
	}
	
	public void setInterface() {
		setSize(1780, 720);
		GridBagConstraints c = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		add(graphics, c);
		
		c.gridx = 2;
		add(UI, c);
		setVisible(true);
	}

	@Override
	public void doCommand(ArrayList<String> pCmd) {
		// TODO Auto-generated method stub
		
	}

}
