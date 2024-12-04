package aoc2024

import tools.*
import kotlin.math.abs

fun main() {
    val test = false
    val file = readFileAs<String>(if (test) "sample" else "aoc2024/day1")

    val listA = file.map { it.split("   ")[0].toInt() }.sorted()
    val listB = file.map { it.split("   ")[1].toInt() }.sorted()

    val sol1 = listA.zip(listB).sumOf { (a, b) ->
        abs(a - b)
    }

    println("Part 1: $sol1")

    val dict = listB.groupingBy { it }.eachCount()
    val sol2 = listA.sumOf { it * dict.getOrDefault(it, 0) }

    println("Part 2: $sol2")
}
