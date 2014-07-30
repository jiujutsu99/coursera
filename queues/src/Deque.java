/*----------------------------------------------------------------
 *  Author:        Raymond Zhang
 *  Written:       7/5/2014
 *  Last updated:  7/5/2014
 * 
 * A double-ended queue or deque that supports inserting and removing items
 * from either the front or the back of the data structure. Each deque operation
 * is in constant worst-case time and use space proportional to the number of
 * items currently in the deque.
 * 
 *----------------------------------------------------------------*/

import java.util.Iterator;  // need to import this for the Iterator interface
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;  // pointers at the front and end
    private int N;             // count the size of deque
    
    // construct an empty deque
    public Deque() {
        last = null;    // create pointers to an empty deque
        first = null;
        N = 0;          // initialize deque size counter to zero
    }
    
    /* check if the deque is empty
     * @return true if deque is empty, else false
     */
    public boolean isEmpty() {
        return N == 0;
    }
    
    /* return the number of items on the deque
     * @return size of deque
     */
    public int size() {
        return N;
    }
    
    /* insert the item at the front
     * @param item the item to add at the front of deque
     * @throws NullPointerException if try to add a null item
     */
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        Node temp = new Node(item);
        if (isEmpty())
            last = temp;
        else
        {
            temp.next = first;
            first.prev = temp;
        }
        first = temp;
        N++;
    }
    
    /* insert the item at the end
     * @param item the item to add at the end of deque
     * @throws NullPointerException if try to add a null item
     */
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        Node temp = new Node(item);
        if (isEmpty())
            first = temp;
        else
        {
            last.next = temp;
            temp.prev = last;
        }
        last = temp;
        N++;
    }
    
    /* delete and return the item at the front
     * @return item at the front of deque
     * @throws NoSuchElementException if deque is empty
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        if (first.next == null)
            last = null;
        else
            first.next.prev = null;
        Node temp = first;
        first = first.next;
        temp.item = null;
        temp.next = null;
        N--;
        return item;
    }
    
    /* delete and return the item at the end
     * @return item at the end of deque
     * @throws NoSuchElementException if deque is empty
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        if (last.prev == null)
            first = null;
        else
            last.prev.next = null;
        Node temp = last;
        last = last.prev;
        temp.item = null;
        temp.prev = null;
        N--;
        return item;
    }
    
    /* return an iterator over items in order from front to end
     * @return an iterator to the deque that iterates through the items from
     * front to end
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        
        public boolean hasNext() {
            return current != null;
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
        
        public Node() {
            this(null, null, null);
        }
        
        public Node(Item x) {
            this(x, null, null);
        }
        
        public Node(Item x, Node y, Node z) {
            item = x;
            next = y;
            prev = z;
        }
    }
    
    // unit testing
    public static void main(String[] args) {
        Deque<String> s = new Deque<String>();
        String[] str = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
        for (int i = 0; i < 10; i++) {
            s.addLast(str[i]);
        }
        for (int i = 0; i < 10; i++)
            StdOut.println(s.removeFirst() + " ");
        System.out.println("new line");
        for (int i = 0; i < 10; i++) {
            s.addFirst(str[i]);
        }
        for (int i = 0; i < 10; i++)
            StdOut.println(s.removeFirst() + " ");
    }
}