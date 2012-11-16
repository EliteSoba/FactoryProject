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
import java.util.*;

import agent.Agent;
import factory.*;
import factory.interfaces.Gantry;
import factory.interfaces.Lane;

public class MasterControl {

	// Agents
	KitRobotAgent kitRobot;
	ConveyorAgent conveyor;
	ConveyorControllerAgent conveyorController;
	public LaneAgent l0t, l0b, l1t, l1b, l2t, l2b, l3t, l3b;
	TreeMap<String, LaneAgent> laneAgentTreeMap;
	NestAgent n0t, n0b, n1t, n1b, n2t, n2b, n3t, n3b;
	TreeMap<String, NestAgent> nestAgentTreeMap;
	GantryAgent gantry;
	PartsRobotAgent partsRobot;
	StandAgent stand;
	VisionAgent vision;
	FCSAgent fcs;

	public FeederAgent f0, f1, f2, f3;
	List<FeederAgent> feederAgents = Arrays.asList(f0, f1, f2, f3);


	TreeMap<String, Agent> agentTreeMap;

	// Data Members

	TreeMap<String, PartHandler> partHandlers;
	TreeMap<String, Boolean> partOccupied;
	private static final List<String> clients = Arrays.asList("fpm", "gm", "kam", "km", "lm", "pm", "multi");
	private static final List<String> agents = Arrays.asList("ca", "cca", "fcsa", "fa", "ga", "kra", "la", "na", "pra", "sa", "va");
	private static final List<String> cmdTypes = Arrays.asList("cmd", "req", "get", "set", "cnf");
	private static final List<String> cmds = Arrays.asList( "makekits", "addkitname", "rmkitname", "addpartame",
			"rmpartname", "lanepowertoggle", "vibration", "kitcontent",
			"startfeeding", "stopfeeding", "purgefeeder", "switchlane",
			"purgetoplane", "purgebottomlane", "stopfactory", "pickupbin");

	// The following are lists of commands that are to be received by multiple clients.

	private static final List<String> partCmds = Arrays.asList("addpartname", "rmpartname");
	private static final List<String> kitCmds = Arrays.asList("kitcontent", "addkitname", "addpartname");


	ServerSocket myServerSocket;
	// Socket myFactorySocket;

	// Constructor

	public MasterControl(boolean debug){
		partHandlers = new TreeMap<String, PartHandler>();
		partOccupied = new TreeMap<String, Boolean>();
		agentTreeMap = new TreeMap<String, Agent>();
		laneAgentTreeMap = new TreeMap<String, LaneAgent>();
		nestAgentTreeMap = new TreeMap<String, NestAgent>();




		try{
			myServerSocket = new ServerSocket(12321);
		} catch(Exception e){

			e.printStackTrace();

		}
		connectAllSockets(debug); // This waits for every client to start up before moving on.
		// At this point, all of the sockets are connected, PartHandlers have been created
		// The TreeMaps are updated with all of the relevant data, and the Factory can go.
		startAgents();
		laneAgentTreeMap.put("l0t", l0t); //tmp for testing.
		String singleD = (debug? partHandlers.firstKey(): null);
		sendConfirm(singleD);
		// At this point, all of the parts have been notified that
		// the connections have been made, and therefore, the
		// Factory simulation can begin.

	}

	// Member Methods

