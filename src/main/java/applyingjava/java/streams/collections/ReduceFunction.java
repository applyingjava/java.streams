package applyingjava.java.streams.collections;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;

import applyingjava.java.streams.util.LOG;

public class ReduceFunction {
    // Associative? Not associative?
    // reduce method might not be associative
    private static int reduce(List<Integer> values, BinaryOperator<Integer> reduction) {
        int result = 0;
        for (int value : values) {
            result = reduction.apply(result, value);
        }
        return result;
    }

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(0, 1, 2, 3, 4, -1, -2, -3, -4);
        List<Integer> list1 = Arrays.asList(0, 1, 2, 3, 4);
        List<Integer> list2 = Arrays.asList(-1, -2, -3, -4);

        BinaryOperator<Integer> op = Integer::max;

        int reduction1 = reduce(list1, op);
        int reduction2 = reduce(list2, op);

        LOG.print.log("Reduction : " + reduce(list, op));
        LOG.print.log("Reduction1 : " + reduction1);
        LOG.print.log("Reduction2 : " + reduction2);
        LOG.print.log("Reduction3 : " + reduce(Arrays.asList(reduction1, reduction2), op));
    }
}
