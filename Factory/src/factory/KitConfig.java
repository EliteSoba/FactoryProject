package factory;

import java.util.*;

public class KitConfig {
	
	   String kitName;

	   // List of parts needed and how many of each
	   List<MyPart> partList = new ArrayList<MyPart>();

	   class MyPart {
	      Part part;
	      int quantity;
	      
	      public MyPart(Part part, int quantity){
	    	  this.part = part;
	    	  this.quantity = quantity;
	      }
	   }
}
