package emg.java.vfs.files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static emg.java.vfs.extensions.ListExtensions.head;
import static emg.java.vfs.extensions.ListExtensions.tail;

public class Directory extends DirEntry {
    public static final String SEPARATOR = "/";
    public static final String ROOT_PATH = "/";

    private final List<DirEntry> contents;

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
        List<DirEntry> entryContents = this.contents.stream().filter(d -> !d.name.equals(entryName)).collect(Collectors.toList());
        entryContents.add(newEntry);
        return new Directory(parentPath, name, entryContents);
    }

    public DirEntry findEntry(String entryName) {
        return findEntryHelper(entryName, contents);
    }

    private DirEntry findEntryHelper(String name, List<DirEntry> contentList) {
        if (contentList.isEmpty()) {
            return null;
        } else if (head(contentList).name.equals(name)) {
            return head(contentList);
        } else {
            return findEntryHelper(name, tail(contentList));
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
            return findEntry(head(path)).asDirectory().findDesendant(tail(path));
        }
    }

    public Directory findDesendant(String relativePath) {
        if (relativePath.isEmpty()) {
            return this;
        } else {
            return findDesendant(Arrays.asList(relativePath.split(Directory.SEPARATOR)));
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

    @Override
    public boolean isDirectory() {
        return true;
    }

    public boolean isRoot() {
        return parentPath.isEmpty();
    }

    public Directory removeEntry(String entryName) {
        if (!hasEntry(entryName)) {
            return this;
        } else {
            return new Directory(parentPath, name, contents.stream().filter(x -> !x.name.equals(entryName)).collect(Collectors.toList()));
        }
    }
}
