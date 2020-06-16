package emg.java.vfs.commands;

import emg.java.vfs.files.DirEntry;
import emg.java.vfs.files.Directory;
import emg.java.vfs.filesystem.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cd extends Command {
    private final String dir;

    public Cd(String dir) {
        this.dir = dir;
    }

    @Override
    public State apply(State state) {
        //1 find root
        var root = state.root();
        var wd = state.wd();
        // find the absolute path of the fir i want to cd to
        var absolutePath = "";
        if (dir.startsWith(Directory.SEPARATOR)) {
            absolutePath = dir;
        } else if (wd.isRoot()) {
            absolutePath = wd.path() + dir;
        } else {
            absolutePath = wd.path() + Directory.SEPARATOR + dir;
        }
        // find the directory I want to cd to
        var destinationDirectory = doFindEntry(root, absolutePath);
        // change the state based on new directory
        if (destinationDirectory == null || !destinationDirectory.isDirectory()) {
            return state.setMessage(dir + " no such directory");
        } else {
            return State.apply(root, destinationDirectory.asDirectory());
        }
    }

    private DirEntry doFindEntry(Directory root, String absolutePath) {
        // 1 tokens
        var tokens = Arrays.asList(absolutePath.substring(1).split(Directory.SEPARATOR));
        // eliminate . tokens
        var newTokens = collapseRelativeTokens(tokens, new ArrayList<>());
        // 2 navigate
        if (newTokens == null) {
            return null;
        }
        return findEntryHelper(root, newTokens);
    }

    private DirEntry findEntryHelper(Directory currentDirectory, List<String> path) {
        if (path.isEmpty() || path.get(0).isEmpty()) {
            return currentDirectory;
        } else if (path.subList(1, path.size()).isEmpty()) {
            return currentDirectory.findEntry(path.get(0));
        } else {
            var nextDir = currentDirectory.findEntry(path.get(0));
            if (nextDir == null || !nextDir.isDirectory()) {
                return null;
            } else {
                return findEntryHelper(nextDir.asDirectory(), path.subList(1, path.size()));
            }
        }
    }

    private List<String> collapseRelativeTokens(List<String> path, List<String> result) {
        if (path.isEmpty()) {
            return result;
        } else if (".".equals(path.get(0))) {
            return collapseRelativeTokens(path.subList(1, path.size()), result);
        } else if ("..".equals(path.get(0))) {
            if (result.isEmpty()) {
                return null;
            } else {
                return collapseRelativeTokens(path.subList(1, path.size()), result.subList(0, result.size() - 1));
            }
        } else {
            result.add(path.get(0));
            return collapseRelativeTokens(path.subList(1, path.size()), result);
        }
    }
}
