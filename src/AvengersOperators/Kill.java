package AvengersOperators;

import java.util.Arrays;
import Avengers.AvengersState;
import generic.Node;
import generic.Problem;

public class Kill extends GeneralLookUpInspection {

	private int[] warriorLocations;
	private int warriorLength;

	public Kill(int[] warriorLocations, int warriorLength) {
		super("kill", 2);
		this.warriorLocations = warriorLocations;
		this.warriorLength = warriorLength;
	}

	@Override
	public Node transition(Problem problem, Node node) {
		int currentIndex = this.warriorLocations.length - this.warriorLength;
		int successorGridStatusIndex = 0;
		Arrays.sort(this.warriorLocations);
		byte[] SuccessorGridStatus = new byte[((AvengersState) node.getState()).getGrid().length - this.warriorLength];
		for (int k = 0; k < ((AvengersState) node.getState()).getGrid().length; k++) {
			if (k > 2 && currentIndex < this.warriorLocations.length
					&& this.warriorLocations[currentIndex] == ((AvengersState) node.getState()).getGrid()[k]) {
				currentIndex++;
			} else {
				SuccessorGridStatus[successorGridStatusIndex] = ((AvengersState) node.getState()).getGrid()[k];
				successorGridStatusIndex++;
			}

		}
		AvengersState successorState = new AvengersState(SuccessorGridStatus);
		int inflicted = problem.pathCost(successorState) + this.getCost();
		int costSuccessor = node.getPathCost() + inflicted;
		Node successorNode = new Node(successorState, this.getName(), costSuccessor, node.getDepth() + 1, node);
		return successorNode;
	}

}
