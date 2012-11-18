package factory.interfaces;

import factory.BinConfig;
import factory.Kit;
import factory.KitConfig;

public interface FCS {

	   
		//msg from Panel to produce a kit
	   public void msgProduceKit( int quantity, String name);
	   
	   public void msgKitIsExported(Kit kit);
}
