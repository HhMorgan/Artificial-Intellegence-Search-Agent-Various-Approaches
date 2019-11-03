package generic;

import generic.State;

/*
 * The generic class Node is defined as the structure {state, depth, parent, operator, cost}, all the instance
 * variables are generic  to be able to solve  any  problem  that can be defined. Node  implements comparable, 
 * where the nodes are compared by the value of the path cost, cost. 
 */
public class Node implements Comparable<Node> {

	protected State state; // The state of the world.
	private int depth; // The depth of the given node in the search tree.
	private Node parent; // The parent node of the given node.
	private String operator; // The operator used to reach the given node.
	private int cost; // The cost of the path from the root to the given node.

	public Node(State state, String operator, int cost, int depth, Node parent) {
		this.state = state;
		this.operator = operator;
		this.cost = cost;
		this.depth = depth;
		this.parent = parent;
	}

	public State getState() {
		return this.state;
	}

	public int getDepth() {
		return this.depth;
	}

	public Node getParent() {
		return this.parent;
	}

	public String getOperator() {
		return this.operator;
	}

	public int getPathCost() {
		return this.cost;
	}

	@Override
	public int compareTo(Node arg0) {
		return Integer.compare(this.getPathCost(), arg0.getPathCost());
	}

	public String toString() {
		if (this.getOperator() != null) {
		      return this.getParent() + "," + String.valueOf(this.getOperator());
		    }
		return "";
	}
}
