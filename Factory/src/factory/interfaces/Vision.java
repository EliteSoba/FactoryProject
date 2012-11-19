package factory.interfaces;

import factory.Kit;
import factory.Part;
public interface Vision {

	public void msgAnalyzeKitAtInspection(Kit kit);

	
	 //Alfonso, which of these should we use?
	public void msgVisionClearForPictureInNests(Nest nest, Nest nest2);
	public void msgMyNestsReadyForPicture(Nest nest, Nest nest2,Feeder feeder);
	
}
