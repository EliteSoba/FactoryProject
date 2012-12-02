package factory.interfaces;

import java.util.List;

import factory.Part;

public interface Nest {

	Part part = null; //temporary.  later, will need to implement a getPart();
	String nestName = "";
	void msgDump();
	String getNestName();
	Part getPart();

	boolean isBeingUsed();
	List<Part> getParts();
	
	int getPosition();
	void msgYouNeedPart(Part part);
	
//	void msgPartsRobotGrabbingPartFromNest(int coordinate);
//	void msgFeedingParts(int numParts);
}
