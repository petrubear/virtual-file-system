package emg.java.vfs.commands;

import emg.java.vfs.files.DirEntry;
import emg.java.vfs.filesystem.State;

import java.util.List;

public class Ls extends Command {
    @Override
    public State apply(State state) {
        var contents = state.wd().contents();
        var niceOutput = createNiceOutput(contents);
        return state.setMessage(niceOutput);
    }

    private String createNiceOutput(List<DirEntry> contents) {
        if (contents.isEmpty()) {
            return "";
        } else {
            var entry = contents.get(0);
            return String.format("%s [%s]\n%s", entry.name(), entry.getType(), createNiceOutput(contents.subList(1, contents.size())));
        }
    }
}
