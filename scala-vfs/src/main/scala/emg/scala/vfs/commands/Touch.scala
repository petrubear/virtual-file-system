package emg.scala.vfs.commands

import emg.scala.vfs.files.{DirEntry, File}
import emg.scala.vfs.filesystem.State

class Touch(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State, entryName: String): DirEntry = File.empty(state.wd.path, name)
}
