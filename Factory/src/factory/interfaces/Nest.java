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
	
	void setBeingUsed(boolean val);
	void msgPartRemovedFromNest(String partName);
	
}
