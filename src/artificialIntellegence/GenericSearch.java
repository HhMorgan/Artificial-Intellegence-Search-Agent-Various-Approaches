package artificialIntellegence;

import java.util.Comparator;
import java.util.PriorityQueue;

public abstract class GenericSearch {

	public static void search(Node problem, Comparator<Node> queuingFunction) {
		PriorityQueue<Node> nodes = new PriorityQueue<Node>(queuingFunction);
		nodes.add(problem);
		while (!nodes.isEmpty()) {
			Node node = nodes.poll();
			
		}

	}

}
