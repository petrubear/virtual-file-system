package emg.kotlin.vfs.commands

import emg.kotlin.vfs.filesystem.State

class Pwd : Command() {
    override fun apply(state: State): State {
        return state.setMessage(state.wd.path())
    }
}