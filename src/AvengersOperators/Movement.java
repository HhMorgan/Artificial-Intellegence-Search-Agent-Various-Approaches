package AvengersOperators;

import Avengers.AvengersNode;
import Avengers.AvengersState;
import Avengers.EndGame;
import generic.Cell;
import generic.Problem;

public class Movement extends GeneralLookUpInspection {

	int direction;
	String[] movement;

	public Movement(int direction) {
		super("Move", 1);
		this.direction = direction;
		String[] movement = { "up", "right", "down", "left" };
		this.movement = movement;
	}

	@Override
	public AvengersNode transition(Problem problem, AvengersNode node) {
		boolean isThanos = false;
		boolean isWarrior = false;
		Cell iron = ((AvengersState) node.getState()).getIron();
		if (isOutOfBounds(problem, node, this.direction)) {
			for (int j = 2; j < 8; j++) {
				if (((AvengersState) node.getState()).getStatus()[j] == 1) {
					if (((iron.getX() == ((EndGame) problem).getCoordinates()[j].getX())
							&& (iron.getY() == ((EndGame) problem).getCoordinates()[j].getY()))) {
						return null;
					}
				}
			}

			// Thanos
			if (((iron.getX() + movementX[direction] == ((EndGame) problem).getCoordinates()[1].getX())
					&& (iron.getY() + movementY[direction] == ((EndGame) problem).getCoordinates()[1].getY()))) {
				isThanos = true;
				if (((EndGame) problem).isCollectedStones(node)) {
					Cell SuccessorIron = new Cell(Byte.valueOf((byte) (iron.getX() + movementX[direction])),
							Byte.valueOf((byte) (iron.getY() + movementY[direction])));
					byte[] SuccessorGridStatus = new byte[((AvengersState) node.getState()).getGridStatus().length];
					SuccessorGridStatus[0] = SuccessorIron.getX();
					SuccessorGridStatus[1] = SuccessorIron.getY();
					for (int j = 2; j < ((AvengersState) node.getState()).getGridStatus().length; j++) {
						SuccessorGridStatus[j] = ((AvengersState) node.getState()).getGridStatus()[j];
					}
					AvengersNode successorState = new AvengersNode(SuccessorGridStatus, node.getDmg(),
							movement[direction], node.getCost(), node.getDepth() + 1, node);
					return successorState;

				}
			}
			// Warrior
			for (int j = 8; j < ((EndGame) problem).getCoordinates().length; j++) {
				if (((AvengersState) node.getState()).getStatus()[j] == 1) {
					if (((iron.getX() + movementX[direction] == ((EndGame) problem).getCoordinates()[j].getX())
							&& (iron.getY() + movementY[direction] == ((EndGame) problem).getCoordinates()[j]
									.getY()))) {
						isWarrior = true;
						break;
					}
				}
			}
			if (!isWarrior && !isThanos) {
				Cell SuccessorIron = new Cell(Byte.valueOf((byte) (iron.getX() + movementX[direction])),
						Byte.valueOf((byte) (iron.getY() + movementY[direction])));
				byte[] SuccessorGridStatus = new byte[((AvengersState) node.getState()).getGridStatus().length];
				SuccessorGridStatus[0] = SuccessorIron.getX();
				SuccessorGridStatus[1] = SuccessorIron.getY();
				for (int j = 2; j < ((AvengersState) node.getState()).getGridStatus().length; j++) {
					SuccessorGridStatus[j] = ((AvengersState) node.getState()).getGridStatus()[j];
				}
				int inflected = problem.pathCost(SuccessorIron, ((AvengersState) node.getState()).getStatus());
				int dmgSuccessor = node.getDmg() + inflected;
				int costSuccessor = dmgSuccessor + inflected + this.getCost();
				AvengersNode successorState = new AvengersNode(SuccessorGridStatus, dmgSuccessor, movement[direction],
						costSuccessor, node.getDepth() + 1, node);
				return successorState;
			}
		}
		return null;
	}

}
