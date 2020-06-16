package emg.kotlin.vfs.commands

import emg.kotlin.vfs.filesystem.State

class Cat(private val fileName: String) : Command() {
    override fun apply(state: State): State {
        val wd = state.wd
        val dirEntry = wd.findEntry(fileName)
        return if (dirEntry == null || !dirEntry.isFile())
            state.setMessage("$fileName: no such File.")
        else
            state.setMessage(dirEntry.asFile().contents)
    }
}