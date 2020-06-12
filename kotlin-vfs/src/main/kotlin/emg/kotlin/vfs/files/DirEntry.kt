package emg.kotlin.vfs.files

import emg.kotlin.vfs.filesystem.FileSystemException

abstract class DirEntry constructor(val parentPath: String, val name: String) {
    fun path(): String = parentPath + Directory.SEPARATOR + name

    open fun asDirectory(): Directory {
        throw FileSystemException("$name is not a Directory")
    }

    open fun asFile(): File {
        throw FileSystemException("$name is not a File")
    }

    abstract fun getType(): String
}