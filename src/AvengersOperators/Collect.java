package AvengersOperators;

import Avengers.AvengersNode;
import Avengers.AvengersState;
import generic.Operator;
import generic.Node;
import generic.Problem;

public class Collect extends Operator {

	int collected;

	public Collect(int collected) {
		super("Collect", 3);
		this.collected = collected + 2;

	}

	@Override
	public Node transition(Problem problem, Node node) {
		int successorGridStatusIndex = 0;
		byte[] SuccessorGridStatus = new byte[((AvengersState) node.getState()).getGridStatus().length - 1];
		for (int k = 0; k < ((AvengersState) node.getState()).getGridStatus().length; k++) {
			if (k == this.collected) {
				continue;
			}
			SuccessorGridStatus[successorGridStatusIndex] = ((AvengersState) node.getState()).getGridStatus()[k];
			successorGridStatusIndex++;
		}
		int inflected = this.getCost();
		int costSuccessor = node.getPathCost() + inflected;
		AvengersNode successorState = new AvengersNode(SuccessorGridStatus, this.getName(),
				costSuccessor, node.getDepth() + 1, node);
		return successorState;
	}
}
