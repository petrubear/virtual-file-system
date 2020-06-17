package emg.kotlin.vfs.commands

import emg.kotlin.vfs.extensions.head
import emg.kotlin.vfs.extensions.tail
import emg.kotlin.vfs.files.Directory
import emg.kotlin.vfs.filesystem.State

class Rm(val name: String) : Command() {
    override fun apply(state: State): State {
        // 1 get working dir
        val wd = state.wd
        // 2 get absolute path
        val absolutePath = when {
            name.startsWith(Directory.SEPARATOR) -> name
            wd.isRoot() -> wd.path() + name
            else -> wd.path() + Directory.SEPARATOR + name
        }
        // 3 do some checks
        return if (Directory.ROOT_PATH == absolutePath)
            state.setMessage("Cannot drop Root")
        else
            doRm(state, absolutePath)
    }


    private fun doRm(state: State, path: String): State {
        fun rmHelper(currentDirectory: Directory, path: List<String>): Directory {
            return if (path.isEmpty()) currentDirectory
            else if (path.tail().isEmpty()) currentDirectory.removeEntry(path.head())
            else {
                val nextDirectory = currentDirectory.findEntry(path.head())
                if (!nextDirectory!!.isDirectory()) currentDirectory
                else {
                    val newNextDirectory = rmHelper(nextDirectory.asDirectory(), path.tail())
                    if (newNextDirectory == nextDirectory) currentDirectory
                    else currentDirectory.replaceEntry(path.head(), newNextDirectory)
                }
            }
        }

        // 4 find the entry to remove
        // 5 update the structure
        val tokens = path.substring(1).split(Directory.SEPARATOR).toList()
        val newRoot: Directory = rmHelper(state.root, tokens)
        return if (newRoot == state.root)
            state.setMessage("$path: no such file or directory")
        else State.apply(newRoot, newRoot.findDesendant(state.wd.path().substring(1)))

    }
}
