package emg.java.vfs.filesystem;

import emg.java.vfs.commands.Command;
import emg.java.vfs.files.Directory;

import java.util.Scanner;

public class FileSystem {
    public static void main(String... args) {

        final var scanner = new Scanner(System.in);
        final var root = Directory.root();
        var state = State.apply(root, root);

        while (true) {
            state.show();
            var input = scanner.nextLine();
            state = Command.from(input).apply(state);
        }
    }
}
