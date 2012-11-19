package factory.interfaces;

import factory.Part;


public interface Lane {

public Nest getNest(); //returns this lane's nest

public void msgIncreaseAmplitude();

public void msgDumpNest();

public void msgPurge();

public void setNest(Nest n); // used for testing purposes only

public void msgNestWasDumped();

public void msgNestNeedsPart(Part pt);

public void msgNestHasStabilized();

public void msgNestHasDestabilized();

public void msgPartRobotHasRemovedParts(int numberOfParts);

public void msgFeedingParts(int numParts);


}
