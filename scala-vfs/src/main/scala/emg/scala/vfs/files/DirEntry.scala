package emg.scala.vfs.files

import java.nio.file.FileSystemException

abstract class DirEntry(val parentPath: String, val name: String) {
  def path: String = {
    val separator = if (Directory.ROOT_PATH.equals(parentPath)) ""
    else Directory.SEPARATOR
    parentPath + separator + name
  }

  def asDirectory: Directory = throw new FileSystemException(s"$name is not a Directory")

  def asFile: File = throw new FileSystemException(s"$name is not a File")

  def getType: String


  def isDirectory: Boolean = false

  def isFile: Boolean = false
}
