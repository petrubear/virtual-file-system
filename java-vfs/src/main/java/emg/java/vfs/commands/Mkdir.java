package emg.java.vfs.commands;

import emg.java.vfs.files.DirEntry;
import emg.java.vfs.files.Directory;
import emg.java.vfs.filesystem.State;

public class Mkdir extends CreateEntry {

    public Mkdir(String name) {
        super(name);
    }

    @Override
    public DirEntry createSpecificEntry(State state, String entryName) {
        return Directory.empty(state.wd().path(), entryName);
    }
}
