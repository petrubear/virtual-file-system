package emg.scala.vfs.commands

import emg.scala.vfs.filesystem.State

trait Command extends (State => State) {
  // al extender de la funciona S => S ya no requiero definir apply
  //def apply(state: State): State
}

object Command {
  private val MKDIR = "mkdir"
  private val LS = "ls"
  private val PWD = "pwd"
  private val TOUCH = "touch"
  private val CD = "cd"
  private val RM = "rm"
  private val ECHO = "echo"
  private val CAT = "cat"

  def emptyCommand: Command = (state: State) => state

  def incompleteCommand(name: String): Command = (state: State) => state.setMessage(s"${name}: Incomplete command!")

  def from(input: String): Command = {
    val tokens: Array[String] = input.split(" ")
    if (input.isEmpty || tokens.isEmpty) emptyCommand
    else tokens(0) match {
      case MKDIR =>
        if (tokens.length < 2) incompleteCommand(MKDIR)
        else new Mkdir(tokens(1))
      case LS => new Ls
      case PWD => Pwd()
      case TOUCH =>
        if (tokens.length < 2) incompleteCommand(TOUCH)
        else new Touch(tokens(1))
      case CD =>
        if (tokens.length < 2) incompleteCommand(CD)
        else new Cd(tokens(1))
      case RM =>
        if (tokens.length < 2) incompleteCommand(RM)
        else new Rm(tokens(1))
      case ECHO =>
        if (tokens.length < 2) incompleteCommand(ECHO)
        else new Echo(tokens.tail)
      case CAT =>
        if (tokens.length < 2) incompleteCommand(CAT)
        else new Cat(tokens(1))
      case _ => new UnknownCommand
    }
  }
}

