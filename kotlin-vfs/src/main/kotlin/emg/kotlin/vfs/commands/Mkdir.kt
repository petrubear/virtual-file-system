package emg.kotlin.vfs.commands

import emg.kotlin.vfs.files.DirEntry
import emg.kotlin.vfs.files.Directory
import emg.kotlin.vfs.filesystem.State

class Mkdir constructor(private val name: String) : Command() {
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
                doMkdir(state, name)
            }
        }
    }

    private fun doMkdir(state: State, name: String): State {
        val wd = state.wd
        //1 all directories in path
        val allDirsInPath = wd.getAllFoldersInPath()
        //2 create new directory in wd
        val newDirectory = Directory.empty(wd.path(), name)
        //3 update the directory structure
        val newRoot = updateStructure(state.root, allDirsInPath, newDirectory)
        //4 find the new WD
        val newWD = newRoot.findDesendant(allDirsInPath)

        return State.apply(newRoot, newWD)
    }

    private fun updateStructure(currentDirectory: Directory, path: List<String>, newEntry: DirEntry): Directory {
        return if (path.isEmpty()) currentDirectory.addEntry(newEntry)
        else {
            val oldEntry = currentDirectory.findEntry(path[0])!!
            currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry.asDirectory(),
                    path.subList(1, path.size), newEntry))
        }
    }

    private fun checkIllegal(name: String): Boolean {
        return name.contains(".")
    }
}