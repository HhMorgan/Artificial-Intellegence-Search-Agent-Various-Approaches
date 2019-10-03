package search;

import java.util.PriorityQueue;
import generic.Node;

public class UCS extends GenericSearch{
	
	public UCS() {
		super.queue = new PriorityQueue<Node>();
	}
	
	@Override
	public void add(Node node) {
		((PriorityQueue<Node>) queue).add(node);
	}

	@Override
	public Node remove() {
		return ((PriorityQueue<Node>) queue).poll();
	}

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

}
