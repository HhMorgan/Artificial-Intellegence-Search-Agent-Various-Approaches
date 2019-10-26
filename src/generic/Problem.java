package generic;

import java.util.ArrayList;
import java.util.HashSet;

import search.QueuingFunction;


public abstract class Problem {
	
	protected Node initialNode;
	protected HashSet<State> statespace;
	protected Operator[] operators;
	protected int expandedNodes;
	
	public Node getNode() {
		return this.initialNode;
	}
	
	public HashSet<State> getStatespace() {
		return this.statespace;
	}
	
	public Operator[] getOperators() {
		return this.operators;
	}
	
	public int getExpandedNodes() {
		return this.expandedNodes;
	}
	
	public abstract void addState(State state);
	
	public abstract void removeState(State state);
	
	public abstract void emptyStateSpace();
	
	public abstract boolean isVisitedState(Node node);
	
	public abstract boolean goalTest(Node node);
	
	public abstract int pathCost(State state);
	
	public abstract ArrayList<Node> expand(Node node);
	
	public Node search(QueuingFunction nodes) {
		nodes.add(getNode());
		addState(getNode().getState());
		while (!nodes.isEmpty()) {
			Node node = nodes.remove();
			expandedNodes++;
			if (goalTest(node)) {
				return node;
			}
			ArrayList<Node> successorStates = expand(node);
			nodes.add(successorStates);
		}
		return null;
	}

}
