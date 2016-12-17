package applyingjava.java.streams.util;

public enum LOG {
    print;

    public <T> void log(T t) {
        System.out.println(t.toString());
    }

    public <T> void error(T t) {
        System.err.println(t.toString());
    }
}
