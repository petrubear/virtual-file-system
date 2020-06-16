package emg.java.vfs.files;

public class File extends DirEntry {
    private final String contents;

    public File(String parentPath, String name, String contents) {
        super(parentPath, name);
        this.contents = contents;
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

    public File appendContents(String newContents) {
        return setContents(contents + "\n" + newContents);
    }

    public File setContents(String newContents) {
        return new File(parentPath, name, newContents);
    }

    public String contents() {
        return contents;
    }
}
