package emg.kotlin.vfs.commands

import emg.kotlin.vfs.files.DirEntry
import emg.kotlin.vfs.filesystem.State

class LS : Command() {
    private fun createNiceOutput(contents: List<DirEntry>): String {
        return if (contents.isEmpty()) ""
        else {
            val entry = contents[0]
            entry.name + " [${entry.getType()}]\n${createNiceOutput(contents.subList(1, contents.size))}"
        }
    }

    override fun apply(state: State): State {
        val contents = state.wd.contents
        val niceOutput = createNiceOutput(contents)
        return state.setMessage(niceOutput)
    }
}