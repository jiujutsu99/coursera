/*************************************************************************
 * Name: Raymond Zhang
 * Email: rayzhang415@gmail.com
 *
 * Description: A program that examines 4 points at a time and checks whether
 * they all lie on the same line segment, printing out any such line segments 
 * to standard output and drawing them using standard drawing. 
 * 
 * The order of growth of the should be N^4 in the worst case and it should 
 * use space proportional to N.
 *
 *************************************************************************/
import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger
        
        // create std input object
        In inFile = new In(args[0]);
        int size = inFile.readInt();      // read number of points
        Point[] array = new Point[size];  // create an array of points
        
        // read in the input
        for (int i = 0; i < size; i++) {
            int x = inFile.readInt();
            int y = inFile.readInt();
            array[i] = new Point(x, y);
            array[i].draw();
        }
        
        // loop through every combination of 4 points and check if colinear
        for (int p = 0; p < size; p++) {
            for (int q = p+1; q < size; q++) {
                double pSlopeToq = array[p].slopeTo(array[q]);
                for (int r = q+1; r < size; r++) {
                    double pSlopeTor = array[p].slopeTo(array[r]);
                    // if these two slopes are not colinear then skip 4th loop
                    if (pSlopeToq == pSlopeTor) {
                        for (int s = r+1; s < size; s++) {
                            // check if the 4th point is colinear
                            if (pSlopeToq == array[p].slopeTo(array[s])) {
                                // sort the 4 points
                                Point[] sortPoints = new Point[4];
                                sortPoints[0] = array[p];
                                sortPoints[1] = array[q];
                                sortPoints[2] = array[r];
                                sortPoints[3] = array[s];
                                Arrays.sort(sortPoints);
                                
                                // print out the 4 points in order
                                StdOut.print(sortPoints[0].toString() + " -> " + sortPoints[1].toString() + " -> ");
                                StdOut.println(sortPoints[2].toString() + " -> " + sortPoints[3].toString());
                                
                                // draw and connect the 4 points
                                sortPoints[0].drawTo(sortPoints[3]);
                            }
                        }
                    }
                }
            }
        }
    }
}