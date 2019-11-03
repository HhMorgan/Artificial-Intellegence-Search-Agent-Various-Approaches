package search;

import java.util.ArrayList;
import java.util.Collection;
import generic.Node;


public abstract class QueuingFunction {
	/*
	 * The queuing function discussed in the lecture is implemented as an abstract class that abstracts the data structure,
	 * and functions that are used for each of the search  strategies. Each  search strategy requires to add a node, remove 
	 * a node, and check if the queue is empty. In addition,  the add functionality has  an additional function which takes 
	 * all nodes added to the queue in an $ArrayList$  to add them in the  appropriate order.  As for the isEmpty function, 
	 * the isEmpty function for data structure used for the search strategy is invoked.
	 */
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
