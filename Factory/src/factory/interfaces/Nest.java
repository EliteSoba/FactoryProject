package factory.interfaces;

import java.util.List;

import factory.Part;

public interface Nest {
	public enum NestState { STABLE, UNSTABLE}

	
	String getNestName();
	Part getPart();

	boolean isBeingUsed();
	List<Part> getParts();
	NestState getState();
	Lane getLane();
	int getPosition();
	void msgYouNeedPart(Part part);
	
<<<<<<< HEAD
	void msgPartRemovedFromNest(String partName);
	
	
//	void msgPartsRobotGrabbingPartFromNest(int coordinate);
//	void msgFeedingParts(int numParts);
=======
>>>>>>> Vision non normatives
}
