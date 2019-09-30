package problemStatment;

import generic.Cell;
import generic.State;

public class AvengersState extends State {
	Cell iron;
	boolean [] status;
	public AvengersState(Cell iron, boolean [] status) {
		this.iron = iron;
		this.status = status;
	}
	
	public Cell getIron() {
		return this.iron;
	}
	
	public boolean [] getStatus() {
		return this.status;
	}
	
	private int countTrueStatus() {
		int count = 0;
		for(int i = 2; i < this.status.length; i++) {
			if(this.status[i]) {
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
		if(count != otherCount) {
			if(count < otherCount) {
				return -1;
			}
			else {
				return 1;
			}
		}
		if(this.iron.getX() == otherAvengerState.iron.getX() && iron.getY() == otherAvengerState.iron.getY()) {
			for(int i = 2; i < this.status.length; i++) {
				if(this.status[i] != otherAvengerState.status[i]) {
					if(this.status[i]) {
						return -1;
					}
					else {
						return 1;
					}
				}
			}
		}
		return 0;
	}

}
