import Avengers.AvengersNode;
import Avengers.EndGame;
import generic.Cell;
import search.BFS;
import search.DFS;
import search.IDS;
import search.Search;
import search.UCS;

public class Main {
	
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
		AvengersNode initialState = new AvengersNode(gridStatus, 0,"start", 0, 0, null);
		EndGame problem = new EndGame(initialState, encoding);
		//search strategy determination.
		AvengersNode goal = null;
		switch(strategy) {
			case "BFS" : goal = (AvengersNode) Search.search(problem, new BFS());break;
			case "DFS" : goal = (AvengersNode) Search.search(problem, new DFS());break;
			case "UCS" : goal = (AvengersNode) Search.search(problem, new UCS());break;
			case "IDS" : goal = (AvengersNode) Search.search(problem, new IDS(problem));break;
		}
		//System.out.println("Depth : " + goal.getDepth());
		String result = (goal != null)? goal.toString() : "There is no solution.";
		
		return result;
	}

	public static void main(String[] args) {
		//String grid = " 5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2";
		//String grid = " 5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3";
		//String grid = " 5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,4,4,3";
		//String grid = "10,10;7,7;5,9;0,2,3,7,5,4,8,6,8,9,9,1;0,3,4,5,8,3,9,7,9,3";
		//String grid = "15,15;7,7;5,9;0,2,3,7,5,4,8,12,11,6,13,10;0,3,4,5,8,3";
		String grid = "15,15;7,7;5,9;0,2,3,7,5,4,8,12,11,6,13,10;0,3,4,5,8,3,9,7,14,3";
		//String grid = "100,100;50,50;25,25;10,20,35,75,40,65,47,90,53,89,80,4;5,15,26,79,38,70,66,77";
		//String grid = " 4,4;2,0;2,3;0,2,0,3,1,0,2,1,3,2,3,3;1,2";
		long startTime = System.currentTimeMillis();
		System.out.println(solve(grid,"IDS",false));
		long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.println(elapsedTime + " Miliseconds");

	}
}
