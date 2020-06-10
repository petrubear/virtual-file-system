package emg.kotlin.vfs.commands

import emg.kotlin.vfs.files.DirEntry
import emg.kotlin.vfs.filesystem.State

class LS : Command() {
    fun createNiceOutput(contents: List<DirEntry>): String {
        if (contents.isEmpty()) return ""
        else {
            val entry = contents.get(0)
            return entry.name + " [${entry.getType()}]\n${createNiceOutput(contents.subList(1, contents.size))}"
        }
    }

    override fun apply(state: State): State {
        val contents = state.wd.contents
        val niceOutput = createNiceOutput(contents)
        return state.setMessage(niceOutput)
    }
}