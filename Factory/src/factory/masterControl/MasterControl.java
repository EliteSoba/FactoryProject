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
import factory.interfaces.Nest;



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
	List<FeederAgent> feederAgents;


	TreeMap<String, Agent> agentTreeMap;

	// Data Members

	TreeMap<String, PartHandler> partHandlers; 
	TreeMap<String, Boolean> partOccupied;
	private static final List<String> clients = Arrays.asList("fpm", "gm", "kam", "km", "lm", "pm", "multi");
	private static final List<String> agents = Arrays.asList("ca", "cca", "fcsa", "fa", "ga", "kra", "la", "na", "pra", "sa", "va");
	private static final List<String> cmdTypes = Arrays.asList("cmd", "req", "get", "set", "cnf");
	private static final List<String> cmds = Arrays.asList(

            "makekits", "addkitname", "rmkitname", "addpartame",
			"rmpartname", "lanepowertoggle", "vibration", "kitcontent",
			"startfeeding", "stopfeeding", "purgefeeder", "switchlane",
			"purgetoplane", "purgebottomlane", "stopfactory", "pickuppurgebin",
			"getnewbin", "bringbin", "putinspectionkitonconveyor", "putemptykitatslot",
            "movekittoinspectionslot", "dumpkitatslot", "exportkitfromcell", "emptykitenterscell",
            "partconfig", "putpartinkit", "movetostand", "droppartsrobotsitems", "movetonest",
            "movetocenter", "nestdestabilized", "neststabilized"

    );

    private ArrayList<PartHandler> partHandlerList;

	// The following are lists of commands that are to be received by multiple clients.

	private static final List<String> multiCmd_1 = Arrays.asList("addpartname", "rmpartname", "partconfig");
    private static final List<String> multiCmdDst_1 = Arrays.asList("km", "fpm");

    // No longer necessary.
	//private static final List<String> kitCmds = Arrays.asList("addkitname", "rmkitname", "kitcontent");


	ServerSocket myServerSocket;
	// Socket myFactorySocket;

	// Constructor

	public MasterControl(Integer debug){
		partHandlers = new TreeMap<String, PartHandler>();
		partOccupied = new TreeMap<String, Boolean>();
		agentTreeMap = new TreeMap<String, Agent>();
		laneAgentTreeMap = new TreeMap<String, LaneAgent>();
		nestAgentTreeMap = new TreeMap<String, NestAgent>();
        partHandlerList = new ArrayList<PartHandler>();




		try{
			myServerSocket = new ServerSocket(12321);
		} catch(Exception e){

			e.printStackTrace();

		}
		connectAllSockets(debug); // This waits for every client to start up before moving on.
		// At this point, all of the sockets are connected, PartHandlers have been created
		// The TreeMaps are updated with all of the relevant data, and the Factory can go.
		startAgents();

		sendConfirm();
		// At this point, all of the parts have been notified that
		// the connections have been made, and therefore, the
		// Factory simulation can begin.

	}

	// Member Methods

	private void startAgents(){
        //Instantiate all of the agents!!!!!!

        // Instantiate the Lanes
        l0t = new LaneAgent(this);
        l0b = new LaneAgent(this);
        l1t = new LaneAgent(this);
        l1b = new LaneAgent(this);
        l2t = new LaneAgent(this);
        l2b = new LaneAgent(this);
        l3t = new LaneAgent(this);
        l3b = new LaneAgent(this);

        // Instantiate the Nests
        n0t = new NestAgent(this,l0t);
        n0b = new NestAgent(this,l0b);
        n1t = new NestAgent(this,l1t);
        n1b = new NestAgent(this,l1b);
        n2t = new NestAgent(this,l2t);
        n2b = new NestAgent(this,l2b);
        n3t = new NestAgent(this,l3t);
        n3b = new NestAgent(this,l3b);

		// Instantiate the Gantry
		gantry = new GantryAgent(this);

		// Instantiate the Stand
		stand = new StandAgent(this, null); 
		
		// Instantiate the Vision
		vision = new VisionAgent(partsRobot,stand,this);

		// Instantiate the Feeders
		f0 = new FeederAgent("f0",0,l0t,l0b,gantry,vision,this);
		f1 = new FeederAgent("f1",1,l1t,l1b,gantry,vision,this);
		f2 = new FeederAgent("f2",2,l2t,l2b,gantry,vision,this);
		f3 = new FeederAgent("f3",3,l3t,l3b,gantry,vision,this);
		feederAgents = Arrays.asList(f0, f1, f2, f3);

		// Set the Lane's Feeders
		l0t.setFeeder(f0);
		l0b.setFeeder(f0);
		l1t.setFeeder(f1);
		l1b.setFeeder(f1);
		l2t.setFeeder(f2);
		l2b.setFeeder(f2);
		l3t.setFeeder(f3);
		l3b.setFeeder(f3);
		
		// Set the Lane's Nests
		l0t.setNest(n0t);
		l0b.setNest(n0b);
		l1t.setNest(n1t);
		l1b.setNest(n1b);
		l2t.setNest(n2t);
		l2b.setNest(n2b);
		l3t.setNest(n3t);
		l3b.setNest(n3b);
		

		// Instantiate the Conveyor and related Agents
		conveyorController = new ConveyorControllerAgent(this);
		conveyor = new ConveyorAgent(this, conveyorController);

		// Instantiate the KitRobot
		kitRobot = new KitRobotAgent(this,conveyor);



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

		// Instantiate the PartsRobot
		List<Nest> nestAgentListForPartsRobot = new ArrayList<Nest>();
		nestAgentListForPartsRobot.add(0, n0t);
		nestAgentListForPartsRobot.add(1, n0b);
		nestAgentListForPartsRobot.add(2, n1t);
		nestAgentListForPartsRobot.add(3, n1b);
		nestAgentListForPartsRobot.add(4, n2t);
		nestAgentListForPartsRobot.add(5, n2b);
		nestAgentListForPartsRobot.add(6, n3t);
		nestAgentListForPartsRobot.add(7, n3b);

		partsRobot = new PartsRobotAgent(this, fcs, vision, stand, nestAgentListForPartsRobot); 
	
		//Hacking References
		conveyor.setKitRobot(kitRobot);
		kitRobot.setStand(stand);
		conveyor.setFCS(fcs);
		stand.setVision(vision);
		stand.setPartsRobot(partsRobot);
		conveyorController.setConveyor(conveyor);
		stand.setKitRobot(kitRobot);

		
		
		agentTreeMap.put("ca", conveyor);
		agentTreeMap.put("cca", conveyorController);
		agentTreeMap.put("ga", gantry );
		agentTreeMap.put("kra", kitRobot);
		agentTreeMap.put("pra", partsRobot);
		agentTreeMap.put("sa", stand);
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

		f0.stopThread();
		f1.stopThread();
		f2.stopThread();
		f3.stopThread();
		
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
			for(PartHandler ph : partHandlerList){
                if(ph.client_id.equals(parsedCommand.get(1))){
                    clientCmd(parsedCommand);
                }
            }
            return false;                                                 // This is called if in Debug mode and the client being sent to is not connected.
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

	// command sent to agent or agents
	// agentCmd is the giant parser that figures out
	// what method to call on what agent.

	public boolean agentCmd(ArrayList<String> cmd){ 		//GO HERE
        if(!checkAgentCmd(cmd)){
            return false;
        }
        Agent destination;
		// 0 = Source
		// 1 = Destination
		// 2 = CmdType
		// 3 = Cmd OR if cnf, this would be optional identifier
		// 4+ = Parameters

		System.out.print("agentCmd() = ");
		for (String c : cmd)
		{
			System.out.print(c + " ");
		}

        System.out.println();

		if(cmd.get(2).equals("cnf")){

			if(agentTreeMap.containsKey(cmd.get(1))){

				destination = agentTreeMap.get(cmd.get(1));

			} else {

				if(cmd.get(1).equals("na")){            // Nest

                    if (nestAgentTreeMap.get(cmd.get(3)) == null)
                    {
                        System.out.println("ERROR: NULL NESTAGENT.");
                        return false;
                    } else {
                        destination = nestAgentTreeMap.get(cmd.get(3));
                    }
				} else if(cmd.get(1).equals("la")){     // Lane

                    if (laneAgentTreeMap.get(cmd.get(3)) == null)
                    {
                        System.out.println("ERROR: NULL LANEAGENT.");
                        return false;
                    } else {
                        destination = laneAgentTreeMap.get(cmd.get(3));
                    }

				} else if(cmd.get(1).equals("fa")){     // Feeder
                    if (feederAgents.get(Integer.valueOf(cmd.get(3))) == null)
                    {
                        System.out.println("ERROR: NULL FEEDERAGENT.");
                        return false;
                    } else {
					    destination = feederAgents.get(Integer.valueOf(cmd.get(3)));
                    }

				} else {
					return false;
				}
			}

			destination.msgAnimationDone();
			return true;


		} else if( cmd.get(2).equals("set")){

			if (cmd.get(1).equals("fcsa")){

				destination = agentTreeMap.get(cmd.get(1));

				if(cmd.get(3).equals("kitcontent")){
					//#oldkitname #kitname #partname1 #partname2 ... #partname8"
					String oldkitname = cmd.get(4);
					String kitname = cmd.get(5);
					String partname1 = cmd.get(6);
					String partname2 = cmd.get(7);
					String partname3 = cmd.get(8);
					String partname4 = cmd.get(9);
					String partname5 = cmd.get(10);
					String partname6 = cmd.get(11);
					String partname7 = cmd.get(12);
					String partname8 = cmd.get(13);

					((FCSAgent) destination).editKitRecipe(oldkitname, kitname, partname1, partname2, partname3, partname4, 
							partname5, partname6, partname7, partname8);
				}
			}


		} else if( cmd.get(2).equals("cmd")){
			// cmd.get(2) is CmdType
			// cmd.get(3) is Command
			// cmd.get(4+) are parameters


			// FCSAgent Commands:
			if (cmd.get(1).equals("fcsa"))
			{
				destination = agentTreeMap.get(cmd.get(1)); // FCSAgent

				if(cmd.get(3).equals("makekits")){	//I know this worked before but should this be in clientCmd
					int quantity = Integer.valueOf(cmd.get(4));
					String name = cmd.get(5);
					((FCSAgent) destination).msgProduceKit(quantity,name);
				}

				if(cmd.get(3).equals("addpartname")){
					//#partname #partid #filepath #stabalizationtime #partdescription"
					String partname = cmd.get(4);
					int partid = Integer.valueOf(cmd.get(5));
					String filepath = cmd.get(6);
					int stabalizationtime = Integer.valueOf(cmd.get(7));
					String partdescription = cmd.get(8);
					((FCSAgent) destination).addPartType(partname, stabalizationtime, partdescription, partid, filepath);
				}


				if(cmd.get(3).equals("rmpartname")){
					//"#partname"
					String partname = cmd.get(4);
					((FCSAgent) destination).removePartType(partname);

				}

				if(cmd.get(3).equals("editpartname")){
					//" #originalpartname #newpartname #newpartid #newfilepath 
					//#newstabalizationtime #newpartdescription"
					String originalpartname = cmd.get(4);
					String newpartname = cmd.get(5);
					int newpartid = Integer.valueOf(cmd.get(6));
					String newfilepath = cmd.get(7);
					int newstabalizationtime = Integer.valueOf(cmd.get(8));
					String newpartdescription = cmd.get(9);
					((FCSAgent) destination).editPartType(originalpartname, newpartname,
							newpartid, newfilepath, newstabalizationtime, newpartdescription);
				}

				if(cmd.get(3).equals("addkitname")){
					//"#kitname #partname1 #partname2 ... #partname8"
					String kitname = cmd.get(4);
					String partname1 = cmd.get(5);
					String partname2 = cmd.get(6);
					String partname3 = cmd.get(7);
					String partname4 = cmd.get(8);
					String partname5 = cmd.get(9);
					String partname6 = cmd.get(10);
					String partname7 = cmd.get(11);
					String partname8 = cmd.get(12);
					((FCSAgent) destination).addKitRecipe(kitname, partname1, partname2, partname3, partname4, 
							partname5, partname6, partname7, partname8);
				}
				
				if (cmd.get(3).equals("rmkitname")){
					//"#kitname"
					String kitname = cmd.get(4);
					((FCSAgent) destination).removeKitRecipe(kitname);
					
				}
			}//End FCSAgent Commands

			// FeederAgent Commands:
			else if (cmd.get(1).equals("fa"))
			{

			}//End FeederAgent Commands

            // NestAgent Commands:
            else if (cmd.get(1).equals("na"))
            {
                //"na cmd neststabilized n" + laneManagerID + (i==0?"t":"b")
                destination = nestAgentTreeMap.get(cmd.get(4));

                if(cmd.get(3).equals("neststabilized")){
                    ((NestAgent) destination).msgNestHasStabilized();
                }
                else if(cmd.get(3).equals("nestdestabilized")){
                    ((NestAgent) destination).msgNestHasDestabilized();
                }
            }//End NestAgent Commands
		}


		return false; // Default is false.
	}
	// command sent to client
	// clientCmd figures out which partHandler to use sendCmd on
	// and then sends it.


	public boolean clientCmd(ArrayList<String> cmd){	
		String s = checkClientCmd(cmd);
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

		for(int i = 3; i < cmd.size(); i++){  // Command ... put command into string form
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
			PartHandler destinationPH = determinePH(b);
			return sendCmd(destinationPH, fullCmd);
		}


	}


	private ArrayList<PartHandler> getDestinations(String myCmd){



		if(multiCmd_1.contains(myCmd)){
            ArrayList<PartHandler> returnAL = new ArrayList<PartHandler>();
            for(String dst : multiCmdDst_1){
                if(partHandlers.containsKey(dst)){
                    returnAL.add(partHandlers.get(dst));
                }
            }
            return returnAL;
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

    private boolean checkAgentCmd(ArrayList<String> pCmd){

        // These are commands going to Agents
        if(!pCmd.get(2).equals("cnf")){
            if(pCmd.size() < 4){
                System.out.println("there must be a command");
                return false;
            }
            if(!cmds.contains(pCmd.get(3))){
                System.out.println("this is not a valid command please check wiki documentation for correct syntax.");
                return false;

            }
        }



        if(!clients.contains(pCmd.get(0)) && !agents.contains(pCmd.get(0))){
            System.out.println("source is not valid client or agent id");
            return false;

        }
        if(pCmd.get(0).equals(pCmd.get(1))){
            System.out.println("source and Destination cannot be the same");
            return false;

        }
        if(!cmdTypes.contains(pCmd.get(2))){
            System.out.println("commandtype is not valid commandtype");
            return false;

        }

        return true;

    }


	// checkCmd is called by parseCmd with the command received to check for errors
	// Therefore, it should be private.

	private String checkClientCmd(ArrayList<String> pCmd) {

		// Check that the cmd is of a valid length

		if(pCmd.size() < 4){
			return "there must be a command";
		}

		// Check that the source is a valid DID

		if(!clients.contains(pCmd.get(0)) && !agents.contains(pCmd.get(0))){
			return "source is not valid client or agent id";
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

	private void connectAllSockets(int debugnum){
		int numConnected = 0;
		int numToConnect = (debugnum > 0 ? debugnum : (clients.size()-1));
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
                partHandlerList.add(ph);
				pw.println("connected");
			} catch (Exception e){
				e.printStackTrace();
			}
			numConnected++;
		}

	}

	// sendConfirm() is the function responsible for sending a confirmation through each socket
	// this confirms that every connection has been made, and therefore, the factory sim can begin.

	private void sendConfirm(){
        for(PartHandler ph : partHandlerList){
            ph.send("mcs start");
        }

	}

	public static void main(String args[]){

        System.out.println("DEBUG MODE : Enter number of clients to connect.");
        System.out.println("PRODUCTION MODE : Enter 0.");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Integer debug = null;

        while(debug == null || debug < 0 || debug > 5){
            try {
                debug = Integer.valueOf(br.readLine());
            } catch (Exception ioe) {
                System.out.println("Error : Please enter a positive Integer (0-5).");
            }
        }

        MasterControl mc = new MasterControl(debug);

      //This pauses for ~5 seconds to allow for the FactoryProductionManager to load up
      		long timeToQuit = System.currentTimeMillis() + 5000;
      		while (System.currentTimeMillis() < timeToQuit);

      		// TEMPORARY, FOR TESTING PURPOSES:
      		Part p0 = new Part("eye",000,"desc","imgPath",2);
      		Part p1 = new Part("eye",000,"desc","imgPath",3);
      		Part p2 = new Part("shoe",001,"desc","imgPath",3);
      		Part p3 = new Part("shoe",001,"desc","imgPath",3);
      		Part p4 = new Part("sword",002,"desc","imgPath",4);
      		Part p5 = new Part("tentacle",002,"desc","imgPath",4);
      		
      		List<Part> partList = new ArrayList<Part>();
      		partList.add(p0);
      		partList.add(p1);
      		partList.add(p2);
      		partList.add(p3);
      		partList.add(p4);
      		partList.add(p5);
      		
      		
      		mc.n0t.msgYouNeedPart(p0);
      		
      		mc.n0t.msgPartsRobotGrabbingPartFromNest(1);
      		mc.n0t.msgPartsRobotGrabbingPartFromNest(1);
      		mc.n0t.msgPartsRobotGrabbingPartFromNest(1);
      		mc.n0t.msgPartsRobotGrabbingPartFromNest(1);
      		mc.n0t.msgPartsRobotGrabbingPartFromNest(1);
      		mc.n0t.msgPartsRobotGrabbingPartFromNest(1);
      		mc.n0t.msgPartsRobotGrabbingPartFromNest(1);
      		mc.n0t.msgPartsRobotGrabbingPartFromNest(1);
      		mc.n0t.msgPartsRobotGrabbingPartFromNest(1);
      		mc.n0t.msgPartsRobotGrabbingPartFromNest(1);
      		mc.n0t.msgPartsRobotGrabbingPartFromNest(1);

      		
//      		mc.n0t.msgYouNeedPart(p3);
//      		
//      		
//      		mc.n1t.msgYouNeedPart(p2);
//      		mc.n1b.msgYouNeedPart(p3);
//      		
//      		mc.n2t.msgYouNeedPart(p4);
//      		mc.n2b.msgYouNeedPart(p5);
      		
      
		
		
//		mc.f0.msgLaneNeedsPart(p0,mc.l0t); //eye to top
//
//		mc.f0.msgLaneNeedsPart(p2,mc.l0b); //shoe to bottom
//		
//		// TESTING PARTSROBOT:
//		KitConfig kc = new KitConfig();
//		kc.listOfParts = partList;
//		mc.partsRobot.topSlot = kc; // stand TOP SLOT position
//
//		mc.partsRobot.msgHereArePartCoordinatesForNest(mc.n0t,p0,0);
		
		
		// partsRobot.armOne,partsRobot.armTwo //instances of part object, must be instantiated
		
		
		
		
//				mc.f0.msgLaneNeedsPart(p0,mc.l0t); //eye to top
//		
//				mc.f0.msgLaneNeedsPart(p2,mc.l0b); //shoe to bottom
//		
//				mc.f0.msgLaneNeedsPart(p4,mc.l0t); //sword to top
//		
//				mc.f0.msgLaneNeedsPart(p5,mc.l0b); //tentacle to bottom
//		


		//mc.f1.msgLaneNeedsPart(p0,mc.l1t); //eye to top

		//mc.f1.msgLaneNeedsPart(p1,mc.l1b); //eye to bottom

		//mc.n1t.msgPartsRobotGrabbingPartFromNest(0); // parts robot grabs part	
		
		
		//		
		//		
		//
		//		mc.f2.msgLaneNeedsPart(p0,mc.l2t); //eye to top
		//
		//		mc.f2.msgLaneNeedsPart(p2,mc.l2b); //shoe to bottom
		//
		//		mc.f2.msgLaneNeedsPart(p4,mc.l2t); //sword to top



		//		mc.f0.msgLaneNeedsPart(p0, mc.l0t);		
		// should make the gantry go get a bin of parts
		// should call DoSwitchLane() and then DoStartFeeding()
		 
		 
	}

}

