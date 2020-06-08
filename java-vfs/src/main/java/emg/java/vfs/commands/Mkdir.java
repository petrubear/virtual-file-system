package emg.java.vfs.commands;

import emg.java.vfs.filesystem.State;

public class Mkdir extends Command {
    private String name;

    public Mkdir(String name) {
        this.name = name;
    }

    @Override
    public State apply(State state) {
        return null;
    }
}
