package factory.interfaces;


public interface Lane {

public Nest getNest(); //returns this lane's nest

public void msgIncreaseAmplitude();

public void msgDumpNest();

public void msgPurge();

public String getName();

public void setNest(Nest n); // used for testing purposes only

public void msgNestWasDumped();

}
