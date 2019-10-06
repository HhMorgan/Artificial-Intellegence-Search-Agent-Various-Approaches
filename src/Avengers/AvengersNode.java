package Avengers;

import generic.Node;

//A node is the state of the world at a given time step.
public class AvengersNode extends Node{
	
	public AvengersNode(byte[] gridStatus, String operator, int cost,int depth, AvengersNode parent) {
		super(operator, cost, depth, parent);
		super.state = new AvengersState(gridStatus);
	}
	
	public String toString() {
		return String.valueOf(this.getOperator()) + "/" + ((AvengersState)this.getState()).getIron() + "?" + this.getParent();
	}

	
}
