import java.util.Arrays;

import Avengers.AvengersState;
import Avengers.EndGame;
import generic.Cell;
import generic.Node;
import search.AS;
import search.BFS;
import search.DFS;
import search.GS;
import search.IDS;
import search.UCS;

public class Main {

	public static void printGrid(String grid) {
		String[] parsedString = grid.split(";");
		String[] sizeString = parsedString[0].split(",");
		String[] characterString = parsedString[1].split(",");
		String[] villainString = parsedString[2].split(",");
		String[] collectableString = parsedString[3].split(",");
		String[] enemyString = parsedString[4].split(",");
		byte m = Byte.parseByte(sizeString[0].trim(), 10);
		byte n = Byte.parseByte(sizeString[1], 10);
		char[][] gridVis = new char[m][n];
		for (char[] row : gridVis)
			Arrays.fill(row, '_');
		byte ix = Byte.parseByte(characterString[0], 10);
		byte iy = Byte.parseByte(characterString[1], 10);
		gridVis[ix][iy] = 'I';
		byte tx = Byte.parseByte(villainString[0], 10);
		byte ty = Byte.parseByte(villainString[1], 10);
		gridVis[tx][ty] = 'T';
		for (int i = 0; i < collectableString.length - 1; i += 2) {
			byte sx = Byte.parseByte(collectableString[i], 10);
			byte sy = Byte.parseByte(collectableString[i + 1], 10);
			gridVis[sx][sy] = 'S';
		}
		for (int i = 0; i < enemyString.length - 1; i += 2) {
			byte wx = Byte.parseByte(enemyString[i], 10);
			byte wy = Byte.parseByte(enemyString[i + 1], 10);
			gridVis[wx][wy] = 'W';
		}
		for (char[] row : gridVis) {
			System.out.println(Arrays.toString(row));
		}
	}

	public static void printPath(String grid, String path) {
		String[] parsedString = grid.split(";");
		String[] sizeString = parsedString[0].split(",");
		String[] characterString = parsedString[1].split(",");
		String[] villainString = parsedString[2].split(",");
		String[] collectableString = parsedString[3].split(",");
		String[] enemyString = parsedString[4].split(",");
		byte m = Byte.parseByte(sizeString[0].trim(), 10);
		byte n = Byte.parseByte(sizeString[1], 10);
		char[][] gridVis = new char[m][n];
		for (char[] row : gridVis)
			Arrays.fill(row, '_');
		byte ix = Byte.parseByte(characterString[0], 10);
		byte iy = Byte.parseByte(characterString[1], 10);
		gridVis[ix][iy] = 'I';
		byte tx = Byte.parseByte(villainString[0], 10);
		byte ty = Byte.parseByte(villainString[1], 10);
		gridVis[tx][ty] = 'T';
		for (int i = 0; i < collectableString.length - 1; i += 2) {
			byte sx = Byte.parseByte(collectableString[i], 10);
			byte sy = Byte.parseByte(collectableString[i + 1], 10);
			gridVis[sx][sy] = 'S';
		}
		for (int i = 0; i < enemyString.length - 1; i += 2) {
			byte wx = Byte.parseByte(enemyString[i], 10);
			byte wy = Byte.parseByte(enemyString[i + 1], 10);
			gridVis[wx][wy] = 'W';
		}
		for (char[] row : gridVis) {
			System.out.println(Arrays.toString(row));
		}
		System.out.println(new String(new char[n]).replace("\0", "=-="));
		if (!(path.equals("There is no solution."))) {
			String[] moves = (path.split(";"))[0].split(",");
			for (int i = 0; i < moves.length; i++) {
				if (moves[i].equals("up")) {
					if (gridVis[ix][iy] == 'P') {
						gridVis[ix][iy] = 'S';
					} else {
						gridVis[ix][iy] = '_';
					}
					if (gridVis[ix - 1][iy] == 'S') {
						gridVis[ix - 1][iy] = 'O';
					} else {
						gridVis[ix - 1][iy] = 'I';
					}
					ix--;
				} else {
					if (moves[i].equals("down")) {
						if (gridVis[ix][iy] == 'P') {
							gridVis[ix][iy] = 'S';
						} else {
							gridVis[ix][iy] = '_';
						}
						if (gridVis[ix + 1][iy] == 'S') {
							gridVis[ix + 1][iy] = 'O';
						} else {
							gridVis[ix + 1][iy] = 'I';
						}
						ix++;
					} else {
						if (moves[i].equals("right")) {
							if (gridVis[ix][iy] == 'P') {
								gridVis[ix][iy] = 'S';
							} else {
								gridVis[ix][iy] = '_';
							}
							if (gridVis[ix][iy + 1] == 'S') {
								gridVis[ix][iy + 1] = 'O';
							} else {
								gridVis[ix][iy + 1] = 'I';
							}
							iy++;
						} else {
							if (moves[i].equals("left")) {
								if (gridVis[ix][iy] == 'P') {
									gridVis[ix][iy] = 'S';
								} else {
									gridVis[ix][iy] = '_';
								}
								if (gridVis[ix][iy - 1] == 'S') {
									gridVis[ix][iy - 1] = 'O';
								} else {
									gridVis[ix][iy - 1] = 'I';
								}
								iy--;
							} else {
								if (moves[i].equals("collect")) {
									gridVis[ix][iy] = 'I';
								} else {
									if (moves[i].equals("kill")) {
										if (gridVis[ix + 1][iy] == 'W') {
											gridVis[ix + 1][iy] = '_';
										}
										if (gridVis[ix - 1][iy] == 'W') {
											gridVis[ix - 1][iy] = '_';
										}
										if (gridVis[ix][iy + 1] == 'W') {
											gridVis[ix][iy + 1] = '_';
										}
										if (gridVis[ix][iy - 1] == 'W') {
											gridVis[ix][iy - 1] = '_';
										}
									}
									if (moves[i].equals("snap")) {
										gridVis[ix][iy] = 'S';
									}
								}
							}
						}
					}
				}

				for (char[] row : gridVis) {
					System.out.println(Arrays.toString(row));
				}
				System.out.println(new String(new char[n]).replace("\0", "=-="));
			}
		}
	}

