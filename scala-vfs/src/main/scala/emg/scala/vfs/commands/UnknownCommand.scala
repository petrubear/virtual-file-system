package emg.scala.vfs.commands

import emg.scala.vfs.filesystem.State

class UnknownCommand extends Command {
  override def apply(state: State): State = state.setMessage("Command not found!")
}
