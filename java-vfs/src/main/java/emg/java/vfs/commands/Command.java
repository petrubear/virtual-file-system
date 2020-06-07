package emg.java.vfs.commands;

import emg.java.vfs.filesystem.State;

public abstract class Command {
    public abstract State apply(State state);

    public static Command from(String input) {
        return new UnknownCommand();
    }
}


