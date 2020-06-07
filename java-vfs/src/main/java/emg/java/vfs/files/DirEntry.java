package emg.java.vfs.files;

public abstract class DirEntry {
    private String parentPath;
    private String name;

    public DirEntry(String parentPath, String name) {
        this.parentPath = parentPath;
        this.name = name;
    }

    public String getParentPath() {
        return parentPath;
    }

    public String getName() {
        return name;
    }
}
