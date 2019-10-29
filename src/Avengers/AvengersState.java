package Avengers;

import java.util.Arrays;
import java.util.Objects;

import generic.Cell;
import generic.State;

public class AvengersState extends State {
	/*
	  The array in the state representation is mapped one to one to the coordinates array initially, 
	  grid[0:1] = Iron Man position, grid[2] = grid dimensions, grid[3] = Thanos position, grid[4:9]
	  = stones indices, grid[10:end] = warrior indices.
	 */
	byte [] grid;
	
	public AvengersState(byte [] gridStatus) {
		this.grid = gridStatus;
	}
	
	public byte[] getGrid() {
		return this.grid;
	}
	
	//Gets Iron Man's position from the grid array, grid[0:1]
	public Cell getIron() {
		return new Cell(this.grid[0], this.grid[1]);
	}
	
	//Gets the indices of the objects existing in the world, grid[2:end]
	public byte[] getStatus() {
		return Arrays.copyOfRange(this.grid, 2, this.grid.length);
	}

	@Override
	public int compareTo(State otherState) {
		AvengersState otherAvengerState = (AvengersState) otherState;
		int count = this.getGrid().length;
		int otherCount = otherAvengerState.getGrid().length;
		Cell iron = this.getIron();
		Cell otherIron = otherAvengerState.getIron();
		byte[] status = getStatus();
		byte[] otherStatus = otherAvengerState.getStatus();
		// Checks if the length between the two nodes is different, which would lead to different states of the world
		if(count != otherCount) {
			if(count < otherCount) {
				return -1;
			}
			else {
				return 1;
			}
		}
		// If the length of the grid for both states match, then the object's status are checked.
		if(iron.getX() == otherIron.getX() && iron.getY() == otherIron.getY()) {
			for(int i = 0; i < status.length; i++) {
				if(status[i] != otherStatus[i]) {
					if(status[i] < otherStatus[i]) {
						return -1;
					}
					else {
						return 1;
					}
				}
			}
			return 0;
		}
		// Compares the location of Iron Man if it was the state of the world.
		return iron.compareTo(otherIron);
	}
	
	
	@Override
	public boolean equals(Object o) { 
		AvengersState otherState = (AvengersState) o;
		int comparison = this.compareTo(otherState);
		if(comparison == 0) {
				return true;
			}
		return false;
	}
	
	
	@Override
    public int hashCode() {
		// The hash for each state consists of the combination of hashing the position of iron man and the hash of the status array of the state.
        return Objects.hash(this.getIron().getX(),this.getIron().getY(), Arrays.hashCode(this.getStatus()));
    }
	
	public String toString() {
		return this.getIron().toString();
	}
	
}
 
