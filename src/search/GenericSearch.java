package search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import generic.Node;
import problemStatment.EndGame;

public abstract class GenericSearch {

	public static Node search(EndGame problem, Comparator<Node> queuingFunction) {
		PriorityQueue<Node> nodes = new PriorityQueue<Node>(queuingFunction);
		nodes.add(problem.getInitialState());
		problem.addState(problem.getInitialState().getState());
		while (!nodes.isEmpty()) {
			Node node = nodes.poll();
			if (problem.goalTest(node)) {
				return node;
			}
			ArrayList<Node> successorStates = problem.transition(node);
			while (!successorStates.isEmpty()) {
				if (problem.isVisitedState(successorStates.get(0))) {
					successorStates.remove(0);
				} else {
					problem.addState(successorStates.get(0).getState());
					nodes.add(successorStates.remove(0));
				}
			}
		}
		return null;
	}

}
