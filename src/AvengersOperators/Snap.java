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
	public Node transition(Problem problem, Node node) {
		int successorGridStatusIndex = 0;
		byte[] SuccessorGridStatus = new byte[((AvengersState) node.getState()).getGrid().length];
		for (int k = 0; k < ((AvengersState) node.getState()).getGrid().length; k++) {
			if (k == 2) {
				if (node.getPathCost() <= 100) {
					SuccessorGridStatus[successorGridStatusIndex] = Byte.valueOf((byte) node.getPathCost());
				} else {
					SuccessorGridStatus[successorGridStatusIndex] = 101;
				}
				successorGridStatusIndex++;
				continue;
			}
			if (k != 2 && k != 3) {
				SuccessorGridStatus[successorGridStatusIndex] = ((AvengersState) node.getState()).getGrid()[k];
				successorGridStatusIndex++;
			}
		}
		int inflicted = 0;
		int costSuccessor = node.getPathCost() + inflicted;
		Node successorState = new Node(new AvengersState(SuccessorGridStatus), this.getName(),
				costSuccessor + this.getCost(), node.getDepth() + 1, node);
		return successorState;

	}

}
