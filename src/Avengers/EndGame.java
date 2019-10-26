package Avengers;

import java.util.ArrayList;
import java.util.Arrays;
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
	static final byte[] movementX = { 0, 0, -1, 1 };
	static final byte[] movementY = { -1, 1, 0, 0};
	//String[] movement = { "up", "right", "down", "left" };
	private Cell[] coordinates;

	public EndGame(State initialState, Cell[] coordinates) {
		this.coordinates = coordinates;
		this.node = new Node(initialState, null, pathCost(initialState), 0, null);
		this.statespace = new HashSet<State>();
		this.statespace.add(initialState);
	}


	public Cell[] getCoordinates() {
		return coordinates;
	}

	// The AddState function adds a give state to the state space of the problem.
	public void addState(State state) {
		this.statespace.add((AvengersState) state);
	}

	public void removeState(State state) {
		this.statespace.remove((AvengersState) state);
	}

	public void emptyStateSpace() {
		this.statespace = new HashSet<State>();
		this.statespace.add(node.getState());
	}

	// IsVisitedState predicate checks if the state is repeated.
	public boolean isVisitedState(Node node) {
		if (statespace.contains(node.getState())) {
			return true;
		}
		return false;
	}

	// The IsCollectedStones predicate checks if all the stones are collected in the
	// world or not.
	public boolean isCollectedStones(Node node) {
		boolean stones = true;
		byte[] status = ((AvengersState) node.getState()).getStatus();
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
		if (node.getOperator() != null && node.getOperator().equals("snap") && node.getPathCost() < 100) {
			return true;
		}
		return false;
	}

	// The Cost function computes the damage units inflicted to Iron Man at a given
	// state.
	public int pathCost(State state) {
		Cell iron = ((AvengersState) state).getIron();
		byte[] status = ((AvengersState) state).getStatus();
		int warriorInitialIndex = binarySearch(Arrays.copyOfRange(status,1,status.length), 0, status.length - 2, 8) + 1;
		Cell gridBorders = this.coordinates[0];
		int dmg = 0;
		for (int i = 0; i < 4; i++) {
			if (iron.getX() + movementX[i] >= 0 && iron.getX() + movementX[i] < gridBorders.getX()
					&& iron.getY() + movementY[i] > 0 && iron.getY() + movementY[i] < gridBorders.getY()) {
				if (warriorInitialIndex > 0) {
					for (int j = warriorInitialIndex; j < status.length; j++) {
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
		}
		if (((iron.getX() == this.coordinates[1].getX()) && (iron.getY() == this.coordinates[1].getY()))) {
			dmg += 5;
		}
		return dmg;
	}

	public ArrayList<Operator> availableActions(Node node) {
		Cell gridBorders = getCoordinates()[0];
		Cell iron = ((AvengersState) node.getState()).getIron();
		byte[] status = ((AvengersState) node.getState()).getStatus();
		ArrayList<Operator> operators = new ArrayList<Operator>();
		if (((iron.getX() == getCoordinates()[1].getX()) && (iron.getY() == getCoordinates()[1].getY()))) {
			if (this.isCollectedStones(node) && (!node.getOperator().equals("Snap"))) {
				operators.add(new Snap());
			}
		}
		int statusIndex = 2;
		while (statusIndex < status.length && status[statusIndex] < 8) {
			byte index = status[statusIndex];
			Cell inspected = getCoordinates()[index];
			// Collect a stone in the cell.
			if (((iron.getX() == inspected.getX()) && (iron.getY() == inspected.getY()))) {
				operators.add(new Collect(statusIndex));
				break;
			}
			statusIndex++;
		}
		int prevStatusIndex = statusIndex;
		// Allowed moves in a state.
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
					Cell inspected = getCoordinates()[index];
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
		if (isWarriors) {
			operators.add(new Kill(warriorLocations, warriorsLength));
		}
		return operators;
	}

	// The Transition function is the function that computes the expansion of some
	// give state.
	public ArrayList<Node> expand(Node node) {
		ArrayList<Node> successorStates = new ArrayList<Node>();
		ArrayList<Operator> operators = availableActions(node);
		for (Operator o : operators) {
			Node successorNode = o.transition(this, node);
			if (successorNode != null && successorNode.getPathCost() < 100 && !isVisitedState(successorNode))
				successorStates.add(successorNode);
			addState(successorNode.getState());
		}
		return successorStates;
	}

	private int binarySearch(byte arr[], int l, int r, int x) {
		if (r >= l) {
			int mid = l + (r - l) / 2;

			// If the element is present at the
			// middle itself
			if (mid >= 1) {
				if (arr[mid] >= x && arr[mid - 1] < x)
					return mid;
			} else {
				if (arr[mid] >= x && mid == 0)
					return mid;
			}

			// If element is smaller than mid, then
			// it can only be present in left subarray
			if (arr[mid] > x)
				return binarySearch(arr, l, mid - 1, x);

			// Else the element can only be present
			// in right subarray
			return binarySearch(arr, mid + 1, r, x);
		}

		// We reach here when element is not present
		// in array
		return -1;
	}

	public int oracleCost(Node node) {
		AvengersState state = (AvengersState) node.getState();
		Cell iron = state.getIron();
		byte[] status = state.getStatus();
		int predictedCost = 0;
		int warriorInitialIndex = binarySearch(Arrays.copyOfRange(status,1,status.length), 0, status.length - 2, 8) + 1;
		int warriorsInspectIndex;
		ArrayList<Integer> warriorsAdjacentStones = new ArrayList<Integer>();
		for (int i = 2; i < status.length; i++) {
			int index = status[i];
			if (index < 8) {
				predictedCost += 3;
				Cell inspectedStone = getCoordinates()[index];
				if (warriorInitialIndex >= 0) {
					warriorsInspectIndex = warriorInitialIndex;
					for (; warriorsInspectIndex < status.length; warriorsInspectIndex++) {
						Cell inspectedWarrior = getCoordinates()[warriorsInspectIndex];
						for (int j = 0; j < 3; j++) {
							if (inspectedStone.getX() + movementX[j] == inspectedWarrior.getX()
									&& inspectedStone.getY() + movementY[j] == inspectedWarrior.getY()) {
								predictedCost += 1;
								warriorsAdjacentStones.add(warriorsInspectIndex);
								break;
							}
							if(inspectedStone.getX() + movementX[j] == getCoordinates()[1].getX()
									&& inspectedStone.getY() + movementY[j] == getCoordinates()[1].getY()) {
								predictedCost += 5;
							}
						}
					}
				}

			} else {
				break;
			}
		}
		if (node.getOperator() == null || !node.getOperator().equals("snap")) {
			if (!((iron.getX() == getCoordinates()[1].getX()) && (iron.getY() == getCoordinates()[1].getY()))) {
				boolean isThanosAdjacent = false;
				for (int j = 0; j < 3; j++) {
					if (((iron.getX() + movementX[j] == getCoordinates()[1].getX())
							&& (iron.getY() + movementY[j] == getCoordinates()[1].getY()))) {
						isThanosAdjacent = true;
					}
				}
				if (!(isCollectedStones(node) && isThanosAdjacent)) {
					predictedCost += 10;
				}
			} else {
				if (isCollectedStones(node)) {
					predictedCost += 5;
				}
			}
		}
		return predictedCost;
	}

	public int oracleCostRelaxed(Node node) {
		AvengersState state = (AvengersState) node.getState();
		byte[] status = state.getStatus();
		int predictedCost = 0;
		int warriorInitialIndex = binarySearch(Arrays.copyOfRange(status,1,status.length), 0, status.length - 2, 8) + 1;
		int warriorsInspectIndex;
		for (int i = 2; i < status.length; i++) {
			int index = status[i];
			if (index < 8) {
				predictedCost += 3;
				Cell inspectedStone = getCoordinates()[index];
				if (warriorInitialIndex >= 0) {
					warriorsInspectIndex = warriorInitialIndex;
					for (; warriorsInspectIndex < status.length; warriorsInspectIndex++) {
						Cell inspectedWarrior = getCoordinates()[warriorsInspectIndex];
						for (int j = 0; j < 3; j++) {
							if (inspectedStone.getX() + movementX[j] == inspectedWarrior.getX()
									&& inspectedStone.getY() + movementY[j] == inspectedWarrior.getY()) {
								predictedCost += 1;
								break;
							}
						}
					}
				}

			} else {
				break;
			}
		}
		if (node.getOperator() == null || !node.getOperator().equals("snap")) {
			predictedCost += 5;
		}
		return predictedCost;
	}

}