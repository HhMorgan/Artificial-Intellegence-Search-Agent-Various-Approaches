package generic;

import java.util.ArrayList;

import problemStatment.EndGame;

public abstract class Problem {
	
	private Operators Operators;
	private Node initialSate;
	private Cell[] coordinates;
	private ArrayList<State> statespace;
	
	public Operators getOperators() {
		return Operators;
	}
	
	public Node getInitialSate() {
		return initialSate;
	}
	
	public Cell[] getCoordinates() {
		return coordinates;
	}
	
	public ArrayList<State> getStatespace() {
		return statespace;
	}
	
	public abstract void addState(State state);
	
	public abstract void removeState(State state);
	
	public abstract boolean isVisitedState(Node node);
	
	public abstract boolean goalTest(Node node);
	
	public abstract int cost(Cell iron, byte[] status);
	
	public abstract ArrayList<Node> transition(Node node);
	

}
