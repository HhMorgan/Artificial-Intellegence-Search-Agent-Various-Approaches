package generic;

import java.util.ArrayList;

public abstract class Operators{
	private ArrayList<Operator> operators;
	
	public Operators() {
		this.operators = new ArrayList<Operator>();
	}
	
	public ArrayList<Operator> getOperators(){
		return this.operators;
	}
	
	public abstract void addOperator(Operator operator);
	
}
