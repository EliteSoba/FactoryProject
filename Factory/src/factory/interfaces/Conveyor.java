package factory.interfaces;

import factory.Kit;

public interface Conveyor {
		public void msgHeresEmptyKit(Kit k);
		public void msgTakingKit();
		public void msgPuttingKit(KitRobot kr, Kit k);
		public void msgNeedEmptyKit(KitRobot kr);
		public void msgExportKit(KitRobot kr);

}
