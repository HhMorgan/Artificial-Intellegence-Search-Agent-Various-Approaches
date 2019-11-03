package search;

import java.util.ArrayList;
import java.util.PriorityQueue;
import generic.Node;

public class UCS extends QueuingFunction {
	/*
	 * Uniform Cost Search is implemented with a priority queue which orders the elements through the path cost of the nodes. 
	 * Priority queues are implemented with minimum heaps in java which allows insertion and removal of elements in O(log(n)). 
	 */
	public UCS() {
		super.queue = new PriorityQueue<Node>();
	}

	@Override
	public void add(Node node) {
		((PriorityQueue<Node>) queue).add(node);
	}
	
	@Override
	public void add(ArrayList<Node> nodes) {
		for (Node n : nodes) {
			((PriorityQueue<Node>) queue).add(n);
		}
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
