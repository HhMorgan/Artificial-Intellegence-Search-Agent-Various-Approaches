package generic;

import java.util.ArrayList;


public abstract class Problem {
	
	private Node initialState;
	private ArrayList<State> statespace;
	
	public Node getInitialState() {
		return initialState;
	}
	
	public ArrayList<State> getStatespace() {
		return statespace;
	}
	
	public abstract void addState(State state);
	
	public abstract void removeState(State state);
	
	public abstract void emptyStateSpace();
	
	public abstract boolean isVisitedState(Node node);
	
	public abstract boolean goalTest(Node node);
	
	public abstract int pathCost(Cell iron, byte[] status);
	
	public abstract ArrayList<Node> expand(Node node);
	

}
