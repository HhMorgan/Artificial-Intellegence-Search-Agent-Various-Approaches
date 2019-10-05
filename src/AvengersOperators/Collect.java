package AvengersOperators;

import Avengers.AvengersNode;
import Avengers.AvengersState;
import generic.Operator;
import generic.Problem;

public class Collect extends Operator {

	int collected;

	public Collect(int collected) {
		super("Collect", 0);
		this.collected = collected + 2;

	}

	@Override
	public AvengersNode transition(Problem problem, AvengersNode node) {
		int successorGridStatusIndex = 0;
		byte[] SuccessorGridStatus = new byte[((AvengersState) node.getState()).getGridStatus().length - 1];
		for (int k = 0; k < ((AvengersState) node.getState()).getGridStatus().length; k++) {
			if (k == this.collected) {
				continue;
			}
			SuccessorGridStatus[successorGridStatusIndex] = ((AvengersState) node.getState()).getGridStatus()[k];
			successorGridStatusIndex++;
		}
		AvengersNode successorState = new AvengersNode(SuccessorGridStatus, node.getDmg(), this.getName(),
				node.getCost() + this.getCost(), node.getDepth() + 1, node);
		return successorState;
	}
}
