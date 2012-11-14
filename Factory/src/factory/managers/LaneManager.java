//Ben Mayeux and Stephanie Reagle
//CS 200

package factory.managers;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import factory.graphics.*;
import factory.client.Client;

public class LaneManager extends Client {
	private static final long serialVersionUID = 1L;


	public LaneManager(JPanel animation) {
		
		super(Client.Type.LM, null, animation); 
		setInterface();
	}
	
	public static void main(String[] args){
		//LaneManPanel buttons = new LaneManPanel(); //to be implemented in V.2
		JPanel animation = new FactoryProductionPanel(null);
		LaneManager l = new LaneManager(animation); //to be implemented in V.2
		//buttons.setManager(l);  //to be implemented in V.2
		
	}
	
	public void sendMessage(int lane, int setting, String message){
		String set = new String("");
		/*
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
		}*/ // to be implemented in V.2
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
		
		/*c.gridx = 2;
		add(UI, c);*/ //to be implemented in V.2
		setVisible(true);
	}

	public void doCommand(ArrayList<String> pCmd) {
		int size = pCmd.size();
		//parameters lay between i = 2 and i = size - 2
		String action = pCmd.get(0);
		String identifier = pCmd.get(1);
		if(action == "cmd"){
			/*if(identifier == command1)
			 * do(command1);
			 * else if(identifier == command2)
			 * do(command2);
			 */
		}
		else if(action == "req"){
			/*if(identifier == request1)
			 * do(request1);
			 * else if(identifier == request2)
			 * do(request2);
			 */
		}
		else if(action == "get"){
			/*if(identifier == get1)
			 * do(get1);
			 * else if(identifier == get2)
			 * do(get2);
			 */
		}
		else if(action == "set"){
			/*if(identifier == set1)
			 * do(set1);
			 * else if(identifier == set2)
			 * do(set2);
			 */
		}
		else if(action == "cnf"){
			/*if(identifier == confirm1)
			 * do(confirm1);
			 * else if(identifier == confirm2)
			 * do(confirm2);
			 */
		}
	}
}
