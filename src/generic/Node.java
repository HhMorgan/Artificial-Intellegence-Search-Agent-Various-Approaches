package generic;

import generic.State;

//A node is the state of the world at a given time step.
public class Node implements Comparable<Node> {

	protected State state;
	private int depth;
	private Node parent;
	private String operator;
	private int cost;

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
