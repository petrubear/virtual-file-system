package emg.java.vfs.commands;

import emg.java.vfs.files.Directory;
import emg.java.vfs.files.File;
import emg.java.vfs.filesystem.State;

import java.util.List;

import static emg.java.vfs.extensions.ListExtensions.head;
import static emg.java.vfs.extensions.ListExtensions.tail;

public class Echo extends Command {
    private final String[] args;

    public Echo(List<String> args) {
        this.args = args.toArray(new String[0]);
    }

    @Override
    public State apply(State state) {
        if (args.length == 0) {
            return state;
        } else if (args.length == 1) {
            return state.setMessage(args[0]);
        } else {
            var operator = args[args.length - 2];
            var fileName = args[args.length - 1];
            var contents = createContent(args, args.length - 2);

            if (">>".equals(operator)) {
                return doEcho(state, contents, fileName, true);
            } else if (">".equals(operator)) {
                return doEcho(state, contents, fileName, false);
            } else {
                return state.setMessage(createContent(args, args.length));
            }
        }
    }

    private String createContent(String[] args, int topIndex) {
        return createContentHelper(0, "", args, topIndex);
    }

    private String createContentHelper(int currentIndex, String accumulator, String[] args, int topIndex) {
        if (currentIndex >= topIndex) {
            return accumulator;
        } else {
            return createContentHelper(currentIndex + 1, String.format("%s %s", accumulator, args[currentIndex]),
                args, topIndex);
        }
    }

    private State doEcho(State state, String contents, String fileName, boolean append) {
        if (fileName.contains(Directory.SEPARATOR)) {
            return state.setMessage("Echo: filename must not contain separators");
        } else {
            var foldersInPath = state.wd().getAllFoldersInPath();
            foldersInPath.add(fileName);
            var newRoot = getRootAfterEcho(state.root(), foldersInPath, contents, append);
            if (newRoot == state.root()) {
                return state.setMessage(fileName + ": No such file or directory");
            } else {
                return State.apply(newRoot, newRoot.findDesendant(state.wd().getAllFoldersInPath()));
            }
        }
    }

    private Directory getRootAfterEcho(Directory currentDirectory, List<String> path, String contents, boolean append) {
        if (path.isEmpty()) {
            return currentDirectory;
        } else if (tail(path).isEmpty()) {
            var dirEntry = currentDirectory.findEntry(head(path));
            if (dirEntry == null) {
                return currentDirectory.addEntry(new File(currentDirectory.path(), head(path), contents));
            } else if (dirEntry.isDirectory()) {
                return currentDirectory;
            } else if (append) {
                return currentDirectory.replaceEntry(head(path), dirEntry.asFile().appendContents(contents));
            } else {
                return currentDirectory.replaceEntry(head(path), dirEntry.asFile().setContents(contents));
            }
        } else {
            var nextDirectory = currentDirectory.findEntry(head(path)).asDirectory();
            var newNextDirectory = getRootAfterEcho(nextDirectory, tail(path), contents, append);

            if (newNextDirectory == nextDirectory) {
                return currentDirectory;
            } else {
                return currentDirectory.replaceEntry(head(path), newNextDirectory);
            }
        }
    }

}

