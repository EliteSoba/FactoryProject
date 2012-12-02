//Ben Mayeux and Stephanie Reagle and Marc Mendiola
//CS 200

package factory.managers;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import factory.graphics.*;
import factory.*;
import factory.swing.LaneManPanel;
import factory.client.Client;

public class LaneManager extends Client {

	private static final long serialVersionUID = 6767006307991802656L;

	LaneManPanel buttons;
	LanePanel animation;
	ArrayList<Integer> laneSpeeds; // stores speeds of each lane
	ArrayList<Integer> laneAmplitudes; // stores amplitudes of each lane

	public LaneManager() {
		super(Client.Type.lm); 

		buttons = new LaneManPanel(this);
		animation = new LanePanel(this);

		laneSpeeds = new ArrayList<Integer>();
		laneAmplitudes = new ArrayList<Integer>(); 
		for (int i = 0; i < 8; i++){    // presets lane speeds and amplitudes
			laneSpeeds.add(2);  
			laneAmplitudes.add(2);
		}

		setInterface();
	}

	public static void main(String[] args){
		LaneManager l = new LaneManager(); 
	}


	public void setInterface() {
		graphics = animation;
		UI = buttons;

		add(graphics, BorderLayout.CENTER);

		add(UI, BorderLayout.LINE_END);  

		pack();
		setVisible(true);
		this.setTitle("Lane Manager");
	}

