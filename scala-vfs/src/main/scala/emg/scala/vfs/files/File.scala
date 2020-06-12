package emg.scala.vfs.files

class File(override val parentPath: String, override val name: String, contents: String)
  extends DirEntry(parentPath, name) {

  override def asFile: File = this

  override def getType: String = "File"

  override def isFile: Boolean = true
}

object File {
  def empty(parentPath: String, name: String): File = new File(parentPath, name, "")
}
