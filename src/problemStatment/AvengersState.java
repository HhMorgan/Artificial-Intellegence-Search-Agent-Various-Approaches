package problemStatment;

import java.util.Arrays;
import java.util.Objects;

import generic.Cell;
import generic.Node;
import generic.State;

public class AvengersState extends State {
	byte [] gridStatus;
	
	public AvengersState(byte [] gridStatus) {
		this.gridStatus = gridStatus;
	}
	
	public byte[] getGridStatus() {
		return this.gridStatus;
	}
	
	public Cell getIron() {
		return new Cell(this.gridStatus[0], this.gridStatus[1]);
	}
	
	public byte[] getStatus() {
		return Arrays.copyOfRange(this.gridStatus, 2, this.gridStatus.length);
	}
	
	private int countTrueStatus() {
		int count = 0;
		byte[] status = getStatus();
		for(int i = 2; i < status.length; i++) {
			if(status[i] == 1) {
				count++;
			}
		}
		return count;
	}

	@Override
	public int compareTo(State otherState) {
		AvengersState otherAvengerState = (AvengersState) otherState;
		int count = this.countTrueStatus();
		int otherCount = otherAvengerState.countTrueStatus();
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
			for(int i = 2; i < status.length; i++) {
				if(status[i] != otherStatus[i]) {
					if(status[i] == 0) {
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
        return Objects.hash(this.getIron().getX(),this.getIron().getY());
    }
	
	
	
}