	public void doCommand(ArrayList<String> pCmd) {
		int size = pCmd.size();
		//parameters lay between i = 2 and i = size - 2
		System.out.println(Arrays.toString(pCmd.toArray()));
		String action = pCmd.get(0);
		String identifier = pCmd.get(1);
		if(action.equals("cmd")){

			// Commands from FeederAgent
			if (identifier.equals("startfeeding"))
			{
				int feederSlot = Integer.valueOf(pCmd.get(2));
				((LanePanel) graphics).feedFeeder(feederSlot);
			}
			else if (identifier.equals("stopfeeding"))
			{
				int feederSlot = Integer.valueOf(pCmd.get(2));
				((LanePanel) graphics).turnFeederOff(feederSlot);
			} 
			else if (identifier.equals("purgefeeder"))
			{
				int feederSlot = Integer.valueOf(pCmd.get(2));
				((LanePanel) graphics).purgeFeeder(feederSlot);
			}
			else if (identifier.equals("switchlane"))
			{
				int feederSlot = Integer.valueOf(pCmd.get(2));
				((LanePanel) graphics).switchFeederLane(feederSlot);
			}
			else if (identifier.equals("purgetoplane"))
			{
				int feederSlot = Integer.valueOf(pCmd.get(2));
				((LanePanel) graphics).purgeTopLane(feederSlot);
			}
			else if (identifier.equals("purgebottomlane"))
			{
				int feederSlot = Integer.valueOf(pCmd.get(2));
				((LanePanel) graphics).purgeBottomLane(feederSlot);
			}
			else if (identifier.equals("jamtoplane"))
			{
				int feederSlot = Integer.valueOf(pCmd.get(2));
				((LanePanel) graphics).jamTopLane(feederSlot);
			}
			else if (identifier.equals("jambottomlane"))
			{
				int feederSlot = Integer.valueOf(pCmd.get(2));
				//((LanePanel) graphics).jamBottomLane(feederSlot);
			}
			else if (identifier.equals("unjamtoplane"))
			{
				int feederSlot = Integer.valueOf(pCmd.get(2));
				((LanePanel) graphics).unjamTopLane(feederSlot);
			}
			else if (identifier.equals("unjambottomlane"))
			{
				int feederSlot = Integer.valueOf(pCmd.get(2));
				//((LanePanel) graphics).unjamBottomLane(feederSlot);
			}
			else if (identifier.equals("dumptopnest"))
			{
				int nestIndex = Integer.valueOf(pCmd.get(2));
				((LanePanel) graphics).dumpNest(nestIndex, true);
			}
			else if (identifier.equals("dumpbottomnest"))
			{
				int nestIndex = Integer.valueOf(pCmd.get(2));
				((LanePanel) graphics).dumpNest(nestIndex, false);
			}
		
			//Commands for speed and amplitude
			/*else if(need the command id here)
			{
				//use changeTopLaneSpeed(int speed) speed here to change speed
				laneSpeeds.get("lane number").changeTopLaneSpeed("speed");
			}
			
			else if(need the command id here)
			{
				//use changeBottomLaneSpeed(int speed) speed here to change speed
				laneSpeeds.get("lane number").changeVLaneSpeed("speed");
			}
			
			else if(need the command id here)
			{
				//use changeBottomLaneAmplitude(int amp) 
				laneAmplitudes.get("lanenumber").changeTopLaneAmplitude("lane Amplitude form command");
			}
			
			else if(need the command id here)
			{
				//use changeBottomLaneAmplitude(int amp) 
				laneAmplitudes.get("lanenumber").changeBottomLaneAmplitude("lane Amplitude form command");

			}
			*/
			
			//Commands from VisionAgent
			else if (identifier.equals("takepictureofnest")) {
				int nestIndex = Integer.valueOf(pCmd.get(2));
				((LanePanel) graphics).cameraFlash(nestIndex);
			}
			// commands from lane manager
			else if (identifier.equals("badparts")) {
				int feederIndex = Integer.valueOf(pCmd.get(2));
				int badPercent = Integer.valueOf(pCmd.get(3));
				((LanePanel) graphics).setBadProbability(feederIndex, badPercent);
			}


		}
		else if(action.equals("req")){

		}
		else if(action.equals("get")){

		}
		else if(action.equals("set")){
			if (identifier.equals("loadpartatfeeder")) {
				int feederSlot = Integer.valueOf(pCmd.get(2));
				GraphicBin bin = new GraphicBin(new Part(pCmd.get(3)));
				((LanePanel) graphics).setFeederBin(feederSlot, bin);
			}
			else if (identifier.equals("nestitemtaken")) {
				int nestIndex = Integer.valueOf(pCmd.get(2));
				int itemIndex = Integer.valueOf(pCmd.get(3));
				((LanePanel) graphics).popNestItem(nestIndex, itemIndex);
			}

			// command from self
			else if (identifier.equals("lanespeed")){
				int laneNumber = Integer.valueOf(pCmd.get(2));
				int speed = Integer.valueOf(pCmd.get(3));
				if(laneNumber % 2 == 0)
					((LanePanel) graphics).getLane(laneNumber/2).changeTopLaneSpeed(speed);
				else
					((LanePanel) graphics).getLane(laneNumber/2).changeBottomLaneSpeed(speed);
				// call graphics function to change speed

			}else if (identifier.equals("laneamplitude")){
				int laneNumber = Integer.valueOf(pCmd.get(2));
				int amplitude = Integer.valueOf(pCmd.get(3));
				if(laneNumber % 2 == 0)
					((LanePanel) graphics).getLane(laneNumber/2).changeTopLaneAmplitude(amplitude);
				else
					((LanePanel) graphics).getLane(laneNumber/2).changeBottomLaneAmplitude(amplitude);
				// call graphics function to change amplitude
			}else if (identifier.equals("lanepower")){
				int laneNumber = Integer.valueOf(pCmd.get(3));
				
				if(pCmd.get(2).equals("on")){
					if(laneNumber % 2 == 0){
						((LanePanel) graphics).startTopLane(laneNumber/2);
					}else{

						((LanePanel) graphics).startBottomLane(laneNumber/2);
					}
				}else if(pCmd.get(2).equals("off")){
					if(laneNumber % 2 == 0){
						((LanePanel) graphics).stopTopLane(laneNumber/2);
					}else{

						((LanePanel) graphics).stopBottomLane(laneNumber/2);
					}
				}
			}else if (identifier.equals("feederpower")){
				int feederNumber = Integer.valueOf(pCmd.get(3));
				
				if(pCmd.get(2).equals("on")){
					((LanePanel) graphics).turnFeederOn(feederNumber);
				}else if(pCmd.get(2).equals("off")){
					((LanePanel) graphics).turnFeederOff(feederNumber);
				}
			}

		}
		else if(action.equals("cnf")){

		}
		else if(action.equals("mcs")){
			try {
				this.server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(0);
		}
		else 
			System.out.println("Stuff is FU with the server...\n(string does not contain a command type)");
	}

	public void setLaneSpeed(int laneNumber, int speed){
		laneSpeeds.set(laneNumber, speed);
	}

	public void setLaneAmplitude(int laneNumber, int amplitude){
		laneAmplitudes.set(laneNumber, amplitude);
	}

	public int getLaneSpeed(int laneNumber){
		return laneSpeeds.get(laneNumber-1);
	}

	public int getLaneAmplitude(int laneNumber){
		return laneAmplitudes.get(laneNumber-1);
	}
}
