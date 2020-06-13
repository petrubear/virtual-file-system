package emg.java.vfs.files;

public class File extends DirEntry {

    public File(String parentPath, String name, String contents) {
        super(parentPath, name);
    }

    public static File empty(String parentPath, String name) {
        return new File(parentPath, name, "");
    }

    @Override
    public File asFile() {
        return this;
    }

    @Override
    public String getType() {
        return "File";
    }

    @Override
    public boolean isFile() {
        return true;
    }
}
