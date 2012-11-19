package factory.interfaces;

import factory.Kit;

public interface Conveyor {
		
		public void msgHeresEmptyKit(Kit k);

		public void msgNeedEmptyKit();

		public void msgExportKit(Kit k);
		
		public Kit getAtConveyor();
		
		public void setAtConveyor(Kit k);
		

}
