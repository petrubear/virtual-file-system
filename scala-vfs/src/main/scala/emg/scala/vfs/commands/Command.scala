package emg.scala.vfs.commands

import emg.scala.vfs.filesystem.State

trait Command {
  def apply(state: State): State
}

object Command {
  def from(input: String): Command = new UnknownCommand
}

