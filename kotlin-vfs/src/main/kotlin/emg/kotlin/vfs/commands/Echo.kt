package emg.kotlin.vfs.commands

import emg.kotlin.vfs.files.Directory
import emg.kotlin.vfs.files.File
import emg.kotlin.vfs.filesystem.State


class Echo(val args: Array<String>) : Command() {
    override fun apply(state: State): State {
        return when {
            args.isEmpty() -> state
            args.size == 1 -> state.setMessage(args[0])
            else -> {
                val operator = args[args.size - 2]
                val fileName = args[args.size - 1]
                val contents = createContent(args, args.size - 2)

                when (operator) {
                    ">>" -> doEcho(state, contents, fileName, append = true)
                    ">" -> doEcho(state, contents, fileName, append = false)
                    else -> state.setMessage(createContent(args, args.size))
                }
            }
        }
    }


    private fun getRootAfterEcho(currentDirectory: Directory, path: List<String>, contents: String, append: Boolean): Directory {
        return if (path.isEmpty()) currentDirectory
        else if (path.subList(1, path.size).isEmpty()) {
            val dirEntry = currentDirectory.findEntry(path[0])
            when {
                dirEntry == null -> currentDirectory.addEntry(File(currentDirectory.path(), path[0], contents))
                dirEntry.isDirectory() -> currentDirectory
                append -> currentDirectory.replaceEntry(path[0], dirEntry.asFile().appendContents(contents))
                else -> currentDirectory.replaceEntry(path[0], dirEntry.asFile().setContents(contents))
            }
        } else {
            val nextDirectory = currentDirectory.findEntry(path[0])!!.asDirectory()
            val newNextDirectory = getRootAfterEcho(nextDirectory, path.subList(1, path.size), contents, append)

            if (newNextDirectory == nextDirectory) currentDirectory
            else currentDirectory.replaceEntry(path[0], newNextDirectory)
        }
    }

    private fun doEcho(state: State, contents: String, fileName: String, append: Boolean): State {
        return if (fileName.contains(Directory.SEPARATOR)) {
            state.setMessage("Echo: filename must not contain separators")
        } else {
            val foldersInPath = state.wd.getAllFoldersInPath().toMutableList()
            foldersInPath.add(fileName)
            val newRoot: Directory = getRootAfterEcho(state.root, foldersInPath, contents, append)
            if (newRoot == state.root)
                state.setMessage("$fileName: No such file or directory")
            else
                State.apply(newRoot, newRoot.findDesendant(state.wd.getAllFoldersInPath()))
        }
    }

    private fun createContent(args: Array<String>, topIndex: Int): String {
        fun createContentHelper(currentIndex: Int, accumulator: String): String {
            return if (currentIndex >= topIndex) accumulator
            else createContentHelper(currentIndex + 1, "$accumulator ${args[currentIndex]}")
        }

        return createContentHelper(0, "")
    }
}