 
package Avengers;

import java.util.ArrayList;
import java.util.HashSet;
import AvengersOperators.*;
import generic.Operator;
import generic.Operators;
import generic.Cell;
import generic.Node;
import generic.Problem;
import generic.State;

//A problem is a 5 tuple which states the problem and has the initial state of the world.
public class EndGame extends Problem {
	// Directions are mapped based on D-pad clockwise
	// movement where the start is the up direction.
	static final byte[] movementX = { -1, 0, 1, 0 };
	static final byte[] movementY = { 0, 1, 0, -1 };
	private AvengersOperators operators;
	private AvengersNode initialState;
	private Cell[] coordinates;
	private HashSet<AvengersState> statespace;

	public EndGame(AvengersNode initialState, Cell[] coordinates) {
		this.initialState = initialState;
		this.coordinates = coordinates;
		this.statespace = new HashSet<AvengersState>();
		this.operators = (AvengersOperators)initalizeOperators();
	}

	protected Operators initalizeOperators(){
		Movement up = new Movement(0);
		Movement right = new Movement(1);
		Movement down = new Movement(2);
		Movement left = new Movement(3);
		Collect collect = new Collect();
		Kill kill = new Kill();
		Snap snap = new Snap();
		AvengersOperators operators = new AvengersOperators();
		operators.addOperator(up);
		operators.addOperator(right);
		operators.addOperator(down);
		operators.addOperator(left);
		operators.addOperator(collect);
		operators.addOperator(kill);
		operators.addOperator(snap);
		return operators;
	}
	
	public Cell[] getCoordinates() {
		return coordinates;
	}


	public AvengersNode getInitialState() {
		return this.initialState;
	}

	// The AddState function adds a give state to the state space of the problem.
	public void addState(State state) {
		this.statespace.add((AvengersState)state);
	}

	public void removeState(State state) {
		this.statespace.remove((AvengersState)state);
	}

	public void emptyStateSpace() {
		this.statespace = new HashSet<AvengersState>();
	}
	
	// IsVisitedState predicate checks if the state is repeated.
	public boolean isVisitedState(Node node) {
		if (!node.getOperator().equals("Snap")) {
			if (statespace.contains(node.getState())) {
				return true;
			}
		}
		return false;
	}

	// The IsCollectedStones predicate checks if all the stones are collected in the
	// world or not.
	public boolean isCollectedStones(Node node) {
		boolean stones = true;
		for (int i = 2; i < 8; i++) {
			if (((AvengersState)node.getState()).getStatus()[i] == 1) {
				stones = false;
				break;
			}
		}
		return stones;
	}

	// The GoalTest predicate checks if the state is a goal state of not.
	public boolean goalTest(Node node) {
		if (this.isCollectedStones((AvengersNode) node)) {
			if (node.getOperator().equals("Snap") && ((AvengersNode) node).getDmg() < 100) {
				return true;
			}
		}
		return false;
	}

	// The Cost function computes the damage units inflicted to Iron Man at a given
	// state.	
	public int pathCost(Cell iron, byte[] status) {
		Cell gridBorders = this.coordinates[0];
		int dmg = 0;
		for (int i = 0; i < 4; i++) {
			if (iron.getX() + movementX[i] >= 0 && iron.getX() + movementX[i] < gridBorders.getX()
					&& iron.getY() + movementY[i] > 0 && iron.getY() + movementY[i] < gridBorders.getY()) {
				for (int j = 8; j < this.coordinates.length; j++) {
					if (status[j] == 1) {
						if (((iron.getX() + movementX[i] == this.coordinates[j].getX())
								&& (iron.getY() + movementY[i] == this.coordinates[j].getY()))) {
							dmg += 1;
						}
					}
				}
			}
			if (((iron.getX() + movementX[i] == this.coordinates[1].getX())
					&& (iron.getY() + movementY[i] == this.coordinates[1].getY()))) {
				dmg += 5;
			}
		}
		return dmg;
	}
	
	// The Transition function is the function that computes the expansion of some
	// give state.
	public ArrayList<Node> expand(Node node) {
		ArrayList<Node> successorStates = new ArrayList<Node>();		
		for(Operator o: this.operators.getOperators()) {
			AvengersNode successorState = o.transition(this, (AvengersNode) node);
			if(successorState != null) {
				successorStates.add(successorState);
			}
		}
		return successorStates;
	}
	
	
	
	
	
	
}
