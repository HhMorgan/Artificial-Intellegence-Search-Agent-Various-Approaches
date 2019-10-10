package search;

import java.util.ArrayList;
import java.util.LinkedList;

import generic.Node;
import generic.Problem;

public class IDS extends QueuingFunction {

	Problem problem;
	int depth = 0;

	public IDS(Problem problem) {
		this.problem = problem;
		super.queue = new LinkedList<Node>();
	}
	
	@Override
	public void add(Node node) {
		((LinkedList<Node>) super.queue).push(node);
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
				// System.out.println(depth);
				super.queue = new LinkedList<Node>();
				this.problem.emptyStateSpace();
				add(this.problem.getInitialState());
			}
		}
		return ((LinkedList<Node>) queue).isEmpty();
	}

}
