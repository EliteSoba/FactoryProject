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
		/*
		parts.add(new Part("Eye",1,"This is used to see.","Images/eye.png",1));
		parts.add(new Part("Body",2,"This is used as the base.","Images/body.png",5));
		parts.add(new Part("Hat",3,"This is used to cover the head.","Images/hat.png",2));
		parts.add(new Part("Arm",4,"This is used to grab things.","Images/arm.png",2));
		parts.add(new Part("Shoe",5,"This is used to walk.","Images/shoe.png",2));
		parts.add(new Part("Mouth",6,"This is used to talk.","Images/mouth.png",1));
		parts.add(new Part("Nose",7,"This is used to smell.","Images/nose.png",1));
		parts.add(new Part("Moustache",8,"This is used to look cool.","Images/moustache.png",1));
		parts.add(new Part("Ear",9,"This is used to hear.","Images/ear.png",2));*/
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
		}
		
		else if (option.equals("remove")){
			message = "pm multi cmd rmpartname " + p.name + " endcmd"; 
		}
		
		sendCommand(message);
		
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
