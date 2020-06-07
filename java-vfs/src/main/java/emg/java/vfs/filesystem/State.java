package emg.java.vfs.filesystem;

import emg.java.vfs.files.Directory;

public class State {
    public final static String SHELL_TOKEN = "[java] $_> ";
    private Directory root;
    private Directory wd;
    private String output;

    public State(Directory root, Directory wd, String output) {
        this.root = root;
        this.wd = wd;
        this.output = output;
    }

    public static State apply(Directory root, Directory wd) {
        return State.apply(root, root, "");
    }

    public static State apply(Directory root, Directory wd, String output) {
        return new State(root, wd, output);
    }

    public void show() {
        System.out.println(output);
        System.out.print(State.SHELL_TOKEN);
    }

    public State setMessage(String message) {
        return State.apply(root, wd, message);
    }
}
