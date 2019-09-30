package search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import generic.Node;
import problemStatment.EndGame;

public class SearchInvoker extends GenericSearch{
	
	public static Node BFS(EndGame problem) {
		//int limit = 30;
		Queue<Node> nodes= new LinkedList<>(); 
		nodes.add(problem.getInitialState());
		problem.addState(problem.getInitialState().getState());
		while (!nodes.isEmpty()) {
//			if(limit <= 0)
//				break;
			Node node = nodes.poll();
			//System.out.println(node);
			if (problem.goalTest(node)) {
//				System.out.println("Hi?");
//				System.out.println("Goal" + node);
				return node;
			}
			ArrayList<Node> successorStates = problem.transition(node);
//			System.out.print("Transition : " );
//			for(Node n : nodes) { 
//				  System.out.print( n.toString() + "  |A|  "); 
//				}
//			System.out.println();
			
			//int successorStatesLength = successorStates.size();
			while(!successorStates.isEmpty()) {
			//for(int l = 0; l < successorStates.size(); l++) {
				if (problem.isVisitedState(successorStates.get(0))) {
					//System.out.println("REMOVED : "+successorStates.get(0));
					successorStates.remove(0);
					//successorStatesLength --;
					continue;
				} else {
					//System.out.println(successorStates.get(0));
					//System.out.println("ADDED : " + successorStates.get(0));
					//System.out.println("ADDED : " + successorStates.get(l));
					problem.addState(successorStates.get(0).getState());
					nodes.add(successorStates.remove(0));
					
					//limit--;
					//System.out.println(limit);
				}
			}
//			if(successorStatesLength == 0) {
//				problem.removeState(node.getState());
//			}
		}
		return null;
	}

	public static Node DFS() {
		return null;
	}

	public static Node IDS() {
		return null;
	}

	public static Node UCS() {
		return null;
	}

	public static Node GS() {
		return null;
	}

	public static Node AS() {
		return null;
	}
}
