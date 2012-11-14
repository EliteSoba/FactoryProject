package factory;

public class Part {
	public Part(String string) {
		this.name = string;
	}
	public String name;               // name of the part type
	public double nestStabilizationTime;   // The average time that the part takes to reach the nest to be used by the feeder
	public String description;
	public int id;
	public String imagePath; //file path of the image associated with the part
}