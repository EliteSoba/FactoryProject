package factory.masterControl;

import java.net.ServerSocket;
import java.util.TreeMap;

public class MasterControl {

	// Data Members

	TreeMap<String, PartHandler> partHandlers;
	TreeMap<String, Boolean> partOccupied;
	private static final String dids[] = {"kra", "krm"};

	ServerSocket myServerSocket;
	// Socket myFactorySocket;

	// Constructor

	public MasterControl(){
		partHandlers = new TreeMap<String, PartHandler>();
		partOccupied = new TreeMap<String, Boolean>();
		try{
            myServerSocket = new ServerSocket(12321);
        } catch(Exception e){


        }
		// myFactorySocket = new Socket("localhost", "12369");
		
		connectAllSockets();

		sendConfirm();

	}

	// Member Methods


	// parseCmd is called by the PartHandler with a string command
	// Therefore, it needs to be public.

	public boolean parseCmd(String cmd) {
        boolean result = false;


        return result;
	}


	// envelopeCmd is called by parseCmd with the details of the message
	// Therefore, it should be private.

	private String envelopeCmd(String src, String dst, String cmdtype, String cmd){

		String myCommand = src;
		myCommand += " ";
		myCommand += dst;
		myCommand += " ";
		myCommand += cmdtype;
		myCommand += " ";
		myCommand += cmd;
		myCommand += "end"+cmdtype;

		return myCommand;

	}


	// sendCmd is called by parseCmd with the command to be sent and the destination
	// sendCmd then calls the Send() method for the requisite PartHandler
	// Therefore, it should be private.

	private boolean sendCmd(String dst, String fullCmd) {

		PartHandler myPh = partHandlers.get(dst);
		return(myPh.send(fullCmd));
		
	
	}

	
	// checkCmd is called by parseCmd with the command received to check for errors
	// Therefore, it should be private.

	private boolean checkCmd(String cmd) {
        boolean result = false;


        return result;

	}


	// connectAllSockets() is the function responsible for managing each socket connection
	// It waits until each connection specified in 'dids' has been made, and then continues.

	private void connectAllSockets(){

		
	}

	// sendConfirm() is the function responsible for sending a confirmation through each socket
	// this confirms that every connection has been made, and therefore, the factory sim can begin.

	private void sendConfirm(){


	}

}

