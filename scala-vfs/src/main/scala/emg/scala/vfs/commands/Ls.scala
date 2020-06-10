package emg.scala.vfs.commands

import emg.scala.vfs.files.DirEntry
import emg.scala.vfs.filesystem.State

class Ls extends Command {

  def createNiceOutput(contents: List[DirEntry]): String = {
    if (contents.isEmpty) ""
    else {
      val entry = contents.head
      entry.name + s" [${entry.getType}]\n${createNiceOutput(contents.tail)} \n"
    }
  }

  override def apply(state: State): State = {
    val contents = state.wd.contents
    val niceOutput = createNiceOutput(contents)
    state.setMessage(niceOutput)
  }
}
