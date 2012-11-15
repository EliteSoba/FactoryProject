//Ben Mayeux and Stephanie Reagle
//CS 200

// Last edited: 11/14/12 10:07pm by Joey Huang
package factory.managers;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
			setLayout(new GridLayout(1,2));

			add(graphics);
			
			add(UI);
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
			if (identifier.equals("startFeeding"))
			{
				int feederSlot = Integer.valueOf(pCmd.get(3));
				((FactoryProductionPanel) graphics).turnFeederOn(feederSlot);
			}
			else if (identifier.equals("stopFeeding"))
			{
				int feederSlot = Integer.valueOf(pCmd.get(3));
				((FactoryProductionPanel) graphics).turnFeederOff(feederSlot);
			} 
			else if (identifier.equals("purgeFeeder"))
			{
				int feederSlot = Integer.valueOf(pCmd.get(3));
				((FactoryProductionPanel) graphics).purgeFeeder(feederSlot);
			}
			else if (identifier.equals("switchLane"))
			{
				int feederSlot = Integer.valueOf(pCmd.get(3));
				((FactoryProductionPanel) graphics).switchFeederLane(feederSlot);
			}
			else if (identifier.equals("addkitname")) {		// add new kit configuration to kit configuration list
		
			}
			else if (identifier.equals("rmkitname")) {		// remove kit configuration from kit configuration list
				
			}
			else if (identifier.equals("addpartname")) {	// add new part to parts list
				
			}
			else if (identifier.equals("rmpartname")) {		// remove part from parts list
				
			}
		}
		else if(action.equals("req")){
			/*if(identifier.equals(request1))
			 * do(request1);
			 * else if(identifier.equals(request2))
			 * do(request2);
			 */
		}
		else if(action.equals("get")){
			/*if(identifier.equals(get1))
			 * do(get1);
			 * else if(identifier.equals(get2))
			 * do(get2);
			 */
		}
		else if(action.equals("set")){
			if (identifier.equals("kitcontent")) { 			// modify content of a kit

			}
			else if (identifier.equals("kitsproduced")) { // updates number of kits produced for schedule
				
			}
		}
		else if(action.equals("cnf")){
			/*if(identifier.equals(confirm1))
			 * do(confirm1);
			 * else if(identifier.equals(confirm2))
			 * do(confirm2);
			 */
		}
	   else if(action.equals("err")){
			String error;
			error = new String();
			for(int i = 1; i<this.parsedCommand.size(); i++)
				error.concat(parsedCommand.get(i));
			System.out.println(error);
		
			
		}
	
	}

}
