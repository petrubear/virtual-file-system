package emg.java.vfs.commands;

import emg.java.vfs.filesystem.State;

public class UnknownCommand extends Command {
    @Override
    public State apply(State state) {
        return state.setMessage("Command not found!");
    }
}
