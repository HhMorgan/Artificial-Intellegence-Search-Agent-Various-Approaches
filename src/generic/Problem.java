package generic;

import java.util.ArrayList;

import problemStatment.EndGame;

public abstract class Problem {
	
	private Operators Operators;
	private Node initialSate;
	private Cell[] coordinates;
	private ArrayList<Node> statespace;
	
	public Operators getOperators() {
		return Operators;
	}
	
	public Node getInitialSate() {
		return initialSate;
	}
	
	public Cell[] getCoordinates() {
		return coordinates;
	}
	
	public ArrayList<Node> getStatespace() {
		return statespace;
	}
	
	public abstract void addState(Node node);
	
	public abstract void removeState(Node node);
	
	public abstract boolean isVisitedState(Node node);
	
	public abstract boolean goalTest(Node node);
	
	public abstract int cost(Cell iron, boolean[] status);
	
	public abstract ArrayList<Node> transition(Node node);
	

}
