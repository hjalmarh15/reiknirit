package s1;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
	    
	    private int experiments; //number of experiments
	    private Percolation perco; //percolation class variable to run the tests
	    private double[] results; //array to hold the results of each test

	    
	    //perform T independent experiments on an N-by-N grid
	    public PercolationStats(int N, int T) {
	    	
	    	
	        if (N <= 0 || T <= 0) {
	            throw new IllegalArgumentException();
	        }
	        
	        experiments = T;
	        results = new double[experiments];
	        
	        //run the tests T times
	        for(int i = 0; i < experiments; i++) {
	        	perco = new Percolation(N);
	        	while(!perco.percolates()) {
	        		int x = StdRandom.uniform(0,N);
	        		int y = StdRandom.uniform(0, N);
	        		if(!perco.isOpen(x, y)) {
	        			perco.open(x, y);
	        		}
	        	}
	        	results[i] = (double) perco.numberOfOpenSites() / (N*N);
	        }
	    }
	
	    
	 
	 //sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(results);
	}
	
	
	//sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(results);
	}
	
	
	//high endpoint of 95% confidence interval
	public double confidenceHigh() {
		return mean() + (1.96*stddev()) / Math.sqrt(experiments);
	}
	
	
	//low endpoint of 95% confidence interval
	public double confidenceLow() {
		return mean() - (1.96*stddev()) / Math.sqrt(experiments);
	}
	
	
	//main function to test the class
	public static void main(String[] args) {
		
		//get N and T from user
		int N = StdIn.readInt();
		int T = StdIn.readInt();
		
		Stopwatch watch = new Stopwatch();
		//run T experiments on a Percolation class of size N
		PercolationStats stats = new PercolationStats(N, T);
		
		StdOut.println("Elapsed time: " + watch.elapsedTime());
		
		//print out results of the functions of the class
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = " + stats.confidenceLow() + ", " + stats.confidenceHigh());
	}
}