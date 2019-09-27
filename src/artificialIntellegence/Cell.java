package artificialIntellegence;

//A cell is the representation of cell in the grid map of the game.
public class Cell{
	byte x;
	byte y;
	public Cell(byte x, byte y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
}
