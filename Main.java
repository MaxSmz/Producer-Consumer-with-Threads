import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Check if command line argument exists
        if (args.length < 1) {
            System.out.println("Please use: java Main <num_consumers>");
            return;
        }

        // Set buffer size, producer max, and number of consumers
        int bufferSize = 5;
        int producerMax = 20;
        int numConsumers = Integer.parseInt(args[0]);

        // Calculate consumer max for each consumer
        int base = producerMax / numConsumers;
        int remainder = producerMax % numConsumers;

        // Create CircularBuffer, Producer and Consumer objects
        CircularBuffer buffer = new CircularBuffer(bufferSize);
        Producer producer = new Producer(buffer, producerMax);
        List<Consumer> consumers = new ArrayList<>();
        for (int i = 0; i < numConsumers; i++) {
            int consumerMax = (i < remainder) ? (base + 1) : base;
            consumers.add(new Consumer(buffer, consumerMax));
        }

        // Create threads for Producer and Consumers
        Thread producerThread = new Thread(producer);
        List<Thread> consumerThreads = new ArrayList<>();
        for (Consumer consumer : consumers) {
            consumerThreads.add(new Thread(consumer));
        }

        // Start threads
        producerThread.start();
        for (Thread consumerThread : consumerThreads) {
            consumerThread.start();
        }

        // Wait for threads to complete
        try {
            producerThread.join();
            for (Thread consumerThread : consumerThreads) {
                consumerThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
