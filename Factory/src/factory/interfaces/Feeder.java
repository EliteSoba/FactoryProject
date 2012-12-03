package factory.interfaces;

import factory.Part;

public interface Feeder {

	public void msgHereAreParts(Part pt);

	public void msgLaneMightBeJammed(int nestNumber0or1);

	public void msgNestWasDumped(Lane la);

	public void msgLaneNeedsPart(Part part, Lane lane);

	public void msgBadNest(int nestNumber);
	
	public void setGantry(Gantry g);

	public boolean getFeederHasABinUnderneath();
	
	public int getFeederNumber();

	public void msgNestHasStabilized(Lane lane);

	public void msgNestHasDeStabilized(Lane lane);

}
