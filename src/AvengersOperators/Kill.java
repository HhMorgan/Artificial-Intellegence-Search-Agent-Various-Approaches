package AvengersOperators;

import Avengers.AvengersNode;
import Avengers.AvengersState;
import Avengers.EndGame;
import generic.Cell;
import generic.Problem;

public class Kill extends GeneralLookUpInspection {

	public Kill() {
		super("Kill", 2);
	}

	@Override
	public AvengersNode transition(Problem problem, AvengersNode node) {
		boolean isWarrior = false;
		byte[] SuccessorGridStatus = new byte[((AvengersState) node.getState()).getGridStatus().length];
		for (int k = 0; k < ((AvengersState) node.getState()).getGridStatus().length; k++) {
			SuccessorGridStatus[k] = ((AvengersState) node.getState()).getGridStatus()[k];
		}

		Cell iron = ((AvengersState) node.getState()).getIron();
		for (int i = 0; i < 4; i++) {
			if (isOutOfBounds(problem, node, i)) {
				for (int j = 8; j < ((EndGame) problem).getCoordinates().length; j++) {
					if (((AvengersState) node.getState()).getStatus()[j] == 1) {
						if (((iron.getX() + movementX[i] == ((EndGame) problem).getCoordinates()[j].getX())
								&& (iron.getY() + movementY[i] == ((EndGame) problem).getCoordinates()[j].getY()))) {
							SuccessorGridStatus[j + 2] = 0;
							isWarrior = true;
						}
					}
				}
			}
		}
		if (isWarrior) {
			int inflicted = problem.pathCost(((AvengersState) node.getState()).getIron(),
					((AvengersState) node.getState()).getStatus()) + this.getCost();
			int dmgSuccessor = node.getDmg() + inflicted;
			int costSuccessor = node.getCost();
			AvengersNode successorState = new AvengersNode(SuccessorGridStatus, dmgSuccessor, this.getName(),
					costSuccessor, node.getDepth() + 1, node);
			return successorState;
		}
		return null;
	}

}
