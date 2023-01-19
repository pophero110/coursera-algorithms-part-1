import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private Node prevLast = null;
    private int count = 0;
    private class Node {
        Item item;
        Node next;
        public boolean isEmpty()
        {
            return first == null;
        }
        public void push(Item item) {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
        }
        public Item pop() {
            Item item = first.item;
            first = first.next;
            return item;
        }
    }
    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty(){
        return count == 0;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item){
        if(item == null) throw new IllegalArgumentException();
        Node newFirst = new Node();
        newFirst.item = item;
        newFirst.next = first;
        count++;
        if(first == null) last = newFirst;
        first = newFirst;
    }

    // add the item to the back
    public void addLast(Item item) {
        if(item == null) throw new IllegalArgumentException();
        Node newLast = new Node();
        newLast.item = item;
        newLast.next = null;
        count++;
        if(last == null) {
            first = newLast;
        } else {
            last.next = newLast;
        }
        prevLast = last;
        last = newLast;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if(count == 0) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        count--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if(count == 0) throw new NoSuchElementException();
        Item item = last.item;
        last = prevLast;
        last.next = null;
        count--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() { return current != null; };
        public void remove() { throw new UnsupportedOperationException(); };
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque deque = new Deque();
        deque.addLast('1');
        deque.addLast('2');
        deque.addLast('3');
        deque.addLast('4');
        deque.addFirst('8');
        deque.addLast('9');
        for (Object s : deque
             ) {
            deque.removeLast();
            System.out.println(s);
        }
        deque.removeLast();
        deque.removeLast();
    }
}