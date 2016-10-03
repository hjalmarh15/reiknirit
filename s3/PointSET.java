package s3;


import java.io.File;

/****************************************************************************
 *  Compilation:  javac PointSET.java
 *  Execution:    
 *  Dependencies:
 *  Author:
 *  Date:
 *
 *  Data structure for maintaining a set of 2-D points, 
 *    including rectangle and nearest-neighbor queries
 *
 *************************************************************************/

import java.util.Arrays;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;

public class PointSET {
	
	private SET<Point2D> tree;
	
    // construct an empty set of points
    public PointSET() {
    	tree = new SET<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    // number of points in the set
    public int size() {
        return tree.size();
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
    	if(!tree.contains(p)) {
    		tree.add(p);
    	}
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return tree.contains(p);
    }

    // draw all of the points to standard draw
    public void draw() {
    	StdDraw.setPenColor(StdDraw.BLACK);
    	StdDraw.setPenRadius(.01);
    	for(Point2D p : tree) {
    		p.draw();
    	}
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        SET<Point2D> pointsInRange = new SET<Point2D>();
        for(Point2D p : tree) {
        	if(rect.contains(p)) {
        		pointsInRange.add(p);
        	}
        }
        
        return pointsInRange;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        double max = 1;
        Point2D nearestPoint = null;
        for(Point2D currentPoint : tree) {
        	double distance = p.distanceSquaredTo(currentPoint);
        	if(distance < max) {
        		max = distance;
        		nearestPoint = currentPoint;
        	}
        }
        return nearestPoint;
    }

    public static void main(String[] args) {
    	
    	//tester for nearest neighbor search
    	File file = new File("/Users/hjelmut/Documents/1m.txt");
		In in = new In(file);
		int N = 1000000;
		PointSET tree = new PointSET();
		for(int i=0; i < N; i++) {
			tree.insert(new Point2D(in.readDouble(), in.readDouble()));
		}
		int counter = 0;
		Stopwatch watch = new Stopwatch();
		while(watch.elapsedTime() < 10) {
			tree.nearest(new Point2D(StdRandom.uniform(), StdRandom.uniform()));
			counter++;
		}
		
		System.out.println("Nearest calls in 10 seconds: " + counter);
	}

}
