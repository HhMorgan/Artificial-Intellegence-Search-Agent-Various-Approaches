package search;

import java.util.ArrayList;
import java.util.LinkedList;

import generic.Node;
import generic.Problem;

public class IDS extends QueuingFunction {
	
	/*
	 * Iterative Deepening Search is implemented with a linked list, the implementation of the Iterative Deepening Search strategy is like the implementation of the 
	 * Depth First Search; however Iterative Deepening  Search has a  cutoff depth at each iteration. The difference  is in the add and isEmpty functions, where the 
	 * add function checks for the cutoff value at the iteration, isEmpty function starts the search over from the root and increase the value of the cutoff.  
	 */

	Problem problem;
	int depth = 0;

	public IDS(Problem problem) {
		this.problem = problem;
		super.queue = new LinkedList<Node>();
	}

	@Override
	public void add(Node node) {
		if (depth >= node.getDepth()) {
			((LinkedList<Node>) super.queue).push(node);
		}
	}

	@Override
	public void add(ArrayList<Node> nodes) {
		for (int i = nodes.size() - 1; i >= 0; i--) {
			if (depth >= (nodes.get(i).getDepth())) {
				((LinkedList<Node>) super.queue).push(nodes.get(i));
			}
		}
	}

	@Override
	public Node remove() {
		return ((LinkedList<Node>) super.queue).pop();
	}

	@Override
	public boolean isEmpty() {
		if (((LinkedList<Node>) queue).isEmpty()) {
			depth++;
			if (depth <= Integer.MAX_VALUE) {
				super.queue = new LinkedList<Node>();
				this.problem.emptyStateSpace();
				add(this.problem.getNode());
			}
		}
		return ((LinkedList<Node>) queue).isEmpty();
	}

}
