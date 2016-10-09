package s4;

import d4.BreadthFirstPaths;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
	
	private Digraph graph;
	
	//constructor takes a digraph(not necessarily a DAG)
	public SAP(Digraph G) {
		graph = G;
	}
	
	// length of the shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w) {
		//TODO: check range
		
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
		//TODO:check range
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
			if((bfsV.distTo(i) + bfsW.distTo(i)) < shortestPath) {
				shortestPath = (bfsV.distTo(i) + bfsW.distTo(i));
				ancestor = i;
			}
		}
		return ancestor;
	}
	
	// length of shortest ancestral path of vertex subsets A and B; -1 if no such path
	public int length(Iterable <Integer > A, Iterable <Integer > B) {
		//todo check if valid
		
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
			if((bfsA.distTo(i) + bfsB.distTo(i)) < shortestPath) {
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
    	
    	//new Digraph takes in In stream
    	Digraph graph = new Digraph(in);
    	
    	//number of test cases
    	int s = in.readInt();
    	for(int i = 0; i < s; i++) {
    		//first number of the set
	    	int v = in.readInt();
	    	//second number of the set
	    	int w = in.readInt();
	    	BreadthFirstPaths bfs = new BreadthFirstPaths(graph, v);
	    	
	    	//print number of shortest paths
	    	StdOut.println(bfs.nrOfPathsTo(w));
    	}
    	
	}
}
