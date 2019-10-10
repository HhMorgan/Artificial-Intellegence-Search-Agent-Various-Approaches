package AvengersOperators;

import Avengers.AvengersNode;
import Avengers.AvengersState;
import generic.Node;
import generic.Operator;
import generic.Problem;

public class Snap extends Operator {

	public Snap() {
		super("Snap", 0);
	}

	@Override
	public AvengersNode transition(Problem problem, Node node) {
		int successorGridStatusIndex = 0;
		byte[] SuccessorGridStatus = new byte[((AvengersState) node.getState()).getGridStatus().length - 1];
		for (int k = 0; k < ((AvengersState) node.getState()).getGridStatus().length; k++) {
			if (k == 2) {
				if (node.getPathCost() < 100) {
					SuccessorGridStatus[successorGridStatusIndex] = Byte.valueOf((byte) node.getPathCost());
				} else {
					SuccessorGridStatus[successorGridStatusIndex] = 100;
				}
				successorGridStatusIndex++;
				continue;
			}
			if (k != 2 && k != 3) {
				SuccessorGridStatus[successorGridStatusIndex] = ((AvengersState) node.getState()).getGridStatus()[k];
				successorGridStatusIndex++;
			}
		}
		int inflicted = 0;
		int costSuccessor = node.getPathCost() + inflicted;
		AvengersNode successorState = new AvengersNode(SuccessorGridStatus, this.getName(),
				costSuccessor + this.getCost(), node.getDepth() + 1, node);
		return successorState;

	}

}
