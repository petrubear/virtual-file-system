package emg.kotlin.vfs.commands

import emg.kotlin.vfs.extensions.head
import emg.kotlin.vfs.extensions.init
import emg.kotlin.vfs.extensions.tail
import emg.kotlin.vfs.files.DirEntry
import emg.kotlin.vfs.files.Directory
import emg.kotlin.vfs.filesystem.State

class Cd(private val dir: String) : Command() {
    override fun apply(state: State): State {
        //1 find root
        val root = state.root
        val wd = state.wd
        // find the absolute path of the fir i want to cd to
        val absolutePath = when {
            dir.startsWith(Directory.SEPARATOR) -> dir
            wd.isRoot() -> wd.path() + dir
            else -> wd.path() + Directory.SEPARATOR + dir
        }
        // find the directory I want to cd to
        val destinationDirectory = doFindEntry(root, absolutePath)
        // change the state based on new directory
        return if (destinationDirectory == null || !destinationDirectory.isDirectory())
            state.setMessage("$dir no such directory")
        else
            State.apply(root, destinationDirectory.asDirectory())
    }

    private fun doFindEntry(root: Directory, absolutePath: String): DirEntry? {
        // 1 tokens
        val tokens: List<String> = absolutePath.substring(1).split(Directory.SEPARATOR).toList()
        // eliminate . tokens
        val newTokens = collapseRelativeTokens(tokens, ArrayList()) ?: return null

        // 2 navigate
        return findEntryHelper(root, newTokens)
    }

    private fun findEntryHelper(currentDirectory: Directory, path: List<String>): DirEntry? {
        return if (path.isEmpty() || path.head().isEmpty()) currentDirectory
        else if (path.tail().isEmpty()) currentDirectory.findEntry(path.head())
        else {
            val nextDir = currentDirectory.findEntry(path.head())
            if (nextDir == null || !nextDir.isDirectory()) null
            else findEntryHelper(nextDir.asDirectory(), path.tail())
        }
    }

    private fun collapseRelativeTokens(path: List<String>, result: MutableList<String>): List<String>? {
        return if (path.isEmpty()) result
        else if ("." == path.head()) collapseRelativeTokens(path.tail(), result)
        else if (".." == path.head()) {
            if (result.isEmpty()) null
            else collapseRelativeTokens(path.tail(), result.init())
        } else {
            result.add(path.head())
            collapseRelativeTokens(path.tail(), result)
        }
    }

}