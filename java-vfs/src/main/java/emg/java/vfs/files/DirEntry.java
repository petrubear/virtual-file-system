package emg.java.vfs.files;

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

    public abstract Directory asDirectory();

    public abstract String getType();
}
