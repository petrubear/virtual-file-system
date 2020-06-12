package emg.kotlin.vfs.commands

import emg.kotlin.vfs.files.DirEntry
import emg.kotlin.vfs.files.File
import emg.kotlin.vfs.filesystem.State

class Touch(name: String) : CreateEntry(name) {
    override fun createSpecificEntry(state: State, entryName: String): DirEntry {
        return File.empty(state.wd.path(), entryName)
    }

}