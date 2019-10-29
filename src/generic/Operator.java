package generic;

public abstract class Operator {

	private String name; // The name of the operator defined by the problem.
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
	
	// The function that generates the successor state node depending on the operator applied.
	public abstract Node transition(Problem problem,Node node);
	
}
