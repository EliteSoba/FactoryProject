package factory;

import java.util.*;

public class BinConfig {
	Map<Bin, Integer> binList = new HashMap<Bin, Integer>(); 
	
	public BinConfig(HashMap<Bin,Integer> m) {
		binList = m;
	}
}
