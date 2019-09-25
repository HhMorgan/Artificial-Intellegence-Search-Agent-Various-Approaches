package artificialIntellegence;

public class Main {

	// Directions are mapped based on D-pad clockwise
	// movement where the start is the up direction.
	static final int[] movementX = { 0, 1, 0, -1 };
	static final int[] movementY = { -1, 0, 1, 0 };

	public static String BFS() {
		return null;
	}

	public static String DFS() {
		return null;
	}

	public static String IDS() {
		return null;
	}

	public static String UCS() {
		return null;
	}

	public static String GS() {
		return null;
	}

	public static String AS() {
		return null;
	}

	// The damage calculated at every time step.
	public static int dmgTimeStep(int[][] gridInt, int i, int j) {
		int dmg = 0;
		for (int k = 0; k < 4; k++) {
			int moveX = i + movementX[k];
			int moveY = j + movementY[k];
			if (moveX >= 0 & moveY >= 0) {
				if (gridInt[moveX][moveY] == 3)
					dmg += 5;
				if (gridInt[moveX][moveY] == 4)
					dmg += 1;
			}
		}
		return dmg;
	}

	// The attack action on a warrior cell if it was adjacent to Iron Man.
	public static int[][] attack(int[][] gridInt, int i, int j, int direction) {
		int adjacentX = i + movementX[direction];
		int adjacentY = j + movementY[direction];
		if (adjacentX >= 0 & adjacentY >= 0) {
			if (gridInt[adjacentX][adjacentY] == 4) {
				gridInt[adjacentX][adjacentY] = 0;
			}
		}
		return gridInt;
	}

	// The conditions for the snap action to be satisfied.
	public static boolean snap(int[][] gridInt, int i, int j, int health, int stonesLeft) {
		if (health < 100 & stonesLeft == 0 & gridInt[i][j] == 3) {
			return true;
		}
		return false;
	}

	// The move action for Iron Man depending on the direction chosen.
	public static int[][] move(int[][] gridInt, int i, int j, int direction) {
		if (j > 0) {
			if (gridInt[i][j] == 1) {
				int adjacentX = i + movementX[direction];
				int adjacentY = j + movementY[direction];
				if (gridInt[adjacentX][adjacentY] <= 2) {
					gridInt[adjacentX][adjacentY] = 0;
					gridInt[adjacentX][adjacentY] = 1;
				}
			}
		}
		return gridInt;
	}

	public static String solve(String grid, String strategy, boolean visualize) {
		String[] parsedString = grid.split(";");
		String[] sizeString = parsedString[0].split(",");
		String[] characterString = parsedString[1].split(",");
		String[] villainString = parsedString[2].split(",");
		String[] collectableString = parsedString[3].split(",");
		String[] enemyString = parsedString[4].split(",");
		byte m = Byte.parseByte(sizeString[0], 10);
		byte n = Byte.parseByte(sizeString[1], 10);
		byte ix = Byte.parseByte(characterString[0], 10);
		byte iy = Byte.parseByte(characterString[1], 10);
		Cell[] stones = new Cell[collectableString.length / 2];
		for (int i = 0; i < collectableString.length - 1; i += 2) {
			byte sx = Byte.parseByte(collectableString[i], 10);
			byte sy = Byte.parseByte(collectableString[i + 1], 10);
			stones[i / 2] = new Cell(sx, sy);
		}
		byte tx = Byte.parseByte(villainString[0], 10);
		byte ty = Byte.parseByte(villainString[1], 10);
		Cell[] warriors = new Cell[enemyString.length / 2];
		for (int i = 0; i < enemyString.length - 1; i++) {
			byte wx = Byte.parseByte(enemyString[i], 10);
			byte wy = Byte.parseByte(enemyString[i + 1], 10);
			warriors[i / 2] = new Cell(wx, wy);
		}
		
		Node initialState =  new Node(ix, iy, stones, warriors, 0, null);
		

		return null;
	}

	public static void main(String[] args) {

	}
}
