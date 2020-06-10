package emg.java.vfs.files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Directory extends DirEntry {
    public final static String SEPARATOR = "/";
    public final static String ROOT_PATH = "/";

    private List<DirEntry> contents;

    public Directory(String parentPath, String name, List<DirEntry> contents) {
        super(parentPath, name);
        this.contents = contents;
    }

    public static Directory root() {
        return Directory.empty("", "");
    }

    public static Directory empty(String parentPath, String name) {
        return new Directory(parentPath, name, new ArrayList<>());
    }

    public boolean hasEntry(String name) {
        return findEntry(name) != null;
    }

    public Directory replaceEntry(String entryName, DirEntry newEntry) {
        List<DirEntry> contents = this.contents.stream().filter(d -> !d.name.equals(entryName)).collect(Collectors.toList());
        contents.add(newEntry);
        return new Directory(parentPath, name, contents);
    }

    public DirEntry findEntry(String entryName) {
        return findEntryHelper(entryName, contents);
    }

    private DirEntry findEntryHelper(String name, List<DirEntry> contentList) {
        if (contentList.isEmpty()) {
            return null;
        } else if (contentList.get(0).name.equals(name)) {
            return contentList.get(0);
        } else {
            return findEntryHelper(name, contentList.subList(1, contentList.size()));
        }
    }

    public Directory addEntry(DirEntry newEntry) {
        contents.add(newEntry);
        return new Directory(parentPath, name, contents);
    }

    public Directory findDesendant(List<String> path) {
        if (path.isEmpty()) {
            return this;
        } else {
            return findEntry(path.get(0)).asDirectory().findDesendant(path.subList(1, path.size()));
        }
    }

    public List<String> getAllFoldersInPath() {
        return Arrays.stream(path().substring(1).split(Directory.SEPARATOR)).filter(d -> !d.isEmpty()).collect(Collectors.toList());
    }

    @Override
    public Directory asDirectory() {
        return this;
    }

    @Override
    public String getType() {
        return "Directory";
    }

    public List<DirEntry> contents() {
        return contents;
    }
}
