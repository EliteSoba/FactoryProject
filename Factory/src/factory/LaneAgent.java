package factory;

import java.util.ArrayList;

import agent.Agent;
import factory.interfaces.Feeder;
import factory.interfaces.Lane;
import factory.interfaces.Nest;

public class LaneAgent extends Agent implements Lane {
	/** DATA **/
	public ArrayList<MyPart> myParts = new ArrayList<MyPart>();
	public Feeder myFeeder;
//	public enum LaneState { NORMAL, NEEDS_TO_PURGE, PURGING };  // not sure about this, need to update wiki still
//	public LaneState state = LaneState.NORMAL; 
	public Nest myNest;
	public int amplitude = 5;
	public static int kMAX_AMPLITUDE = 20;
	public enum NestState { NORMAL, NEEDS_TO_DUMP, WAITING_FOR_DUMP_CONFIRMATION, NEST_WAS_DUMPED, NEEDS_TO_INCREASE_AMPLITUDE }
	public NestState nestState;

	public enum MyPartState {  NEEDED, REQUESTED }

	public class MyPart {
		public Part pt;
		public MyPartState state;

		public MyPart(Part part){
			this.state = MyPartState.NEEDED;
			this.pt = part;
		}
	}


	/** MESSAGES **/
	public void msgIncreaseAmplitude() {
		nestState = NestState.NEEDS_TO_INCREASE_AMPLITUDE;
	}

	public void msgNestNeedsPart(Part part) {
		myParts.add(new MyPart(part));
	}

	public void msgDumpNest() {
		nestState = NestState.NEEDS_TO_DUMP;
	}

	public void msgNestWasDumped() {
		nestState = NestState.NEST_WAS_DUMPED;
	}

	public void msgPurge() {
		// TODO Auto-generated method stub

	}

	/** SCHEDULER **/
	public boolean pickAndExecuteAnAction() {
		if (nestState == NestState.NEEDS_TO_DUMP)
		{
			dumpNest();
			return true;
		}
		if (nestState == NestState.NEST_WAS_DUMPED)
		{
			tellFeederNestWasDumped();
			return true;
		}
		if (nestState == NestState.NEEDS_TO_INCREASE_AMPLITUDE)
		{
			increaseAmplitude();
			return true;
		}

		for(MyPart p : myParts)
		{
			if (p.state == MyPartState.NEEDED) 
			{
				askFeederToSendParts(p);
				return true;
			}
		}


		return false;
	}


	/** ACTIONS **/
	
	public void tellFeederNestWasDumped() {
		//if (nestState != NestState.NEEDS_TO_INCREASE_AMPLITUDE) // unnecessary, will never happen
			nestState = NestState.NORMAL;
		myFeeder.msgNestWasDumped(this);
	}
	
	public void increaseAmplitude() {
		if (amplitude+5 <= kMAX_AMPLITUDE)
		{
			amplitude += 5;
			DoIncreaseAmplitude(amplitude);
			nestState = NestState.NORMAL;
		}
	}
	
	public void askFeederToSendParts(MyPart part) { 
		myFeeder.msgLaneNeedsPart(part.pt, this);
		part.state = MyPartState.REQUESTED;
	}

	public void dumpNest() { 
		nestState = NestState.WAITING_FOR_DUMP_CONFIRMATION;
		myNest.msgDump();
	}

	
	
	/** ANIMATIONS **/
	private void DoIncreaseAmplitude(int amp) 
	{
		print("amplitude increased to " + amp + ".");
	}



	/** OTHER **/

	/** This method sets the nest of this lane.  
	 * NOTE: Used for testing purposes only. 
	 */
	public void setNest(Nest n) {
		myNest = n;
	}

	public Nest getNest() {
		return myNest;
	}
	
	public void setFeeder(Feeder f) {
		myFeeder = f;
	}
	

}
