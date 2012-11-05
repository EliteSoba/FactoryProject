//	Server-Socket Team -- Devon, Mher & Ben


//  This component was coded by Devon Meyer
//  As a part of the Server-Socket subteam, this is my work for Submission 1.
//  Not really related to this submission,
//  but nonetheless required for the final project.


//	CSCI-200 Factory Project Team 2
//	Fall 2012
// 	Prof. Crowley

package factory.masterControl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class MasterControl {

	// Data Members

	TreeMap<String, PartHandler> partHandlers;
	TreeMap<String, Boolean> partOccupied;
	private static final List<String> dids = Arrays.asList("krm", "kra");
    private static final List<String> cmdTypes = Arrays.asList("cmd", "req", "get", "set", "cnf");
    private static final List<String> cmds = Arrays.asList("sendrobottokit");


    ServerSocket myServerSocket;
	// Socket myFactorySocket;

	// Constructor

	public MasterControl(){
		partHandlers = new TreeMap<String, PartHandler>();
		partOccupied = new TreeMap<String, Boolean>();
		try{
            myServerSocket = new ServerSocket(12321);
        } catch(Exception e){

            e.printStackTrace();

        }
		// myFactorySocket = new Socket("localhost", "12369");
		
		connectAllSockets(); // This waits for every client to start up before moving on.

                             // At this point, all of the sockets are connected, PartHandlers have been created
                             // The TreeMaps are updated with all of the relevant data, and the Factory can go.

		sendConfirm();
                             // At this point, all of the parts have been notified that
                             // the connections have been made, and therefore, the
                             // Factory simulation can begin.

	}

	// Member Methods


	// parseCmd is called by the PartHandler with a string command
	// Therefore, it needs to be public.

	public boolean parseCmd(String cmd, PartHandler sourcePH) {
        // Format is : "src dst cmdtype cmd"

        System.out.println("Server has received ... "+cmd);

        ArrayList<String> parsedCommand = new ArrayList<String>(Arrays.asList(cmd.split(" ")));
        String s = checkCmd(parsedCommand);
        if(s != null){
            sourcePH.send("ERR : Failed to parse command. Log : "+s);
            return false;
        }

        String a = parsedCommand.get(0);        // Source
        String b = parsedCommand.get(1);        // Destination
        String c = parsedCommand.get(2);        // Cmd Type
        String d = "";
        for(int i = 3; i < parsedCommand.size(); i++){  // Command
            d+= parsedCommand.get(i);
        }

        String fullCmd = envelopeCmd(a, b, c, d);

        // Now, fullCmd is the full text of the command, parsed and checked by our Server.

        System.out.println("Server is about to send ... "+fullCmd);

        return sendCmd(b, fullCmd);

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

	private String checkCmd(ArrayList<String> pCmd) {

        // Check that the cmd is of a valid length

        if(pCmd.size() < 4){
            return "There must be a command.";
        }

        // Check that the source is a valid DID

        if(!dids.contains(pCmd.get(0))){
            return "Source is not valid Destination ID";
        }

        // Check that the destination is a valid DID

        if(!dids.contains(pCmd.get(1))){
            return "Destination is not valid Destination ID";
        }

        // Check that the source != the destination

        if(pCmd.get(0).equals(pCmd.get(1))){
            return "Source and Destination cannot be the same";
        }

        // Check that the destination is not currently busy

        if(!partOccupied.get(pCmd.get(1))){
            return "Destination is busy, cannot send message.";
        }

        // Check that the cmdType is a valid cmdType

        if(!cmdTypes.contains(pCmd.get(2))){
            return "CommandType is not valid CommandType";
        }

        // Check that the first sub-string in the cmd is a valid cmd

        if(!cmds.contains(pCmd.get(3))){
            return "This is not a valid command. Please check wiki documentation for correct syntax.";
        }

        // TODO Check that there are enough sub-strings in the cmd for the cmdType

        return null;

	}


	// connectAllSockets() is the function responsible for managing each socket connection
	// It waits until each connection specified in 'dids' has been made, and then continues.

	private void connectAllSockets(){
        int numConnect = 0;
        while(numConnect != dids.size()){
            try{
                Socket s = myServerSocket.accept();
                PrintWriter pw = new PrintWriter( s.getOutputStream(), true );
                BufferedReader br = new BufferedReader( new InputStreamReader( s.getInputStream() ) );
                String name = br.readLine();
                while(name == null){
                    name = br.readLine();
                }
                PartHandler ph = new PartHandler(s, br, pw, name, this);
                partHandlers.put(name, ph);
                partOccupied.put(name, false);
            } catch (Exception e){
                e.printStackTrace();
            }
            numConnect++;
        }
		
	}

	// sendConfirm() is the function responsible for sending a confirmation through each socket
	// this confirms that every connection has been made, and therefore, the factory sim can begin.

	private void sendConfirm(){

        for(String p : dids){
            PartHandler ph = partHandlers.get(p);
            ph.send("mcs start");
        }

	}

    public static void main(String args[]){
        MasterControl mc = new MasterControl();
    }

}

