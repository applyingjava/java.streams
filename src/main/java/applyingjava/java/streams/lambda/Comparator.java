package applyingjava.java.streams.lambda;

import java.util.function.Function;

public interface Comparator<T> {
    int compare(T t1, T t2);

    default Comparator<T> thenCompare(Comparator<T> other) {
        return (t1, t2) -> {
            int res = compare(t1, t2);
            return res != 0 ? res : other.compare(t1, t2);
        };
    }

    static <U, V extends Comparable<? super V>> Comparator<U> comparing(
            Function<? super U, ? extends V> function) {
        return (t1, t2) -> function.apply(t1).compareTo(function.apply(t2));
    }

}
