package search;

import java.util.ArrayList;
import java.util.Collection;
import generic.Node;


public abstract class QueuingFunction {
	// The data structure for the search strategy is abstracted.
	Collection<Node> queue;

	public abstract void add(ArrayList<Node>  nodes);
	public abstract void add(Node  node);
	public abstract Node remove();
	public abstract boolean isEmpty();

}
