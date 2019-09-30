package generic;

import problemStatment.AvengersState;

//A node is the state of the world at a given time step.
public class Node{
	private State state;
	private int depth;
	private Node parent;
	private char operator;
	private int cost;
	
	public Node(byte[] gridStatus, char operator, int cost,int depth, Node parent) {
		this.state = new AvengersState(gridStatus);
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
	
	public char getOperator() {
		return this.operator;
	}
	
	public int getCost() {
		return this.cost;
	}
	
	public String toString() {
		return String.valueOf(this.operator) + "/" + ((AvengersState)this.getState()).getIron() + "?" +this.parent;
	}
	
}
