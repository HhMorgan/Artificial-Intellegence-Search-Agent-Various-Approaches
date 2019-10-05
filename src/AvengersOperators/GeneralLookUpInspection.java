package AvengersOperators;

import generic.Operator;

public abstract class GeneralLookUpInspection extends Operator {
	// Directions are mapped based on D-pad clockwise
	// movement where the start is the up direction.
	static final byte[] movementX = { -1, 0, 1, 0 };
	static final byte[] movementY = { 0, 1, 0, -1 };

	public GeneralLookUpInspection(String name, int cost) {
		super(name, cost);
	}
	
}
