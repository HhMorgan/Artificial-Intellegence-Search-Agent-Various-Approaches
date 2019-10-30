package AvengersOperators;

import Avengers.AvengersState;
import generic.Operator;
import generic.Node;
import generic.Problem;

public class Collect extends Operator {

	int collected; // The index of the collected stone. 

	public Collect(int collected) {
		super("collect", 3);
		this.collected = collected + 2;

	}

	@Override
	public Node applyOperator(Problem problem, Node node) {
		int successorGridStatusIndex = 0; // The index for the new array that will be formulated for the successor node.
		byte[] SuccessorGridStatus = new byte[((AvengersState) node.getState()).getGrid().length - 1];
		for (int k = 0; k < ((AvengersState) node.getState()).getGrid().length; k++) {
			if (k == this.collected) { // Check for the collected stone to not include in the successor state.
				continue;
			}
			// Include the previous state of the objects that were not affected by the action.
			SuccessorGridStatus[successorGridStatusIndex] = ((AvengersState) node.getState()).getGrid()[k];
			successorGridStatusIndex++;
		}
		AvengersState successorState = new AvengersState(SuccessorGridStatus);
		int inflected = this.getCost() + problem.pathCost(successorState); // The inflicted damage to Iron Man in the state.
		int costSuccessor = node.getPathCost() + inflected; // The new path cost including the inflicted cost in the state.
		Node successorNode = new Node(successorState, this.getName(), costSuccessor,
				node.getDepth() + 1, node);
		return successorNode;
	}
}
