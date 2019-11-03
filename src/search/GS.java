package search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.function.Function;
import generic.Node;

public class GS extends QueuingFunction {
	
	/*
	 * Greedy Search is implemented in a similar manner as the Uniform Cost Search. The priority queue comparator function is 
	 * overridden to only consider  the value  of the heuristics. For the Greedy Search to be generic to  any search problem, 
	 * the function interface is used, which allows  to pass a function as  an argument to the constructor.  Furthermore, the 
	 * input and output types for the variables must be specified in the definition of the variable. 
	 */

	public GS(Function<Node, Integer> heuristicFunc) {

		super.queue = new PriorityQueue<Node>(new Comparator<Node>() {

			@Override
			public int compare(Node node1, Node node2) {
				int heuristicCostA = heuristicFunc.apply(node1);
				int heuristicCostB = heuristicFunc.apply(node2);

				return Integer.compare(heuristicCostA, heuristicCostB);
			}

		});

	}

	@Override
	public void add(Node s) {
		((PriorityQueue<Node>) queue).add(s);
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
