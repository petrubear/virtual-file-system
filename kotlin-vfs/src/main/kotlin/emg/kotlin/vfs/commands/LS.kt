package emg.kotlin.vfs.commands

import emg.kotlin.vfs.extensions.head
import emg.kotlin.vfs.extensions.tail
import emg.kotlin.vfs.files.DirEntry
import emg.kotlin.vfs.filesystem.State

class LS : Command() {
    private fun createNiceOutput(contents: List<DirEntry>): String {
        return if (contents.isEmpty()) ""
        else {
            val entry = contents.head()
            entry.name + " [${entry.getType()}]\n${createNiceOutput(contents.tail())}"
        }
    }

    override fun apply(state: State): State {
        val contents = state.wd.contents
        val niceOutput = createNiceOutput(contents)
        return state.setMessage(niceOutput)
    }
}