/**
 A Consumer thread implementation that removes items from a CircularBuffer until it reaches a maximum count.
 */
public class Consumer implements Runnable {
    private CircularBuffer buffer; // The CircularBuffer to remove items from
    private int max; // The maximum count of items to remove

    public Consumer(CircularBuffer buffer, int max) {
        this.buffer = buffer;
        this.max = max;
    }

    @Override
    public void run() {
        for (int consumed = 0; consumed < max; consumed++) {
            try {
                int value = buffer.remove(); // Remove an item from the CircularBuffer
                System.out.println("Consumed: " + value); // Print the removed item
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}