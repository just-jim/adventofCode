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
                else -> throw Exception("Cannot read file as ${T::class}")
            } as T
        }.toMutableList()
    }

fun List<String>.print() {
    this.forEach { println(it) }
    println()
}

fun MutableMap<String, Int>.print() {
    this.forEach { println("${it.key} : ${it.value}") }
}

// Use URL (maybe it will come in handy)
// val file = URL("https://adventofcode.com/2020/day/1/input").readText()
