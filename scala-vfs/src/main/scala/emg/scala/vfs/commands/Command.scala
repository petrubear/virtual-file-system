package emg.scala.vfs.commands

import emg.scala.vfs.filesystem.State

trait Command {
  def apply(state: State): State
}

object Command {
  private val MKDIR = "mkdir"
  private val LS = "ls"
  private val PWD = "pwd"
  private val TOUCH = "touch"
  private val CD = "cd"

  def emptyCommand: Command = (state: State) => state

  def incompleteCommand(name: String): Command = (state: State) => state.setMessage(s"${name}: Incomplete command!")


  def from(input: String): Command = {
    val tokens: Array[String] = input.split(" ")
    if (input.isEmpty || tokens.isEmpty) emptyCommand
    else if (MKDIR.equals(tokens(0))) { //TODO esto puede hacerse con pattern matching?
      if (tokens.length < 2) incompleteCommand(MKDIR)
      else new Mkdir(tokens(1))
    } else if (LS.equals(tokens(0))) {
      new Ls
    } else if (PWD.equals(tokens(0))) {
      Pwd()
    } else if (TOUCH.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(TOUCH)
      else new Touch(tokens(1))
    } else if (CD.equals(tokens(0))) {
      if (tokens.length < 2) incompleteCommand(CD)
      else new Cd(tokens(1))
    }
    else new UnknownCommand
  }
}

