package applyingjava.java.streams.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {
        try {
            Files.lines(Paths.get(FileUtil.getStream("orders").get().getAbsolutePath()))
                    .map(line -> (line.split(","))[3]).distinct().forEach(LOG.print::log);
        } catch (IOException e) {
            LOG.print.error(e.getMessage());
        }
    }
}
