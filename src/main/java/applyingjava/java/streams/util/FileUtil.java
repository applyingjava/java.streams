package applyingjava.java.streams.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import applyingjava.java.streams.mapper.Mapper;
import applyingjava.java.streams.mapper.Mapper.Order;

public final class FileUtil {
    private static final String slash = "-";
    private static Map<String, File> files = new HashMap<>();
    private static final String TYPE = "part-";
    private static final String path = "./src/main/java/resources/retail_db/";

    private FileUtil() {
        LOG.print.log("Do nothing here ...");
    }

    static {
        init();
    }

    private static void init() {
        Path start = Paths.get(path);
        try {
            files = Files.walk(start).filter(foundPath -> foundPath.toString().contains(TYPE)).map(Function.identity())
                    .collect(Collectors.toMap(path -> path.getParent().getFileName() + slash + path.getFileName(),
                            Path::toFile, (key1, key2) -> key1.equals(key2) ? key1 : key2));
        } catch (IOException e1) {
            LOG.print.error(e1.getMessage());
        }
    }

    public static Optional<File> getStream(String dataName) {
        Objects.requireNonNull(dataName);
        return files.entrySet().stream().filter(path -> path.getKey().contains(dataName)).map(Map.Entry::getValue)
                .findFirst();
    }

    public static Stream<Order> getSampleOrders(int limit) {
        try {
            return Files.lines(Paths.get(FileUtil.getStream("orders").get().getAbsolutePath()))
                    .map(Mapper::mapOrders).limit(limit);
        } catch (IOException e) {
            LOG.print.error(e.getMessage());
        }
        return Stream.empty();
    }

    public static Stream<Order> getSampleOrders() {
        try {
            return Files.lines(Paths.get(FileUtil.getStream("orders").get().getAbsolutePath()))
                    .map(Mapper::mapOrders);
        } catch (IOException e) {
            LOG.print.error(e.getMessage());
        }
        return Stream.empty();
    }

    public static Stream<String> getDataStream(String dataType) {
        try {
            return Files.lines(Paths.get(FileUtil.getStream(dataType).get().getAbsolutePath()));
        } catch (IOException e) {
            LOG.print.error(e.getMessage());
        }
        return Stream.empty();
    }
}
