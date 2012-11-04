package factory.test.mock;

import factory.BinConfig;
import factory.Part;
import factory.interfaces.Feeder;
import factory.interfaces.Gantry;

public class MockGantry extends MockAgent implements Gantry {

	public EventLog log = new EventLog();
	
	public MockGantry(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	} 


	@Override
	public void msgChangeGantryBinConfig(BinConfig binConfig) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void msgFeederNeeds(Part pt, Feeder feeder) {
		log.add(new LoggedEvent("msgFeederNeeds(...)")); 
	}
}