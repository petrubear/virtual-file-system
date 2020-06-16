package emg.java.vfs.commands;

import emg.java.vfs.filesystem.State;

import java.util.Arrays;

public abstract class Command {
    public abstract State apply(State state);

    public static Command from(String input) {
        final String MKDIR = "mkdir";
        final String LS = "ls";
        final String PWD = "pwd";
        final String TOUCH = "touch";
        final String CD = "cd";
        final String RM = "rm";
        final String ECHO = "echo";
        final String CAT = "cat";

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
        } else if (TOUCH.equals(tokens[0])) {
            if (tokens.length < 2) {
                return incompleteCommand(TOUCH);
            } else {
                return new Touch(tokens[1]);
            }
        } else if (CD.equals(tokens[0])) {
            if (tokens.length < 2) {
                return incompleteCommand(CD);
            } else {
                return new Cd(tokens[1]);
            }
        } else if (RM.equals(tokens[0])) {
            if (tokens.length < 2) {
                return incompleteCommand(RM);
            } else {
                return new Rm(tokens[1]);
            }
        } else if (ECHO.equals(tokens[0])) {
            if (tokens.length < 2) {
                return incompleteCommand(ECHO);
            } else {
                return new Echo(Arrays.asList(tokens).subList(1, tokens.length));
            }
        } else if (CAT.equals(tokens[0])) {
            if (tokens.length < 2) {
                return incompleteCommand(CAT);
            } else {
                return new Cat(tokens[1]);
            }
        }
        return new UnknownCommand();
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


