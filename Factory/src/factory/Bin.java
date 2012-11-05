package factory;

public class Bin {
	enum BinState { EMPTY, FULL }
	   Part part;
	   BinState state = BinState.FULL; // initially
	   
	   public Bin(Part p) {
		   part = p;
	   }
	   
}
