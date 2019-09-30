package generic;

//A cell is the representation of cell in the grid map of the game.
public class Cell{
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
}
