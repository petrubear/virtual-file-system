package emg.kotlin.vfs.commands

import emg.kotlin.vfs.filesystem.State


abstract class Command {
    companion object {
        private const val MKDIR = "mkdir"
        private const val LS = "ls"
        private const val PWD = "pwd"
        private const val TOUCH = "touch"
        private const val CD = "cd"

        fun from(input: String): Command {
            val tokens = input.split(" ")
            if (input.isEmpty() || tokens.isEmpty()) {
                return emptyCommand()
            } else if (MKDIR.equals(tokens[0])) {
                return if (tokens.size < 2) {
                    incompleteCommand(MKDIR)
                } else {
                    Mkdir(tokens[1])
                }
            } else if (LS.equals(tokens[0])) {
                return LS()
            } else if (PWD.equals(tokens[0])) {
                return Pwd()
            } else if (TOUCH.equals(tokens[0])) {
                return if (tokens.size < 2) {
                    incompleteCommand(TOUCH)
                } else {
                    Touch(tokens[1])
                }
            } else if (CD.equals(tokens[0])) {
                return if (tokens.size < 2) {
                    incompleteCommand(CD)
                } else {
                    Cd(tokens[1])
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