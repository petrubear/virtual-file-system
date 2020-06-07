package emg.kotlin.vfs.commands

import emg.kotlin.vfs.filesystem.State

class UnknownCommand : Command() {
    override fun apply(state: State): State {
        return state.setMessage("Command not found!")
    }
}