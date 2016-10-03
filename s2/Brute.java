package s2;

import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class Brute {
	
	static int N = 0;
	
	//check every possible connection between points on a line
	public static void sorter(Point[] points){
		for(int i = 0; i < N; i++){	
			for(int j = i + 1; j < N;j++){
				double slopeiToj = points[i].slopeTo(points[j]);
				for(int k = j + 1; k < N;k++){
					double slopeiTok = points[i].slopeTo(points[k]);
					if(slopeiToj == slopeiTok){
						for(int m = k + 1; m < N; m++){
							double slopeiTom = points[i].slopeTo(points[m]);
							if(slopeiToj == slopeiTom){
								StdOut.println(points[i] + " -> " + points[j] + " -> " + points[k] + " -> " + points[m]);
							}
						}
					}
				}
			}
		}
	}
	
	public static void main(String args[]){
		In in = new In();
        N = in.readInt();
        Point[] points = new Point[N];
        int n = 0;
        while (n < N) {
            int x = in.readInt();
            int y = in.readInt();
            points[n] = new Point(x, y);
            n++;
        }
        Stopwatch stpw = new Stopwatch();
        Arrays.sort(points);
        sorter(points);
   
        double time = stpw.elapsedTime();
		//StdOut.println("Time: " + time + " seconds");
	}
	
}