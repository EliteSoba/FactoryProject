package factory;

import java.util.ArrayList;

import agent.Agent;
import factory.interfaces.Lane;
import factory.interfaces.Nest;

public class NestAgent extends Agent implements Nest {
	protected NestAgent(Type t) {
		super(t);
		// TODO Auto-generated constructor stub
	}


	/** DATA **/
	public ArrayList<MyPart> myParts = new ArrayList<MyPart>();
	public Lane myLane;
	public enum NestState { NORMAL, NEEDS_TO_DUMP }
	public NestState nestState = NestState.NORMAL;
	public enum MyPartState {  NEEDED, REQUESTED }
	public class MyPart {
		public Part pt;
		public MyPartState state;

		public MyPart(Part partType){
			this.state = MyPartState.NEEDED;
			this.pt = partType;
		}
	}

	/** MESSAGES **/
	public void msgDump() {
		nestState = NestState.NEEDS_TO_DUMP;
		stateChanged();
	}
	public void msgYouNeedPart(Part part) {
		debug("received msgYouNeedPart("+part.name+").");
		myParts.add(new MyPart(part));
		stateChanged();
	}


	/** SCHEDULER **/
	public boolean pickAndExecuteAnAction() {
		debug("here?");
		if (nestState == NestState.NEEDS_TO_DUMP)
		{
			debug("1?");
			dump();
			return true;
		}
		for(MyPart p : myParts)
		{
			debug("2?");

			if (p.state == MyPartState.NEEDED)
			{
				debug("3?");

				askLaneToSendParts(p);
				return true;
			}
		}
		
		debug("4?");

		return false;
	}


	/** ACTIONS **/
	public void dump() {
		DoDumpNest();
		nestState = NestState.NORMAL;
		stateChanged();
	}
	
	public void askLaneToSendParts(MyPart part) { 
		debug("asking lane to send parts of type "+part.pt.name + ".");
		part.state = MyPartState.REQUESTED;
		myLane.msgNestNeedsPart(part.pt);
		stateChanged();
	}
	
	
	/** ANIMATIONS **/
	private void DoDumpNest() {
		debug("dumping.");
	}
	
	
	/** OTHER **/
	public void setLane(Lane la) {
		myLane = la;
	}

}




