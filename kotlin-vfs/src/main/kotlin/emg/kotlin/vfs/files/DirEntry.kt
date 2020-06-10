package emg.kotlin.vfs.files

abstract class DirEntry constructor(val parentPath: String, val name: String) {
    fun path(): String = parentPath + Directory.SEPARATOR + name

    abstract fun asDirectory(): Directory

    abstract fun getType(): String
}