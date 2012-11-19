package factory.interfaces;

import factory.Part;

public interface Nest {

	Part part = null; //temporary.  later, will need to implement a getPart();
	String nestName = "";
	void msgDump();
	String getNestName();
	

	void msgYouNeedPart(Part part);
	
	void msgPartsRobotGrabbingPartFromNest(int coordinate);
	void msgFeedingParts(int numParts);
}
