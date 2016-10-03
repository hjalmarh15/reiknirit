package d0;

import edu.princeton.cs.algs4.StdOut;

public class Tester {
	public static void main(String[] args) {
		int sum = 0;
		int n = 3000;
		
		for (int i=0; i < n; i++) {
		        for (int j=0; j < i; j+=2) {
		           sum++;
		        }
		}
		
		StdOut.println(sum);
	}
}
