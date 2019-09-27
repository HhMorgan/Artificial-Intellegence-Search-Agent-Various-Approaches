package artificialIntellegence;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public abstract class GenericSearch {

	public static Node search(Problem problem, Comparator<Node> queuingFunction) {
		PriorityQueue<Node> nodes = new PriorityQueue<Node>(queuingFunction);
		nodes.add(problem.initialState);
		while (!nodes.isEmpty()) {
			Node node = nodes.poll();
			if (Problem.goalTest(node)) {
				return node;
			}
			ArrayList<Node> successorStates = Problem.transition(node);
			while (!successorStates.isEmpty()) {
				nodes.add(successorStates.remove(0));
			}
		}
		return null;
	}

}
