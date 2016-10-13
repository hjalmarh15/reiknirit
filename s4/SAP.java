package s4;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
	
	private Digraph graph;
	
	//constructor takes a digraph(not necessarily a DAG)
	public SAP(Digraph G) {
		checkIfAcyclic(G);
		checkIfRooted(G);
		graph = G;
	}
	
	
	//checks if the digraph is a DAG
	public void checkIfAcyclic(Digraph G) {
		DirectedCycle cycle = new DirectedCycle(G);
		if (cycle.hasCycle()) {
			throw new IllegalArgumentException("Graph is not acyclic");
		}
	}
	
	//check if the digraph is rooted
	public void checkIfRooted(Digraph G) {
		Bag<Integer> roots = new Bag<Integer>();
		for(int v = 0; v < G.V(); v++) {
			if(G.outdegree(v) == 0) {
				roots.add(v);
			}
		}
		if(roots.size() != 1) {
			throw new IllegalArgumentException("Graph is not rooted");
		}
	}
	// length of the shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) throws IndexOutOfBoundsException {
		//TODO: check range
		if(v < 0 || w < 0 || v >= graph.V() || w >= graph.V()) {
			throw new IndexOutOfBoundsException();
		}
		
		BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(graph, v);
		BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(graph, w);
		int ancestor = ancestor(v,w);
		if(ancestor == -1) {
			return -1;
		}
		else {
			return bfsV.distTo(ancestor) + bfsW.distTo(ancestor);
		}
	}
	
	// a shortest common common ancestor of v and w; -1 if no such path
	public int ancestor(int v, int w) {
		if(v < 0 || w < 0 || v >= graph.V() || w >= graph.V()) {
			throw new IndexOutOfBoundsException();
		}
		BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(graph, v);
		BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(graph, w);
		int ancestor = -1;
		int shortestPath = Integer.MAX_VALUE;
		Queue<Integer> ancestors = new Queue<Integer>();
		for(int i=0; i < graph.V(); i++ ) {
			if(bfsV.hasPathTo(i) && bfsW.hasPathTo(i)) {
				ancestors.enqueue(i);
			}
		}
		
		for(Integer i : ancestors) {
			if((bfsV.distTo(i) + bfsW.distTo(i)) <= shortestPath) {
				shortestPath = (bfsV.distTo(i) + bfsW.distTo(i));
				ancestor = i;
			}
		}
		return ancestor;
	}
	
	// length of shortest ancestral path of vertex subsets A and B; -1 if no such path
	public int length(Iterable <Integer > A, Iterable <Integer > B) {

		BreadthFirstDirectedPaths bfsA = new BreadthFirstDirectedPaths(graph, A);
		BreadthFirstDirectedPaths bfsB = new BreadthFirstDirectedPaths(graph, B);
		int ancestor = ancestor(A, B);
		if(ancestor == -1) {
			return -1;
		}
		else {
			return bfsA.distTo(ancestor) + bfsB.distTo(ancestor);
		}
	}
	
	
	// a shortest common ancestor of vertex subsets A and B; -1 if no such path
	public int ancestor(Iterable <Integer > A, Iterable <Integer > B) {
		int shortestPath = Integer.MAX_VALUE;
		Queue<Integer> ancestors = new Queue<Integer>();
		int ancestor = -1;
		
		BreadthFirstDirectedPaths bfsA = new BreadthFirstDirectedPaths(graph, A);
		BreadthFirstDirectedPaths bfsB = new BreadthFirstDirectedPaths(graph, B);
		
		for(int i = 0; i < graph.V(); i++) {
			if(bfsA.hasPathTo(i) && bfsB.hasPathTo(i)) {
				ancestors.enqueue(i);
				
			}
		}
		
		for(Integer i : ancestors ) {
			if((bfsA.distTo(i) + bfsB.distTo(i)) <= shortestPath) {
				shortestPath = 	bfsA.distTo(i) + bfsB.distTo(i);
				ancestor = i;
			}
		}
		return ancestor;
	}
	
	// do unit testing of this class
	public static void main(String[] args) {
		//new In stream
    	In in = new In();
    	Digraph graph = new Digraph(in);
    	
    	SAP sap = new SAP(graph);
    	
    	//number of tests
    	int N = in.readInt();
    	
    	//test for length of one v and one w
    	for(int i = 0; i < N; i++) {
    		int v = in.readInt();
    		int w = in.readInt();
    		StdOut.println("Length: " + sap.length(v, w) + ", ancestor: " + sap.ancestor(v, w));
    	}
    	 	
	}
}
