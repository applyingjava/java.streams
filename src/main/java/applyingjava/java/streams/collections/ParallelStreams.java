package applyingjava.java.streams.collections;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

import applyingjava.java.streams.util.LOG;

public class ParallelStreams {
    public static void main(String[] args) throws IOException {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2");
        List<String> strings = new CopyOnWriteArrayList<>();
        Stream.iterate("+", s -> s + "+").parallel().limit(10)
                .peek(s -> LOG.print.log(s + " processed in the thread " + Thread.currentThread().getName()))
                .forEach(strings::add);

        Stream.iterate("+", s -> s + "+").limit(10)
                .peek(s -> LOG.print.log(s + " processed in the thread " + Thread.currentThread().getName()))
                .forEach(strings::add);
    }
}
