package factory.interfaces;

import java.util.TimerTask;
import factory.Kit;
public interface Vision {

	public void msgMyNestsReadyForPicture(Nest nest, Nest nest2, Feeder feeder);
	public void msgAnalyzeKitAtInspection(Kit kit);
}
