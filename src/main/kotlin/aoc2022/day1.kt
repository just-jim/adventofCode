package aoc2022

import readFileAsStrings

fun main() {
    val test = false
    val file = readFileAsStrings(if (test) "sample" else "aoc2022/day1")

    val calories = file
        .flatMapIndexed { index, x ->
            when {
                index == 0 || index == file.lastIndex -> listOf(index)
                x.isBlank() -> listOf(index - 1, index + 1)
                else -> emptyList()
            }
        }.windowed(size = 2, step = 2) {
                (from, to) ->
            file.slice(from..to)
        }.map {
            it.sumOf { num -> num.toInt() }
        }.sortedDescending()

    val sol1 = calories.first()
    println("Part 1: $sol1")

    val sol2 = calories.take(3).sum()
    println("Part 2: $sol2")
}