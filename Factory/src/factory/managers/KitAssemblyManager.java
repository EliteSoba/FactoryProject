//Ben Mayeux, Stephanie Reagle, Marc Mendiola
//CS 200

package factory.managers;

import java.awt.*;
import java.util.ArrayList;

import factory.client.Client;
import factory.graphics.KitAssemblyPanel;
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
	if(action.equals("cmd")){
		/*if(identifier.equals(command1))
		 * do(command1);
		 * else if(identifier.equals(command2))
		 * do(command2);
		 */
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
		/*if(identifier.equals(set1))
		 * do(set1);
		 * else if(identifier.equals(set2))
		 * do(set2);
		 */
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
