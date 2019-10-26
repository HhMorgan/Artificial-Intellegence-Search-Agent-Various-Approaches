package search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.function.Function;
import generic.Node;


public class AS extends QueuingFunction {

	public AS(Function<Node, Integer> heuristicFunc) {
		super.queue = new PriorityQueue<Node>(new Comparator<Node>() {

			@Override
			public int compare(Node node1, Node node2) {
				int heuristicCostA = heuristicFunc.apply(node1) + node1.getPathCost();
				int heuristicCostB = heuristicFunc.apply(node2) + node2.getPathCost();

				return Integer.compare(heuristicCostA, heuristicCostB);
			}

		});
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
