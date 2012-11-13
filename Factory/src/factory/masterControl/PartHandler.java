package factory.masterControl;

//	Server-Socket Team -- Devon, Mher & Ben
//	CSCI-200 Factory Project Team 2
//	Fall 2012
// 	Prof. Crowley
// Another test

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PartHandler implements Runnable {

Socket mySocket = null;
PrintWriter out = null;
BufferedReader in = null;
boolean haveCMD = false; 
String cmd = null;
String message;
MasterControl master = null;
String Client_id = null;

	public PartHandler(Socket s, BufferedReader b, PrintWriter p, String me, MasterControl mc){
		//need to add other initializations
		mySocket = s;
		out = p;
		in = b;
		master = mc;
		Client_id = me;
		
		(new Thread(this)).start();
	}
	
	public void run() 
	{
	    //This thread loops to get confirmations sent by clients 

	    while(true) 
	    {
		cmd = gotCmd();
		if(haveCMD)
		{//if there was a command then call parseCmd and send the cmd to Server to assess
			master.parseCmd(cmd,this); 
			//sets haveCMD to false because parseCmd notified server
			haveCMD = false;
		}
	    }
	}
	
	public boolean send(String cmd) {
		boolean result = false;

		out.println(cmd);	//output command
		result = true;
		if(out.checkError())
		{
			result = false;
		}
		return result;
	}
	
	//this loops until it gets a cmd from client 
	private String gotCmd()
	{
		try {
		    //Wait for the client to send a String 
		    message = in.readLine();
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}

		//sets haveCMD to true is there was a command sent so can call parseCmd
		if(message != null)
		{
			haveCMD = true;
		}
		return message;
	}
	
}
