package search;

import java.util.ArrayList;
import java.util.LinkedList;

import generic.Node;


public class BFS extends GenericSearch{
	
		public BFS() {
			super.queue = new LinkedList<Node>();
		}

		@Override
		public void add(Node node) {
			((LinkedList<Node>) super.queue).add(node);
		}
		
		@Override
		public void add(ArrayList<Node>  nodes) {
			for(Node n:nodes) {
				((LinkedList<Node>) super.queue).add(n);
			}
		}

		@Override
		public Node remove() {
			return ((LinkedList<Node>) super.queue).removeFirst();
		}

		@Override
		public boolean isEmpty() {
			return super.queue.isEmpty();
		}

}
