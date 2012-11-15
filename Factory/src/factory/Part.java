package factory;

import java.io.Serializable;
import java.io.*;

public class Part implements Serializable{

	public String name;               // name of the part type
	public int id;
	public String description;
	public String imagePath; //file path of the image associated with the part
	public double nestStabilizationTime;   // The average time that the part takes to reach the nest to be used by the feeder


	public Part(String n,int i,String d,String p,double t) {
		name = n;
		id = i;
		description = d;
		imagePath = p;
		nestStabilizationTime = t;
	}
	// temporary
	public Part(String s)
	{
		name = s;
	}
	
	public Part(){}
}
