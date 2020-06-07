package emg.java.vfs.filesystem;

import java.util.Scanner;

public class FileSystem {
    public static void main(String... args) {

        var scanner = new Scanner(System.in);
        while (true) {
            System.out.print("$_> ");
            var command = scanner.nextLine();
            if (command.equals("exit")) {
                break;
            } else {
                System.out.println(command);
            }
        }

    }
}
