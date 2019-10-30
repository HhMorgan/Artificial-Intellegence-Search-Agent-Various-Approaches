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

public class EndGame extends Problem {
	// Directions are mapped based on the following left, right, up, down
	static final byte[] movementX = { 0, 0, -1, 1 };
	static final byte[] movementY = { -1, 1, 0, 0 };
	// Stores the coordinates of Thanos, the Warriors, and the Stones.
	private Cell[] coordinates;

	public EndGame(State initialState, Cell[] coordinates) {
		this.coordinates = coordinates;
		this.initialNode = new Node(initialState, null, pathCost(initialState), 0, null);
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

	// removes a state from the state space.
	public void removeState(State state) {
		this.statespace.remove((AvengersState) state);
	}

	public void emptyStateSpace() {
		this.statespace = new HashSet<State>();
		this.statespace.add(initialNode.getState());
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
		Cell iron = ((AvengersState) state).getIron();// The position of Iron Man.
		byte[] status = ((AvengersState) state).getStatus();// The state of the warriors and stones.
		int warriorInitialIndex = modifiedBinarySearch(Arrays.copyOfRange(status, 1, status.length), 0,
				status.length - 2, 8) + 1;// The index of the first warrior in the status array.
		Cell gridBorders = this.coordinates[0];
		int dmg = 0;// The damage sustained in the state of the node.
		for (int i = 0; i <= 3; i++) {
			if (iron.getX() + movementX[i] >= 0 && iron.getX() + movementX[i] < gridBorders.getX()
					&& iron.getY() + movementY[i] >= 0 && iron.getY() + movementY[i] < gridBorders.getY()) {
				// Checks for adjacent warriors.
				if (warriorInitialIndex > 0) {
					for (int j = warriorInitialIndex; j < status.length; j++) {
						if (((iron.getX() + movementX[i] == this.coordinates[status[j]].getX())
								&& (iron.getY() + movementY[i] == this.coordinates[status[j]].getY()))) {
							dmg += 1;
						}
					}
				}
				// Checks if Thanos is adjacent.
				if (((iron.getX() + movementX[i] == this.coordinates[1].getX())
						&& (iron.getY() + movementY[i] == this.coordinates[1].getY()))) {
					dmg += 5;
				}
			}
		}
		// Checks if Iron Man is in Thanos' Cell
		if (((iron.getX() == this.coordinates[1].getX()) && (iron.getY() == this.coordinates[1].getY()))) {
			dmg += 5;
		}
		return dmg;
	}

	// Computes the available actions that can be executed at the state of the node.
	public ArrayList<Operator> availableActions(Node node) {
		Cell gridBorders = getCoordinates()[0];
		Cell iron = ((AvengersState) node.getState()).getIron(); // The position of Iron Man.
		byte[] status = ((AvengersState) node.getState()).getStatus(); // The state of the warriors and stones.
		ArrayList<Operator> operators = new ArrayList<Operator>(); // The operators that can be applied to the state of
																	// the node.
		// Checks if Snap's preconditions are satisfied.
		if (((iron.getX() == getCoordinates()[1].getX()) && (iron.getY() == getCoordinates()[1].getY()))) {
			if (this.isCollectedStones(node) && (!node.getOperator().equals("Snap"))) {
				operators.add(new Snap());
			}
		}
		// Checks if collecting a stone preconditions are satisfied.
		int statusIndex = 2; // Stone indices in the status array begin at index 2.
		while (statusIndex < status.length && status[statusIndex] < 8) {
			byte index = status[statusIndex];
			Cell inspected = getCoordinates()[index];// Inspected stone for the check.
			// Checks if Iron Man is in the inspected stone's cell.
			if (((iron.getX() == inspected.getX()) && (iron.getY() == inspected.getY()))) {
				operators.add(new Collect(statusIndex));
				break;
			}
			statusIndex++;
		}
		int prevStatusIndex = statusIndex; // The warrior indices.
		int warriorsLength = 0;
		boolean isWarriors = false; // Flag to indicate if there is a warriors in an adjacent cell.
		int[] warriorLocations = new int[4]; // The locations of the warriors that are adjacent.
		for (int i = 0; i < 4; i++) { // Checks the adjacent cells for warriors.
			boolean isWarriorAtDirection = false; // Flag to indicate a warrior in an adjacent cell with direction.
			if (iron.getX() + movementX[i] >= 0 && iron.getX() + movementX[i] < gridBorders.getX()
					&& iron.getY() + movementY[i] >= 0 && iron.getY() + movementY[i] < gridBorders.getY()) {
				for (; statusIndex < status.length; statusIndex++) { // Checks on the adjacent cells for warriors.
					byte index = status[statusIndex]; // The warrior index from the status array.
					Cell inspected = getCoordinates()[index]; // The warrior to be inspected.
					if (index >= 8) { // Warrior index must be 8 or greater.
						if (((iron.getX() + movementX[i] == inspected.getX())
								&& (iron.getY() + movementY[i] == inspected.getY()))) {
							warriorLocations[warriorsLength] = index;
							warriorsLength++;
							isWarriors = true;
							isWarriorAtDirection = true;
						}
					}
				}
				statusIndex = prevStatusIndex;// resets the index for the check for the warrior adjacency.
				if (!isWarriorAtDirection) { // There are no warriors in direction being checked.
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
		if (isWarriors) { // Checks if there are warriors in adjacent cells to kill.
			operators.add(new Kill(warriorLocations, warriorsLength));
		}
		return operators;
	}

	// The Transition function is the function that computes the expansion of some
	// give state.
	public ArrayList<Node> expand(Node node) {
		ArrayList<Node> successorNodes = new ArrayList<Node>();
		ArrayList<Operator> operators = availableActions(node);
		for (Operator o : operators) {
			Node successorNode = o.applyOperator(this, node);
			if (successorNode != null && successorNode.getPathCost() < 100 && !isVisitedState(successorNode)) {
				successorNodes.add(successorNode);
				addState(successorNode.getState());
			}
		}
		return successorNodes;
	}

	// Retrieves the index of the element or the element that is greater than it.
	private int modifiedBinarySearch(byte array[], int left, int right, int element) {
		if (right >= left) {
			int middle = left + (right - left) / 2;
			// Check for the value of the element if it was the element or a value greater
			// than it.
			if (middle >= 1) {
				if (array[middle] >= element && array[middle - 1] < element)
					return middle;
			} else {
				if (array[middle] >= element && middle == 0)
					return middle;
			}
			if (array[middle] > element)
				return modifiedBinarySearch(array, left, middle - 1, element);
			return modifiedBinarySearch(array, middle + 1, right, element);
		}
		return -1;
	}

	/*
	 * The heuristics function calculates the value of the remaining stones in the
	 * world, the damage inflicted by Thanos to perform the snapping action, and the
	 * damage of the warriors and Thanos for the stones that adjacent to them
	 */
	public int oracleCost(Node node) {
		AvengersState state = (AvengersState) node.getState();
		Cell iron = state.getIron();
		byte[] status = state.getStatus();
		int predictedCost = 0;
		boolean stoneAdjacentThanos = false; // Flag that indicates any stone is adjacent to Thanos.
		int warriorInitialIndex = modifiedBinarySearch(Arrays.copyOfRange(status, 1, status.length), 0,
				status.length - 2, 8) + 1;
		int warriorsInspectIndex;
		for (int i = 2; i < status.length; i++) {
			int index = status[i];
			if (index < 8) {
				predictedCost += 3; // The cost of collecting the remaining stones.
				Cell inspectedStone = getCoordinates()[index];
				for (int j = 0; j <= 3; j++) { // Checks if any stone is adjacent to Thanos.
					if (inspectedStone.getX() + movementX[j] == getCoordinates()[1].getX()
							&& inspectedStone.getY() + movementY[j] == getCoordinates()[1].getY()) {
						predictedCost += 5;
						stoneAdjacentThanos = true;
						break;
					}
				}
				if (warriorInitialIndex >= 0) { // Checks if any stone is adjacent to any warrior.
					warriorsInspectIndex = warriorInitialIndex;
					for (; warriorsInspectIndex < status.length; warriorsInspectIndex++) {
						Cell inspectedWarrior = getCoordinates()[warriorsInspectIndex];
						for (int j = 0; j <= 3; j++) {
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
		if (node.getOperator() == null || !node.getOperator().equals("snap")) { // Calculates the cost of reaching Thanos' cell before Snapping.
			if (!((iron.getX() == getCoordinates()[1].getX()) && (iron.getY() == getCoordinates()[1].getY()))) {
				boolean isThanosAdjacent = false;
				for (int j = 0; j < 3; j++) {
					if (((iron.getX() + movementX[j] == getCoordinates()[1].getX())
							&& (iron.getY() + movementY[j] == getCoordinates()[1].getY()))) {
						isThanosAdjacent = true;
					}
				}
				// To not calculate the cost of going to a cell adjacent to Thanos before Thanos multiple times.
				if (!(isCollectedStones(node) && isThanosAdjacent) && !stoneAdjacentThanos) { 
					predictedCost += 10;
				} else {
			          if (!(isCollectedStones(node) && isThanosAdjacent)) {
				            predictedCost += 5;
				          }
				        }
			} else {
				if (isCollectedStones(node)) { // Already in Thanos' cell.
					predictedCost += 5;
				}
			}
		}
		return predictedCost;
	}

	public int oracleCostRelaxed(Node node) {
		AvengersState state = (AvengersState) node.getState();
		Cell iron = state.getIron();
		byte[] status = state.getStatus();
		int predictedCost = 0;
		for (int i = 2; i < status.length; i++) {
			int index = status[i];
			if (index < 8) {
				predictedCost += 3; // The cost of collecting the remaining stones.
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
				if (!(isCollectedStones(node) && isThanosAdjacent)) { // Cost of reaching Thanos' cell before Snapping.
					predictedCost += 10;
				}
			} else {
				if (isCollectedStones(node)) { // Already in Thanos' cell.
					predictedCost += 5;
				}
			}
		}
		return predictedCost;
	}

}