	private void startAgents(){
		//Instantiate all of the agents!!!!!!


		// Instantiate the Nests
		n0t = new NestAgent(this);
		n0b = new NestAgent(this);
		n1t = new NestAgent(this);
		n1b = new NestAgent(this);
		n2t = new NestAgent(this);
		n2b = new NestAgent(this);
		n3t = new NestAgent(this);
		n3b = new NestAgent(this);


		// Instantiate the Lanes
		l0t = new LaneAgent(this);
		l0b = new LaneAgent(this);
		l1t = new LaneAgent(this);
		l1b = new LaneAgent(this);
		l2t = new LaneAgent(this);
		l2b = new LaneAgent(this);
		l3t = new LaneAgent(this);
		l3b = new LaneAgent(this);

		// Instantiate the Gantry
		gantry = new GantryAgent(this);

		// Instantiate the Feeders
		f0 = new FeederAgent("f0",0,l0t,l0b,gantry,this);
		f1 = new FeederAgent("f1",1,l1t,l1b,gantry,this);
		f2 = new FeederAgent("f2",2,l2t,l2b,gantry,this);
		f3 = new FeederAgent("f3",3,l3t,l3b,gantry,this);


		// Instantiate the Conveyor and related Agents
		conveyor = new ConveyorAgent();
		conveyorController = new ConveyorControllerAgent();

		// Instantiate the KitRobot
		kitRobot = new KitRobotAgent(this,conveyor);

		// Instantiate the PartsRobot
		//partsRobot = new PartsRobotAgent(); // bad code

		// Instantiate the Stand
		//stand = new StandAgent(); // bad code

		// Instantiate the Vision
		vision = new VisionAgent(partsRobot,stand,this);

		// Instantiate the FCS
		fcs = new FCSAgent(gantry,partsRobot, this);


		// SET A FEW THINGS
		conveyor.setKitRobot(kitRobot);
		kitRobot.setStand(stand);
		conveyor.setFCS(fcs);


		// Set up the TreeMaps
		laneAgentTreeMap.put("l0t", l0t);
		laneAgentTreeMap.put("l0b", l0b);
		laneAgentTreeMap.put("l1t", l1t);
		laneAgentTreeMap.put("l1b", l1b);
		laneAgentTreeMap.put("l2t", l2t);
		laneAgentTreeMap.put("l2b", l2b);
		laneAgentTreeMap.put("l3t", l3t);
		laneAgentTreeMap.put("l3b", l3b);

		nestAgentTreeMap.put("n0t", n0t);
		nestAgentTreeMap.put("n0b", n0b);
		nestAgentTreeMap.put("n1t", n1t);
		nestAgentTreeMap.put("n1b", n1b);
		nestAgentTreeMap.put("n2t", n2t);
		nestAgentTreeMap.put("n2b", n2b);
		nestAgentTreeMap.put("n3t", n3t);
		nestAgentTreeMap.put("n3b", n3b);

		agentTreeMap.put("ca", conveyor);
		agentTreeMap.put("cca", conveyorController);
		agentTreeMap.put("ga", gantry );
		agentTreeMap.put("kra", kitRobot);
		//        agentTreeMap.put("pra", partsRobot);
		//        agentTreeMap.put("sa", stand);
		agentTreeMap.put("va", vision);
		agentTreeMap.put("fcsa", fcs);


		// Start the FeederAgent
		f0.startThread();
		f1.startThread();
		f2.startThread();
		f3.startThread();

		//Starting all agent threads in agentTreeMap
		for (Map.Entry<String, Agent> agentMap : agentTreeMap.entrySet()) {
			agentMap.getValue().startThread();
		}

		//Starting all lane agent threads in laneAgentTreeMap
		for (Map.Entry<String, LaneAgent> laneAgentMap : laneAgentTreeMap.entrySet()) {
			laneAgentMap.getValue().startThread();
		}

		//Starting all nest agent threads in nestAgentTreeMap
		for (Map.Entry<String, NestAgent> nestAgentMap : nestAgentTreeMap.entrySet()) {
			nestAgentMap.getValue().startThread();
		}
	}

	private void closeAgents() {
		//Close all of the agents!!!!!!

		//Stopping all agent threads in agentTreeMap
		for (Map.Entry<String, Agent> agentMap : agentTreeMap.entrySet()) {
			agentMap.getValue().stopThread();
		}

		//Stopping all lane agent threads in laneAgentTreeMap
		for (Map.Entry<String, LaneAgent> laneAgentMap : laneAgentTreeMap.entrySet()) {
			laneAgentMap.getValue().stopThread();
		}

		//Stopping all nest agent threads in nestAgentTreeMap
		for (Map.Entry<String, NestAgent> nestAgentMap : nestAgentTreeMap.entrySet()) {
			nestAgentMap.getValue().stopThread();
		}
	}

	// parseDst is called by Clients and Agents and determines whether to
	// call methods locally to Agents, or to send them through to a Client

