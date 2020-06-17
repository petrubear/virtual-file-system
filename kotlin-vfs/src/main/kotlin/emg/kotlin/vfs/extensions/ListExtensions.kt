package emg.kotlin.vfs.extensions


fun <T> List<T>.head(): T {
    return this[0]
}

fun <T> List<T>.tail(): List<T> {
    return this.subList(1, this.size)
}

fun <T> MutableList<T>.init(): MutableList<T> {
    return this.subList(0, this.size - 1)
}
