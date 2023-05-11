/**
 A Producer thread implementation that add items to a CircularBuffer until it reaches a maximum count.
 */
public class Producer implements Runnable {
    private CircularBuffer buffer; // The CircularBuffer to remove items from
    private int max; // The maximum count of items to add

    /**
     Constructs a Producer with the given buffer and the maximum number of integers to be produced.
     @param buffer the shared buffer
     @param max the maximum number of integers to be produced
     */
    public Producer(CircularBuffer buffer, int max) {
        this.buffer = buffer;
        this.max = max;
    }
    /**
     The run() method of this class is called when the thread is started.
     It generates a sequence of integers and writes them to the buffer until the maximum
     number of integers to be produced is reached.
     */
    @Override
    public void run() {
        for (int valueCnt = 0; valueCnt < max; valueCnt++) {
            try {
                buffer.append(valueCnt); // Add an item from the CircularBuffer
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}



