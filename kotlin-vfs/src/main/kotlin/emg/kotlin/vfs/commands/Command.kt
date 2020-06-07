package emg.kotlin.vfs.commands

import emg.kotlin.vfs.filesystem.State


abstract class Command {
    companion object {
        fun from(input: String): Command = UnknownCommand()
    }

    abstract fun apply(state: State): State
}