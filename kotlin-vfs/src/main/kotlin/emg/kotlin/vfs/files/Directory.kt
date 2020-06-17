package emg.kotlin.vfs.files

import emg.kotlin.vfs.extensions.head
import emg.kotlin.vfs.extensions.tail
import java.util.*

class Directory constructor(parentPath: String, name: String,
                            val contents: List<DirEntry> = Collections.emptyList()) : DirEntry(parentPath, name) {

    fun isRoot(): Boolean = parentPath.isEmpty()

    fun hasEntry(name: String): Boolean {
        return findEntry(name) != null
    }

    fun replaceEntry(entryName: String, newEntry: DirEntry): Directory {
        val newContents = contents.filter { d -> d.name != entryName }.toMutableList()
        newContents.add(newEntry)
        return Directory(parentPath, name, newContents)
    }

    //TODO cambiar el retorno de null
    fun findEntry(entryName: String): DirEntry? {
        fun findEntryHelper(name: String, contentList: List<DirEntry>): DirEntry? {
            return when {
                contentList.isEmpty() -> null
                contentList.head().name == name -> contentList.head()
                else -> findEntryHelper(name, contentList.tail())
            }
        }

        return findEntryHelper(entryName, contents)
    }

    fun addEntry(newEntry: DirEntry): Directory {
        val newContents = contents.toMutableList()
        newContents.add(newEntry)
        return Directory(parentPath, name, newContents)
    }

    fun findDesendant(path: List<String>): Directory {
        return if (path.isEmpty()) this
        else findEntry(path.head())!!.asDirectory().findDesendant(path.tail())
    }

    fun findDesendant(relativePath: String): Directory {
        return if (relativePath.isEmpty()) this
        else findDesendant(relativePath.split(SEPARATOR).toList())
    }

    fun getAllFoldersInPath(): List<String> {
        return path().substring(1).split(SEPARATOR).filter { d -> d.isNotEmpty() }
    }

    override fun asDirectory(): Directory = this

    override fun isDirectory(): Boolean = true

    override fun getType(): String {
        return "Dir"
    }

    fun removeEntry(entryName: String): Directory {
        return if (!hasEntry(entryName)) this
        else Directory(parentPath, name, contents.filter { x -> x.name != entryName })
    }

    companion object {
        const val SEPARATOR = "/"
        const val ROOT_PATH = "/"

        fun root(): Directory {
            return empty("", "")
        }

        fun empty(parentPath: String, name: String): Directory {
            return Directory(parentPath, name, Collections.emptyList())
        }
    }
}