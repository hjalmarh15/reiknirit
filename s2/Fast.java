package s2;

import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Fast {
	
   private static void findLines(Point[] points) {
	   
	   Point p = points[0];
	   
	   //create new array for points on a line
	   Point[] lines = new Point[points.length];
	   lines[0] = p;
	   //counter for number of points on a line
	   int pointsOnLine = 0;
	   
	   //slope from the previous point
	   double lastSlope = p.slopeTo(points[1]);
	   for(int i = 1; i < points.length; i++) {
		   //check next point
		   Point newPoint = points[i];
		   double slope = p.slopeTo(newPoint);
		   
		   //if they have the same slope, add the point
		   //to the line.
		   if(slope == lastSlope) {
			   pointsOnLine++;
			   lines[pointsOnLine] = newPoint;
		   }
		   else {
			   //print the line if there are enough
			   //points on the line
			   if(pointsOnLine >= 3) {
				   printLine(lines, pointsOnLine + 1);
			   }
			   pointsOnLine = 1;
			   lines[1] = newPoint;
		   }
		   lastSlope = slope;
	   }
	   
	   if(pointsOnLine >= 3) {
		   printLine(lines, pointsOnLine + 1);
	   }
   }
   
   private static void printLine(Point[] lines, int size) {
	   Arrays.sort(lines, 1, size);
	   //print the points on the line in format
	   // point -> point -> point -> point
	   if(lines[0].compareTo(lines[1]) < 0) {
		   StdOut.print(lines[0]);
		   for(int i = 1; i < size; i++) {
			   StdOut.print(" -> " + lines[i]);
		   }
		   StdOut.println();
	   }
   }
   
   public static void main(String[] args) {
	   In in = new In();
	   int N = in.readInt();
	   Point[] points = new Point[N];
	   for(int i=0; i< N; i++) {
		   int x = in.readInt();
		   int y = in.readInt();
		   points[i] = new Point(x,y);
	   }
	   in.close();
	   Stopwatch watch = new Stopwatch();
	   Point [] pointsCopy = Arrays.copyOf(points, points.length);
	   for(Point startPoint : points) {
           Arrays.sort(pointsCopy, startPoint.SLOPE_ORDER);
           findLines(pointsCopy);
       }
	   
	   
	   //StdOut.println("Time: " +watch.elapsedTime() + " seconds");
   }
}
