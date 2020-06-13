package emg.kotlin.vfs.files

import emg.kotlin.vfs.filesystem.FileSystemException

abstract class DirEntry constructor(val parentPath: String, val name: String) {
    fun path(): String {
        val separator = if (Directory.ROOT_PATH == parentPath) ""
        else Directory.SEPARATOR
        return parentPath + separator + name
    }

    open fun asDirectory(): Directory {
        throw FileSystemException("$name is not a Directory")
    }

    open fun asFile(): File {
        throw FileSystemException("$name is not a File")
    }

    abstract fun getType(): String

    open fun isFile(): Boolean = false

    open fun isDirectory(): Boolean = false
}