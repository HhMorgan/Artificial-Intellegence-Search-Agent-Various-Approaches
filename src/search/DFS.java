package search;

import java.util.LinkedList;
import generic.Node;

public class DFS extends GenericSearch{
	
	public DFS() {
		super.queue = new LinkedList<Node>();
	}

	@Override
	public void add(Node node) {
		((LinkedList<Node>) super.queue).push(node);
	}

	@Override
	public Node remove() {
		return ((LinkedList<Node>) super.queue).pop();
	}

	@Override
	public boolean isEmpty() {
		return super.queue.isEmpty();
	}
}
