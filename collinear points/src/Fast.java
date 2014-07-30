/*************************************************************************
 * Name: Raymond Zhang
 * Email: rayzhang415@gmail.com
 * 
 * A faster, sorting-based solution than brute force. Given a point p, the 
 * following method determines whether p participates in a set of 4 or more 
 * collinear points.
 * 
 * Using p as the origin, for each other point q, determine the slope it makes
 * with p then sort the points according to the slopes they make with p.
 * Checks if any 3 (or more) adjacent points in the sorted order have equal
 * slopes with respect to p. If so, these points, together with p, are collinear.
 * 
 * Applying this method for each of the N points in turn yields an efficient
 * algorithm to the problem. The algorithm solves the problem because points
 * that have equal slopes with respect to p are collinear, and sorting brings
 * such points together. The algorithm is fast because the bottleneck operation
 * is sorting.
 * 
 * The order of growth of the running time of the program should be N^2 log N 
 * in the worst case and should use space proportional to N.
 *
 *************************************************************************/
import java.util.Arrays;

public class Fast {
    
    public static void main(String[] args) {
        
        int n = 0;
        
        In inputFile;
        Point[] points;
        Point lastPoint = null;
        
        inputFile = new In(args[0]);
        
        // How many points do we have in the input file?
        n = inputFile.readInt();
        
        // Allocate enough space for them
        points = new Point[n];
        
        // Rescale coordinate system for proper visualization.
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        
        for (int i = 0; !inputFile.isEmpty(); i++) {
            int x = inputFile.readInt();
            int y = inputFile.readInt();
            
            points[i] = new Point(x, y);
        }
        
        Arrays.sort(points);
        
        Point[] sortedPoints = new Point[n];
        
        for (int i = 0; i < n; i++) {
            // Pick the origin
            Point p = points[i];
            
            p.draw();
            
            // Copy points
            System.arraycopy(points, 0, sortedPoints, 0, sortedPoints.length);
            
            // Sort all points acording that point
            Arrays.sort(sortedPoints, i, n, p.SLOPE_ORDER);
            
            int low = 0,
                high = 0;
                
            double lastSlope = p.slopeTo(sortedPoints[i]);
            
            for (int k = i + 1; k < n; k++) {
                
                double currentSlope = p.slopeTo(sortedPoints[k]);
                
                if (currentSlope == lastSlope) {
                    high++;
                } else {
                    if (high - low >= 2 && sortedPoints[high] != lastPoint) {
                        lastPoint = sortedPoints[high];
                        
                        StdOut.print(p);
                        
                        for (int j = low; j <= high; j++)
                            StdOut.print(" -> " + sortedPoints[j]);
                            
                        StdOut.println();
                        
                        p.drawTo(sortedPoints[high]);
                    }
                    
                    low = k;
                    high = k;
                    lastSlope = currentSlope;
                }
            }
            
            if (high - low >= 2 && sortedPoints[high] != lastPoint) {
                lastPoint = sortedPoints[high];
                
                StdOut.print(p);
                
                for (int j = low; j <= high; j++)
                    StdOut.print(" -> " + sortedPoints[j]);
                    
                StdOut.println();
                
                p.drawTo(sortedPoints[high]);
            }
        }
    }
}