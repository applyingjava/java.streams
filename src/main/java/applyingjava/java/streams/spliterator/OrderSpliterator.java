package applyingjava.java.streams.spliterator;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import applyingjava.java.streams.mapper.Mapper;
import applyingjava.java.streams.mapper.Mapper.Order;
import applyingjava.java.streams.util.FileUtil;
import applyingjava.java.streams.util.LOG;

public class OrderSpliterator implements Spliterator<Mapper.Order> {
    private final Spliterator<String> lineSpliterator;
    private Mapper.Order order;

    private OrderSpliterator(Spliterator<String> lineSpliterator) {
        this.lineSpliterator = lineSpliterator;
    }

    @Override
    public int characteristics() {
        return lineSpliterator.characteristics();
    }

    @Override
    public long estimateSize() {
        return lineSpliterator.estimateSize();
    }

    @Override
    public boolean tryAdvance(Consumer<? super Order> action) {
        if (lineSpliterator.tryAdvance(line -> this.order = Mapper.mapOrders(line))) {
            action.accept(this.order);
            return true;
        }
        return false;
    }

    @Override
    public Spliterator<Order> trySplit() {
        return null;
    }

    public static void main(String[] args) {
        Stream<String> sampleOrders = FileUtil.getDataStream("orders");
        Spliterator<String> lineSpliterator = sampleOrders.spliterator();
        Spliterator<Mapper.Order> orderSpliterator = new OrderSpliterator(lineSpliterator);

        Stream<Mapper.Order> order = StreamSupport.stream(orderSpliterator, false);
        // get max order number
        LOG.print.log(order.max(Comparator.comparing(Order::getId)).get());

        sampleOrders = FileUtil.getDataStream("orders");
        lineSpliterator = sampleOrders.spliterator();
        orderSpliterator = new OrderSpliterator(lineSpliterator);
        order = StreamSupport.stream(orderSpliterator, false);
        order.limit(10).filter(newOrder -> newOrder.getStatus().equals(Mapper.Status.COMPLETE)).forEach(LOG.print::log);

    }

}
