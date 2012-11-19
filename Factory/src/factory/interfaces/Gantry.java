package factory.interfaces;

import factory.BinConfig;
import factory.Part;

public interface Gantry {

	void msgFeederNeedsPart(Part pt, Feeder feederAgent);

}
