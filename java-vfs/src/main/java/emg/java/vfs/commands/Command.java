package emg.java.vfs.commands;

import emg.java.vfs.filesystem.State;

public abstract class Command {
    public abstract State apply(State state);

    public static Command from(String input) {
        final String MKDIR = "mkdir";
        final String LS = "ls";
        final String PWD = "pwd";

        var tokens = input.split(" ");
        if (input.isEmpty() || tokens.length == 0) {
            return emptyCommand();
        } else if (MKDIR.equals(tokens[0])) {
            if (tokens.length < 2) {
                return incompleteCommand(MKDIR);
            } else {
                return new Mkdir(tokens[1]);
            }
        } else if (LS.equals(tokens[0])) {
            return new Ls();
        } else if (PWD.equals(tokens[0])) {
            return new Pwd();
        } else {
            return new UnknownCommand();
        }
    }

    private static Command incompleteCommand(String name) {
        return new Command() {
            @Override
            public State apply(State state) {
                return state.setMessage(name + ": Incomplete Command!");
            }
        };
    }

    private static Command emptyCommand() {
        return new Command() {
            @Override
            public State apply(State state) {
                return state;
            }
        };
    }
}


