package AvengersOperators;

import Avengers.AvengersState;
import generic.Node;
import generic.Operator;
import generic.Problem;

public class Snap extends Operator {

	public Snap() {
		super("snap", 0);
	}

	@Override
	public Node applyOperator(Problem problem, Node node) {
		int successorGridStatusIndex = 0; // The index for the new array that will be formulated for the successor node.
		byte[] SuccessorGridStatus = new byte[((AvengersState) node.getState()).getGrid().length];
		for (int k = 0; k < ((AvengersState) node.getState()).getGrid().length; k++) {
			// Thanos is eliminated by this action, so it status is replaced by the health to allow for comparison for the states that have the snap operator applied.
			if (k == 2) { 
				if (node.getPathCost() < 100) {
					SuccessorGridStatus[successorGridStatusIndex] = Byte.valueOf((byte) node.getPathCost());
				} else {
					SuccessorGridStatus[successorGridStatusIndex] = 100;
				}
				successorGridStatusIndex++;
				continue;
			}
			if (k != 2 && k != 3) { // Include the previous state of the objects that were not affected by the action. 
				SuccessorGridStatus[successorGridStatusIndex] = ((AvengersState) node.getState()).getGrid()[k];
				successorGridStatusIndex++;
			}
		}
		int inflicted = 0; // The inflicted damage to Iron Man in the state.
		int costSuccessor = node.getPathCost() + inflicted;
		Node successorState = new Node(new AvengersState(SuccessorGridStatus), this.getName(),
				costSuccessor + this.getCost(), node.getDepth() + 1, node);
		return successorState;

	}

}
