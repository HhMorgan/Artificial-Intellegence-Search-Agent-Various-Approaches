package artificialIntellegence;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class EndGame extends GenericSearch{
	
	public static Node BFS(Problem problem) {
		//int limit = 50;
		Queue<Node> nodes= new LinkedList<>(); 
		nodes.add(problem.initialState);
		problem.addState(problem.initialState);
		while (!nodes.isEmpty()) {
//			if(limit <= 0)
//				break;
			Node node = nodes.poll();
			//System.out.println(node);
			if (Problem.goalTest(node)) {
//				System.out.println("Hi?");
//				System.out.println("Goal" + node);
				return node;
			}
			ArrayList<Node> successorStates = Problem.transition(problem, node);
//			System.out.print("Transition : " );
//			for(Node n : nodes) { 
//				  System.out.print( n.toString() + "  |A|  "); 
//				}
//			System.out.println();
			
			int successorStatesLength = successorStates.size();
			for(int l = 0; l < successorStates.size(); l++) {
				if (Problem.isVisitedState(problem, successorStates.get(l))) {
					//System.out.println("REMOVED : "+successorStates.get(l));
//					successorStates.remove(0);
					successorStatesLength --;
					continue;
				} else {
					//System.out.println(successorStates.get(0));
					//System.out.println("ADDED : " + successorStates.get(0));
					//System.out.println("ADDED : " + successorStates.get(l));
					problem.addState(successorStates.get(l));
					nodes.add(successorStates.get(l));
					
					//limit--;
					//System.out.println(limit);
				}
			}
			if(successorStatesLength == 0) {
				problem.removeState(node);
			}
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
