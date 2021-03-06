package emg.scala.vfs.files

import scala.annotation.tailrec

class Directory(override val parentPath: String, override val name: String, val contents: List[DirEntry])
  extends DirEntry(parentPath, name) {
  def removeEntry(entryName: String): Directory = {
    if (!hasEntry(entryName)) this
    else new Directory(parentPath, name, contents.filter(x => !x.name.equals(entryName)))
  }


  def replaceEntry(entryName: String, newEntry: DirEntry): Directory =
    new Directory(parentPath, name, contents.filter(d => !d.name.equals(entryName)) :+ newEntry)

  def findEntry(entryName: String): DirEntry = {
    @tailrec
    def findEntryHelper(name: String, contentList: List[DirEntry]): DirEntry = {
      if (contentList.isEmpty) null
      else if (contentList.head.name.equals(name)) contentList.head
      else findEntryHelper(name, contentList.tail)
    }

    findEntryHelper(entryName, contents)
  }

  def addEntry(newEntry: DirEntry): Directory = new Directory(parentPath, name, contents :+ newEntry)

  def findDesendant(path: List[String]): Directory =
    if (path.isEmpty) this
    else findEntry(path.head).asDirectory.findDesendant(path.tail)

  def findDesendant(relativePath: String): Directory = {
    if (relativePath.isEmpty) this
    else findDesendant(relativePath.split(Directory.SEPARATOR).toList)
  }

  def getAllFoldersInPath: List[String] =
    path.substring(1).split(Directory.SEPARATOR).toList.filter(d => !d.isEmpty)

  def hasEntry(name: String): Boolean = findEntry(name) != null

  override def asDirectory: Directory = this

  override def getType: String = "Dir"

  def isRoot: Boolean = parentPath.isEmpty

  override def isDirectory: Boolean = true

  override def toString: String = parentPath + name
}

object Directory {
  val SEPARATOR = "/"
  val ROOT_PATH = "/"

  def ROOT: Directory = Directory.empty("", "")

  def empty(parentPath: String, name: String): Directory = {
    new Directory(parentPath, name, List())
  }
}
