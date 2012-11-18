package factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import factory.KitRobotAgent.StandInfo;
import factory.StandAgent.MySlotState;
import factory.interfaces.*;
import factory.masterControl.MasterControl;
import factory.test.mock.MockNest;
import agent.Agent;

public class PartsRobotAgent extends Agent implements PartsRobot {
	
	/** ================================================================================ **/
	/** 									Data	 									 **/
	/** ================================================================================ **/
		
		// ENUM to keep track of the position of the PartsRobot
		public enum PartsRobotPositions { STAND, CENTER, NEST_ZERO, NEST_ONE, NEST_TWO, NEST_THREE, NEST_FOUR, NEST_FIVE, NEST_SIX, NEST_SEVEN }
		public PartsRobotPositions position;
	
		// ENUM to keep track of state of kitConfig
		public enum KitConfigState { EMPTY, REQUESTED, PRODUCING }
		public KitConfigState currentKitConfigurationState;
		
		// ENUM to keep state of the nests
		public enum NestState { WAITING, PICTURE_NEEDED, WAITING_ON_PICTURE, PICK_UP_NEEDED }
		
		// Configuration of Kit we are currently producing
		public KitConfig currentKitConfiguration;
		
		// Kits to keep track of kits in slots
		public KitConfig topSlot;
		public KitConfig bottomSlot;

		// ENUM to know state of the stand
		public enum StandState { DOING_NOTHING };
		public StandState standState;
		
		// ENUM to know state of the stand
		public enum SlotState { EMPTY, BUILD_REQUESTED, BUILDING };
		public SlotState topSlotState;
		public SlotState bottomSlotState;
		
		// Nests
		public List<MyNest> nests = Collections.synchronizedList(new ArrayList<MyNest>());

		// Agents
		public FCS fcs;
		public Vision vision;
		public Stand stand;
		
		
		/**
		 * Private class to keep track of nests
		 */
		public class MyNest {
			public Nest nest;
			public NestState state;
			public Part part;
			public int partCoordinate;
		
			public MyNest(Nest nest){
				this.nest = nest;
				this.partCoordinate = -1;
				this.state = NestState.WAITING;
				this.part = null;
			}
		}
	
	/**
	 * Constructor for PartsRobot Agent
	 * @param mc
	 */
	public PartsRobotAgent(MasterControl mc, FCS fcs, Vision vision, Stand stand, List<Nest> nests) {
		
		// Call Constructor of Parent Class
		super(mc);
		
		// Parts Robot starts in the middle
		position = PartsRobotPositions.CENTER;
		
		// The current configuration is null
		this.currentKitConfiguration = null;
		this.currentKitConfigurationState = KitConfigState.EMPTY;
		this.topSlot = null;
		this.bottomSlot = null;
		this.topSlotState = SlotState.EMPTY;
		this.bottomSlotState = SlotState.EMPTY;
		
		// Agents
		this.fcs = fcs;
		this.vision = vision;
		this.stand = stand;
		this.standState = StandState.DOING_NOTHING;
		
		for(int i = 0; i < 8; i++){
			this.nests.add(new MyNest(nests.get(i)));
		}
		
		
	}

/** ================================================================================ **/
/** 									MESSAGES 									 **/
/** ================================================================================ **/
	
	/**
	 * Message that is received from the FCS when a new kit configuration comes in.
	 */
	public void msgMakeKit(KitConfig kitConfig) {
		this.currentKitConfiguration = kitConfig;
		this.currentKitConfigurationState = KitConfigState.REQUESTED;
		
	}
	
	public void msgBuildKitAtSlot(String slot) {
		if (slot.equals("topSlot")){
			this.topSlotState = SlotState.BUILD_REQUESTED;
		}
		else {
			this.bottomSlotState = SlotState.BUILD_REQUESTED;
		}
	}

