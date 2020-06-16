package emg.java.vfs.commands;

import emg.java.vfs.files.DirEntry;
import emg.java.vfs.filesystem.State;

import java.util.List;

import static emg.java.vfs.extensions.ListExtensions.head;
import static emg.java.vfs.extensions.ListExtensions.tail;

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
            var entry = head(contents);
            return String.format("%s [%s]%n%s", entry.name(), entry.getType(), createNiceOutput(tail(contents)));
        }
    }
}