	public static String solve(String grid, String strategy, boolean visualize) {
		String[] parsedString = grid.split(";");
		String[] sizeString = parsedString[0].split(",");
		String[] characterString = parsedString[1].split(",");
		String[] villainString = parsedString[2].split(",");
		String[] collectableString = parsedString[3].split(",");
		String[] enemyString = parsedString[4].split(",");
		byte m = Byte.parseByte(sizeString[0].trim(), 10);
		byte n = Byte.parseByte(sizeString[1], 10);
		byte ix = Byte.parseByte(characterString[0], 10);
		byte iy = Byte.parseByte(characterString[1], 10);
		Cell iron = new Cell(ix, iy);
		byte tx = Byte.parseByte(villainString[0], 10);
		byte ty = Byte.parseByte(villainString[1], 10);
		Cell thanos = new Cell(tx, ty);
		int totalLength = 8 + enemyString.length / 2;
		Cell[] encoding = new Cell[totalLength];
		encoding[0] = new Cell(m, n);
		encoding[1] = thanos;
		for (int i = 0; i < collectableString.length - 1; i += 2) {
			byte sx = Byte.parseByte(collectableString[i], 10);
			byte sy = Byte.parseByte(collectableString[i + 1], 10);
			encoding[(i / 2) + 2] = new Cell(sx, sy);
		}
		for (int i = 0; i < enemyString.length - 1; i += 2) {
			byte wx = Byte.parseByte(enemyString[i], 10);
			byte wy = Byte.parseByte(enemyString[i + 1], 10);
			encoding[(i / 2) + 8] = new Cell(wx, wy);
		}

		byte[] gridStatus = new byte[encoding.length + 2];
		gridStatus[0] = iron.getX();
		gridStatus[1] = iron.getY();
		for (int i = 2; i < gridStatus.length; i++) {
			gridStatus[i] = Byte.valueOf((byte) (i - 2));
		}
		AvengersState initialState = new AvengersState(gridStatus);
		EndGame problem = new EndGame(initialState, encoding);
		// search strategy determination.
		Node goal = null;		
		switch (strategy) {
		case "BF":
			goal = (Node) problem.search(new BFS());
			break;
		case "DF":
			goal = (Node) problem.search(new DFS());
			break;
		case "UC":
			goal = (Node) problem.search(new UCS());
			break;
		case "ID":
			goal = (Node) problem.search(new IDS(problem));
			break;
		case "AS1":
			goal = (Node) problem.search(new AS(problem::oracleCostRelaxed));
			break;
		case "AS2":
			goal = (Node) problem.search(new AS(problem::oracleCost));
			break;
		case "GR1":
			goal = (Node) problem.search(new GS(problem::oracleCostRelaxed));
			break;
		case "GR2":
			goal = (Node) problem.search(new GS(problem::oracleCost));
			break;
		}
		String result = (goal != null)
				? goal.toString().substring(1) + ";" + goal.getPathCost() + ";" + problem.getExpandedNodes()
				: "There is no solution.";
//				System.out.println(problem.oracleCost(problem.getNode()) + problem.getNode().getPathCost());
		if (visualize) {
			printPath(grid, result);
		}
		return result;
	}

