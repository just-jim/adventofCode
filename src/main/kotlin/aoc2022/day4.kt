package aoc2022

import readFileAsStrings

fun main() {
    val test = true
    val file = readFileAsStrings(if (test) "sample" else "aoc2022/day4")

    fun Pair<Int, Int>.containedIn(otherPair: Pair<Int, Int>): Boolean =
        (this.first >= otherPair.first) and (this.second <= otherPair.second)

    fun Pair<Int, Int>.overlapWith(otherPair: Pair<Int, Int>): Boolean = (
        ((this.first >= otherPair.first) and (this.first <= otherPair.second))
            or ((this.second >= otherPair.first) and (this.second <= otherPair.second))
        )

    var sum = 0
    var sum2 = 0
    file.forEach { line ->
        val assignments = line.split(",")
        val elf1Range = assignments.first().split("-").map { it.toInt() }.zipWithNext().first()
        val elf2Range = assignments[1].split("-").map { it.toInt() }.zipWithNext().first()
        if (elf1Range.containedIn(elf2Range) or elf2Range.containedIn(elf1Range)) {
            sum++
        }
        if (elf1Range.overlapWith(elf2Range) or elf2Range.overlapWith(elf1Range)) {
            sum2++
        }
    }

    val sol1 = sum
    println("Part 1: $sol1")

    val sol2 = sum2
    println("Part 2: $sol2")
}