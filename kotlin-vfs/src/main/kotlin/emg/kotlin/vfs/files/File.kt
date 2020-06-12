package emg.kotlin.vfs.files

class File(parentPath: String, name: String, val contents: String = "") : DirEntry(parentPath, name) {
    companion object {
        fun empty(parentPath: String, name: String): File {
            return File(parentPath, name)
        }
    }

    override fun getType(): String {
        return "File"
    }

    override fun asFile(): File {
        return this
    }

}