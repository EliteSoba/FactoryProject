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
	
	public HashMap<String, Part> parts;
	// Kit Configurations ArrayList

	public PartsManager() {
		super(Client.Type.pm, null, null);
		
		parts = new HashMap<String, Part>();
		loadData();
		//parts = new HashMap<String, Part>();
		//parts.put("Part", new Part("Part",1,"Hey","Image",2));
		System.out.println(parts.size());

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
		setVisible(true);
	}
	
	public void sendMessage(String option, Part p){
		String message = null;
		
		if(option.equals("add")){
			message = "pm multi cmd addpartname " + p.name + " " + p.id + " " + p.imagePath + " " + p.nestStabilizationTime + " " + p.description;
			System.out.println(message);
		}
		
		else if (option.equals("remove")){
			message = "pm multi cmd rmpartname " + p.name + " endcmd"; 
		}
		
		//sendCommand(message);
		
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
				//saveData();
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
	/*
	public void saveData(){
		FileOutputStream f;
		ObjectOutputStream o;
		try {    // uses output stream to serialize and save array of Players
			
			f = new FileOutputStream("InitialData/initialParts.ser");
			o = new ObjectOutputStream(f);
			o.writeObject(parts);
			o.close();
			System.out.println("It worked");
			
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}*/
	
	public void loadData(){
		FileInputStream f;
		ObjectInputStream o;
		try{    // loads previously saved player data
			f = new FileInputStream("InitialData/initialParts.ser");
			o = new ObjectInputStream(f);
			parts = (HashMap<String, Part>) o.readObject();
			System.out.println("Good");
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
		// TODO Auto-generated method stub
		
	}
}
