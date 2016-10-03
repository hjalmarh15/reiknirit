package d0;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;

public class StandardDeviation {
	public static void main(String[] args) {
		
		int n = StdIn.readInt();
		
		double arr[] = new double[n];
		
		for(int i = 0; i < n; i++) {
			arr[i] = StdIn.readDouble();
		}
		
		double dev = StdStats.stddevp(arr);
		
		StdOut.println(dev);
		
		
	}
}