	public void msgDeliverKitParts() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgClearLineOfSight(Nest nestOne, Nest nestTwo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgPictureTaken(Nest nestOne, Nest nestTwo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgHereArePartCoordiantes(Part part, Coordinate coordinateTwo) {
		// TODO Auto-generated method stub
		
	}
	
/** ================================================================================ **/
/** 									SCHEDULER 									 **/
/** ================================================================================ **/

	public boolean pickAndExecuteAnAction() {
		
		synchronized(this.currentKitConfigurationState){
			
			// If a new Kit Configuration was requested
			if (this.currentKitConfigurationState == KitConfigState.REQUESTED) {
				DoProcessNewKitConfiguration();
				return true;
			}
			
			if (this.currentKitConfigurationState == KitConfigState.PRODUCING && this.topSlotState == SlotState.BUILD_REQUESTED){
				DoStartBuildingKitAtSlot("topSlot");
				return true;
			}
			
			if (this.currentKitConfigurationState == KitConfigState.PRODUCING && this.bottomSlotState == SlotState.BUILD_REQUESTED){
				DoStartBuildingKitAtSlot("bottomSlot");
				return true;
			}
	
		}	
		
		
		return false;
	}

	
/** ================================================================================ **/
/** 									ACTIONS 									 **/
/** ================================================================================ **/
	
	/**
	 * Action to process the new Kit Configuration
	 */
	public void DoProcessNewKitConfiguration() {
		debug("Executing DoProcessNewKitConfiguration()");
		
		// Reset the slots to empty
		this.topSlot = null;
		if(this.topSlotState == SlotState.BUILDING){
			this.topSlotState = SlotState.EMPTY;
		}
		this.bottomSlot = null;
		if(this.bottomSlotState == SlotState.BUILDING){
			this.bottomSlotState = SlotState.EMPTY;
		}
		
		// Tell the stand to clear stand
		stand.msgClearStand();
		
		// set parts to corresponding nests - this algorithm won't replace lanes that are needed in the new one but already present in the old
		int currentIndex = 0;
		int newIndex = 0;

		List<Integer> newNeeded = new ArrayList<Integer>();
		List<Integer> currentNotNeeded = new ArrayList<Integer>();
		
		// Check which new ones are not in the old config
		for(int i = 0; i < currentKitConfiguration.listOfParts.size(); i++){
			boolean present = false;
			
			for(int j = 0; j < nests.size() && !newNeeded.contains(i); j++){
				
				if(nests.get(j).part != null && nests.get(j).part.name == currentKitConfiguration.listOfParts.get(i).name ){
					present = true;
				}
			}
			
			if(!present){
				newNeeded.add(i);
			}
		}
		
		// Check which old ones are not in the new config
		for(int i = 0; i < nests.size(); i++){
			boolean present = false;
			
			for(int j = 0; j < currentKitConfiguration.listOfParts.size() && !currentNotNeeded.contains(i); j++){
				if(nests.get(j).part != null && nests.get(j).part.name == currentKitConfiguration.listOfParts.get(i).name ){
					present = true;
				}
			}
			
			if(!present){
				currentNotNeeded.add(i);
			}
		} 
		
		for(int i = 0; i < currentNotNeeded.size(); i++){
			
			nests.get(currentNotNeeded.get(i)).part = currentKitConfiguration.listOfParts.get(newNeeded.get(i));
			nests.get(currentNotNeeded.get(i)).nest.msgYouNeedPart(currentKitConfiguration.listOfParts.get(newNeeded.get(i)));
			
		}
		
		// The new configuration is now in prodution
		this.currentKitConfigurationState = KitConfigState.PRODUCING;
	}
	
	/**
	 * Action to start producing a Kit in Slot
	 */
	public void DoStartBuildingKitAtSlot(String slot){
		debug("Executing DoStartBuildingKitAtSlot("+slot+")");
		if(slot.equals("topSlot")){
			this.topSlot = this.currentKitConfiguration;
			this.topSlotState = SlotState.BUILDING;
		}
		else {
			this.bottomSlot = this.currentKitConfiguration;
			this.bottomSlotState = SlotState.BUILDING;
		}
	}
	


/** ================================================================================ **/
/** 									ANIMATIONS 									 **/
/** ================================================================================ **/
	
	

}

