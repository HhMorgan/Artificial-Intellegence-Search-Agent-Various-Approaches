package search;

import java.util.LinkedList;

import generic.Node;
import generic.Problem;

public class IDS extends GenericSearch {

	Problem problem;
	int depth = 0;

	public IDS(Problem problem) {
		this.problem = problem;
		super.queue = new LinkedList<Node>();
	}

	@Override
	public void add(Node node) {
		if (node.getDepth() >= this.depth) {
			((LinkedList<Node>) super.queue).add(node);
		}
			}

	@Override
	public Node remove() {
		Node popped = ((LinkedList<Node>) super.queue).removeFirst();
		if (popped.getDepth() == 0) {
			this.depth ++;
			this.problem.emptyStateSpace();
		}
		((LinkedList<Node>) super.queue).add(popped);
		
		return popped;
	}

	@Override
	public boolean isEmpty() {
		return super.queue.isEmpty();
	}

}
