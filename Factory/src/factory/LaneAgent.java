package factory;

import java.util.ArrayList;

import agent.Agent;
import factory.interfaces.Feeder;
import factory.interfaces.Lane;
import factory.interfaces.Nest;

public class LaneAgent extends Agent implements Lane {

	ArrayList<MyPart> myParts = new ArrayList<MyPart>();
	Feeder myFeeder;
	Nest myNest;
	int amplitude = 5;
	int kMAX_AMPLITUDE = 20;
	enum NestState { NORMAL, NEEDS_TO_DUMP, WAITING_FOR_DUMP_CONFIRMATION, NEST_WAS_DUMPED, NEEDS_TO_INCREASE_AMPLITUDE }
	NestState nestState;

	enum MyPartState {  NEEDED, REQUESTED }

	class MyPart {
		Part pt;
		MyPartState state;

		public MyPart(Part part){
			this.state = MyPartState.NEEDED;
			this.pt = part;
		}
	}

	@Override
	public Nest getNest() {
		return myNest;
	}

	@Override
	public void msgIncreaseAmplitude() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgDumpNest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgPurge() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean pickAndExecuteAnAction() {
		// TODO Auto-generated method stub
		return false;
	}

	/** This method sets the nest of this lane.  
	 * NOTE: Used for testing purposes only. 
	 */
	public void setNest(Nest n) {
		myNest = n;
	}


}
