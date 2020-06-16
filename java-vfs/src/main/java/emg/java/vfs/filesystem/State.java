package emg.java.vfs.filesystem;

import emg.java.vfs.files.Directory;

public class State {
    public static final String SHELL_TOKEN = "[java] $_> ";
    private final Directory root;
    private final Directory wd;
    private final String output;

    public State(Directory root, Directory wd, String output) {
        this.root = root;
        this.wd = wd;
        this.output = output;
    }

    public static State apply(Directory root, Directory wd) {
        return State.apply(root, wd, "");
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

    public Directory wd() {
        return wd;
    }

    public Directory root() {
        return root;
    }
}
