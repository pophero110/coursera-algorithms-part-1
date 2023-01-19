public class RandomizedQueue<Item> implements Iterable<Item> {

    private int count = 0;
    private Node last = null;
    private Node first = null;
    private class Node {
        Item item;
        Node next;
    }
    // construct an empty randomized queue
    public RandomizedQueue(){}

    // is the randomized queue empty?
    public boolean isEmpty(){
        return count == 0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return count;
    }

    // add the item
    public void enqueue(Item item){
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if(isEmpty()) first = last;
        else oldlast.next = last;
        count++;
    }

    // remove and return a random item
    public Item dequeue(){
        Item item = first.item;
        first = first.next;
        if(isEmpty()) last = null;
        count--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {}

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {}

    // unit testing (required)
    public static void main(String[] args) {}

}