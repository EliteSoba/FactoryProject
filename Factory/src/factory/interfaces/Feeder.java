package factory.interfaces;

import factory.Part;

public interface Feeder {

	public void msgHereAreParts(Part pt);

	public void msgEmptyNest(Nest n);

	public void msgNestWasDumped(Lane la);

	public void msgLaneNeedsPart(Part part, Lane lane);

	public void msgBadNest(Nest n);
	
	public void setGantry(Gantry g);

	public boolean getFeederHasABinUnderneath();
	
	public int getFeederNumber();

	public void msgNestHasStabilized(Lane lane);
}
