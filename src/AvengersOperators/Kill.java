package AvengersOperators;

import java.util.Arrays;

import Avengers.AvengersNode;
import Avengers.AvengersState;
import generic.Problem;

public class Kill extends GeneralLookUpInspection {

	private int[] warriorLocations;
	private int warriorLength;

	public Kill(int[] warriorLocations, int warriorLength) {
		super("Kill", 2);
		this.warriorLocations = warriorLocations;
		this.warriorLength = warriorLength;
	}

	@Override
	public AvengersNode transition(Problem problem, AvengersNode node) {
		int currentIndex =  this.warriorLocations.length - this.warriorLength;
		int successorGridStatusIndex = 0;
		Arrays.sort(this.warriorLocations);
		byte[] SuccessorGridStatus = new byte[((AvengersState) node.getState()).getGridStatus().length - this.warriorLength];
		for (int k = 0; k < ((AvengersState) node.getState()).getGridStatus().length; k++) {
			if (currentIndex < this.warriorLocations.length  && this.warriorLocations[currentIndex] == ((AvengersState) node.getState()).getGridStatus()[k]) {
				currentIndex++;
			}
			else {
				SuccessorGridStatus[successorGridStatusIndex] = ((AvengersState) node.getState()).getGridStatus()[k];
				successorGridStatusIndex ++;
			}
			
		}
		int inflicted = problem.pathCost(((AvengersState) node.getState()).getIron(),
				((AvengersState) node.getState()).getStatus()) + this.getCost();
		int dmgSuccessor = node.getDmg() + inflicted;
		int costSuccessor = node.getCost() + this.getCost();
		AvengersNode successorState = new AvengersNode(SuccessorGridStatus, dmgSuccessor, this.getName(), costSuccessor,
				node.getDepth() + 1, node);
		return successorState;
	}

}
 
