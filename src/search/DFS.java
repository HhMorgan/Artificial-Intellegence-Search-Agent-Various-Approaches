package search;

import java.util.ArrayList;
import java.util.LinkedList;
import generic.Node;

public class DFS extends QueuingFunction {
	/*
	 * Depth First Search is implemented with a linked list, as linked lists allow insertion and removal at arbitrary 
	 * positions elements in constant  time through  positions given by an  iterator, and not indexing. Therefore for 
	 * the strict use used in search  problem, it  was observed that  linked lists have  better  computational  time. 
	 * Moreover, linked list has the stack push and pop functions pre-implemented.
	 */
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
