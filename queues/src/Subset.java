/*----------------------------------------------------------------
 *  Author:        Raymond Zhang
 *  Written:       7/5/2014
 *  Last updated:  7/5/2014
 * 
 * A client program Subset.java that takes a command-line integer k; reads in 
 * a sequence of N strings from standard input using StdIn.readString(); and 
 * prints out exactly k of them, uniformly at random. Each item from the 
 * sequence can be printed out at most once.
 * 
 *----------------------------------------------------------------*/


public class Subset {
    public static void main(String[] args) {
        int num = Integer.parseInt(args[0]);
        RandomizedQueue<String> s = new RandomizedQueue<String>();
        while (!StdIn.isEmpty())
            s.enqueue(StdIn.readString());
        for (int i = 0; i < num; i++)
            System.out.println(s.dequeue());
    }
}