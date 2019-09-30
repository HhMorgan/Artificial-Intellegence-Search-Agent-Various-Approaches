package problemStatment;

import java.util.ArrayList;
import java.util.Arrays;

import generic.Node;
import generic.Cell;
import generic.Node;
import generic.Problem;

//A problem is a 5 tuple which states the problem and has the initial state of the world.
public class EndGame extends Problem{
	// Directions are mapped based on D-pad clockwise
	// movement where the start is the up direction.
	static final byte[] movementX = { -1, 0, 1, 0 };
	static final byte[] movementY = { 0, 1, 0, -1 };
	// up, right, down, left, collect, kill, snap
	private static final char[] operators = { 'u', 'r', 'd', 'l', 'c', 'k', 'e' };
	private Node initialState;
	private Cell[] coordinates;
	private ArrayList<Node> statespace;

	public EndGame(Node initialState, Cell[] coordinates) {
		this.initialState = initialState;
		this.coordinates = coordinates;
		this.statespace = new ArrayList<Node>();
	}

	public Node getInitialState() {
		return this.initialState;
	}
	
	// The AddState function adds a give state to the state space of the problem.
	public void addState(Node node) {
		this.statespace.add(node);
	}
	
	public void removeState(Node node) {
		this.statespace.remove(node);
	}

	// IsVisitedState predicate checks if the state is repeated.
	public boolean isVisitedState(Node node) {
		if(node.getOperator() != 'e')
		for (Node previousState : this.statespace) {
				if (node.getIron().getX() == previousState.getIron().getX() && node.getIron().getY() == previousState.getIron().getY()) {

					boolean match = true;
					//int i;
					for (int i = 2; i < node.getStatus().length; i++) {
						//System.out.print((node.getStatus()[i] != previousState.getStatus()[i]) + ",");
						if (node.getStatus()[i] != previousState.getStatus()[i]) {
							match = false;
							break;
						}
//						if(i > 2 && i < 8 && node.getStatus()[i] && node.getIron().getX() == coordinates[i].getX() && node.getIron().getY() == coordinates[i].getY()) {
//							match = false;
//							break;
//						}
					}
					//System.out.println();
					// System.out.println(i + "," + (node.getStatus()[i-1] !=
					// previousState.getStatus()[i-1]));
					if (match) {
						
						if ((node.getOperator() == 'k' && node.getOperator() == previousState.getOperator())
								|| (node.getOperator() == 'c' && node.getOperator() == previousState.getOperator())) {
							//System.out.println("REMOVED FROM : " + previousState);
							//System.out.println("Breaker : " + previousState);
							return true;
						}
						else {
							if(node.getOperator() != 'k' && node.getOperator() != 'c') {
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
	public boolean isCollectedStones(Node node) {
		boolean stones = true;
		for (int i = 2; i < 8; i++) {
			if (node.getStatus()[i]) {
				stones = false;
				break;
			}
		}
		return stones;
	}

	// The GoalTest predicate checks if the state is a goal state of not.
	public boolean goalTest(Node node) {
		if (this.isCollectedStones(node)) {
//			System.out.println(node.getCost());
			if (node.getOperator() == 'e' && node.getCost() < 100) {
//				System.out.println("SNAPPED!!!!");
				return true;
			}
		}
		return false;
	}

	// The Cost function computes the damage units inflicted to Iron Man at a given
	// state.
	public int cost(Cell iron, boolean[] status) {
		Cell gridBorders = this.coordinates[0];
		int dmg = 0;
		for (int i = 0; i < 4; i++) {
			if (iron.getX() + movementX[i] >= 0 && iron.getX() + movementX[i] < gridBorders.getX()
					&& iron.getY() + movementY[i] > 0 && iron.getY() + movementY[i] < gridBorders.getY()) {
				for (int j = 8; j < this.coordinates.length; j++) {
					if (status[j]) {
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
				//System.out.println("ISCOLLECTED!!!!!!!!");
				Node successorState = new Node(iron, node.getStatus(), operators[6], node.getCost(), node.getDepth() + 1, node);
				successorStates.add(successorState);
			}
		} else {
			// Collect a stone in the cell.
			for (int i = 2; i < 8; i++) {
				if (node.getStatus()[i]) {
					if (((iron.getX() == this.coordinates[i].getX()) && (iron.getY() == this.coordinates[i].getY()))) {
						collectStone = true;
						collectedStone = i;
						//System.out.println("STONE : "+ i);
					}
				}
			}
			if (collectStone) {
				boolean[] SuccessorStatus = new boolean[node.getStatus().length];
				for (int k = 0; k < node.getStatus().length; k++) {
					if (k != collectedStone) {
						SuccessorStatus[k] = node.getStatus()[k];
					} else {
						SuccessorStatus[k] = false;

					}
				}
				Node successorState = new Node(iron, SuccessorStatus, operators[4], node.getCost(), node.getDepth() + 1, node);
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
							if (node.getStatus()[j]) {
								if (((iron.getX() + movementX[i] == this.coordinates[j].getX())
										&& (iron.getY() + movementY[i] == this.coordinates[j].getY()))) {
									warriorLocation = j;
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
									Node successorState = new Node(SuccessorIron, node.getStatus(), operators[i], node.getCost(),
											node.getDepth() + 1, node);
									successorStates.add(successorState);
								}
							} else {
								// Transition to the successor state where Iron man moved to an empty cell.
								Cell SuccessorIron = new Cell(Byte.valueOf((byte) (iron.getX() + movementX[i])),
										Byte.valueOf((byte) (iron.getY() + movementY[i])));
								// System.out.println(SuccessorIron.x + ","+ SuccessorIron.y);
								int costSuccessor = node.getCost() + this.cost(SuccessorIron, node.getStatus());
								Node successorState = new Node(SuccessorIron, node.getStatus(), operators[i], costSuccessor,
										node.getDepth() + 1, node);
								successorStates.add(successorState);
							}
						} else {
							// Eliminate a warrior that is adjacent to Iron Man.
							if (!flag) {
								boolean[] SuccessorStatus = new boolean[node.getStatus().length];
								for (int k = 0; k < node.getStatus().length; k++) {
									if (k != warriorLocation) {
										SuccessorStatus[k] = node.getStatus()[k];
									} else {
										SuccessorStatus[k] = false;
									}
								}
								int costSuccessor = node.getCost() + this.cost(node.getIron(), node.getStatus()) + 2;
								Node successorState = new Node(iron, SuccessorStatus, operators[5], costSuccessor,
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
