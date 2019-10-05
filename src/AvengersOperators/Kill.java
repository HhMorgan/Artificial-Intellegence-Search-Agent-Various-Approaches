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
//		System.out.println("currentIndex : " + currentIndex);
		Arrays.sort(this.warriorLocations);
//		System.out.println( this.warriorLocations[currentIndex] );
//		System.out.println(((AvengersState) node.getState()).getIron());
//		System.out.println("Kill Length : " + this.warriorLength);
		byte[] SuccessorGridStatus = new byte[((AvengersState) node.getState()).getGridStatus().length - this.warriorLength];
//		System.out.println("Kill Array : " + Arrays.toString(warriorLocations));
		for (int k = 0; k < ((AvengersState) node.getState()).getGridStatus().length; k++) {
			if (currentIndex < this.warriorLocations.length  && this.warriorLocations[currentIndex] == ((AvengersState) node.getState()).getGridStatus()[k]) {
				currentIndex++;
			}
			else {
				SuccessorGridStatus[successorGridStatusIndex] = ((AvengersState) node.getState()).getGridStatus()[k];
				successorGridStatusIndex ++;
//				System.out.print(k + ", ");
			}
			
		}
		//System.out.println();
		int inflicted = problem.pathCost(((AvengersState) node.getState()).getIron(),
				((AvengersState) node.getState()).getStatus()) + this.getCost();
		int dmgSuccessor = node.getDmg() + inflicted;
		int costSuccessor = node.getCost() + this.getCost();
		AvengersNode successorState = new AvengersNode(SuccessorGridStatus, dmgSuccessor, this.getName(), costSuccessor,
				node.getDepth() + 1, node);
		return successorState;
//		return null;
	}

}
 
