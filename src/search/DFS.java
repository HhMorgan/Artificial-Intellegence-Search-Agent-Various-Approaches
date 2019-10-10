package search;

import java.util.ArrayList;
import java.util.LinkedList;
import generic.Node;

public class DFS extends GenericSearch {

	public DFS() {
		super.queue = new LinkedList<Node>();
	}

	@Override
	public void add(Node node) {
		((LinkedList<Node>) super.queue).push(node);
	}
	
	@Override
	public void add(ArrayList<Node> nodes) {
		for (int i = nodes.size() - 1; i >= 0; i--) {
			((LinkedList<Node>) super.queue).push(nodes.get(i));
		}
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
