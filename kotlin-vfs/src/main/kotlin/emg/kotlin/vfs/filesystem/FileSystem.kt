package emg.kotlin.vfs.filesystem

import emg.kotlin.vfs.commands.Command
import emg.kotlin.vfs.files.Directory
import java.util.*

class FileSystem {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            FileSystem().main()
        }
    }

    private val scanner = Scanner(System.`in`)
    private val root = Directory.root()
    private var state = State.apply(root, root)

    fun main() {
        while (true) {
            state.show()
            val input = scanner.nextLine()
            state = Command.from(input).apply(state)
        }
    }
}