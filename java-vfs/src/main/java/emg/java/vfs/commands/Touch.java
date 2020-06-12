package emg.java.vfs.commands;

import emg.java.vfs.files.DirEntry;
import emg.java.vfs.files.File;
import emg.java.vfs.filesystem.State;

public class Touch extends CreateEntry {
    public Touch(String name) {
        super(name);
    }

    @Override
    public DirEntry createSpecificEntry(State state, String entryName) {
        return File.empty(state.wd().path(), entryName);
    }
}
