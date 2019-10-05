
package Avengers;

import java.util.ArrayList;
import java.util.HashSet;
import AvengersOperators.*;
import generic.Operator;
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
	private AvengersNode initialState;
	private Cell[] coordinates;
	private HashSet<AvengersState> statespace;

	public EndGame(AvengersNode initialState, Cell[] coordinates) {
		this.initialState = initialState;
		this.coordinates = coordinates;
		this.statespace = new HashSet<AvengersState>();
	}

	public Cell[] getCoordinates() {
		return coordinates;
	}

	public AvengersNode getInitialState() {
		return this.initialState;
	}

	// The AddState function adds a give state to the state space of the problem.
	public void addState(State state) {
		this.statespace.add((AvengersState) state);
	}

	public void removeState(State state) {
		this.statespace.remove((AvengersState) state);
	}

	public void emptyStateSpace() {
		this.statespace = new HashSet<AvengersState>();
	}

	// IsVisitedState predicate checks if the state is repeated.
	public boolean isVisitedState(Node node) {
//		if (node.getOperator().equals("Snap")) {
//			System.out.println((statespace.contains(node.getState())));
//		}
		//if (!node.getOperator().equals("Snap")) {
			if (statespace.contains(node.getState())) {
				return true;
			}
		//}
		return false;
	}

	// The IsCollectedStones predicate checks if all the stones are collected in the
	// world or not.
	public boolean isCollectedStones(Node node) {
		boolean stones = true;
		byte [] status = ((AvengersState) node.getState()).getStatus();
		for (int i = 2; i < status.length; i++) {
			if (status[i] < 8) {
				stones = false;
				break;
			}
		}
		return stones;
	}

	// The GoalTest predicate checks if the state is a goal state of not.
	public boolean goalTest(Node node) {
		//System.out.println(node);
		if (this.isCollectedStones((AvengersNode) node)) {
			// System.out.println(((AvengersNode) node).getDmg());
			
			if (node.getOperator().equals("Snap")) {
				//System.out.println("SNAP");
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
				for (int j = 2; j < status.length; j++) {
					if (((iron.getX() + movementX[i] == this.coordinates[status[j]].getX())
							&& (iron.getY() + movementY[i] == this.coordinates[status[j]].getY()))) {
						dmg += 1;
					}
				}
			}
			if (((iron.getX() + movementX[i] == this.coordinates[1].getX())
					&& (iron.getY() + movementY[i] == this.coordinates[1].getY()))) {
				dmg += 5;
			}
		}
		if (((iron.getX() == this.coordinates[1].getX()) && (iron.getY() == this.coordinates[1].getY()))) {
			dmg += 5;
		}

		return dmg;
	}

	public ArrayList<Operator> availableActions(Node node) {
		boolean isCollect = false;
		Cell gridBorders = getCoordinates()[0];
		Cell iron = ((AvengersState) node.getState()).getIron();
		byte[] status = ((AvengersState) node.getState()).getStatus();
		ArrayList<Operator> operators = new ArrayList<Operator>();
		if (((iron.getX() == getCoordinates()[1].getX()) && (iron.getY() == getCoordinates()[1].getY()))) {
			if (this.isCollectedStones(node)) {
				operators.add(new Snap());
			}
		}

		else {
			int statusIndex = 2;
			while (statusIndex < status.length && status[statusIndex] < 8) {
				byte index = status[statusIndex];
				Cell inspected = getCoordinates()[index];
				// Collect a stone in the cell.
				if (((iron.getX() == inspected.getX()) && (iron.getY() == inspected.getY()))) {
					isCollect = true;
					operators.add(new Collect(statusIndex));
					break;
				}
				statusIndex++;
			}
			int prevStatusIndex = statusIndex;
			// Allowed moves in a state.
			if (!isCollect) {
				// checks the four adjacent cells.
				
				int warriorsLength = 0;
				boolean isWarriors = false;
				int[] warriorLocations = new int[4];
				for (int i = 0; i < 4; i++) {
					boolean isWarriorAtDirection = false;
					if (iron.getX() + movementX[i] >= 0 && iron.getX() + movementX[i] < gridBorders.getX()
							&& iron.getY() + movementY[i] >= 0 && iron.getY() + movementY[i] < gridBorders.getY()) {
						for (; statusIndex < status.length; statusIndex++) {
							byte index = status[statusIndex];
							//System.out.print("INDEX : " + index + ",");
							Cell inspected = getCoordinates()[index];
							//System.out.print(inspected + "; " + (iron.getX() + movementX[i]) + "," + (iron.getY() + movementY[i]) + "| ");
							if (index >= 8) {
								if (((iron.getX() + movementX[i] == inspected.getX())
										&& (iron.getY() + movementY[i] == inspected.getY()))) {
									warriorLocations[warriorsLength] = index;
									warriorsLength++;
									isWarriors = true;
									isWarriorAtDirection = true;
								}
							}
						}
						statusIndex = prevStatusIndex;
						if (!isWarriorAtDirection) {
							// checks if Thanos is adjacent and all of the stones are collected to
							// transition of the goal state.
							if (((iron.getX() + movementX[i] == this.coordinates[1].getX())
									&& (iron.getY() + movementY[i] == this.coordinates[1].getY()))) {
								if (this.isCollectedStones(node)) {
									operators.add(new Movement(i));
								}
							} else {
								// Transition to the successor state where Iron man moved to an empty cell.
								operators.add(new Movement(i));
							}
						}

					}
				}
				
				//System.out.println();
				if (isWarriors) {
					operators.add(new Kill(warriorLocations, warriorsLength));
				}
				
				
			}
		}
		return operators;
	}

	// The Transition function is the function that computes the expansion of some
	// give state.
	public ArrayList<Node> expand(Node node) {
		ArrayList<Node> successorStates = new ArrayList<Node>();
		ArrayList<Operator> operators = availableActions(node);
		// System.out.println(((AvengersState) ((AvengersNode)
		// node).getState()).getIron() + ".");
		for (Operator o : operators) {
			// System.out.print(o + ", ");
			AvengersNode successorState = o.transition(this, (AvengersNode) node);
			if (successorState != null)
				successorStates.add(successorState);
		}
		// System.out.println();
		return successorStates;
	}

}
