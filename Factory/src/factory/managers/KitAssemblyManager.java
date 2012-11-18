//Ben Mayeux, Stephanie Reagle, Marc Mendiola
//CS 200

package factory.managers;

import java.awt.*;
import java.util.ArrayList;

import factory.KitConfig;
import factory.Part;
import factory.client.Client;
import factory.graphics.FactoryProductionPanel;
import factory.graphics.KitAssemblyPanel;
import factory.swing.FactoryProdManPanel;
import factory.swing.KitAssManPanel;

public class KitAssemblyManager extends Client {
	private static final long serialVersionUID = -4230607892468748490L;
	
	KitAssManPanel buttons;
	KitAssemblyPanel animation;

		public KitAssemblyManager() {
			super(Client.Type.kam, null, null);
			
			buttons = new KitAssManPanel(this);
			animation = new KitAssemblyPanel(this);
			
			this.setInterface();
		}
		public static void main(String[] args){
			KitAssemblyManager k = new KitAssemblyManager();
		}

		public void setInterface() {
			graphics = animation;
			UI = buttons;
			
			setLayout(new BorderLayout());

			add(graphics, BorderLayout.CENTER);
			pack();
			
			//add(UI, BorderLayout.LINE_END); //to be implemented in V.2
			setVisible(true);

		}

		@Override
public void doCommand(ArrayList<String> pCmd) {
			int size = pCmd.size();
			//parameters lay between i = 2 and i = size - 2
			String action = pCmd.get(0);
			String identifier = pCmd.get(1);
			System.out.println("Got command");
			System.out.println(action);
			System.out.println(identifier);
			if(action.equals("cmd")) {
				//Graphics Receive Commands
				
			
				// Commands from FeederAgent
				
				// Commands from GantryAgent:
				
				// Commands from PartsRobotAgent
				if (identifier.equals("putpartinkit"))
				{
					int kitNumber = Integer.valueOf(pCmd.get(2));
					((FactoryProductionPanel) graphics).movePartsRobotToStation(kitNumber);
				}
				else if (identifier.equals("movetostand"))
				{
					int kitIndex = Integer.valueOf(pCmd.get(2));
					((FactoryProductionPanel) graphics).movePartsRobotToStation(kitIndex); //not sure if this is the right method
				}
				else if (identifier.equals("droppartsrobotsitems"))
				{
					((FactoryProductionPanel) graphics).dropPartsRobotsItems();
				}
				else if (identifier.equals("movetonest"))
				{
					int nestIndex = Integer.valueOf(pCmd.get(2));
					int itemIndex = Integer.valueOf(pCmd.get(3));
					((FactoryProductionPanel) graphics).movePartsRobotToNest(nestIndex, itemIndex);
				}
				else if (identifier.equals("movetocenter"))
				{
					((FactoryProductionPanel) graphics).movePartsRobotToCenter();
				}

				// End Commands from PartsRobotAgent
				
				// Commands from KitRobotAgent
				else if (identifier.equals("putinspectionkitonconveyor")) {
					((FactoryProductionPanel) graphics).moveKitFromInspectionToConveyor();
				}
				else if (identifier.equals("putemptykitatslot")) {
					if (pCmd.get(2).equals("topSlot")) {
						((FactoryProductionPanel) graphics).moveEmptyKitToSlot(0);
					} else if (pCmd.get(2).equals("bottomSlot")) {
						((FactoryProductionPanel) graphics).moveEmptyKitToSlot(1);
					}
				}
				else if (identifier.equals("movekittoinspectionslot")) {
					if (pCmd.get(2).equals("topSlot")) {
						((FactoryProductionPanel) graphics).moveKitToInspection(0);
					} else if (pCmd.get(2).equals("bottomSlot")) {
						((FactoryProductionPanel) graphics).moveKitToInspection(1);
					}
				}
				else if (identifier.equals("dumpkitatslot")) {
					if (pCmd.get(2).equals("topSlot")) {
						((FactoryProductionPanel) graphics).dumpKitAtSlot(0);
					} else if (pCmd.get(2).equals("bottomSlot")) {
						((FactoryProductionPanel) graphics).dumpKitAtSlot(1);
					} else if (pCmd.get(2).equals("inspectionSlot")) {
						((FactoryProductionPanel) graphics).dumpKitAtInspection();
					}
				}

				// Commands from ConveyorAgent
				else if (identifier.equals("exportkitfromcell")) {
					((FactoryProductionPanel) graphics).exportKit();
				}
				
				// Commands from ConveyorControllerAgent
				else if (identifier.equals("emptykitenterscell")) {
					((FactoryProductionPanel) graphics).newEmptyKit();
				}
				
				//Swing Receive Commands
			}
			
			else if(action.equals("req")){
			}
			
			else if(action.equals("get")){
			}
			
			else if(action.equals("set")){
			}
			
			else if(action.equals("cnf")){
			}
			
		   else if(action.equals("err")){
				String error;
				error = new String();
				for(int i = 1; i<this.parsedCommand.size(); i++)
					error.concat(parsedCommand.get(i));
				System.out.println(error);
		   }
    else 
       System.out.println("Stuff is FU with the server...\n(string does not contain a command type)");
}
}
