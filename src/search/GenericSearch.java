package search;

import java.util.Collection;
import generic.Node;


public abstract class GenericSearch {

	Collection<Node> queue;

	public abstract void add(Node node);

	public abstract Node remove();

	public abstract boolean isEmpty();

}
