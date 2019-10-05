package search;

import java.util.ArrayList;
import java.util.Arrays;

import generic.Node;
import Avengers.AvengersNode;
import Avengers.AvengersState;
import Avengers.EndGame;

public class Search {

	public static Node search(EndGame problem, GenericSearch nodes) {
		// down/3,3?right/2,3?
		//int limit = 50;
		//int counter = 0;
		nodes.add(problem.getInitialState());
		problem.addState(problem.getInitialState().getState());
		while (!nodes.isEmpty()) {
//			if(limit <= 0) {
//				break;
//			}

			// System.out.println(((LinkedList<Node>)((IDS)nodes).queue).getFirst());
//			System.out.print("QUEUE : ");
//			for(Node n : nodes.queue) {
//				System.out.print(n + " |A| ");
//			}
//			System.out.println();

			Node node = nodes.remove();
		//	counter++;
			//System.out.println(node);
//
			//if (node.getOperator().equals("Snap")) {
//				for (byte b : ((AvengersState) node.getState()).getGridStatus()) {
//					System.out.print(b + ",");
//				}
//				System.out.println();
			//}

			if (problem.goalTest(node)) {
			//	System.out.println(counter);
				return node;
			}
			ArrayList<Node> successorStates = problem.expand(node);
			while (!successorStates.isEmpty()) {
				if (problem.isVisitedState(successorStates.get(0))) {
					// System.out.println("REMOVED : " + node);
					successorStates.remove(0);
					continue;
				} else {
					problem.addState(successorStates.get(0).getState());
					nodes.add(successorStates.remove(0));
					//limit--;
				}
			}
		}
		return null;
	}
}
