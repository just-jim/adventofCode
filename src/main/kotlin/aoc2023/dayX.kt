package aoc2023

import readFileAsStrings

private const val test = true

fun main() {
    val file = readFileAsStrings(if (test) "sample" else "aoc2023/dayX")

    var sum = 0
    file.forEach { line ->
    }

    val sol1 = sum
    println("Part 1: $sol1")

    sum = 0

    val sol2 = sum
    println("Part 2: $sol2")
}