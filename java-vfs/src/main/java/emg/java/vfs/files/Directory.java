package emg.java.vfs.files;

import java.util.Collections;
import java.util.List;

public class Directory extends DirEntry {
    public final static String SEPARATOR = "/";
    public final static String ROOT_PATH = "/";

    private List<DirEntry> contents;

    public Directory(String parentPath, String name, List<DirEntry> contents) {
        super(parentPath, name);
        this.contents = Collections.unmodifiableList(contents);
    }

    public static Directory root() {
        return Directory.empty("", "");
    }

    public static Directory empty(String parentPath, String name) {
        return new Directory(parentPath, name, Collections.emptyList());
    }
}
