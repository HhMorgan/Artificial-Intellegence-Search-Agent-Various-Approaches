package generic;

import java.util.ArrayList;
import java.util.HashSet;

import search.QueuingFunction;

// A search problem class is a generic definition for any problem that can be defined.
public abstract class Problem {
	
	protected Node initialNode;
	protected HashSet<State> statespace;
	protected Operator[] operators;
	protected int expandedNodes; // The number of the expanded nodes in the search procedure
	
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
	
	/*
	 * Initially the initial node of the problem is enqueued to the queue. Afterwards, the procedure iterates on the queue till it is 
	 * empty, expands each node, adds the expanded state to the state space, and enqueues the generated nodes in the queue.
	 */
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
