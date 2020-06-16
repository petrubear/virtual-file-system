package emg.scala.vfs.files

class File(override val parentPath: String, override val name: String, contents: String)
  extends DirEntry(parentPath, name) {
  def appendContents(newContent: String): File = setContents(contents + "\n" + newContent)

  def setContents(newContent: String): File = new File(parentPath, name, newContent)


  override def asFile: File = this

  override def getType: String = "File"

  override def isFile: Boolean = true
}

object File {
  def empty(parentPath: String, name: String): File = new File(parentPath, name, "")
}
