/*----------------------------------------------------------------
 *  Author:        Raymond Zhang
 *  Written:       7/5/2014
 *  Last updated:  7/5/2014
 * 
 * A randomized queue where an item removed is chosen uniformly at random from
 * items in the data structure. Each randomized queue operation is in constant
 * amortized time and use space proportional to the number of items currently
 * in the queue. 
 * 
 *----------------------------------------------------------------*/

import java.util.Iterator;  // need to import this for the Iterator interface
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int N;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[1];
        N = 0;
    }

    // is the queue empty? rand.uniform(10)
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the queue
    public int size() {
        return N;
    }
    
    // insert item at the end
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (N == s.length)
            resize(s.length*2);
        s[N] = item;
        N++;
    }
    
    // delete and return a random item, fill empty spot with element at end
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        if (N < s.length/4)
            resize(s.length/2);
        int num = StdRandom.uniform(N);
        Item item = s[num];
        s[num] = s[N-1];
        s[N-1] = null;
        N--;
        return item;
    }
    
    // return (but do not delete) a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        return s[StdRandom.uniform(N)];
    }
    
    // return an independent iterator over items in random order
    // creating two iterators from the same queue object must have items of different random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }
    
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++)
            copy[i] = s[i];
        s = copy;
    }
    
    private class ArrayIterator implements Iterator<Item> {
        private int i = N;
        private Item[] iter;
        
        public ArrayIterator() {
            iter = (Item[]) new Object[i]; // in order to randomize the iteration of the queue
            for (int j = 0; j < i; j++) {  // make a copy of original array in iterator then
                iter[j] = s[j];            // shuffle the elements
            }
            StdRandom.shuffle(iter);
        }
        
        public boolean hasNext() {
            return i > 0;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return iter[--i];
        }
    }
    
    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();

        for (int i = 0; i < 100; i++)
            queue.enqueue(i);
        
        Iterator<Integer> iter1 = queue.iterator();
        Iterator<Integer> iter2 = queue.iterator();
        
        for (int i = 0; i < 100; i++) {
            System.out.println(iter1.next() + ", " + iter2.next());
        }
    }
}