	public boolean command(String cmd){
		// Split into array
		// Figure out destination
		// Call either agentCmd() or clientCmd()

		System.out.println(cmd);
		ArrayList <String> parsedCommand = new ArrayList<String>(Arrays.asList(cmd.split(" "))); //puts string into array list



		if(clients.contains(parsedCommand.get(1))){
			return clientCmd(parsedCommand);
		} else if(agents.contains(parsedCommand.get(1))) {
			return agentCmd(parsedCommand);
		} else if(parsedCommand.get(1).equals("mcs")) {
			// This is a message specifically meant for the server.
			// Currently, there is only one of these : end the factory
			// If more need to be implemented, I'll add them here.
			System.out.println("HAPPY");
			endAll();
			return true;
		} else {
			return false;
		}



	}

	private void endAll(){

		//First, end all of the Client threads.
		for(Map.Entry<String,PartHandler> x : partHandlers.entrySet()){
			x.getValue().endClient();
		}

		// Now, end all of the Agent threads.

		closeAgents();


	}


	// agentCmd is the giant parser that figures out
	// what method to call on what agent.

	public boolean agentCmd(ArrayList<String> cmd){ 		//GO HERE
		// 0 = Source
		// 1 = Destination
		// 2 = CmdType
		// 3 = Cmd OR if cnf, this would be optional identifier
		// 4+ = Parameters

		System.out.println("agentCmd()");

		if(cmd.get(2).equals("cnf")){

			Agent destination;

			if(agentTreeMap.containsKey(cmd.get(1))){

				destination = agentTreeMap.get(cmd.get(1));

			} else {

				if(cmd.get(1).equals("na")){            // Nest

					destination = nestAgentTreeMap.get(cmd.get(3));

				} else if(cmd.get(1).equals("la")){     // Lane

					destination = laneAgentTreeMap.get(cmd.get(3));

				} else if(cmd.get(1).equals("fa")){     // Feeder

					destination = feederAgents.get(Integer.valueOf(cmd.get(3)));

				} else {
					return false;
				}
			}

			destination.msgAnimationDone();
			return true;


		} else if( cmd.get(2).equals("set")){

		} else if( cmd.get(2).equals("cmd")){
			// cmd.get(2) is CmdType
			// cmd.get(3) is Command
			// cmd.get(4+) are parameters
			Agent destination;

			// FCSAgent Commands:
			if (cmd.get(1).equals("fcsa"))
			{
				destination = agentTreeMap.get(cmd.get(1)); // FCSAgent

				if(cmd.get(3).equals("makekits")){
					int quantity = Integer.valueOf(cmd.get(4));
					String name = cmd.get(5);
					((FCSAgent) destination).msgProduceKit(quantity,name);
				}
				//more fcsagent commands
			}
			/*
			//MCS Commands:
	
			if (cmd.get(1).equals("mcs"))
			{
				destination = agentTreeMap.get(cmd.get(1));
				
				//fpm mcs cmd stopfactory
				if(cmd.get(3).equals("stopfactory")){
					
					//method for stopping factory?
					//this.closeAgents();
				}
				//more fcsagent commands
			}*/
			

		}

		//If...
		//If
		//Else if...
		//Else if...
		//Else if
		//If
		//Else if...
		//Else if...
		//...
		return false; // Default is false.
	}

	// clientCmd figures out which partHandler to use sendCmd on
	// and then sends it.


	public boolean clientCmd(ArrayList<String> cmd){	
		String s = checkCmd(cmd);
		System.out.println(s);
		String a = cmd.get(0); // Source
		if(s != null){
			if(clients.contains(a)){
				PartHandler sourcePH = determinePH(a);
				sourcePH.send("err failed to parse command XXX log "+s);
			}
			return false;
		}

		String b = cmd.get(1); // Destination
		String c = cmd.get(2); // CommandType
		String d = "";

		for(int i = 3; i < cmd.size(); i++){  // Command
			d+= cmd.get(i)+" ";
		}

		String fullCmd = envelopeCmd(c, d);

		System.out.println("Server received ... "+cmd+" from "+a);
		System.out.println("Server is about to send ... "+fullCmd);

		if(b.equals("multi")){
			ArrayList<PartHandler> destinations = getDestinations(cmd.get(3));
			if(destinations == null){
				return false;
			} else {
				for(PartHandler x : destinations){
					if(!sendCmd(x, fullCmd)){
						return false;
					}
				}
				return true;
			}


		} else {
			System.out.println("1");
			PartHandler destinationPH = determinePH(b);
			System.out.println("2");
			boolean result = sendCmd(destinationPH, fullCmd);
			System.out.println("3");
			System.out.println(result);
			return result;
		}


	}

