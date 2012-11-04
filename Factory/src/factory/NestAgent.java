package factory;

import java.util.ArrayList;

import agent.Agent;
import factory.interfaces.Lane;
import factory.interfaces.Nest;

public class NestAgent extends Agent implements Nest {
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
	}
	public void msgYouNeedPart(Part part) {
		myParts.add(new MyPart(part));
	}


	/** SCHEDULER **/
	public boolean pickAndExecuteAnAction() {
		if (nestState == NestState.NEEDS_TO_DUMP)
		{
			dump();
			return true;
		}
		for(MyPart p : myParts)
		{
			if (p.state == MyPartState.NEEDED)
			{
				askLaneToSendParts(p);
				return true;
			}
		}

		return false;
	}


	/** ACTIONS **/
	public void dump() {
		DoDumpNest();
		nestState = NestState.NORMAL;
	}
	
	public void askLaneToSendParts(MyPart part) { 
		part.state = MyPartState.REQUESTED;
		myLane.msgNestNeedsPart(part.pt);
	}
	
	
	/** ANIMATIONS **/
	private void DoDumpNest() {
		print("dumping.");
	}
	
	
	/** OTHER **/
	public void setLane(Lane la) {
		myLane = la;
	}

}




