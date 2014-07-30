/*********************************************************************
 * Author: Raymond Zhang
 *  Execution:    java KdTree.java
 *  Dependencies: Point2D.java, RectHV.java, stdlib.jar, algs4.jar
 *  
 *  A mutable data type that uses a 2d-tree to implement the same API 
 *  as PointSET. A 2d-tree is a generalization of a BST to two-dimensional
 *  keys. The idea is to build a BST with points in the nodes, using 
 *  the x- and y-coordinates of the points as keys in strictly alternating
 *  sequence.
 * 
 * 
 *********************************************************************/

public class KdTree {
	
	private static class Node {
		   private Point2D p;      // the point
		   private RectHV rect;    // the axis-aligned rectangle corresponding to this node
		   private Node lb;        // the left/bottom subtree
		   private Node rt;        // the right/top subtree
		   private boolean isVertical = true;
		   
		   public Node(Point2D p, RectHV rect, Node n1, Node n2, boolean isVertical) {
			   this.p = p;
			   this.rect = rect;
			   this.lb = n1;
			   this.rt = n2;
			   this.isVertical = isVertical;
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
    	size++;
    	root = insert(root, null, p, true);
    }
    
    private Node insert(Node node, Node parentNode, Point2D p, boolean isVertical) {
    	if (isEmpty())
    		return new Node(p, new RectHV(0.0, 0.0, 1.0, 1.0), null, null, true);
        return null;
    }
    
    // does the set contain the point p?
    public boolean contains(Point2D p) {
    	return false;
    }
    
    // draw all of the points to standard draw
    public void draw() {
    	
    }
    
    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
    	return null;
    }
    
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
    	return p;
    }
    
    public static void main(String[] args) {
    	System.out.println("works #2");
    }
}