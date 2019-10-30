package AvengersOperators;

import generic.Operator;

// An abstract class that include the required movement for the operator functions.
public abstract class GeneralLookUpInspection extends Operator {
	// Directions are mapped based on the following left, right, up, down
	static final byte[] movementX = { 0, 0, -1, 1 };
	static final byte[] movementY = { -1, 1, 0, 0};
	public GeneralLookUpInspection(String name, int cost) {
		super(name, cost);
	}
	
}
