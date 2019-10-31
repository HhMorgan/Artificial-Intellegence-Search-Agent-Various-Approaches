package AvengersOperators;

import Avengers.AvengersState;
import Avengers.EndGame;
import generic.Cell;
import generic.Node;
import generic.Operator;
import generic.Problem;

public class Movement extends Operator {

	int direction; // The direction the agent will move to.
	final String[] movement = { "left", "right", "up", "down" }; // The order the movement is stored in.
	// Directions are mapped based on the following left, right, up, down
	static final byte[] movementX = { 0, 0, -1, 1 };
	static final byte[] movementY = { -1, 1, 0, 0};
	public Movement(int direction) {
		super("Move", 0);
		this.direction = direction;
	}

	@Override
	public Node applyOperator(Problem problem, Node node) {
		Cell iron = ((AvengersState) node.getState()).getIron();
		Cell SuccessorIron = new Cell(Byte.valueOf((byte) (iron.getX() + movementX[direction])),
				Byte.valueOf((byte) (iron.getY() + movementY[direction])));
		byte[] SuccessorGridStatus = new byte[((AvengersState) node.getState()).getGrid().length];
		SuccessorGridStatus[0] = SuccessorIron.getX(); // Applying the move action in the x-axis.
		SuccessorGridStatus[1] = SuccessorIron.getY(); // Applying the move action in the y-axis.
		// Include the previous state of the objects that were not affected by the action. 
		for (int j = 2; j < ((AvengersState) node.getState()).getGrid().length; j++) {
			SuccessorGridStatus[j] = ((AvengersState) node.getState()).getGrid()[j];
		}
		AvengersState successorState = new AvengersState(SuccessorGridStatus);
		int inflicted = problem.pathCost(successorState) + this.getCost(); // The inflicted damage to Iron Man in the state.
		int costSuccessor = node.getPathCost() + inflicted;
		Node successorNode = new Node(successorState, movement[direction], costSuccessor, node.getDepth() + 1, node);
		return successorNode;
	}

	public String toString() {
		return this.getName() + ";" + this.direction;
	}

}