	// getDestinations parses the command and determines which Clients need to receive it.

	private ArrayList<PartHandler> getDestinations(String myCmd){



		if(partCmds.contains(myCmd)){
			return new ArrayList<PartHandler>(Arrays.asList(partHandlers.get("km"), partHandlers.get("fpm")));
		} else if(kitCmds.contains(myCmd) ){
			return new ArrayList<PartHandler>(Arrays.asList(partHandlers.get("fpm"), partHandlers.get("pm")));
		} else {
			return null;
		}

	}



	// envelopeCmd is called by parseCmd with the details of the message
	// Therefore, it should be private.

	private String envelopeCmd(String cmdtype, String cmd){

		String myCommand = cmdtype;
		myCommand += " ";
		myCommand += cmd;
		myCommand += "end"+cmdtype;

		return myCommand;

	}


	// sendCmd is called by parseCmd with the command to be sent and the destination
	// sendCmd then calls the Send() method for the requisite PartHandler
	// Therefore, it should be private.

	private boolean sendCmd(PartHandler myPH, String fullCmd) {

		return(myPH.send(fullCmd));


	}

	private PartHandler determinePH(String id){
		return partHandlers.get(id);
	}


	// checkCmd is called by parseCmd with the command received to check for errors
	// Therefore, it should be private.

	private String checkCmd(ArrayList<String> pCmd) {

		// Check that the cmd is of a valid length

		if(pCmd.size() < 4){
			return "there must be a command";
		}

		if(pCmd.size() == 4){
			return "missing parameters for command";
		}

		// Check that the source is a valid DID

		if(!clients.contains(pCmd.get(0)) && !agents.contains(pCmd.get(0))){
			return "source is not valid client or agent id";
		}

		// Check that the destination is a valid DID

		if(!clients.contains(pCmd.get(0)) && !agents.contains(pCmd.get(0))){
			return "destination is not valid client or agent id";
		}

		// Check that the source != the destination

		if(pCmd.get(0).equals(pCmd.get(1))){
			return "source and Destination cannot be the same";
		}

		// Check that the destination is not currently busy

		if(partOccupied.get(pCmd.get(1))){
			return "destination is busy cannot send message.";
		}

		// Check that the cmdType is a valid cmdType

		if(!cmdTypes.contains(pCmd.get(2))){
			return "commandtype is not valid commandtype";
		}

		// Check that the first sub-string in the cmd is a valid cmd

		if(!cmds.contains(pCmd.get(3))){
			return "this is not a valid command please check wiki documentation for correct syntax.";
		}

		return null;

	}


	// connectAllSockets() is the function responsible for managing each socket connection
	// It waits until each connection specified in 'dids' has been made, and then continues.

	private void connectAllSockets(boolean debugmode){
		int numConnected = 0;
		int numToConnect = (debugmode ? 1 : clients.size());
		while(numConnected != numToConnect){
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
				pw.println("connected");
			} catch (Exception e){
				e.printStackTrace();
			}
			numConnected++;
		}

	}

	// sendConfirm() is the function responsible for sending a confirmation through each socket
	// this confirms that every connection has been made, and therefore, the factory sim can begin.

	private void sendConfirm(String singleDest){
		if(singleDest != null){
			PartHandler ph = partHandlers.get(singleDest);
			ph.send("mcs start");

		} else {
			for(String p : clients){
				PartHandler ph = partHandlers.get(p);
				ph.send("mcs start");
			}
		}
	}

	public static void main(String args[]){
		// boolean debug = Boolean.getBoolean(args[0]);
		MasterControl mc = new MasterControl(true);

		//This pauses for ~5 seconds to allow for the FactoryProductionManager to load up
		long timeToQuit = System.currentTimeMillis() + 5000;
		while (System.currentTimeMillis() < timeToQuit);

//		Part p0 = new Part("Ear");

		// request a part for the lane
//		mc.f0.msgLaneNeedsPart(p0, mc.l0t);		
		// should make the gantry go get a bin of parts
		// should call DoSwitchLane() and then DoStartFeeding()
	}

}

