package aoc2023

import tools.*

private const val test = false

data class Galaxy(
    var cords: Cords
)

fun main() {
    val file = readFileAs<String>(if (test) "sample" else "aoc2023/day11")
    val map = file.toMatrix<Char>()

    // identify which rows and columns to expand
    val expandXOn = mutableListOf<Int>()
    for (x in map.xRange()) {
        if (map.col(x).all { it == '.' }) {
            expandXOn.add(x)
        }
    }
    val expandYOn = mutableListOf<Int>()
    for (y in map.yRange()) {
        if (map.row(y).all { it == '.' }) {
            expandYOn.add(y)
        }
    }

    fun <T> getAllPairs(list: List<T>): List<Pair<T, T>> {
        val result = mutableListOf<Pair<T, T>>()
        for (i in 0 until list.size - 1) {
            for (j in i + 1 until list.size) {
                result.add(list[i] to list[j])
            }
        }
        return result
    }

    fun calculateSumOfDistances(originalMultiplier: Int): Long {
        val multiplier = originalMultiplier - 1
        val galaxies = mutableListOf<Galaxy>()
        map.foreachIndexed { cords, it ->
            if (it == '#') galaxies.add(Galaxy(cords))
        }

        // modify Galaxy cords
        galaxies.forEach {
            val expandedXs = expandXOn.count { x -> x < it.cords.x }
            val expandedYs = expandYOn.count { y -> y < it.cords.y }
            it.cords = Cords(it.cords.x + (expandedXs * multiplier), it.cords.y + (expandedYs * multiplier))
        }

        return getAllPairs(galaxies).sumOf { it.first.cords.manhatanTo(it.second.cords) }
    }

    val sol1 = calculateSumOfDistances(2)
    println("Part 1: $sol1")


    val sol2 = calculateSumOfDistances(1000000)
    println("Part 2: $sol2")
}