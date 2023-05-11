import java.util.ArrayList;
import java.util.List;

public class Starter {
    private int bufferSize = 5;
    private int producerMax = 20;
    private CircularBuffer buffer;
    private Producer producer;
    private List<Consumer> consumers;

    private Starter() {
        buffer = new CircularBuffer(bufferSize);
        producer = new Producer(buffer, producerMax);
        consumers = new ArrayList<>();
    }

    private void runThread(int numConsumers) {
        Thread producerThread = startProducerThread();
        List<Thread> consumerThreads = startConsumerThreads(numConsumers);
        joinThread(producerThread,consumerThreads);
    }

    private Thread startProducerThread() {
        Thread producerThread = getProducerThread();
        producerThread.start();
        return producerThread;
    }

    private Thread getProducerThread() {
        return new Thread(producer);
    }

    private List<Thread> startConsumerThreads(int numConsumers) {
        List<Thread> consumerThreads = getConsumerThreads(numConsumers);
        for (Thread consumerThread : consumerThreads) {
            consumerThread.start();
        }
        return consumerThreads;
    }

    private List<Thread> getConsumerThreads(int numConsumers) {
        appendConsumer(numConsumers);
        List<Thread> consumerThreads = new ArrayList<>();
        for (Consumer consumer : consumers) {
            consumerThreads.add(new Thread(consumer));
        }
        return consumerThreads;
    }

    private void appendConsumer(int numConsumers) {
        int base = producerMax / numConsumers;
        int remainder = producerMax % numConsumers;
        for (int i = 0; i < numConsumers; i++) {
            int consumerMax = (i < remainder) ? (base + 1) : base;
            consumers.add(new Consumer(buffer, consumerMax));
        }
    }

    private void joinThread(Thread producerThread, List<Thread> consumerThreads) {
        try {
            producerThread.join();
            for (Thread consumerThread : consumerThreads) {
                consumerThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void checkArg(String[] args) throws Exception {
        if (args.length < 1) {
            throw new InvalidArgNumException("Please use: java Main <num_consumers>");
        }
    }

    public static void main(String[] args) throws Exception {
        checkArg(args);
        int numConsumers = Integer.parseInt(args[0]);
        Starter starter = new Starter();
        starter.runThread(numConsumers);
    }
}
