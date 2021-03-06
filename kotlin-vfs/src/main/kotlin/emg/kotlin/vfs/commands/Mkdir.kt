package emg.kotlin.vfs.commands

import emg.kotlin.vfs.files.DirEntry
import emg.kotlin.vfs.files.Directory
import emg.kotlin.vfs.filesystem.State

class Mkdir(name: String) : CreateEntry(name) {
    override fun createSpecificEntry(state: State, entryName: String): DirEntry {
        return Directory.empty(state.wd.path(), entryName)
    }
}