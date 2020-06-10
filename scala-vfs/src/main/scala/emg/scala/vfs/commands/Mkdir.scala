package emg.scala.vfs.commands

import emg.scala.vfs.files.{DirEntry, Directory}
import emg.scala.vfs.filesystem.State

class Mkdir(name: String) extends Command {

  def checkIllegal(name: String): Boolean = name.contains('.')

  def doMkdir(state: State, name: String): State = {
    def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory = {
      if (path.isEmpty) currentDirectory.addEntry(newEntry)
      else {
        val oldEntry = currentDirectory.findEntry(path.head)
        currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry.asDirectory, path.tail, newEntry))
      }
    }

    val wd = state.wd
    //1 all directories in path
    val allDirsInPath = wd.getAllFoldersInPath
    //2 create new directory in wd
    val newDirectory = Directory.empty(wd.path, name)
    //3 update the directory structure
    val newRoot = updateStructure(state.root, allDirsInPath, newDirectory)
    //4 find the new WD
    val newWD = newRoot.findDesendant(allDirsInPath)

    State(newRoot, newWD)
  }

  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(name)) {
      state.setMessage("Directory $name already exists")
    } else if (name.contains(Directory.SEPARATOR)) {
      state.setMessage(s"$name must not contain separators")
    } else if (checkIllegal(name)) {
      state.setMessage(s"$name is illegal entry name")
    } else {
      doMkdir(state, name)
    }
  }
}
