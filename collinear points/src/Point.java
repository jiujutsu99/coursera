/*************************************************************************
 * Name: Raymond Zhang
 * Email: rayzhang415@gmail.com
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope to this point
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();       // YOUR DEFINITION HERE
    
    private class SlopeOrder implements Comparator<Point> {
        public int compare(Point a, Point b) {
            double slopeA = slopeTo(a);
            double slopeB = slopeTo(b);
            if (slopeA < slopeB)
                return -1;
            if (slopeA > slopeB)
                return 1;
            return 0;
        }
    }

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // construct the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (that == null) throw new NullPointerException();
        if (that.x == this.x) {
            if (that.y == this.y)               // degenerate line segment (a point and itself)
                return Double.NEGATIVE_INFINITY;
            else                                // vertical line segment
                return Double.POSITIVE_INFINITY;
        }
        if (that.y == this.y)    // horizontal line segment
            return 0.0;
        return (double) (that.y - this.y)/(that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y < that.y || this.y == that.y && this.x < that.x)
            return -1;
        if (this.y == that.y && this.x == that.x)
            return 0;
        return 1;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        Point p = new Point(1, 1);
        Point q = new Point(4, 4);
        StdOut.println(p.slopeTo(q));
    }
}