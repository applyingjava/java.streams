package applyingjava.java.streams.collections;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import applyingjava.java.streams.util.LOG;

public class StreamFunctions {
    public static void main(String[] args) {

        List<Integer> ints = Arrays.asList(0, 1, 2, 3, 4);

        Stream<Integer> stream1 = ints.stream();
        Stream<Integer> stream = Stream.of(0, 1, 2, 3, 4);

        stream1.forEach(LOG.print::log);
        stream.forEach(LOG.print::log);

        Stream<String> streamOfStrings = Stream.generate(() -> "one");
        streamOfStrings.limit(5).forEach(LOG.print::log);

        Stream<String> streamOfStrings2 = Stream.iterate("+", s -> s + "+");
        streamOfStrings2.limit(5).forEach(LOG.print::log);
    }
}
