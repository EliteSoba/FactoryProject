package factory;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List; 

import factory.Part;


public class Kit {
   public enum KitState { EMPTY, INCOMPLETE, COMPLETE, PASSED_INSPECTION, FAILED_INSPECTION }
   public KitState state;
   public List<Part> parts = Collections.synchronizedList(new ArrayList<Part>());
   
	//added this to force picture inspection to fail.
	public boolean forceFail = false;

   public Kit(){
	   this.state = KitState.EMPTY;
	   this.parts = new ArrayList<Part>();
   }
}
