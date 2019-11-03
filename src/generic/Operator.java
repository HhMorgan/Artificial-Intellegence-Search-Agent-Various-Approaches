package generic;

//Operator is defined the set of actions defined in the problem. Each operator compromise of a name and its cost, and function that applies that operator on the state.
public abstract class Operator {

	private String name; // The name of the operator defined by the problem.
	private int cost; // The cost of the operator defined by the problem.
	
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
	public abstract Node applyOperator(Problem problem,Node node);
	
}
