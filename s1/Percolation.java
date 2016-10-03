package s1;


import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	//used to connect two sites
	private WeightedQuickUnionUF uf;
	
	private int size;
	
	//the array grid, true for open, false for closed
	private boolean[][] grid;
	
	//virtual top and bottom
	private int top;
	private int bottom;
	
	//number of open sites
	private int openSites;
	
	
	//create N-by-N grid, with all sites initially blocked
	public Percolation(int N) {
		if(N <= 0) {
			throw new IllegalArgumentException();
		}
		
		size = N;
		uf = new WeightedQuickUnionUF(N*N +2);	//+2 to accomodate virtual top and bottom
		grid = new boolean[N][N];
		
		top = N*N;			//virtual top and bottom are given N*N and N*N+1
		bottom = N*N + 1;	//so it is definitely not within the grid
		
	}
	
	
	//convert 2 dimensional xy coordinates to 1 dimensional number
	// so coordinate (1,2) should correspond to 5
	// so 1 * 3(size) + 2 = 5
	/*	
	  	| 0 | 1 | 2 |
		-------------
		| 3 | 4 | 5 |
		-------------
		| 6 | 7 | 8 | 
	*/
	private int xyTo1D(int row, int col) {
		checkIndex(row, col);
		return ((row * size) + col);
	}
	
	
	
	//throws exception if x or y is not between 0 and N-1
	private void checkIndex(int row, int col) {
		if(row < 0 || col < 0 || row > size-1 || col > size-1)
			throw new IndexOutOfBoundsException();
	}
	
	

	// open the site (row, col) if it is not already open
	public void open(int row, int col) {
		checkIndex(row, col);	//make sure the indexes are within bounds
		
		if(isOpen(row, col)) { 	//if it is already open, do nothing
			return;
		}
		
		grid[row][col] = true;
		openSites++;
		connectToOpenSites(row, col); //connect to the open sites surrounding the site
	
	}
	
	
	private void connectToOpenSites(int row, int col) {
		int index = xyTo1D(row, col);
		
		if(row == 0) {	//if the site is in the top row, connect to virtual top
			uf.union(top, index);
		}
		
		if(row == size-1) { // if the site is in the bottom row. connect to virtual bottom
			uf.union(bottom, index);
		}
		
		//connect left
		if(col -1 >= 0 && isOpen(row, col-1)) {
			uf.union(xyTo1D(row, col -1), index);
		}
		//connect right
		if(col+1 < size && isOpen(row, col+1)) {
			uf.union(xyTo1D(row, col+1), index);
		}
		
		//connect up
		if(row-1 >= 0 && isOpen(row-1, col)) {
			uf.union(xyTo1D(row-1, col), index);
		}
		
		//connect down
		if(row+1 < size && isOpen(row+1, col)) {
			uf.union(xyTo1D(row+1, col), index);
		}
	}
	
	
	
	//is the site (row, col) open?
	public boolean isOpen(int row, int col) {
		checkIndex(row, col); 
		return grid[row][col];
	}
	
	
	//is the site (row, col) full?
	public boolean isFull(int row, int col) {
		checkIndex(row, col);
		return uf.connected(xyTo1D(row, col), top); //if the site is connected to the
													//virtual top, it is full
	}
	
	
	//number of open sites
	public int numberOfOpenSites() {
		return openSites;
	}
	
	//does the system percolate?
	public boolean percolates() {
		return uf.connected(top, bottom);	//if virtual top is connected to
											//virtual bottom, the system percolates
	}
	
	
	//main function for testing
	public static void main(String[] args) {
		Percolation perc = new Percolation(3);
		
		//open also tests isOpen() function
		perc.open(0,0);
		if(perc.isOpen(0, 0)) {
			StdOut.println("grid[0][0] has been opened!");
		}
		perc.open(1,0);
		if(perc.isFull(1, 0)) {
			StdOut.println("grid[1][0] is full!");
		}
		perc.open(2,0);
		
		//should throw exception, commented out for submission
		//perc.open(3, 0);
		StdOut.println("Number of open sites: " + perc.numberOfOpenSites());
		
		if(perc.percolates()) {
			StdOut.println("percolates!!");
		}
		else {
			StdOut.println("does not percolate");
		}
	}
	
}