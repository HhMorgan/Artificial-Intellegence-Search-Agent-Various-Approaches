package AvengersOperators;

import Avengers.AvengersNode;
import Avengers.AvengersState;
import Avengers.EndGame;
import generic.Cell;
import generic.Operator;
import generic.Problem;

public abstract class GeneralLookUpInspection extends Operator {
	// Directions are mapped based on D-pad clockwise
	// movement where the start is the up direction.
	static final byte[] movementX = { -1, 0, 1, 0 };
	static final byte[] movementY = { 0, 1, 0, -1 };

	public GeneralLookUpInspection(String name, int cost) {
		super(name, cost);
	}
	
	public boolean isOutOfBounds(Problem problem, AvengersNode node, int direction) {
		Cell gridBorders = ((EndGame) problem).getCoordinates()[0];
		Cell iron = ((AvengersState) node.getState()).getIron();
		if (iron.getX() + movementX[direction] >= 0 && iron.getX() + movementX[direction] < gridBorders.getX()
				&& iron.getY() + movementY[direction] >= 0 && iron.getY() + movementY[direction] < gridBorders.getY()) {
			return true;
		}
		return false;
	}
	
}
