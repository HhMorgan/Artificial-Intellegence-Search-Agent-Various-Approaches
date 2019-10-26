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
import search.Search;
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
		char [][] gridVis = new char [m][n];
		for (char[] row: gridVis)
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
		for(char[] row : gridVis) {
			System.out.println(Arrays.toString(row));
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
		for(int i = 2; i < gridStatus.length; i++) {
			gridStatus[i] = Byte.valueOf((byte) (i - 2));
		}
		AvengersState initialState = new AvengersState(gridStatus);
//		Node initialNode = new Node(initialState, null, problem.pathCost(initialState), 0, null);
		EndGame problem = new EndGame(initialState, encoding);
		//search strategy determination.
		Node goal = null;
		
//		switch(strategy) {
//		case "BF" : goal = (Node) Search.search(problem, new BFS());break;
//		case "DF" : goal = (Node) Search.search(problem, new DFS());break;
//		case "UC" : goal = (Node) Search.search(problem, new UCS());break;
//		case "ID" : goal = (Node) Search.search(problem, new IDS(problem));break;
//		case "AS1" : goal = (Node) Search.search(problem, new AS(problem :: oracleCost));break;
//	}
		
		switch(strategy) {
			case "BF" : goal = (Node) problem.search(new BFS());break;
			case "DF" : goal = (Node) problem.search( new DFS());break;
			case "UC" : goal = (Node) problem.search( new UCS());break;
			case "ID" : goal = (Node) problem.search( new IDS(problem));break;
			case "AS1" : goal = (Node) problem.search( new AS(problem :: oracleCost));break;
			case "AS2" : goal = (Node) problem.search( new AS(problem :: oracleCostRelaxed));break;
			case "GR1" : goal = (Node) problem.search( new GS(problem :: oracleCost));break;
			case "GR2" : goal = (Node) problem.search( new GS(problem :: oracleCostRelaxed));break;
		}		
//		System.out.println(problem.oracleCost(problem.getNode()) + problem.getNode().getPathCost());
//		System.out.println(problem.oracleCostRelaxed(problem.getNode()) + problem.getNode().getPathCost());
		String result = (goal != null)? goal.toString().substring(1) + ";" + goal.getPathCost() + ";" + problem.getExpandedNodes(): "There is no solution.";
		return result;
	}

	public static void main(String[] args) {
		//String grid = " 5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2";
		String grid = "12,12;0,6;9,11;8,3,3,0,11,8,7,4,7,7,10,2;2,8,11,2,2,6,4,6,9,8";
		//String grid = " 5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3";
		//String grid = "6,6;5,3;0,1;4,3,2,1,3,0,1,2,4,5,1,1;1,3,3,3,1,0,1,4,2,2";
		//String grid = " 5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,4,4,3";
		//String grid = "10,10;7,7;5,9;0,2,3,7,5,4,8,6,8,9,9,1;0,3,4,5,8,3,9,7,9,3";
		//String grid = "15,15;7,7;5,9;0,2,3,7,5,4,8,12,11,6,13,10;0,3,4,5,8,3";
		//String grid = "15,15;7,7;5,9;0,2,3,7,5,4,8,12,11,6,13,10;0,3,4,5,8,3,9,7,14,3";
		//String grid = "100,100;50,50;25,25;10,20,35,75,40,65,47,90,53,89,80,4;5,15,26,79,38,70,66,77";
		//String grid = " 4,4;2,0;2,3;0,2,0,3,1,0,2,1,3,2,3,3;1,2";
		printGrid(grid);
		long startTime = System.currentTimeMillis();
		System.out.println(solve(grid,"DF",false));
		long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.println(elapsedTime + " Miliseconds");

	}
}
