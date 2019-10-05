 
package search;

import java.util.ArrayList;
import generic.Node;
import generic.Problem;

public class Search{
	
	public static Node search(Problem problem, GenericSearch nodes) {
		nodes.add(problem.getInitialState());
		problem.addState(problem.getInitialState().getState());
		while (!nodes.isEmpty()) {			
			Node node = nodes.remove();
			if (problem.goalTest(node)) {
				return node;
			}
			ArrayList<Node> successorStates = problem.expand(node);
			while(!successorStates.isEmpty()) {
				if (problem.isVisitedState(successorStates.get(0))) {
					successorStates.remove(0);
					continue;
				} else {
					problem.addState(successorStates.get(0).getState());
					nodes.add(successorStates.remove(0));
				}
			}
		}
		return null;
	}
}
