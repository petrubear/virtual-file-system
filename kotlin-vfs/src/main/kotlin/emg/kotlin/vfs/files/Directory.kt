package emg.kotlin.vfs.files

import java.util.*

class Directory constructor(val parentPath: String, val name: String,
                            contents: List<DirEntry> = Collections.emptyList()) : DirEntry(parentPath, name) {
    companion object {
        val SEPARATOR = "/"
        val ROOT_PATH = "/"

        fun root(): Directory {
            return empty("", "")
        }

        fun empty(parentPath: String, name: String): Directory {
            return Directory(parentPath, name, Collections.emptyList())
        }
    }
}