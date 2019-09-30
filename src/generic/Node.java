package generic;

import generic.Node;
import problemStatment.AvengersState;

//A node is the state of the world at a given time step.
public class Node{
	private AvengersState state;
	private int depth;
	private Node parent;
	private char operator;
	private int cost;
	public Node(Cell iron, boolean [] status, char operator, int cost,int depth, Node parent) {
		this.state = new AvengersState(iron, status);
		this.operator = operator;
		this.cost = cost;
		this.depth = depth;
		this.parent = parent;	
	}
	
	public AvengersState getState() {
		return this.state;
	}
	
	public Cell getIron() {
		return this.state.getIron();
	}
	
	public boolean [] getStatus() {
		return this.state.getStatus();
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
		return String.valueOf(this.operator) + "/" + this.getIron() + "?" +this.parent;
	}
	
}
