package emg.scala.vfs.files

import java.nio.file.FileSystemException

abstract class DirEntry(val parentPath: String, val name: String) {
  def path: String = parentPath + Directory.SEPARATOR + name

  def asDirectory: Directory = throw new FileSystemException(s"$name is not a Directory")

  def asFile: File = throw new FileSystemException(s"$name is not a File")

  def getType: String
}
