package emg.java.vfs.files;

import emg.java.vfs.filesystem.FileSystemException;

public abstract class DirEntry {
    protected String parentPath;
    protected String name;

    public DirEntry(String parentPath, String name) {
        this.parentPath = parentPath;
        this.name = name;
    }

    public String parentPath() {
        return parentPath;
    }

    public String name() {
        return name;
    }

    public String path() {
        var separator = "";
        if (!Directory.ROOT_PATH.equals(parentPath)) {
            separator = Directory.SEPARATOR;
        }
        return parentPath + separator + name;
    }

    public Directory asDirectory() {
        throw new FileSystemException(String.format("%s is not a Directory", name));
    }

    public File asFile() {
        throw new FileSystemException(String.format("%s is not a File", name));
    }

    public abstract String getType();

    public boolean isFile() {
        return false;
    }

    public boolean isDirectory() {
        return false;
    }
}
