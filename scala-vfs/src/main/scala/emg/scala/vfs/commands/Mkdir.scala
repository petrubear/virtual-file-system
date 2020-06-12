package emg.scala.vfs.commands

import emg.scala.vfs.files.{DirEntry, Directory}
import emg.scala.vfs.filesystem.State

class Mkdir(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State, entryName: String): DirEntry = Directory.empty(state.wd.path, name)
}
