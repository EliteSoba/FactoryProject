package factory;

import java.io.*;
import java.util.ArrayList;

public class Part implements Serializable,  Cloneable{

	public String name;               // name of the part type
	public int id;
	public String description;
	public String imagePath; //file path of the image associated with the part
	public int nestStabilizationTime;   // The average time that the part takes to reach the nest to be used by the feeder


	public Part(String n,int i,String d,String p,int t) {
		name = n;
		id = i;
		description = d;
		imagePath = p;
		nestStabilizationTime = t;
	}
	
	public Part(ArrayList<String> command) {
		description = new String();
		name = command.get(2);
		id = Integer.parseInt(command.get(3));
		for(int i = 6; i < command.size()-1; i++)
			description.concat(command.get(i) + " ");
		imagePath = command.get(4);
		nestStabilizationTime = Integer.parseInt(command.get(5));
	}
	// temporary
	public Part(String s)
	{
		name = s;
		id = 0;
		description = "";
		imagePath = "Images/"+name+".png";
		nestStabilizationTime = 50;
	}

	public Part(){}

	 public Object clone() {
         try {
             return super.clone();
         }
         catch( CloneNotSupportedException e ) {
             return null;
         }
     } 
	 
}



