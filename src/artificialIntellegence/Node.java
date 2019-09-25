package artificialIntellegence;

public class Node {
	byte ix;
	byte iy;
	Cell [] stones;
	Cell [] warriors; 
	int depth;
	Node node;
	//To represent the 4 allowed actions that the state can be reached through.
	boolean bit1; boolean bit2;
	public Node(byte ix, byte iy, Cell [] stones, Cell [] warriors, int depth, Node node) {
		this.ix = ix;
		this.iy = iy;
		this.stones = stones;
		this.warriors = warriors;
		this.depth = depth;
		this.node = node;
	}
	
	public void setDirection(boolean bit1, boolean bit2) {
		this.bit1 = bit1;
		this.bit2 = bit2;
	}

}
