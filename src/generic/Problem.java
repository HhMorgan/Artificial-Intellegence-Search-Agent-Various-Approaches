package generic;

import java.util.ArrayList;
import java.util.HashSet;


public abstract class Problem {
	
	protected Node initialState;
	protected HashSet<State> statespace;
	protected Operator[] operators;
	
	public Node getInitialState() {
		return this.initialState;
	}
	
	public HashSet<State> getStatespace() {
		return this.statespace;
	}
	
	public Operator[] getOperators() {
		return this.operators;
	}
	
	public abstract void addState(State state);
	
	public abstract void removeState(State state);
	
	public abstract void emptyStateSpace();
	
	public abstract boolean isVisitedState(Node node);
	
	public abstract boolean goalTest(Node node);
	
	public abstract int pathCost(State state);
	
	public abstract ArrayList<Node> expand(Node node);
	

}
