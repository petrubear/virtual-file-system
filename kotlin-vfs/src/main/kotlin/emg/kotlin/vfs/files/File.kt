package emg.kotlin.vfs.files

class File(parentPath: String, name: String, val contents: String = "") : DirEntry(parentPath, name) {
    companion object {
        fun empty(parentPath: String, name: String): File {
            return File(parentPath, name)
        }
    }

    override fun getType(): String = "File"

    override fun asFile(): File = this

    override fun isFile(): Boolean = true

}