package tools

import java.io.File

const val path = "src/main/resources/"

inline fun <reified T> readFileAs(fileName: String): MutableList<T> =
    File(path + fileName).useLines { lines ->
        lines.map {
            when (T::class) {
                Int::class -> it.toInt()
                Long::class -> it.toLong()
                String::class -> it
                else -> throw Exception("Cannot read file as ${T::class.simpleName}")
            } as T
        }.toMutableList()
    }

fun <T> List<T>.print() {
    this.forEach { println(it) }
    println()
}

fun <K, V> MutableMap<K, V>.print() {
    this.forEach { println("${it.key} : ${it.value}") }
}

inline fun <reified T>MutableList<String>.toMatrix(): Matrix2d<T> {
    val matrix = Matrix2d<T>(this[0].length, this.size)
    for (y in this.indices) {
        for (x in (0 until this[0].length)) {
            matrix[x,y] = when(T::class){
                Int::class -> this[y][x].toString().toInt()
                Long::class -> this[y][x].toString().toLong()
                Char::class -> this[y][x]
                else -> throw Exception("Cannot convert file to Matrix of ${T::class.simpleName}")
            } as T
        }
    }
    return matrix
}

// Use URL (maybe it will come in handy)
// val file = URL("https://adventofcode.com/2020/day/1/input").readText()
