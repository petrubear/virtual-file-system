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
        return parentPath + Directory.SEPARATOR + name;
    }

    public Directory asDirectory() {
        throw new FileSystemException(String.format("%s is not a Directory", name));
    }

    public File asFile() {
        throw new FileSystemException(String.format("%s is not a File", name));
    }

    public abstract String getType();
}
