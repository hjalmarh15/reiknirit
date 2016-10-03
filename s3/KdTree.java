package s3;


import java.awt.Font;
import java.io.File;
import java.util.ArrayList;

/*************************************************************************
 *************************************************************************/



import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.In;

public class KdTree {
	private static class Node {
		private Point2D p; 			// the point
		private RectHV rect;		// axis-aligned rectangle corresponding to this node
		private Node lb;			// the left/bottom subtree
		private Node rt;			// the right/top subtree
		
		//constructor for the Node
		public Node(Point2D p, RectHV rect) {
			this.p = p;
			this.rect = rect;
		}

	}
	
	private Node root;
	private int size;
    // construct an empty set of points
    public KdTree() {
    	root = null;
    	size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
    	//if p is already in the set we do nothing
    	if(!contains(p)) {
    		//if root is null(tree is empty) insert at the root
	    	if(root == null) {
	    		root = new Node(p, new RectHV(0, 0, 1, 1));
	    		size++;
	    	}
	    	//call insert with the root were vertical is true
	    	else {
	    		insert(root, p, true);
	    	}
    	}
    }
    
    //helper function to add a new LB node
    private Node newLB(Node node, Point2D p, boolean vertical) {
    	if(vertical) {
    		return new Node(p, new RectHV(node.rect.xmin(),
					 node.rect.ymin(),
					 node.p.x(),
					 node.rect.ymax()));
    	}
    	else {
    		return new Node(p, new RectHV(node.rect.xmin(),
					 node.rect.ymin(),
					 node.rect.xmax(), 
					 node.p.y()));
    	}
    }
    
    
    //helper function to add a new RT node
    private Node newRT(Node node, Point2D p, boolean vertical) {
    	if(vertical) {
    		return new Node(p, new RectHV(node.p.x(),
					 node.rect.ymin(),
					 node.rect.xmax(),
					 node.rect.ymax()));
    	}
    	else {
    		return new Node(p, new RectHV(node.rect.xmin(),
					 node.p.y(),
					 node.rect.xmax(), 
					 node.rect.ymax()));
    	}
    }
    
    //helper function to recursively insert a new Node
    private void insert(Node node, Point2D p, boolean vertical) {
    	if(p.compareTo(node.p) != 0) {
    		if(vertical) {
    			//Red nodes
    			if(p.x() < node.p.x()) {
    				//check left subtree
    				if(node.lb != null) {
    					//recursively insert until we are at a leaf
    					insert(node.lb, p, !vertical);
    				}
    				else {
    					//add a new node if node is null
    					node.lb = newLB(node, p, vertical);
    					size++;
    				}
    			}
    			else {
    				//check right subtree
    				if(node.rt != null) {
    					//recursively insert until we are at a leaf
    					insert(node.rt, p, !vertical);
    				}
    				else {
    					//add a new node if node is null
    					node.rt = newRT(node, p, vertical);
    					size++;
    				}
    			}
    		}
    		else {
    			//blue nodes
    			if(p.y() < node.p.y()) {
    				//check left subtree
    				if(node.lb != null) {
    					//recursively insert until we are at a leaf
    					insert(node.lb, p, !vertical);
    				}
    				else {
    					//add a new node if node is null
    					node.lb = newLB(node, p, vertical);
    					size++;
    				}
    			}
    			else {
    				//check right subtree
    				if(node.rt != null) {
    					//recursively insert until we are at a leaf
    					insert(node.rt, p, !vertical);
    				}
    				else {
    					//add a new node if node is null
    					node.rt = newRT(node, p, vertical);
    					size++;
    				}
    			}
    		}
    	}
    	
    	
    }

    
    
