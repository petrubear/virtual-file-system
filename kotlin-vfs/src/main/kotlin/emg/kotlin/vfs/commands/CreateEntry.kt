package emg.kotlin.vfs.commands

import emg.kotlin.vfs.extensions.head
import emg.kotlin.vfs.extensions.tail
import emg.kotlin.vfs.files.DirEntry
import emg.kotlin.vfs.files.Directory
import emg.kotlin.vfs.filesystem.State

abstract class CreateEntry constructor(private val name: String) : Command() {
    override fun apply(state: State): State {
        val wd = state.wd
        return when {
            wd.hasEntry(name) -> {
                state.setMessage("Directory $name already exists")
            }
            name.contains(Directory.SEPARATOR) -> {
                state.setMessage("$name must not contain separators")
            }
            checkIllegal(name) -> {
                state.setMessage("$name is illegal entry name")
            }
            else -> {
                doCreateEntry(state, name)
            }
        }
    }

    private fun doCreateEntry(state: State, name: String): State {
        val wd = state.wd
        //1 all directories in path
        val allDirsInPath = wd.getAllFoldersInPath()
        //2 create new directory in wd
        val newDirectory = createSpecificEntry(state, name)
        //3 update the directory structure
        val newRoot = updateStructure(state.root, allDirsInPath, newDirectory)
        //4 find the new WD
        val newWD = newRoot.findDesendant(allDirsInPath)

        return State.apply(newRoot, newWD)
    }

    private fun updateStructure(currentDirectory: Directory, path: List<String>, newEntry: DirEntry): Directory {
        return if (path.isEmpty()) currentDirectory.addEntry(newEntry)
        else {
            val oldEntry = currentDirectory.findEntry(path.head())!!
            currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry.asDirectory(),
                    path.tail(), newEntry))
        }
    }

    private fun checkIllegal(name: String): Boolean {
        return name.contains(".")
    }

    abstract fun createSpecificEntry(state: State, entryName: String): DirEntry

}