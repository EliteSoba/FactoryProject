package factory.interfaces;

import factory.BinConfig;
import factory.Kit;
import factory.KitConfig;

public interface FCS {
	public void msgInitialize(BinConfig binConfig);
	   
		//msg from Panel to produce a kit
	   public void msgProduceKit(String name, int quantity);
	   
	   //msg from KitRobot that says kit is finished. ADDED THIS
	   public void kitIsFinished();
	   
	   public void msgKitIsExported(Kit kit);
}
