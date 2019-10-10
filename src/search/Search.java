
package search;

import java.util.ArrayList;
import generic.Node;
import generic.Problem;

public class Search {

	public static Node search(Problem problem, QueuingFunction nodes) {
		nodes.add(problem.getInitialState());
		problem.addState(problem.getInitialState().getState());
		while (!nodes.isEmpty()) {
			Node node = nodes.remove();
			if (problem.goalTest(node)) {
				return node;
			}
			ArrayList<Node> successorStates = problem.expand(node);
			nodes.add(successorStates);
		}
		return null;
	}
}
