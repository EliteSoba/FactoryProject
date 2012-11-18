package factory;

import java.util.ArrayList;

import agent.Agent;
import factory.interfaces.Feeder;
import factory.interfaces.Lane;
import factory.interfaces.Nest;
import factory.masterControl.MasterControl;

public class LaneAgent extends Agent implements Lane {

	public LaneAgent(MasterControl mc) {
		super(mc);
	}

	/** DATA **/
	public ArrayList<MyPart> myParts = new ArrayList<MyPart>();
	public Feeder myFeeder;
	//	public enum LaneState { NORMAL, NEEDS_TO_PURGE, PURGING };  // not sure about this, need to update wiki still
	//	public LaneState state = LaneState.NORMAL; 
	public Nest myNest;
	public int amplitude = 5;
	public static int kMAX_AMPLITUDE = 20;
	public enum NestState { NORMAL, HAS_STABILIZED, NEEDS_TO_DUMP, WAITING_FOR_DUMP_CONFIRMATION,
		NEST_WAS_DUMPED, NEEDS_TO_INCREASE_AMPLITUDE }
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
		stateChanged();
	}

	public void msgNestNeedsPart(Part part) {
		debug("received msgNestNeedsPart("+part.name+").");
		myParts.add(new MyPart(part));
		stateChanged();
	}

	public void msgNestHasStabilized() {
		nestState = NestState.HAS_STABILIZED;
		stateChanged();
	}

	public void msgDumpNest() {
		nestState = NestState.NEEDS_TO_DUMP;
		stateChanged();
	}

	public void msgNestWasDumped() {
		nestState = NestState.NEST_WAS_DUMPED;
		stateChanged();
	}

	public void msgPurge() {
		// TODO Auto-generated method stub
		stateChanged();
	}

	/** SCHEDULER **/
	public boolean pickAndExecuteAnAction() {
		if (nestState == NestState.HAS_STABILIZED)
		{
			tellFeederNestHasStabilized();
			return true;
		}
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
	public void tellFeederNestHasStabilized() {
		nestState = NestState.NORMAL;
		myFeeder.msgNestHasStabilized(this);
		stateChanged();
	}

	public void tellFeederNestWasDumped() {
		//if (nestState != NestState.NEEDS_TO_INCREASE_AMPLITUDE) // unnecessary, will never happen
		nestState = NestState.NORMAL;
		myFeeder.msgNestWasDumped(this);
		stateChanged();
	}

	public void increaseAmplitude() {
		if (amplitude+5 <= kMAX_AMPLITUDE)
		{
			amplitude += 5;
			DoIncreaseAmplitude(amplitude);
			nestState = NestState.NORMAL;
		}
		stateChanged();
	}

	public void askFeederToSendParts(MyPart part) { 
		debug("asking feeder to send parts of type " + part.pt.name + ".");
		myFeeder.msgLaneNeedsPart(part.pt, this);
		part.state = MyPartState.REQUESTED;
		stateChanged();
	}

	public void dumpNest() { 
		debug("telling my nest it should dump.");
		nestState = NestState.WAITING_FOR_DUMP_CONFIRMATION;
		myNest.msgDump();
		stateChanged();
	}



	/** ANIMATIONS **/
	private void DoIncreaseAmplitude(int amp) 
	{
		debug("amplitude increased to " + amp + ".");
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
