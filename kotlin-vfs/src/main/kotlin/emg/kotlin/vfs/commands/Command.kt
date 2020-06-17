package emg.kotlin.vfs.commands

import emg.kotlin.vfs.extensions.tail
import emg.kotlin.vfs.filesystem.State
import java.util.function.Function


abstract class Command : Function<State, State> {
    companion object {
        private const val MKDIR = "mkdir"
        private const val LS = "ls"
        private const val PWD = "pwd"
        private const val TOUCH = "touch"
        private const val CD = "cd"
        private const val RM = "rm"
        private const val ECHO = "echo"
        private const val CAT = "cat"

        fun from(input: String): Command {
            val tokens = input.split(" ")
            return if (input.isEmpty() || tokens.isEmpty()) {
                emptyCommand()
            } else when (tokens[0]) {
                MKDIR -> if (tokens.size < 2) {
                    incompleteCommand(MKDIR)
                } else {
                    Mkdir(tokens[1])
                }
                LS -> LS()
                PWD -> Pwd()
                TOUCH -> if (tokens.size < 2) {
                    incompleteCommand(TOUCH)
                } else {
                    Touch(tokens[1])
                }
                CD -> if (tokens.size < 2) {
                    incompleteCommand(CD)
                } else {
                    Cd(tokens[1])
                }
                RM -> if (tokens.size < 2) {
                    incompleteCommand(RM)
                } else {
                    Rm(tokens[1])
                }
                ECHO -> if (tokens.size < 2) {
                    incompleteCommand(ECHO)
                } else {
                    Echo(tokens.tail().toTypedArray())
                }
                CAT -> if (tokens.size < 2) {
                    incompleteCommand(CAT)
                } else {
                    Cat(tokens[1])
                }
                else -> UnknownCommand()
            }
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
}