package generic;

//A cell is the representation of cell in the grid map of the game.
public class Cell implements Comparable<Cell>{
	byte x;
	byte y;
	public Cell(byte x, byte y) {
		this.x = x;
		this.y = y;
	}
	
	public byte getX() {
		return this.x;
	}
	
	public byte getY() {
		return this.y;
	}
	
	public String toString() {
		return this.x + "," + this.y;
	}

	@Override
	public int compareTo(Cell otherCell) {
		// Checks if the value in the x coordinates is different.
		int checkX = (this.x > otherCell.x)? 1 : (this.x < otherCell.x)? -1 : 0;
		// Checks if the value in the y coordinates is different.
		int checkY = (this.y > otherCell.y)? 1 : (this.y < otherCell.y)? -1 : 0;
		// Determines the relation between the two cells, if the relation in the x or y coordinates both or greater or lower than the cell being checked.
		// However, if the relation differs between the two coordinates, then the x coordinates is prioritized. 
		int checkSum = (checkX + checkY > 1)? 1: (checkX + checkY < -1)? -1 : 
			(checkX == 1 && checkY == -1)? 1: (checkX == -1 && checkY == 1)? -1:(checkX + checkY); 
		return checkSum;
	}
}
