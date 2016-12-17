package applyingjava.java.streams.lambda;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import applyingjava.java.streams.mapper.Mapper;
import applyingjava.java.streams.util.FileUtil;
import applyingjava.java.streams.util.LOG;


public class CustomCompare {
    public static void main(String[] args) {
        try {
            Files.lines(Paths.get(FileUtil.getStream("orders").get().getAbsolutePath()))
                    .map(Mapper::mapOrders).limit(10)
                    .sorted((o1, o2) -> (int) (o1.getCustId() - o2.getCustId())).forEach(LOG.print::log);
        } catch (IOException e) {
            LOG.print.error(e.getMessage());
        }
    }
}
