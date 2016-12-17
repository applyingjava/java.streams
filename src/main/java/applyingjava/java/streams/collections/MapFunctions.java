package applyingjava.java.streams.collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import applyingjava.java.streams.mapper.Mapper;
import applyingjava.java.streams.mapper.Mapper.Order;
import applyingjava.java.streams.mapper.Mapper.Status;
import applyingjava.java.streams.util.FileUtil;
import applyingjava.java.streams.util.LOG;

public class MapFunctions {
    public static void main(String[] args) {
        Stream<Order> sampleOrders = FileUtil.getSampleOrders(10);

        // get orders by status
        Map<Mapper.Status, List<Order>> orderMap = new HashMap<>();
        Order order = sampleOrders.findFirst().get();

        // putIfAbsent
        orderMap.putIfAbsent(Mapper.Status.PENDING, new ArrayList<>());
        orderMap.get(Mapper.Status.PENDING).add(order);

        // computeIfAbsent
        orderMap.computeIfAbsent(Mapper.Status.COMPLETE, orders -> new ArrayList<>()).add(order);

        // getOrDefault
        LOG.print.log(orderMap.getOrDefault(Mapper.Status.PENDING, Collections.emptyList()));
        LOG.print.log(orderMap.getOrDefault(Mapper.Status.COMPLETE, Collections.emptyList()));

        // get sample orders
        sampleOrders = FileUtil.getSampleOrders(100);
        Map<Status, List<Order>> orderMapGroupByStatus = sampleOrders
                .collect(Collectors.groupingBy(Mapper.Order::getStatus, Collectors.toList()));

        // map printing
        orderMapGroupByStatus.entrySet()
                .forEach(entry -> LOG.print.log(entry.getKey() + ":" + entry.getValue()));

        // merge two maps
        Map<Status, List<Order>> orderMapGroupByPendingStatus = new HashMap<>();
        orderMapGroupByPendingStatus.computeIfAbsent(Mapper.Status.PENDING, orders -> new ArrayList<>())
                .addAll(orderMapGroupByStatus.get(Mapper.Status.PENDING));

        Map<Status, List<Order>> orderMapGroupByCompleteStatus = new HashMap<>();
        orderMapGroupByCompleteStatus.computeIfAbsent(Mapper.Status.COMPLETE, orders -> new ArrayList<>())
                .addAll(orderMapGroupByStatus.get(Mapper.Status.COMPLETE));

        orderMapGroupByPendingStatus.forEach((status, newOrder) -> orderMapGroupByCompleteStatus.merge(status, newOrder, (map1, map2) -> {
            map1.addAll(map2);
            return map1;
        }));

        orderMapGroupByPendingStatus.entrySet()
                .forEach(entry -> LOG.print.log(entry.getKey() + ":" + entry.getValue()));
    }
}
