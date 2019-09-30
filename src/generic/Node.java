package generic;

import java.util.Objects;
import generic.Node;
import problemStatment.AvengersState;

//A node is the state of the world at a given time step.
public class Node implements Comparable<Node>{
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

	@Override
	public int compareTo(Node otherNode) {
		int comparison = this.state.compareTo(otherNode.state);
		//System.out.println(comparison + "First : " + this + ", Second : "+ otherNode);
		if(comparison == 0) {
			if ((this.getOperator() == 'k' && this.getOperator() == otherNode.getOperator())
					|| (this.getOperator() == 'c' && this.getOperator() == otherNode.getOperator())) {
				return 0;
			}
			else {
				if(this.getOperator() != 'k' && this.getOperator() != 'c') {
					return 0;
				}
			}
		}
		else {
			return comparison;
		}
		return 1;
	}
	
	@Override
	public boolean equals(Object o) { 
		Node otherNode = (Node) o;
		int comparison = this.state.compareTo(otherNode.state);
		if(comparison == 0) {
			if ((this.getOperator() == 'k' && this.getOperator() == otherNode.getOperator())
					|| (this.getOperator() == 'c' && this.getOperator() == otherNode.getOperator())) {
				return true;
			}
			else {
				if(this.getOperator() != 'k' && this.getOperator() != 'c') {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
    public int hashCode() {
		//System.out.println("===========----------============-----------");
        return Objects.hash(state.getStatus(), state.getIron().getX(), state.getIron().getY());
    }

	
}
