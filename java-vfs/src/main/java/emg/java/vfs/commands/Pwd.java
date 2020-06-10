package emg.java.vfs.commands;

import emg.java.vfs.filesystem.State;

public class Pwd extends Command {
    @Override
    public State apply(State state) {
        return state.setMessage(state.wd().path());
    }
}
