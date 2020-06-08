package emg.kotlin.vfs.commands

import emg.kotlin.vfs.filesystem.State


abstract class Command {
    companion object {
        private const val MKDIR = "mkdir"
        fun from(input: String): Command {
            val tokens = input.split(" ")
            if (input.isEmpty() || tokens.isEmpty()) {
                return emptyCommand()
            } else {
                if (MKDIR.equals(tokens[0])) {
                    if (tokens.size < 2) {
                        return incompleteCommand(MKDIR)
                    } else {
                        return Mkdir(tokens[1])
                    }
                }
            }
            return UnknownCommand()
        }

        private fun incompleteCommand(name: String): Command {
            return object : Command() {
                override fun apply(state: State): State {
                    return state.setMessage("$name: Incompete command!")
                }

            }

        }

        private fun emptyCommand(): Command {
            return object : Command() {
                override fun apply(state: State): State {
                    return state
                }
            }
        }
    }

    abstract fun apply(state: State): State
}