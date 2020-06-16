package emg.java.vfs.commands;

import emg.java.vfs.filesystem.State;

import java.util.Arrays;
import java.util.function.Function;

public abstract class Command implements Function<State, State> {

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
        } else {
            switch (tokens[0]) {
                case MKDIR:
                    if (tokens.length < 2) {
                        return incompleteCommand(MKDIR);
                    } else {
                        return new Mkdir(tokens[1]);
                    }
                case LS:
                    return new Ls();
                case PWD:
                    return new Pwd();
                case TOUCH:
                    if (tokens.length < 2) {
                        return incompleteCommand(TOUCH);
                    } else {
                        return new Touch(tokens[1]);
                    }
                case CD:
                    if (tokens.length < 2) {
                        return incompleteCommand(CD);
                    } else {
                        return new Cd(tokens[1]);
                    }
                case RM:
                    if (tokens.length < 2) {
                        return incompleteCommand(RM);
                    } else {
                        return new Rm(tokens[1]);
                    }
                case ECHO:
                    if (tokens.length < 2) {
                        return incompleteCommand(ECHO);
                    } else {
                        return new Echo(Arrays.asList(tokens).subList(1, tokens.length));
                    }
                case CAT:
                    if (tokens.length < 2) {
                        return incompleteCommand(CAT);
                    } else {
                        return new Cat(tokens[1]);
                    }
                default:
                    return new UnknownCommand();
            }
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


