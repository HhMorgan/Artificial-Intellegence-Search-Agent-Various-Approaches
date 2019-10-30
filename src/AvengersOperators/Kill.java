package AvengersOperators;

import java.util.Arrays;
import Avengers.AvengersState;
import generic.Node;
import generic.Problem;

public class Kill extends GeneralLookUpInspection {

	private int[] warriorLocations; // The indices of the adjacent warriors.
	private int warriorLength; // The number of adjacent warriors.

	public Kill(int[] warriorLocations, int warriorLength) {
		super("kill", 2);
		this.warriorLocations = warriorLocations;
		this.warriorLength = warriorLength;
	}

	@Override
	public Node applyOperator(Problem problem, Node node) {
		// The locations of the warriors are not sorted for the checks that needs to be
		// done. So the array is sorted,and the check is done from the first non zero
		// element, as warriors starts from 10.
		Arrays.sort(this.warriorLocations);
		int currentIndex = this.warriorLocations.length - this.warriorLength; // The starting non zero element's index in the warrior array.
		int successorGridStatusIndex = 0; // The index for the new array that will be formulated for the successor node.
		byte[] SuccessorGridStatus = new byte[((AvengersState) node.getState()).getGrid().length - this.warriorLength];
		for (int k = 0; k < ((AvengersState) node.getState()).getGrid().length; k++) {
			// Check for the adjacent warriors to not include in the successor state.
			if (k >= 2 && currentIndex < this.warriorLocations.length
					&& this.warriorLocations[currentIndex] == ((AvengersState) node.getState()).getGrid()[k]) {
				currentIndex++;
			} else {// Include the previous state of the objects that were not affected by the
					// action.
				SuccessorGridStatus[successorGridStatusIndex] = ((AvengersState) node.getState()).getGrid()[k];
				successorGridStatusIndex++;
			}

		}
		AvengersState successorState = new AvengersState(SuccessorGridStatus);
		int inflicted = problem.pathCost(successorState) + (this.getCost() * this.warriorLength); // The inflicted damage to Iron Man in the state.
		int costSuccessor = node.getPathCost() + inflicted; // The new path cost including the inflicted cost in the state.
		Node successorNode = new Node(successorState, this.getName(), costSuccessor, node.getDepth() + 1, node);
		return successorNode;
	}

}