    // does the set contain the point p?
    public boolean contains(Point2D p) {
    	return contains(root, p, true);
	}

    
    //helper function to recursively check if the tree contains p
    private boolean contains(Node node, Point2D p, boolean vertical) {
    	if(node == null) {
    		return false;
    	}
    	
    	if(node.p.equals(p)) {
    		return true;
    	}
    	
    	if(vertical) {
    		if(p.x() < node.p.x()) {
    			return contains(node.lb, p, !vertical);
    		}
    		else {
    			return contains(node.rt, p, !vertical);
    		}
    	}
    	else {
    		if(p.y() < node.p.y()) {
    			return contains(node.lb, p, !vertical);
    		}
    		else {
    			return contains(node.rt, p, !vertical);
    		}
    	}
    }
    
    
    // draw all of the points to standard draw
    public void draw() {
    	StdDraw.clear();
    	StdDraw.rectangle(0.5,0.5,0.5, 0.5);
    	draw(root, true);
    	StdDraw.show();
	}
    
    
    //helper function to recursively draw the points to standard draw
    private void draw(Node node, boolean vertical) {
    	if(node != null) {
    		StdDraw.setPenColor(StdDraw.BLACK);
    		StdDraw.setPenRadius(.01);
    		StdDraw.point(node.p.x(), node.p.y());
    		StdDraw.setFont(new Font("TimesRoman", Font.PLAIN, 12));
    		StdDraw.text(node.p.x() + 0.01, node.p.y() + 0.01, "(" + (node.p.x()) + ", " + (node.p.y())  + ")");
    		if(vertical) {
    			StdDraw.setPenColor(StdDraw.RED);
    			StdDraw.setPenRadius();
    			StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x() , node.rect.ymax());
    			draw(node.lb, !vertical);
    			draw(node.rt, !vertical);
			}
    		else {
    			StdDraw.setPenColor(StdDraw.BLUE);
    			StdDraw.setPenRadius();
    			StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
    			draw(node.lb, !vertical);
    			draw(node.rt, !vertical);
    		}
    	}
    }
    
    
    
    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> pointsInRange = new ArrayList<Point2D>();
        range(root, rect, pointsInRange);
        return pointsInRange;
    }
    
    
    //helper function to recursively find all the points inside the rectangle rect
    private void range(Node node, RectHV rect, ArrayList<Point2D> points) {
    	if(node != null) {
    		//if a node is found in the rect add it to the list
    		if(rect.contains(node.p)) {
    			points.add(node.p);
    		}
    		//if node rectangles intersect with rect, look left and right
    		if(node.lb != null && rect.intersects(node.lb.rect)) {
    			range(node.lb, rect, points);
    		}
    		if(node.rt != null && rect.intersects(node.rt.rect)) {
    			range(node.rt, rect, points);
    		}
    	}
    }

    
    
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
    	if(root != null) {
    		return nearest(root, p, root.p);
    	}
    	return null;
    }
    
    
    //helper function to recursively find nearest neighbor to p
    private Point2D nearest(Node node, Point2D p, Point2D nearest) {
    	Point2D newNearest = null;
    	if(node != null) {
    		if(p.distanceSquaredTo(nearest) <= p.distanceSquaredTo(node.p)) {
    			newNearest = nearest;
    		}
    		else {
    			newNearest = node.p;
    		}
    		
    		//check left subtree for newNearest.
    		if(node.lb != null && (p.distanceSquaredTo(newNearest)) > node.lb.rect.distanceSquaredTo(p)) {
    			newNearest = nearest(node.lb, p, newNearest);
    		}
    		
    		//check right subtree for newNearest
    		if(node.rt != null && (p.distanceSquaredTo(newNearest)) > node.rt.rect.distanceSquaredTo(p)) {
    			newNearest = nearest(node.rt, p, newNearest);
    		}
    	}
    	
    	return newNearest;
    }
    
    
    
    
    /*******************************************************************************
     * Test client
     ******************************************************************************/
	public static void main(String[] args) {
		
		//tester for nearest neighbor search
		File file = new File("/Users/hjelmut/Downloads/SomeInputs/circle10.txt");
		In in = new In(file);
		int N = 10;
		KdTree tree = new KdTree();
		
		for(int i=0; i < N; i++) {
			tree.insert(new Point2D(in.readDouble(), in.readDouble()));
		}
		/*
		int counter = 0;
		Stopwatch watch = new Stopwatch();
		while(watch.elapsedTime() < 10) {
			tree.nearest(new Point2D(StdRandom.uniform(), StdRandom.uniform()));
			counter++;
		}
		
		System.out.println("Nearest calls in 10 seconds: " + counter);
		*/
		tree.draw();
	}
}