//Ben Mayeux, Marc Mendiola

package factory.managers;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import factory.client.Client;
import factory.KitConfig;
import factory.swing.KitManPanel;
import factory.Part;

public class KitManager extends Client implements WindowListener{

	private static final long serialVersionUID = -3161852324870948654L;

	private HashMap<String,KitConfig> kitConfigList;
	private HashMap<String,Part> partsList;

	public KitManager() {
		super(Client.Type.km, null, null);
		loadData();
		UI = new KitManPanel(this);
		
		setInterface();
		this.addWindowListener(this);
	}
	public static void main(String[] args){
		KitManager k = new KitManager();
	}

//	public void sendMessage(String kitname, int setting, String message){
//		String cmd = new String("");
//		if (message == "power"){
//			cmd =  "km kma set kitcontent #kitname #itemnumber #itemname";
//			output.println(cmd);
//		}
//
//	}

	public void setInterface() {
		
		add(UI, BorderLayout.LINE_END);
		pack();
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

	// Load Data - remember to import the file - FOR EVERYONE
	@SuppressWarnings("unchecked")
	public void loadData(){
		FileInputStream f;
		ObjectInputStream o;
		try{    // loads previously saved player data
			f = new FileInputStream("InitialData/initialParts.ser");
			o = new ObjectInputStream(f);
			setPartsList((HashMap<String,Part>) o.readObject());
			System.out.println("Good");
			o.close();
		}catch(IOException e){
			e.printStackTrace();
		} catch(ClassNotFoundException c){
			c.printStackTrace();
		}
		try{    // loads previously saved player data
			f = new FileInputStream("InitialData/initialKitConfigs.ser");
			o = new ObjectInputStream(f);
			setKitConfigList((HashMap<String,KitConfig>) o.readObject());
			System.out.println("Good");
			o.close();
		}catch(IOException e){
			e.printStackTrace();
		} catch(ClassNotFoundException c){
			c.printStackTrace();
		}
	}


	// Parts manager will save the most recent parts list to file
	public void savePartData() {
		FileOutputStream f;
		ObjectOutputStream o;
		try {    

			f = new FileOutputStream("InitialData/initialParts.ser");
			o = new ObjectOutputStream(f);
			o.writeObject(getPartsList());
			o.close();
			System.out.println("It worked");

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// kit manager will save the most recent kit configuration list to file
	public void saveKitData() {
		FileOutputStream f;
		ObjectOutputStream o;
		try {  

			f = new FileOutputStream("InitialData/initialKitConfigs.ser");
			o = new ObjectOutputStream(f);
			o.writeObject(getKitConfigList());
			o.close();
			System.out.println("It worked");

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public HashMap<String,KitConfig> getKitConfigList() {
		return kitConfigList;
	}
	public void setKitConfigList(HashMap<String,KitConfig> kitConfigList) {
		this.kitConfigList = kitConfigList;
	}
	public void addToKitConfigList(KitConfig k) {
		this.kitConfigList.put(k.kitName, k);
	}
	public void removeFromKitConfigList(KitConfig k) {
		this.kitConfigList.remove(k.kitName);
	}
	public HashMap<String,Part> getPartsList() {
		return partsList;
	}
	public void setPartsList(HashMap<String,Part> partsList) {
		this.partsList = partsList;
	}
}
