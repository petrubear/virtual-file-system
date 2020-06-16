package emg.scala.vfs.commands

import emg.scala.vfs.filesystem.State

class Cat(fileName: String) extends Command {
  override def apply(state: State): State = {
    val wd = state.wd
    val dirEntry = wd.findEntry(fileName)
    if (dirEntry == null || !dirEntry.isFile)
      state.setMessage(s"$fileName: no such File.")
    else
      state.setMessage(dirEntry.asFile.contents)
  }
}
