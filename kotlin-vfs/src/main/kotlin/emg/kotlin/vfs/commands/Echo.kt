package emg.kotlin.vfs.commands

import emg.kotlin.vfs.filesystem.State


class Echo(val args: Array<String>) : Command() {
    override fun apply(state: State): State {
        if (args.isEmpty()) return state
        else if (args.size == 1) return state.setMessage(args[0])
        else {
            val operator = args[args.size - 2]
            val fileName = args[args.size - 1]
            val contents = createContent(args, args.size - 2)

            if (">>".equals(operator))
                return doEcho(state, contents, fileName, append = true)
            else if (">".equals(operator))
                return doEcho(state, contents, fileName, append = false)
            else return state.setMessage(createContent(args, args.size))
        }
    }

    private fun doEcho(state: State, contents: String, fileName: String, append: Boolean): State {
        TODO("Not yet implemented")
    }

    private fun createContent(args: Array<String>, topIndex: Int): String {
        fun createContentHelper(currentIndex: Int, accumulator: String): String {
            return if (currentIndex >= topIndex) accumulator
            else createContentHelper(currentIndex + 1, "$accumulator ${args[currentIndex]}")
        }

        return createContentHelper(0, "")
    }
}