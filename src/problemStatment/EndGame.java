 
package problemStatment;

import java.util.ArrayList;
import java.util.HashSet;
import generic.Node;
import generic.Cell;
import generic.Problem;

//A problem is a 5 tuple which states the problem and has the initial state of the world.
public class EndGame extends Problem {
	// Directions are mapped based on D-pad clockwise
	// movement where the start is the up direction.
	static final byte[] movementX = { -1, 0, 1, 0 };
	static final byte[] movementY = { 0, 1, 0, -1 };
	private AvengersOperators operators;
	private Node initialState;
	private Cell[] coordinates;
	private HashSet<AvengersState> statespace;

	public EndGame(Node initialState, Cell[] coordinates) {
		this.initialState = initialState;
		this.coordinates = coordinates;
		this.statespace = new HashSet<AvengersState>();
		// up, right, down, left, collect, kill, snap
		char[] operators = { 'u', 'r', 'd', 'l', 'c', 'k', 'e' };
		this.operators = new AvengersOperators(operators);
	}

	public Node getInitialState() {
		return this.initialState;
	}

	// The AddState function adds a give state to the state space of the problem.
	public void addState(AvengersState state) {
		this.statespace.add(state);
	}

	public void removeState(AvengersState state) {
		this.statespace.remove(state);
	}

	
	// IsVisitedState predicate checks if the state is repeated.
	public boolean isVisitedState(Node node) {
		if (node.getOperator() != 'e') {
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
			if (node.getStatus()[i] == 1) {
				stones = false;
				break;
			}
		}
		return stones;
	}

	// The GoalTest predicate checks if the state is a goal state of not.
	public boolean goalTest(Node node) {
		if (this.isCollectedStones(node)) {
			if (node.getOperator() == 'e' && node.getCost() < 100) {
				return true;
			}
		}
		return false;
	}

	// The Cost function computes the damage units inflicted to Iron Man at a given
	// state.	
	public int cost(Cell iron, byte[] status) {
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
	public ArrayList<Node> transition(Node node) {
		ArrayList<Node> successorStates = new ArrayList<Node>();
		Cell iron = node.getIron();
		Cell gridBorders = this.coordinates[0];
		boolean collectStone = false;
		int collectedStone = -1;
		// Transition Iron Man to Snap.
		if (((iron.getX() == this.coordinates[1].getX()) && (iron.getY() == this.coordinates[1].getY()))) {
			if (this.isCollectedStones(node)) {
				//TODO Change thanos byte 3 value
				Node successorState = new Node(node.getState().getGridStatus(), operators.getOperators()[6], node.getCost(),
						node.getDepth() + 1, node);
				successorStates.add(successorState);
			}
		} else {
			// Collect a stone in the cell.
			for (int i = 2; i < 8; i++) {
				if (node.getStatus()[i] == 1) {
					if (((iron.getX() == this.coordinates[i].getX()) && (iron.getY() == this.coordinates[i].getY()))) {
						collectStone = true;
						collectedStone = i + 2;
						break;
					}
				}
			}
			if (collectStone) {
				byte[] SuccessorGridStatus = new byte[node.getState().getGridStatus().length];
				for (int k = 0; k < node.getStatus().length; k++) {
					if (k != collectedStone) {
						SuccessorGridStatus[k] = node.getState().getGridStatus()[k];
					} else {
						SuccessorGridStatus[k] = 0;

					}
				}
				Node successorState = new Node(SuccessorGridStatus, operators.getOperators()[4], node.getCost(), node.getDepth() + 1,
						node);
				successorStates.add(successorState);
			}
			// Allowed moves in a state.
			if (!collectStone) {
				// checks the four adjacent cells.
				for (int i = 0; i < 4; i++) {
					// checks that the adjacent cells are in the grid borders (checks if the move is
					// legal).
					if (iron.getX() + movementX[i] >= 0 && iron.getX() + movementX[i] < gridBorders.getX()
							&& iron.getY() + movementY[i] >= 0 && iron.getY() + movementY[i] < gridBorders.getY()) {
						// checks if there is a warrior in the adjacent cell.
						boolean flag = true;
						int warriorLocation = -1;
						for (int j = 8; j < this.coordinates.length; j++) {
							if (node.getStatus()[j] == 1) {
								if (((iron.getX() + movementX[i] == this.coordinates[j].getX())
										&& (iron.getY() + movementY[i] == this.coordinates[j].getY()))) {
									warriorLocation = j + 2;
									flag = false;
									break;
								}
							}
						}
						if (flag) {
							// checks if Thanos is adjacent and all of the stones are collected to
							// transition of the goal state.
							if (((iron.getX() + movementX[i] == this.coordinates[1].getX())
									&& (iron.getY() + movementY[i] == this.coordinates[1].getY()))) {
								if (this.isCollectedStones(node)) {
									Cell SuccessorIron = new Cell(Byte.valueOf((byte) (iron.getX() + movementX[i])),
											Byte.valueOf((byte) (iron.getY() + movementY[i])));
									byte[] SuccessorGridStatus = new byte[node.getState().getGridStatus().length];
									SuccessorGridStatus[0] = SuccessorIron.getX();
									SuccessorGridStatus[1] = SuccessorIron.getY();
									for(int j = 2; j < node.getState().getGridStatus().length; j++) {
										SuccessorGridStatus[j] = node.getState().getGridStatus()[j];
									}
									Node successorState = new Node(SuccessorGridStatus, operators.getOperators()[i],
											node.getCost(), node.getDepth() + 1, node);
									successorStates.add(successorState);
								}
							} else {
								// Transition to the successor state where Iron man moved to an empty cell.
								Cell SuccessorIron = new Cell(Byte.valueOf((byte) (iron.getX() + movementX[i])),
										Byte.valueOf((byte) (iron.getY() + movementY[i])));
								byte[] SuccessorGridStatus = new byte[node.getState().getGridStatus().length];
								SuccessorGridStatus[0] = SuccessorIron.getX();
								SuccessorGridStatus[1] = SuccessorIron.getY();
								for(int j = 2; j < node.getState().getGridStatus().length; j++) {
									SuccessorGridStatus[j] = node.getState().getGridStatus()[j];
								}
								int costSuccessor = node.getCost() + this.cost(SuccessorIron, node.getStatus());
								Node successorState = new Node(SuccessorGridStatus, operators.getOperators()[i],
										costSuccessor, node.getDepth() + 1, node);
								successorStates.add(successorState);
							}
						} else {
							// Eliminate a warrior that is adjacent to Iron Man.
							if (!flag) {
								
								byte[] SuccessorGridStatus = new byte[node.getState().getGridStatus().length];
								for (int k = 0; k < node.getStatus().length; k++) {
									if (k != warriorLocation) {
										SuccessorGridStatus[k] = node.getState().getGridStatus()[k];
									} else {
										SuccessorGridStatus[k] = 0;

									}
								}
								int costSuccessor = node.getCost() + this.cost(node.getIron(), node.getStatus()) + 2;
								Node successorState = new Node(SuccessorGridStatus, operators.getOperators()[5], costSuccessor,
										node.getDepth() + 1, node);
								successorStates.add(successorState);
							}
						}
					}
				}
			}
		}

		return successorStates;
	}
	
	
}
