package factory.interfaces;

import java.util.TimerTask;
import factory.Kit;
public interface Vision {

	public void msgHereArePartCoordiantesForNest(Nest nest, Nest nest2, Feeder feeder);
	public void msgAnalyzeKitAtInspection(Kit kit);

	public void msgVisionClearForPictureInNests(Nest nest, Nest nest2);
	
	
}
