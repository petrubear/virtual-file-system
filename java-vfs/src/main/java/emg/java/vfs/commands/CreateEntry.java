package emg.java.vfs.commands;

import emg.java.vfs.files.DirEntry;
import emg.java.vfs.files.Directory;
import emg.java.vfs.filesystem.State;

import java.util.List;

public abstract class CreateEntry extends Command {
    private final String name;

    public CreateEntry(String name) {
        this.name = name;
    }

    @Override
    public State apply(State state) {
        var wd = state.wd();
        if (wd.hasEntry(name)) {
            return state.setMessage(String.format("Directory %s already exists", name));
        } else if (name.contains(Directory.SEPARATOR)) {
            return state.setMessage(String.format("%s must not contain separators", name));
        } else if (checkIllegal(name)) {
            return state.setMessage(String.format("%s is illegal entry name", name));
        } else {
            return doCreateEntry(state, name);
        }
    }

    private State doCreateEntry(State state, String name) {
        var wd = state.wd();
        //1 all directories in path
        var allDirsInPath = wd.getAllFoldersInPath();
        //2 create new directory in wd
        var newDirectory = createSpecificEntry(state, name);
        //3 update the directory structure
        var newRoot = updateStructure(state.root(), allDirsInPath, newDirectory);
        //4 find the new WD
        var newWD = newRoot.findDesendant(allDirsInPath);

        return State.apply(newRoot, newWD);
    }

    public abstract DirEntry createSpecificEntry(State state, String entryName);

    private Directory updateStructure(Directory currentDirectory, List<String> path, DirEntry newEntry) {
        if (path.isEmpty()) {
            return currentDirectory.addEntry(newEntry);
        } else {
            var oldEntry = currentDirectory.findEntry(path.get(0));
            return currentDirectory.replaceEntry(oldEntry.name(), updateStructure(oldEntry.asDirectory(),
                path.subList(1, path.size()), newEntry));
        }
    }

    private boolean checkIllegal(String name) {
        return name.contains(".");
    }
}
