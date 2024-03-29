package aoc2021

import tools.*
import kotlin.math.abs

fun main() {
    val test = false
    val file = readFileAs<String>(if (test) "sample" else "aoc2021/day6")

    val days = LongArray(7)
    val nextWeekFishes = LongArray(7)
    file[0].split(',').forEach { days[it.toInt()]++ }

    for (i in 0 until 256) {
        if (i == 80) println("Part 1: ${days.sum() + nextWeekFishes.sum()}")
        nextWeekFishes[(i + 2) % 7] += days[i % 7]
        days[abs(i - 1) % 7] += nextWeekFishes[abs(i - 1) % 7]
        nextWeekFishes[abs(i - 1) % 7] = 0
    }
    println("Part 2: ${days.sum() + nextWeekFishes.sum()}")
}
