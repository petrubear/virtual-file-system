package emg.kotlin.vfs.filesystem

import emg.kotlin.vfs.files.Directory

class State constructor(val root: Directory, val wd: Directory, private val output: String) {
    companion object {
        const val SHELL_TOKEN = "[kotlin] $ _> "

        fun apply(root: Directory, wd: Directory, output: String = ""): State {
            return State(root, wd, output)
        }
    }

    fun show() {
        println(output)
        print(SHELL_TOKEN)
    }

    fun setMessage(message: String): State {
        return State(root, wd, message)
    }
}