package AvengersOperators;

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
		Cell iron = ((AvengersState) node.getState()).getIron();
		if (((iron.getX() == ((EndGame) problem).getCoordinates()[1].getX())
				&& (iron.getY() == ((EndGame) problem).getCoordinates()[1].getY()))) {
			if (((EndGame) problem).isCollectedStones(node)) {
				byte[] SuccessorGridStatus = new byte[((AvengersState) node.getState()).getGridStatus().length];
				for (int k = 0; k < ((AvengersState) node.getState()).getStatus().length; k++) {
					if (k != 3) {
						SuccessorGridStatus[k] = ((AvengersState) node.getState()).getGridStatus()[k];
					} else {
						SuccessorGridStatus[k] = 0;

					}
				}
				AvengersNode successorState = new AvengersNode(((AvengersState) node.getState()).getGridStatus(),
						node.getDmg(), this.getName(), node.getCost() + this.getCost(), node.getDepth() + 1, node);
				return successorState;
			}
		}
		return null;
	}

}
