package artificialIntellegence;

import java.util.Arrays;

public class Main {

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
		Cell iron = new Cell(ix, iy);
		byte tx = Byte.parseByte(villainString[0], 10);
		byte ty = Byte.parseByte(villainString[1], 10);
		Cell thanos = new Cell(tx, ty);
		
		int totalLenght = 7 + enemyString.length / 2;
		Cell[] encoding = new Cell[totalLenght];
		encoding[0] = new Cell(m,n);
		encoding[1] = thanos;
		for (int i = 0; i < collectableString.length - 1; i += 2) {
			byte sx = Byte.parseByte(collectableString[i], 10);
			byte sy = Byte.parseByte(collectableString[i + 1], 10);
			encoding[i + 2] = new Cell(sx, sy);
		}
		for (int i = 0; i < enemyString.length - 1; i++) {
			byte wx = Byte.parseByte(enemyString[i], 10);
			byte wy = Byte.parseByte(enemyString[i + 1], 10);
			encoding[i + 8] = new Cell(wx, wy);
		}
		
		boolean[] status = new boolean[encoding.length];
		Arrays.fill(status, true);
		Node initialState = new Node(iron, status, 's', 0, 0, null);
		Problem problem = new Problem(initialState, encoding);
		
		return null;
	}

	public static void main(String[] args) {

	}
}
