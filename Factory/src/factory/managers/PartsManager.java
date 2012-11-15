//Ben Mayeux and Stephanie Reagle and Marc Mendiola
//CS 200
package factory.managers;

import java.awt.BorderLayout;
import java.util.ArrayList;

import factory.client.Client;
import factory.swing.PartsManPanel;

public class PartsManager extends Client {
	private static final long serialVersionUID = -205350261062308096L;
	
	ArrayList<String> parts;
	// Kit Configurations ArrayList

	public PartsManager() {
		super(Client.Type.PM, null, null);
		UI = new PartsManPanel();
		((PartsManPanel) UI).setManager(this);
		setInterface();
		parts = new ArrayList<String>();
	}
	
	public static void main(String[] args){
		PartsManager manager = new PartsManager();
	}
	
	public void setInterface() {
		add(graphics, BorderLayout.CENTER);
		
		add(UI, BorderLayout.LINE_END);
		pack();
		setVisible(true);
	}
	
	public void sendMessage(String option, String itemName, String filePath){
		String message = null;
		
		if(option.equals("add")){
			message = "pm km cmd addpartname " + itemName + " " + filePath;
		}
		
		else if (option.equals("remove")){
			message = "pm km cmd rmpartname " + itemName + " " + filePath;
		}
		
		sendCommand(message);
		
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
	
	public ArrayList<String> getParts(){
		return parts;
	}
}
