package factory.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/*Abstract class used by each client
 * written by Ben Mayeux
 * aaaa
 */



public abstract class Client extends JFrame implements ActionListener {
	
	public enum Type{//enumeration for the 6 types
		 fpm, gm, kam, km, lm, pm;
	}
	protected Socket server; //connection to server
	protected JPanel graphics; //possibly null graphics frame (graphics team)
	protected JPanel UI; //user interface (swing team)
	public Type type; //type of client
	public PrintWriter output; //output stream to server
	public BufferedReader input; //input stream from server
	public String currentCommand; //current command string from server
	public ArrayList<String> parsedCommand; //current command parsed into strings
	// Timer updater; //repaints graphics
	boolean connected;
	
	
	Runnable independentInput = new Runnable(){
		public void run(){
			while(true){
//				System.out.println("1");
				if(connected)
				try {
					currentCommand = input.readLine(); //reads from input each time there is a new string
					parseInput();
					doCommand(parsedCommand);
				} catch (Exception e) {
					//System.out.println("inputStream not open"); // not necessary 
				}
			}//end for
		}//end run
	}; //end independentInput
	
	public Client(Type t, JPanel buttons, JPanel Animation){
		
		connected = false;
		type = t;
		UI = buttons;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		graphics = Animation;
		connect(); //connects to server
		System.out.println(connected);
		//updater = new Timer(1000/30, this); //sets timer to update graphics
		//updater.start();
		//setInterface(); //to be implemented...set size, layout, add UI etc...
		Thread inputThread = new Thread(independentInput);
		inputThread.start();
		this.addWindowListener(new WindowCloser(this));
		
		
	}
		
	public void connect(){
		try {
			server = new Socket("127.0.0.1", 12321); //connects to server on localhost
			input = new BufferedReader(new InputStreamReader(server.getInputStream()));//opens inputStream
			output = new PrintWriter(server.getOutputStream(),true);//opens outputStream
			System.out.println(this.type.toString());
			output.println(this.type.toString());
			
			String reply = input.readLine();
			System.out.println(reply);
			if(reply.equals("connected")){
				connected = true;
			}
			System.out.println("connected to server!");
			System.out.println("waiting for agents to start...");
			input.readLine();
			System.out.println("Factory Started....yo!");
		} catch (Exception e) {
			System.out.println("Host unavailable");
		} 	
	}
	
	public void parseInput(){
		parsedCommand = new ArrayList<String>(Arrays.asList(currentCommand.split(" "))); //puts string into array list   
    }
	public void sendCommand(String cmd){
		output.println(cmd);
	}

	
	public void actionPerformed(ActionEvent arg0) {
		/*if(arg0.getSource() == updater)
			if(graphics != null)
				graphics.repaint();*/
		
	}
	

	public abstract void setInterface(); // to be implemented by child class
    public abstract void doCommand(ArrayList<String> pCmd); // also must be implemented by child class
    
    private class WindowCloser implements WindowListener{

    	Client client;
    	public WindowCloser(Client client2) {
			client = client2;
		}
		

		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}


		public void windowClosed(WindowEvent e) {
			
		}


	
		public void windowClosing(WindowEvent e) {
			
			if(client.type == Type.fpm && connected == true)
				client.sendCommand("fpm mcs cmd stopfactory");
				
			client.quit();
		}

		

		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}


		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}


		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Window opened");
		}
    }

	public void quit() {
		if(connected){
		try{
		dispose();
		System.out.println("resources released");
		if(input != null)
		{	
			System.out.println("input stream closing...");
			input.close();
			System.out.println("input closed");
		}
		if(output != null)
			System.out.println("output stream closing...");
			output.close();
			System.out.println("output closed");
		} catch (IOException e1) {
			//never executes but wont compile w/o
		}}
	System.out.println("window closed");
		if(type == Type.fpm || connected == false)
			System.exit(0);
	}
    
    
}

