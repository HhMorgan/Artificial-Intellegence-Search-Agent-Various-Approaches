package artificialIntellegence;

import java.util.ArrayList;
import java.util.Arrays;

//A problem is a 5 tuple which states the problem and has the initial state of the world.
public class Problem {
	// Directions are mapped based on D-pad clockwise
	// movement where the start is the up direction.
	static final byte[] movementX = { -1, 0, 1, 0 };
	static final byte[] movementY = { 0, 1, 0, -1 };
	// up, right, down, left, collect, kill, snap
	private static final char[] action = { 'u', 'r', 'd', 'l', 'c', 'k', 'e' };
	Node initialState;
	private Cell[] coordinates;
	private ArrayList<Node> statespace;

	public Problem(Node initialState, Cell[] coordinates) {
		this.initialState = initialState;
		this.coordinates = coordinates;
		this.statespace = new ArrayList<Node>();
	}

	// The AddState function adds a give state to the state space of the problem.
	public void addState(Node node) {
		this.statespace.add(node);
	}
	
	public void removeState(Node node) {
		this.statespace.remove(node);
	}

	// IsVisitedState predicate checks if the state is repeated.
	public static boolean isVisitedState(Problem problem, Node node) {
		if(node.operator != 'e')
		for (Node previousState : problem.statespace) {
				if (node.iron.getX() == previousState.iron.getX() && node.iron.getY() == previousState.iron.getY()) {

					boolean match = true;
					//int i;
					for (int i = 2; i < node.status.length; i++) {
						//System.out.print((node.status[i] != previousState.status[i]) + ",");
						if (node.status[i] != previousState.status[i]) {
							match = false;
							break;
						}
//						if(i > 2 && i < 8 && node.status[i] && node.iron.getX() == coordinates[i].getX() && node.iron.getY() == coordinates[i].getY()) {
//							match = false;
//							break;
//						}
					}
					//System.out.println();
					// System.out.println(i + "," + (node.status[i-1] !=
					// previousState.status[i-1]));
					if (match) {
						
						if ((node.operator == 'k' && node.operator == previousState.operator)
								|| (node.operator == 'c' && node.operator == previousState.operator)) {
							//System.out.println("REMOVED FROM : " + previousState);
							//System.out.println("Breaker : " + previousState);
							return true;
						}
						else {
							if(node.operator != 'k' && node.operator != 'c') {
								//System.out.println("REMOVED FROM : " + previousState);
								return true;
							}
						}
					}
			}
		}
		return false;
	}

	// The IsCollectedStones predicate checks if all the stones are collected in the
	// world or not.
	public static boolean isCollectedStones(Node node) {
		boolean stones = true;
		for (int i = 2; i < 8; i++) {
			if (node.status[i]) {
				stones = false;
				break;
			}
		}
		return stones;
	}

	// The GoalTest predicate checks if the state is a goal state of not.
	public static boolean goalTest(Node node) {
		if (isCollectedStones(node)) {
//			System.out.println(node.cost);
			if (node.operator == 'e' && node.cost < 100) {
//				System.out.println("SNAPPED!!!!");
				return true;
			}
		}
		return false;
	}

	// The Cost function computes the damage units inflicted to Iron Man at a given
	// state.
	public static int cost(Problem problem,Cell iron, boolean[] status) {
		Cell gridBorders = problem.coordinates[0];
		int dmg = 0;
		for (int i = 0; i < 4; i++) {
			if (iron.getX() + movementX[i] >= 0 && iron.getX() + movementX[i] < gridBorders.getX()
					&& iron.getY() + movementY[i] > 0 && iron.getY() + movementY[i] < gridBorders.getY()) {
				for (int j = 8; j < problem.coordinates.length; j++) {
					if (status[j]) {
						if (((iron.getX() + movementX[i] == problem.coordinates[j].getX())
								&& (iron.getY() + movementY[i] == problem.coordinates[j].getY()))) {
							dmg += 1;
						}
					}
				}
			}
			if (((iron.getX() + movementX[i] == problem.coordinates[1].getX())
					&& (iron.getY() + movementY[i] == problem.coordinates[1].getY()))) {
				dmg += 5;
			}
		}
		return dmg;
	}

	// The Transition function is the function that computes the expansion of some
	// give state.
	public static ArrayList<Node> transition(Problem problem, Node node) {
		ArrayList<Node> successorStates = new ArrayList<Node>();
		Cell iron = node.iron;
		Cell gridBorders = problem.coordinates[0];
		boolean collectStone = false;
		int collectedStone = -1;
		// Transition Iron Man to Snap.
		if (((iron.getX() == problem.coordinates[1].getX()) && (iron.getY() == problem.coordinates[1].getY()))) {
			if (isCollectedStones(node)) {
				//System.out.println("ISCOLLECTED!!!!!!!!");
				Node successorState = new Node(iron, node.status, action[6], node.cost, node.depth + 1, node);
				successorStates.add(successorState);
			}
		} else {
			// Collect a stone in the cell.
			for (int i = 2; i < 8; i++) {
				if (node.status[i]) {
					if (((iron.getX() == problem.coordinates[i].getX()) && (iron.getY() == problem.coordinates[i].getY()))) {
						collectStone = true;
						collectedStone = i;
						//System.out.println("STONE : "+ i);
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
				Node successorState = new Node(iron, SuccessorStatus, action[4], node.cost, node.depth + 1, node);
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
						for (int j = 8; j < problem.coordinates.length; j++) {
							if (node.status[j]) {
								if (((iron.getX() + movementX[i] == problem.coordinates[j].getX())
										&& (iron.getY() + movementY[i] == problem.coordinates[j].getY()))) {
									warriorLocation = j;
									flag = false;
									break;
								}
							}
						}
						if (flag) {
							// checks if Thanos is adjacent and all of the stones are collected to
							// transition of the goal state.
							if (((iron.getX() + movementX[i] == problem.coordinates[1].getX())
									&& (iron.getY() + movementY[i] == problem.coordinates[1].getY()))) {
								if (isCollectedStones(node)) {
									Cell SuccessorIron = new Cell(Byte.valueOf((byte) (iron.getX() + movementX[i])),
											Byte.valueOf((byte) (iron.getY() + movementY[i])));
									Node successorState = new Node(SuccessorIron, node.status, action[i], node.cost,
											node.depth + 1, node);
									successorStates.add(successorState);
								}
							} else {
								// Transition to the successor state where Iron man moved to an empty cell.
								Cell SuccessorIron = new Cell(Byte.valueOf((byte) (iron.getX() + movementX[i])),
										Byte.valueOf((byte) (iron.getY() + movementY[i])));
								// System.out.println(SuccessorIron.x + ","+ SuccessorIron.y);
								int costSuccessor = node.cost + cost(problem, SuccessorIron, node.status);
								Node successorState = new Node(SuccessorIron, node.status, action[i], costSuccessor,
										node.depth + 1, node);
								successorStates.add(successorState);
							}
						} else {
							// Eliminate a warrior that is adjacent to Iron Man.
							if (!flag) {
								boolean[] SuccessorStatus = new boolean[node.status.length];
								for (int k = 0; k < node.status.length; k++) {
									if (k != warriorLocation) {
										SuccessorStatus[k] = node.status[k];
									} else {
										SuccessorStatus[k] = false;
									}
								}
								int costSuccessor = node.cost + cost(problem, node.iron, node.status) + 2;
								Node successorState = new Node(iron, SuccessorStatus, action[5], costSuccessor,
										node.depth + 1, node);
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
