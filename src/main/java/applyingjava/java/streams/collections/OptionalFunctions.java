package applyingjava.java.streams.collections;

import java.util.Optional;
import java.util.stream.Stream;

import applyingjava.java.streams.mapper.Mapper.Order;
import applyingjava.java.streams.util.FileUtil;
import applyingjava.java.streams.util.LOG;

public class OptionalFunctions {
    public static void main(String[] args) {
        Stream<Order> sampleOrder = FileUtil.getSampleOrders(1);
        Optional<Order> order = sampleOrder.findFirst();
        // ifPresent
        order.ifPresent(LOG.print::log);
        // get
        LOG.print.log(order.get());
        // orElse
        order.orElse(new Order());
        // orElseThrow
        order = Optional.empty();
        order.orElseThrow(() -> new NullPointerException("No Object Found."));

    }
}