	public static void main(String[] args) {
		//String grid = " 5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2";
		//String grid = "6,6;5,3;0,1;4,3,2,1,3,0,1,2,4,5,1,1;1,3,3,3,1,0,1,4,2,2";
		String grid = "7,7;3,3;1,1;0,1,1,0,1,2,2,1,4,4,6,0;0,0,0,2,1,3,2,0,2,2,3,1,3,4,4,3,5,0,0,6,5,6,6,6";
		//String grid = "7,7;3,3;1,1;0,5,3,0,4,2,2,1,4,4,6,0;6,6";
		//String grid = "15,15;12,13;5,7;7,0,9,14,14,8,5,8,8,9,8,4;6,6,4,3,10,2,7,4,3,11";
		//String grid = " 5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3";
		// String grid = "15,15;12,13;5,7;7,0,9,14,14,8,5,8,8,9,8,4;6,6,4,3,10,2,7,4,3,11";
		// String grid = " 5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,4,4,3";
		// String grid = "10,10;7,7;5,9;0,2,3,7,5,4,8,6,8,9,9,1;0,3,4,5,8,3,9,7,9,3";
		// String grid = "15,15;7,7;5,9;0,2,3,7,5,4,8,12,11,6,13,10;0,3,4,5,8,3";
		//String grid = "15,15;7,7;5,9;0,2,3,7,5,4,8,12,11,6,13,10;0,3,4,5,8,3,9,7,14,3";
		// String grid = "100,100;50,50;25,25;10,20,35,75,40,65,47,90,53,89,80,4;5,15,26,79,38,70,66,77";
		// String grid = " 4,4;2,0;2,3;0,2,0,3,1,0,2,1,3,2,3,3;1,2";		
//		String grid = "5,5;2,2;4,2;4,0,1,2,3,0,2,1,4,1,2,4;3,2,0,0,3,4,4,3,4,4";
//		String grid = "6,6;5,3;0,1;4,3,2,1,3,0,1,2,4,5,1,1;1,3,3,3,1,0,1,4,2,2";
//		String grid = "7,7;5,4;0,1;5,0,5,6,3,1,4,3,1,2,6,3;2,5,2,6,1,0,5,5,6,5";
//		String grid = "8,8;7,2;2,2;7,6,2,3,3,0,0,1,6,0,5,5;7,3,4,4,1,6,2,4,2,6";
//		String grid = "9,9;2,5;3,3;6,2,5,1,3,0,2,8,8,3,0,5;5,4,5,5,1,6,6,3,4,8";
//		String grid = "10,10;5,1;0,4;3,1,6,8,1,2,9,2,1,5,0,8;7,8,7,6,3,3,6,0,3,8";
//		String grid = "11,11;9,5;7,1;9,0,8,8,9,1,8,4,2,3,9,10;2,0,0,10,6,3,10,6,6,2";
//		String grid = "12,12;0,6;9,11;8,3,3,0,11,8,7,4,7,7,10,2;2,8,11,2,2,6,4,6,9,8";
//		String grid = "13,13;4,2;2,4;6,1,1,10,8,4,9,2,2,8,9,4;6,4,3,4,3,11,1,12,1,9";
//		String grid = "14,14;2,13;12,7;8,6,9,4,7,1,4,4,4,7,2,3;8,13,0,4,0,8,5,7,10,0";
//		String grid = "15,15;12,13;5,7;7,0,9,14,14,8,5,8,8,9,8,4;6,6,4,3,10,2,7,4,3,11";
		printGrid(grid);
		long startTime = System.currentTimeMillis();
		System.out.println(solve(grid, "AS2", false));
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime + " Miliseconds");

	}
}
