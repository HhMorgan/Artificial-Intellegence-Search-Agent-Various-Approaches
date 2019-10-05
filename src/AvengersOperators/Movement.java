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
		Cell iron = ((AvengersState) node.getState()).getIron();
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
		int costSuccessor = node.getCost() + inflected + this.getCost();
		AvengersNode successorState = new AvengersNode(SuccessorGridStatus, dmgSuccessor, movement[direction],
				costSuccessor, node.getDepth() + 1, node);
		return successorState;
	}
	
	public String toString() {
		return this.getName() + ";" + this.direction;
	}

}
