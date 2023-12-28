package aoc2023

import tools.*

fun String.findIndexesOf(char: Char): List<Int> {
    val regex = Regex("\\$char")
    val matches = regex.findAll(this)
    return matches.map { it.range.first }.toList()
}

fun MutableList<Char>.springsCount(): List<Int> {
    var count = 0
    var onSpring = false
    val result = mutableListOf<Int>()
    this.forEach {
        if (it == '#') {
            count++
            onSpring = true
        } else {
            if (onSpring) {
                result.add(count)
            }
            count = 0
            onSpring = false
        }
    }
    if (this.last() == '#') {
        result.add(count)
    }
    return result
}

fun String.possibleArrangements(): Long {
    var sum = 0L
    val parts = this.split(" ")
    val springs = parts.first().toMutableList()
    val report = parts.last().split(',').map { it.toInt() }

    val indexesOfUnknown = springs.joinToString("").findIndexesOf('?')

    val target = Integer.parseInt("1".repeat(indexesOfUnknown.size), 2)

    for (x in (0..target)) {
        val binary = Integer.toBinaryString(x).padStart(indexesOfUnknown.size, '0')
        val newSprings = springs.toMutableList()
        indexesOfUnknown.forEachIndexed { i, it ->
            newSprings[it] = if (binary[i] == '0') '.' else '#'
        }
        if (report == newSprings.springsCount())
            sum++
    }
    return sum
}

private const val test = true

fun main() {
    val file = readFileAs<String>(if (test) "sample" else "aoc2023/day12")

    var sum = 0L
    file.forEach { line ->
        sum += line.possibleArrangements()
    }

    val sol1 = sum
    println("Part 1: $sol1")

    sum = 0L
    file.forEach { line ->
        val parts = line.split(" ")
        val newLine = List(5) { parts.first() }.joinToString("?") + " " + List(5) { parts.last() }.joinToString(",")
        println(newLine)
        sum += newLine.possibleArrangements()
    }

    val sol2 = sum
    println("Part 2: $sol2")
}
