package aoc2022

import tools.*

fun main() {
    val test = false
    val file = readFileAs<String>(if (test) "sample" else "aoc2022/day6")
    val signal = file.first()

    fun String.detectMarker(bufferLength: Int): Int? {
        (bufferLength until this.length).forEach {
            val buffer = this.subSequence(it - bufferLength, it).toList()
            if (buffer.distinct().count() == buffer.count()) {
                return it
            }
        }
        return null
    }

    val sol1 = signal.detectMarker(4)
    println("Part 1: $sol1")

    val sol2 = signal.detectMarker(14)
    println("Part 2: $sol2")
}
