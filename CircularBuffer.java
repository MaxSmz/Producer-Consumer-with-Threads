/**
 Circular buffer class that supports appending and removing integers in a synchronized manner.
 */
public class CircularBuffer {
    private int[] buffer; // buffer to hold integers
    private int head; // index of the first item in the buffer
    private int tail; // index of the next free slot in the buffer
    private int size; // size of the buffer

    /**
     Constructor for the CircularBuffer class.
     @param size the size of the buffer
     */
    public CircularBuffer(int size) {
        buffer = new int[size]; // create the buffer
        head = 0; // initialize the head
        tail = 0; // initialize the tail
        this.size = size; // set the size
    }
    /**
     Appends an integer to the buffer.
     @param value the integer to append
     @throws InterruptedException if the thread is interrupted while waiting
     */
    public synchronized void append(int value) throws InterruptedException {
        // wait until there is space in the buffer
        while ((tail + 1) % size == head) {
            wait();
        }
        // append the value to the buffer
        buffer[tail] = value;
        // update the tail index
        tail = (tail + 1) % size;
        // notify all waiting threads that the buffer has been updated
        notifyAll();
    }
    /**
     Removes an integer from the buffer.
     @return the integer removed from the buffer
     @throws InterruptedException if the thread is interrupted while waiting
     */
    public synchronized int remove() throws InterruptedException {
        // wait until there is an item in the buffer
        while (head == tail) {
            wait();
        }
        // remove the value from the buffer
        int value = buffer[head];
        // update the head index
        head = (head + 1) % size;
        // notify all waiting threads that the buffer has been updated
        notifyAll();
        // return the value that was removed
        return value;
    }
}