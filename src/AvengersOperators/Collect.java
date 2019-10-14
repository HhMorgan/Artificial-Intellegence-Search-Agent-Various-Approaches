package AvengersOperators;

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
		byte[] SuccessorGridStatus = new byte[((AvengersState) node.getState()).getGrid().length - 1];
		for (int k = 0; k < ((AvengersState) node.getState()).getGrid().length; k++) {
			if (k == this.collected) {
				continue;
			}
			SuccessorGridStatus[successorGridStatusIndex] = ((AvengersState) node.getState()).getGrid()[k];
			successorGridStatusIndex++;
		}
		AvengersState successorState = new AvengersState(SuccessorGridStatus);
		int inflected = this.getCost() + problem.pathCost(successorState);
		int costSuccessor = node.getPathCost() + inflected;
		Node successorNode = new Node(successorState, this.getName(), costSuccessor,
				node.getDepth() + 1, node);
		return successorNode;
	}
}
