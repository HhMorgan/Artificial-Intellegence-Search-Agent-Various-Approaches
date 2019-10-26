
package search;

import java.util.ArrayList;

import Avengers.AvengersState;
import generic.Node;
import generic.Problem;

public class Search {

	public static Node search(Problem problem, QueuingFunction nodes) {
		// int limit = 30;
		// int counter = 0;
		nodes.add(problem.getInitialState());
		problem.addState(problem.getInitialState().getState());
		while (!nodes.isEmpty()) {
//			if(limit <= 0) {
//				break;
//			}
//			System.out.print("QUEUE : ");
//			for (Node n : nodes.queue) {
//				System.out.print(n + " |A| ");
//			}
			// System.out.println();

			//System.out.print("QUEUE : ");
//			for (Node n : nodes.queue) {
////				if (n.getOperator()!= null && n.getOperator().equals("snap")) {
////					System.out.println(n.getPathCost());
//					System.out.println(n + " |A| ");
//			//	}
//			}
			//System.out.println();

			Node node = nodes.remove();
			
//			counter++;
			// System.out.println(node);
			// if (node.getOperator().equals("Snap")) {
//			for (byte b : ((AvengersState) node.getState()).getGrid()) {
//				System.out.print(b + ",");
//			}
//			System.out.println();
			// }
			if (problem.goalTest(node)) {
//				System.out.println(counter);
				return node;
			}
			ArrayList<Node> successorStates = problem.expand(node);
//			for (Node n : successorStates) {
//				if (n.getOperator().equals("snap")) {
//					System.out.println(n.getPathCost());
//				}
//			}
			nodes.add(successorStates);
			// limit--;
		}
		return null;
	}
}
