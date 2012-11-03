package factory;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List; 

import factory.Part;

enum KitState { INCOMPLETE, COMPLETE, PASSED_INSPECTION, FAILED_INSPECTION }

public class Kit {
   public KitState state;
   public List<Part> parts = Collections.synchronizedList(new ArrayList<Part>());

}
