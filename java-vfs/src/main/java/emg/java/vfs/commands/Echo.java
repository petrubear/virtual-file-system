package emg.java.vfs.commands;

import emg.java.vfs.filesystem.State;

import java.util.List;

public class Echo extends Command {
    private List<String> args;

    public Echo(List<String> args) {
        this.args = args;
    }

    @Override
    public State apply(State state) {
        if (args.isEmpty()) {
            return state;
        } else if (args.size() == 1) {
            return state.setMessage(args.get(0));
        } else {
            var operator = args.get(args.size() - 2);
            var fileName = args.get(args.size() - 1);
            var contents = createContent(args, args.size() - 2);

            if (">>".equals(operator)) {
                return doEcho(state, contents, fileName, true);
            } else if (">".equals(operator)) {
                return doEcho(state, contents, fileName, false);
            } else {
                return state.setMessage(createContent(args, args.size()));
            }
        }
    }

    private String createContent(List<String> args, int size) {
        if (size == 0) {
            return "";
        }
        if (args.size() != size) {
            for (int i = size; i <= args.size(); i++) {
                args.remove(i - 1);
            }
        }
        return String.join(" ", args);
    }

    private State doEcho(State state, String contents, String fileName, boolean append) {
        return null;

    }
}

