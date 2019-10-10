package generic;

import Avengers.AvengersNode;

public abstract class Operator {

	private String name;
	private int cost;
	
	public Operator(String name, int cost) {
		this.name = name;
		this.cost = cost;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getCost() {
		return this.cost;
	}
	
	public String toString() {
		return this.getName();
	}
	
	public abstract Node transition(Problem problem,Node node);
	
}
