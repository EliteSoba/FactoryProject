package factory;

import java.io.Serializable;
import java.util.*;

public class KitConfig implements Serializable{
	
	   public String kitName;

	   // List of parts needed and how many of each
//	   public List<MyPart> partList = new ArrayList<MyPart>();
	   
	   public List<Part> listOfParts = new ArrayList<Part>();
	   public int quantity;

//	   public class MyPart {
//	      Part part;
//	      int quantity;
//	      
//	      public MyPart(Part part, int quantity){
//	    	  this.part = part;
//	    	  this.quantity = quantity;
//	      }
//	   }
	   /**
	    * The KitConfig class is basically a recipe for a kit.
	    * @param kitName this the name you will give to the kit configuration
	    */
	   public KitConfig(String kitName){
		   this.kitName = kitName;
	   }
	   
	   public KitConfig(){}
//	   /**
//	    * This method is used to add a part that is required for the kit configuration you are making
//	    * @param part The part you want to add to the kit configuration
//	    * @param quantity The number of parts you need for the kit
//	    */
//	   public void addPart(Part part, int quantity){
//		   MyPart myPart = new MyPart(part, quantity);
//		   partList.add(myPart);
//	   }
}

