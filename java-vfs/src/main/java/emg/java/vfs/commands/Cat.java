package emg.java.vfs.commands;

import emg.java.vfs.filesystem.State;

public class Cat extends Command {
    private final String fileName;

    public Cat(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public State apply(State state) {
        var wd = state.wd();
        var dirEntry = wd.findEntry(fileName);
        if (dirEntry == null || !dirEntry.isFile()) {
            return state.setMessage(fileName + ": no such File.");
        } else {
            return state.setMessage(dirEntry.asFile().contents());
        }
    }
}
