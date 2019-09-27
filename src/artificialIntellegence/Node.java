package artificialIntellegence;

//A node is the state of the world at a given time step.
public class Node {
	Cell iron;
	boolean [] status;
	int depth;
	Node node;
	char operator;
	int cost;
	public Node(Cell iron, boolean [] status, char operator, int cost,int depth, Node node) {
		this.iron = iron;
		this.status = status;
		this.operator = operator;
		this.cost = cost;
		this.depth = depth;
		this.node = node;	
	}
	
	public String toString() {
		return String.valueOf(this.operator);
	}
	
}
