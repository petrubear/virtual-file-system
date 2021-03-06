package emg.java.vfs.commands;

import emg.java.vfs.files.Directory;
import emg.java.vfs.filesystem.State;

import java.util.Arrays;
import java.util.List;

import static emg.java.vfs.extensions.ListExtensions.head;
import static emg.java.vfs.extensions.ListExtensions.tail;

public class Rm extends Command {

    private final String name;

    public Rm(String name) {
        this.name = name;
    }

    @Override
    public State apply(State state) {
        // 1 get working dir
        var wd = state.wd();
        // 2 get absolute path
        var absolutePath = "";
        if (name.startsWith(Directory.SEPARATOR)) {
            absolutePath = name;
        } else if (wd.isRoot()) {
            absolutePath = wd.path() + name;
        } else {
            absolutePath = wd.path() + Directory.SEPARATOR + name;
        }
        // 3 do some checks
        if (Directory.ROOT_PATH.equals(absolutePath)) {
            return state.setMessage("Cannot drop Root");
        } else {
            return doRm(state, absolutePath);
        }
    }

    private State doRm(State state, String path) {
        // 4 find the entry to remove
        // 5 update the structure
        var tokens = Arrays.asList(path.substring(1).split(Directory.SEPARATOR));
        var newRoot = rmHelper(state.root(), tokens);
        if (newRoot == state.root()) {
            return state.setMessage(path + ": no such file or directory");
        } else {
            return State.apply(newRoot, newRoot.findDesendant(state.wd().path().substring(1)));
        }
    }

    private Directory rmHelper(Directory currentDirectory, List<String> path) {
        if (path.isEmpty()) {
            return currentDirectory;
        } else if (tail(path).isEmpty()) {
            return currentDirectory.removeEntry(head(path));
        } else {
            var nextDirectory = currentDirectory.findEntry(head(path));
            if (!nextDirectory.isDirectory()) {
                return currentDirectory;
            } else {
                var newNextDirectory = rmHelper(nextDirectory.asDirectory(), tail(path));
                if (newNextDirectory == nextDirectory) {
                    return currentDirectory;
                } else {
                    return currentDirectory.replaceEntry(head(path), newNextDirectory);
                }
            }
        }
    }
}
