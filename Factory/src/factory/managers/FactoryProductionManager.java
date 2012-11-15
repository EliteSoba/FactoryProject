//Ben Mayeux and Stephanie Reagle
//CS 200

// Last edited: 11/11/12 3:27pm by Joey Huang
package factory.managers;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import factory.client.Client;
import factory.graphics.FactoryProductionPanel;
import factory.swing.FactoryProdManPanel;

public class FactoryProductionManager extends Client {
	static final long serialVersionUID = -2074747328301562732L;

	public FactoryProductionManager(JPanel buttons, FactoryProductionPanel animation) {
		super(Client.Type.FPM, buttons, animation);
		setInterface();
	}
	public static void main(String[] args){
		FactoryProdManPanel buttons = new FactoryProdManPanel();
		FactoryProductionPanel animation = new FactoryProductionPanel(null); //TODO does not currently work but will by 11/13 -->Tobi
		FactoryProductionManager f = new FactoryProductionManager(buttons, animation);
		buttons.setManager(f);
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


	public void sendDone(String process) { //sends message out from graphics (Tobi's Function)
		//Process changes depending on previous command
		sendCommand(process);
	}

	@Override
	public void doCommand(ArrayList<String> pCmd) {
		int size = pCmd.size();
		//parameters lay between i = 2 and i = size - 2
		String action = pCmd.get(0);
		String identifier = pCmd.get(1);
		if(action == "cmd"){
			/*
			 * fa fpm cmd startFeeding " + feederSlot + " endcmd
			 */
			if (identifier == "startFeeding")
			{
				int feederSlot = Integer.valueOf(pCmd.get(3));
				((FactoryProductionPanel) graphics).turnFeederOn(feederSlot);
			}
			else if (identifier == "stopFeeding")
			{
				int feederSlot = Integer.valueOf(pCmd.get(3));
				((FactoryProductionPanel) graphics).turnFeederOff(feederSlot);
			} 
			else if (identifier == "purgeFeeder")
			{
				int feederSlot = Integer.valueOf(pCmd.get(3));
				((FactoryProductionPanel) graphics).purgeFeeder(feederSlot);
			}
			else if (identifier == "switchLane")
			{
				int feederSlot = Integer.valueOf(pCmd.get(3));
				((FactoryProductionPanel) graphics).switchFeederLane(feederSlot);
			}
			/*if(identifier == command1)
			 * do(command1);
			 * else if(identifier == command2)
			 * do(command2);
			 */

			// add kit
			// remove kit
			// modify kit
			// add part
			// remove part
			// update kit
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
