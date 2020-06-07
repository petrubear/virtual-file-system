package emg.scala.vfs.filesystem

import java.util.Scanner

import emg.scala.vfs.commands.Command
import emg.scala.vfs.files.Directory

object FileSystem extends App {

  val scanner = new Scanner(System.in)
  val root = Directory.ROOT
  var state = State(root, root)

  while (true) {

    state.show()
    val input = scanner.nextLine()
    state = Command.from(input).apply(state)
  }
}
