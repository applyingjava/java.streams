package applyingjava.java.streams.lambda;

import static java.util.stream.Collectors.*;

import java.io.IOException;
import java.util.*;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collector;

import applyingjava.java.streams.mapper.Mapper;
import applyingjava.java.streams.mapper.Mapper.Order;
import applyingjava.java.streams.mapper.Mapper.Status;
import applyingjava.java.streams.util.FileUtil;
import applyingjava.java.streams.util.LOG;

public class Statistics {
    public static void main(String[] args) throws IOException {
        Map<Date, List<Order>> orderByDataList = FileUtil.getSampleOrders().limit(10)
                .collect(groupingBy(Mapper.Order::getCreateTime));

        LOG.print.log(orderByDataList);

        Map<Date, Set<Order>> orderByDateSet = FileUtil.getSampleOrders().limit(10)
                .collect(groupingBy(Mapper.Order::getCreateTime, mapping(Function.identity(), toSet())));
        LOG.print.log(orderByDateSet);

        HashMap<Status, AtomicLong> orderByStatus = FileUtil.getSampleOrders().collect(HashMap::new,
                (initMap, order) -> initMap.computeIfAbsent(order.getStatus(), value -> new AtomicLong())
                        .incrementAndGet(), // accumulator
                HashMap::putAll);
        LOG.print.log(orderByStatus);

        Map<Date, HashMap<Status, AtomicLong>> orderByDateAndStatus = FileUtil.getSampleOrders()
                .collect(
                        groupingBy(Mapper.Order::getCreateTime,
                                Collector.of(HashMap::new,
                                        (initMap, order) -> initMap.computeIfAbsent(order.getStatus(), value -> new AtomicLong()).incrementAndGet(),// accumulator
                                        (map1, map2) -> {
                                            map2.entrySet().forEach(
                                                    entry2 -> map1.merge(entry2.getKey(), entry2.getValue(),
                                                            (count1, count2) -> {
                                                                count1.addAndGet(count2.get());
                                                                return count1;
                                                            }
                                                    ));
                                            return map1;
                                        }, // combiner
                                        Collector.Characteristics.IDENTITY_FINISH)));

        LOG.print.log(orderByDateAndStatus);

        //validating orderByDateAndStatus map count
        Map<Status, AtomicLong> orderByStatusCount = new HashMap<>();
        orderByDateAndStatus.values().forEach(map -> map.entrySet()
                .forEach(entry -> orderByStatusCount.merge(entry.getKey(), entry.getValue(), (count1, count2) -> {
                    count1.addAndGet(count2.get());
                    return count1;
                })));

        LOG.print.log(orderByStatusCount);

        //get max status count
        Entry<Status, AtomicLong> max = Collections.max(orderByStatusCount.entrySet(), java.util.Comparator.comparingInt(entry -> (int) entry.getValue().get()));
        LOG.print.log(max);

        //get max status count
        Entry<Status, AtomicLong> min = Collections.min(orderByStatusCount.entrySet(), Comparator.comparingInt(entry -> (int) entry.getValue().get()));
        LOG.print.log(min);

        LongSummaryStatistics summaryStatistics = orderByStatusCount.values().stream().mapToLong(AtomicLong::get)
                .summaryStatistics();
        LOG.print.log(summaryStatistics);
    }
}
