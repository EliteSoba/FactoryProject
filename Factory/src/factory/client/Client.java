package factory.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
 * 
 */



public abstract class Client extends JFrame implements ActionListener {
	
	public enum Type{//enumeration for the 6 types
		KITMANAGER, PARTSMANAGER, FACTORYPRODUCTIONMANAGER,
		KITASSEMBLYMANAGER, LANEMANAGER,
		GANTRYROBOTMANAGER
	}
	protected Socket server; //connection to server
	protected JPanel graphics; //possibly null graphics frame (graphics team)
	protected JPanel UI; //user interface (swing team)
	public Type type; //type of client
	public PrintWriter output; //output stream to server
	public BufferedReader input; //input stream from server
	public String currentCommand; //current command string from server
	public ArrayList<String> parsedCommand; //current command parsed into strings
	protected Timer updater; //repaints graphics
	boolean connected;
	
	
	Runnable independentInput = new Runnable(){
		public void run(){
			for(;;){
				if(connected)
				try {
					currentCommand = input.readLine(); //reads from input each time there is a new string
					parseInput();
				} catch (Exception e) {
					System.out.println("inputStream not open");
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
		updater = new Timer(1000/30, this); //sets timer to update graphics
		setInterface(); //to be implemented...set size, layout, add UI etc...
		Thread inputThread = new Thread(independentInput);
		inputThread.start();
	}
	
	public void connect(){
		try {
			server = new Socket("127.0.0.1", 12321); //connects to server on localhost
			input = new BufferedReader(new InputStreamReader(server.getInputStream()));//opens inputStream
			output = new PrintWriter(new OutputStreamWriter(server.getOutputStream()));//opens outputStream
			connected = true;
			System.out.println("connected to server!");
		} catch (Exception e) {
			System.out.println("Host unavailable");
		} 	
	}
	
	public void parseInput(){
		parsedCommand = new ArrayList<String>(Arrays.asList(currentCommand.split(" "))); //puts string into array list
	    doCommand(parsedCommand);
    }
	public void sendCommand(String cmd){
		output.println(cmd);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == updater)
			if(graphics != null)
				graphics.repaint();
	}
	
	public abstract void setInterface(); // to be implemented by child class
    public abstract void doCommand(ArrayList<String> pCmd); // also must be implemented by child class
}

