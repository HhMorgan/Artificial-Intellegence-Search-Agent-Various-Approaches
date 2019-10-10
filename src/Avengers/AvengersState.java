package Avengers;

import java.util.Arrays;
import java.util.Objects;

import generic.Cell;
import generic.State;

public class AvengersState extends State {
	byte [] grid;
	
	public AvengersState(byte [] gridStatus) {
		this.grid = gridStatus;
	}
	
	public byte[] getGrid() {
		return this.grid;
	}
	
	public Cell getIron() {
		return new Cell(this.grid[0], this.grid[1]);
	}
	
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
			
		if(count != otherCount) {
			if(count < otherCount) {
				return -1;
			}
			else {
				return 1;
			}
		}
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
        return Objects.hash(this.getIron().getX(),this.getIron().getY(), Arrays.hashCode(this.getStatus()));
    }
	
	public String toString() {
		return this.getIron().toString();
	}
	
}
 
