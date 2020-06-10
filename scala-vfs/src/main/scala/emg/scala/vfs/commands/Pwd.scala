package emg.scala.vfs.commands

import emg.scala.vfs.filesystem.State

object Pwd {
  def apply() = new Pwd
}

class Pwd extends Command {
  override def apply(state: State): State = state.setMessage(state.wd.path)
}
