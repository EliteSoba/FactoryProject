//Ben Mayeux and Stephanie Reagle and Marc Mendiola
//CS 200
package factory.managers;


import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import factory.Part;
import factory.client.Client;
import factory.swing.PartsManPanel;

public class PartsManager extends Client implements WindowListener{
	private static final long serialVersionUID = -205350261062308096L;
	
	public HashMap<String, Part> parts;  // parts list

	public PartsManager() {
		super(Client.Type.pm, null, null);
		
		parts = new HashMap<String, Part>();
		loadData();

		UI = new PartsManPanel(this);
		setInterface();
		
		this.addWindowListener(this);
	}
	
	public static void main(String[] args){
		PartsManager manager = new PartsManager();
	}
	
	public void setInterface() {
		//add(graphics, BorderLayout.CENTER);
		
		add(UI, BorderLayout.LINE_END);
		pack();
		this.setSize(460,600);
		this.setResizable(false);
		setVisible(true);
	}
	
	public HashMap<String, Part> getParts(){
		return parts;
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
				saveData();  // saves upon exiting window
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void saveData(){
		FileOutputStream f;
		ObjectOutputStream o;
		try {    // uses output stream to serialize and save array of Players
			
			f = new FileOutputStream("InitialData/initialParts.ser");
			o = new ObjectOutputStream(f);
			o.writeObject(parts);
			o.close();
			
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public void loadData(){
		FileInputStream f;
		ObjectInputStream o;
		try{    // loads previously saved player data
			f = new FileInputStream("InitialData/initialParts.ser");
			o = new ObjectInputStream(f);
			parts = (HashMap<String, Part>) o.readObject();
		}catch(IOException e){
			e.printStackTrace();
			parts = new HashMap<String, Part>();
		} catch(ClassNotFoundException c){
			c.printStackTrace();
			parts = new HashMap<String, Part>();
		}
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
          else if(action.equals("mcs")){
			   try {
					this.server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				   System.exit(0);
			   }
 else 
     System.out.println("Stuff is FU with the server...\n(string does not contain a command type)");
}
}
