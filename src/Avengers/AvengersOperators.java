package Avengers;

import generic.Operator;
import generic.Operators;

public class AvengersOperators extends Operators{
	
	public AvengersOperators () {
		super();
	}
	
	public void addOperator(Operator operator) {
		this.getOperators().add(operator);
	}
	
}
