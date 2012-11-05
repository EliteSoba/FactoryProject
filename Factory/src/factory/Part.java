package factory;

public class Part {
	public Part(String string) {
		this.name = string;
	}
	public String name;               // name of the part type
	public double averageDelayTime;   // The average time that the part takes to reach the nest to be used by the feeder
	public double productionTime;       // The time it takes to produce a piece when refilling
}