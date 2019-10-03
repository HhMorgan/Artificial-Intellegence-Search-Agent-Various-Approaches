package Avengers;

import generic.Node;

//A node is the state of the world at a given time step.
public class AvengersNode extends Node{
	
	private int dmg;
	
	public AvengersNode(byte[] gridStatus, int dmg, String operator, int cost,int depth, AvengersNode parent) {
		super(operator, cost, depth, parent);
		super.state = new AvengersState(gridStatus);
		this.dmg = dmg;
	}
	
	public int getDmg() {
		return this.dmg;
	}
	
	public String toString() {
		return String.valueOf(this.getOperator()) + "/" + ((AvengersState)this.getState()).getIron() + "?" + this.getParent();
	}

	
}
