package AvengersOperators;

import java.util.Arrays;

import Avengers.AvengersNode;
import Avengers.AvengersState;
import Avengers.EndGame;
import generic.Cell;
import generic.Operator;
import generic.Problem;

public class Snap extends Operator {

	public Snap() {
		super("Snap", 0);
	}

	@Override
	public AvengersNode transition(Problem problem, AvengersNode node) {
		int successorGridStatusIndex = 0;
		byte[] SuccessorGridStatus = new byte[((AvengersState) node.getState()).getGridStatus().length - 2];
		//System.out.println(Arrays.toString(((AvengersState) node.getState()).getGridStatus()));
		for (int k = 0; k < ((AvengersState) node.getState()).getGridStatus().length; k++) {
			if (k != 2 && k != 3) {
				SuccessorGridStatus[successorGridStatusIndex] = ((AvengersState) node.getState()).getGridStatus()[k];
				successorGridStatusIndex++;
			} 
		}
		//System.out.println(Arrays.toString(SuccessorGridStatus));
		AvengersNode successorState = new AvengersNode(SuccessorGridStatus, node.getDmg(),
				this.getName(), node.getCost() + this.getCost(), node.getDepth() + 1, node);
		return successorState;

	}

}
