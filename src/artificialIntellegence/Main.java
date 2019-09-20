package artificialIntellegence;

public class Main {

	// Directions are mapped based on D-pad clockwise 
	// movement  where the start is the up direction.
	static final int[] movementX = { 0, 1, 0, -1 };
	static final int[] movementY = { -1, 0, 1, 0 };
	//Constants that represents the types of the cell in the grid.
	static final int EMPTYCELL = 0;
	static final int IRONMAN = 1;
	static final int STONECELL = 2;
	static final int THANOS = 3;
	static final int WARRIOR = 4;

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
		int m = Integer.parseInt(sizeString[0]);
		int n = Integer.parseInt(sizeString[1]);
		int[][] gridInt = new int[m][n];
		/*
		 * The mapping of the values to the representation of the grid 
		 * Empty Cell = 0, Iron Man = 1, Stone = 2, Thanos = 3, Warrior = 4 
		 * Mapping the types of the cell to int would make the encoding of 
		 * the problem easier, as there are not a lot of cell types.
		 */
		int ix = Integer.parseInt(characterString[0]);
		int iy = Integer.parseInt(characterString[1]);
		gridInt[ix][iy] = IRONMAN;
		for (int i = 0; i < collectableString.length - 1; i++) {
			int sx = Integer.parseInt(collectableString[i]);
			int sy = Integer.parseInt(collectableString[i + 1]);
			gridInt[sx][sy] = STONECELL;
		}
		int tx = Integer.parseInt(villainString[0]);
		int ty = Integer.parseInt(villainString[1]);
		gridInt[tx][ty] = THANOS;
		for (int i = 0; i < enemyString.length - 1; i++) {
			int wx = Integer.parseInt(enemyString[i]);
			int wy = Integer.parseInt(enemyString[i + 1]);
			gridInt[wx][wy] = WARRIOR;
		}
		int collectableAmount = collectableString.length / 2;

		return null;
	}

	public static void main(String[] args) {

	}
}
