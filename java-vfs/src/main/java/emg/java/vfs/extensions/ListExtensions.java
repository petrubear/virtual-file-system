package emg.java.vfs.extensions;

import java.util.List;

public class ListExtensions {
    public static <T> List<T> tail(List<T> list) {
        return list.subList(1, list.size());
    }

    public static <T> T head(List<T> list) {
        return list.get(0);
    }
}
