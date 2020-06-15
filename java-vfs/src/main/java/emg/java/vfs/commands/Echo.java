package emg.java.vfs.commands;

import emg.java.vfs.filesystem.State;

import java.util.List;

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
        return null;

    }
}

