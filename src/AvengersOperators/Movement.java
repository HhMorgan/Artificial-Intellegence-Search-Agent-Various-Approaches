package AvengersOperators;

import Avengers.AvengersState;
import Avengers.EndGame;
import generic.Cell;
import generic.Node;
import generic.Problem;

public class Movement extends GeneralLookUpInspection {

	int direction;
	String[] movement;
	
	public Movement(int direction) {
		super("Move", 0);
		this.direction = direction;
		String[] movement = { "up", "right", "down", "left" };
		this.movement = movement;
	}

	@Override
	public Node transition(Problem problem, Node node) {
		Cell iron = ((AvengersState) node.getState()).getIron();
		Cell SuccessorIron = new Cell(Byte.valueOf((byte) (iron.getX() + movementX[direction])),
				Byte.valueOf((byte) (iron.getY() + movementY[direction])));
		byte[] SuccessorGridStatus = new byte[((AvengersState) node.getState()).getGrid().length];
		SuccessorGridStatus[0] = SuccessorIron.getX();
		SuccessorGridStatus[1] = SuccessorIron.getY();
		for (int j = 2; j < ((AvengersState) node.getState()).getGrid().length; j++) {
			SuccessorGridStatus[j] = ((AvengersState) node.getState()).getGrid()[j];
		}
		int inflicted = problem.pathCost(node) + this.getCost();
		int costSuccessor = node.getPathCost() + inflicted;
		Node successorState = new Node(new AvengersState(SuccessorGridStatus), movement[direction],
				costSuccessor, node.getDepth() + 1, node);
		return successorState;
	}
	
	public String toString() {
		return this.getName() + ";" + this.direction;
	}

}
