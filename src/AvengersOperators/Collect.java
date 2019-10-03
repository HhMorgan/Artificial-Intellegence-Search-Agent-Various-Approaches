package AvengersOperators;

import Avengers.AvengersNode;
import Avengers.AvengersState;
import generic.Cell;
import generic.Operator;
import generic.Problem;
import Avengers.EndGame;

public class Collect extends Operator {

	public Collect() {
		super("Collect", 0);
	}

	@Override
	public AvengersNode transition(Problem problem, AvengersNode node) {
		int collectedStone = -1;
		boolean collectStone = false;
		Cell iron = ((AvengersState) node.getState()).getIron();
		for (int i = 2; i < 8; i++) {
			if (((AvengersState) node.getState()).getStatus()[i] == 1) {
				if (((iron.getX() == ((EndGame) problem).getCoordinates()[i].getX())
						&& (iron.getY() == ((EndGame) problem).getCoordinates()[i].getY()))) {
					collectStone = true;
					collectedStone = i + 2;
					break;
				}
			}
		}
		if (collectStone) {
			byte[] SuccessorGridStatus = new byte[((AvengersState) node.getState()).getGridStatus().length];
			for (int k = 0; k < ((AvengersState) node.getState()).getGridStatus().length; k++) {
				if (k == collectedStone) {
					SuccessorGridStatus[k] = 0;
					continue;
				}
				SuccessorGridStatus[k] = ((AvengersState) node.getState()).getGridStatus()[k];
			}
			AvengersNode successorState = new AvengersNode(SuccessorGridStatus, node.getDmg(), this.getName(),
					node.getCost() + this.getCost(), node.getDepth() + 1, node);
			return successorState;
		}
		return null;
	}
}
