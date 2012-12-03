package factory.interfaces;

import java.util.List;

import factory.Coordinate;
import factory.Kit;
import factory.KitConfig;
import factory.Part;

public interface PartsRobot {

	public void msgBuildKitAtSlot(String slot);
	public void msgDeliverKitParts();
	public void msgMakeKit(KitConfig kitConfig);
	public void msgClearLineOfSight(Nest nestOne, Nest nestTwo);
	public void msgPictureTaken(Nest nestOne, Nest nestTwo);
	public void msgGrabGoodPartFromNest(Nest nest, Part part);
	public void msgNoMoreOrders();
	public void msgFixKitAtSlot(String name, List<String> brokenPartsList);
}
