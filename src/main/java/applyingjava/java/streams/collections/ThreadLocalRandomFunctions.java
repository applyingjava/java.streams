package applyingjava.java.streams.collections;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import applyingjava.java.streams.util.LOG;

public class ThreadLocalRandomFunctions {
    public static void main(String[] args) {
        IntStream streamOfInt = ThreadLocalRandom.current().ints();
        streamOfInt.limit(5).forEach(LOG.print::log);

        streamOfInt = ThreadLocalRandom.current().ints(10);
        streamOfInt.forEach(LOG.print::log);

        // unlimited stream
        streamOfInt = ThreadLocalRandom.current().ints(10, 25);
        streamOfInt.limit(5).forEach(LOG.print::log);
    }
}
