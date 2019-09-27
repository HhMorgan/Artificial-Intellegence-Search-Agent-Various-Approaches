package artificialIntellegence;

import java.util.ArrayList;

//A problem is a 5 tuple which states the problem and has the initial state of the world.
public class Problem {
	// Directions are mapped based on D-pad clockwise
	// movement where the start is the up direction.
	static final byte[] movementX = { 0, 1, 0, -1 };
	static final byte[] movementY = { -1, 0, 1, 0 };
	static final char[] action = { 'u', 'r', 'd', 'l', 'c', 'k', 'e' };
	Node initialState;
	static Cell[] coordinates;
	static ArrayList<Node> statespace;

	public Problem(Node initialState, Cell[] coordinates) {
		this.initialState = initialState;
		this.coordinates = coordinates;
		this.statespace = new ArrayList<Node>();
	}
	
	//The AddState function adds a give state to the state space of the problem.
	public void addState(Node node) {
		this.statespace.add(node);
	}

	//The CollectedStones function checks if all the stones are collected in the world or not.
	public static boolean collectedStones(Node node) {
		boolean stones = true;
		for (int i = 2; i < 8; i++) {
			if (!node.status[i]) {
				stones = false;
				break;
			}
		}
		return stones;
	}

	//The GoalTest function checks if the state is a goal state of not.
	public static boolean goalTest(Node node) {
		if (collectedStones(node)) {
			if (node.operator == 'e' && node.cost < 100) {
				return true;
			}
		}
		return false;
	}

	//The Cost function computes the damage units inflicted to Iron Man at a given state.
	public static int cost(Cell iron, boolean[] status) {
		Cell gridBorders = coordinates[0];
		int dmg = 0;
		for (int i = 0; i < 4; i++) {
			if (iron.x + movementX[i] >= 0 && iron.x + movementX[i] < gridBorders.x && iron.y + movementY[i] > 0
					&& iron.y + movementY[i] < gridBorders.y) {
				for (int j = 8; j < coordinates.length; j++) {
					if (status[j]) {
						if (((iron.x + movementX[i] == coordinates[j].x)
								&& (iron.y + movementY[i] == coordinates[j].y))) {
							dmg += 1;
						}
					}
				}
			}
			if (((iron.x + movementX[i] == coordinates[1].x) && (iron.y + movementY[i] == coordinates[1].y))) {
				dmg += 5;
			}
		}
		return dmg;
	}
	
	//The Transition function is the function that computes the expansion of some give state.
	public static ArrayList<Node> transition(Node node) {
		ArrayList<Node> successorStates = new ArrayList<Node>();
		Cell iron = node.iron;
		Cell gridBorders = coordinates[0];
		boolean collectStone = false;
		int collectedStone = -1;
		//Transition Iron Man to Snap.
		if (((iron.x == coordinates[1].x) && (iron.y == coordinates[1].y))) {
			if (collectedStones(node)) {
				Node successorState = new Node(iron, node.status, action[6], node.cost, node.depth + 1, node);
				successorStates.add(successorState);
			}
		} else {
			//Collect a stone in the cell.
			for (int i = 2; i < 8; i++) {
				if (node.status[i]) {
					if (((iron.x == coordinates[i].x) && (iron.y == coordinates[i].y))) {
						collectStone = true;
						collectedStone = i;
					}
				}
			}
			if (collectStone) {
				boolean[] SuccessorStatus = new boolean[node.status.length];
				for (int k = 0; k < node.status.length; k++) {
					if (k != collectedStone) {
						SuccessorStatus[k] = node.status[k];
					} else {
						SuccessorStatus[k] = false;
					}
				}
				Node successorState = new Node(iron, node.status, action[4], node.cost, node.depth + 1, node);
				successorStates.add(successorState);
			}
			//Allowed moves in a state.
			if (!collectStone) {
				//checks the four adjacent cells.
				for (int i = 0; i < 4; i++) {
					//checks that the adjacent cells are in the grid borders (checks if the move is legal).
					if (iron.x + movementX[i] >= 0 && iron.x + movementX[i] < gridBorders.x && iron.y + movementY[i] > 0
							&& iron.y + movementY[i] < gridBorders.y) {
						//checks if there is a warrior in the adjacent cell.
						boolean flag = true;
						int warriorLocation = -1;
						for (int j = 8; j < coordinates.length; j++) {
							if (node.status[j]) {
								if (((iron.x + movementX[i] == coordinates[j].x)
										&& (iron.y + movementY[i] == coordinates[j].y))) {
									warriorLocation = j;
									flag = false;
									break;
								}
							}
						}
						if (flag) {
							//checks if Thanos is adjacent and all of the stones are collected to transition of the goal state.
							if (((iron.x + movementX[i] == coordinates[1].x)
									&& (iron.y + movementY[i] == coordinates[1].y))) {
								if (collectedStones(node)) {
									Cell SuccessorIron = new Cell(Byte.valueOf((byte) (iron.x + movementX[i])),
											Byte.valueOf((byte) (iron.y + movementY[i])));
									Node successorState = new Node(SuccessorIron, node.status, action[i], node.cost,
											node.depth + 1, node);
									successorStates.add(successorState);
								}
							} else {
								//Transition to the successor state where Iron man moved to an empty cell.
								Cell SuccessorIron = new Cell(Byte.valueOf((byte) (iron.x + movementX[i])),
										Byte.valueOf((byte) (iron.y + movementY[i])));
								int costSuccessor = node.cost + cost(SuccessorIron, node.status);
								Node successorState = new Node(SuccessorIron, node.status, action[i], costSuccessor,
										node.depth + 1, node);
								successorStates.add(successorState);
							}
						} else {
							//Eliminate a warrior that is adjacent to Iron Man.
							boolean[] SuccessorStatus = new boolean[node.status.length];
							for (int k = 0; k < node.status.length; k++) {
								if (k != warriorLocation) {
									SuccessorStatus[k] = node.status[k];
								} else {
									SuccessorStatus[k] = false;
								}
							}
							int costSuccessor = node.cost + cost(node.iron, node.status) + 2;
							Node successorState = new Node(iron, node.status, action[5], costSuccessor, node.depth + 1,
									node);
							successorStates.add(successorState);
						}
					}
				}
			}
		}

		return successorStates;
	}
}
