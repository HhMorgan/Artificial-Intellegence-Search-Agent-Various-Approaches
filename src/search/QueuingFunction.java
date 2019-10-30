package search;

import java.util.ArrayList;
import java.util.Collection;
import generic.Node;


public abstract class QueuingFunction {
	// The data structure for the search strategy is abstracted.
	Collection<Node> queue;
	// Adds all elements that are generated in the queue.
	public abstract void add(ArrayList<Node>  nodes);
	// Adds one element to the queue.
	public abstract void add(Node  node);
	// Removes an element from the queue.
	public abstract Node remove();
	// Checks if the queue is empty.
	public abstract boolean isEmpty();

}
