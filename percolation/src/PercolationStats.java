/**----------------------------------------------------------------
 *  Author:        Raymond Zhang
 *  Written:       6/25/2014
 *  Last updated:  6/25/2014
 *
 *  Compilation:   javac PercolationStats.java
 *  Execution:     java PercolationStats
 *  
 *  Perform a series of computational experiments with Percolation
 *  Prints mean, stddev, and 95% confidence interval
 *
 *----------------------------------------------------------------*/

public class PercolationStats {
   private Percolation perc;
   private double[] counter;
   private int totalruns;
   
   public PercolationStats(int N, int T) {    // perform T independent computational experiments on an N-by-N grid
     if (N <= 0 || T <= 0) throw new IllegalArgumentException("T and N cannot be less than 1");
     totalruns = T;
     counter   = new double[T];
     for (int k = 0; k < T; k++) {
       //int i, j;
       perc       = new Percolation(N);
       counter[k] = 0;
       while (!perc.percolates()) {
         int i = StdRandom.uniform(N)+1;  // set random row i
         int j = StdRandom.uniform(N)+1;  // set random column j
         if (!perc.isOpen(i, j)) {
           perc.open(i, j);
           counter[k]++;
         }
       }
       counter[k] = counter[k]/(N*N);
     }
   }

   public double mean() {                   // sample mean of percolation threshold
     return StdStats.mean(counter);
   }

   public double stddev() {                 // sample standard deviation of percolation threshold
     return StdStats.stddev(counter);
   }

   public double confidenceLo() {           // returns lower bound of the 95% confidence interval
     return mean()-((1.96*stddev())/Math.sqrt(totalruns));
   }
   
   public double confidenceHi() {           // returns upper bound of the 95% confidence interval
     return mean()+((1.96*stddev())/Math.sqrt(totalruns));
   }

   public static void main(String[] args) {   // test client, described below
     PercolationStats percstats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
     System.out.println("Mean: " + percstats.mean() + "\nStddev: " + percstats.stddev());
     System.out.println("95% confidence interv: " + percstats.confidenceLo() + ", " + percstats.confidenceHi());
   }
}