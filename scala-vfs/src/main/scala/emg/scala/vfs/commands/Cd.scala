package emg.scala.vfs.commands

import emg.scala.vfs.files.{DirEntry, Directory}
import emg.scala.vfs.filesystem.State

import scala.annotation.tailrec

class Cd(dir: String) extends Command {

  override def apply(state: State): State = {
    //1 find root
    val root = state.root
    val wd = state.wd
    // find the absolute path of the fir i want to cd to
    val absolutePath = if (dir.startsWith(Directory.SEPARATOR)) dir
    else if (wd.isRoot) wd.path + dir
    else wd.path + Directory.SEPARATOR + dir
    // find the directory I want to cd to
    val destinationDirectory = doFindEntry(root, absolutePath)
    // change the state based on new directory
    if (destinationDirectory == null || !destinationDirectory.isDirectory)
      state.setMessage(dir + " no such directory")
    else
      State(root, destinationDirectory.asDirectory)
  }


  def doFindEntry(root: Directory, absolutePath: String): DirEntry = {
    @tailrec
    def findEntryHelper(currentDirectory: Directory, path: List[String]): DirEntry = {
      if (path.isEmpty || path.head.isEmpty) currentDirectory
      else if (path.tail.isEmpty) currentDirectory.findEntry(path.head)
      else {
        val nextDir = currentDirectory.findEntry(path.head)
        if (nextDir == null || !nextDir.isDirectory) null
        else findEntryHelper(nextDir.asDirectory, path.tail)
      }
    }

    @tailrec
    def collapseRelativeTokens(path: List[String], result: List[String]): List[String] = {
      if (path.isEmpty) result
      else if (".".equals(path.head)) collapseRelativeTokens(path.tail, result)
      else if ("..".equals(path.head)) {
        if (result.isEmpty) null
        else collapseRelativeTokens(path.tail, result.init)
      }
      else collapseRelativeTokens(path.tail, result :+ path.head)
    }

    // 1 tokens
    val tokens: List[String] = absolutePath.substring(1).split(Directory.SEPARATOR).toList
    // eliminate . tokens
    val newTokens = collapseRelativeTokens(tokens, List())

    // 2 navigate
    if (newTokens == null) return null
    findEntryHelper(root, newTokens)
  }
}
