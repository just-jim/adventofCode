package aoc2024

import tools.*

private const val test = false

fun main() {
    val file = readFileAs<String>(if (test) "sample" else "aoc2024/day7")

    fun processNumbers(bitmask: Int, numbers: List<Long>, radix: Int): Long {
        val operators = bitmask.toString(radix).padStart(numbers.size-1, '0').toCharArray()
        var calculation = numbers.first()

        operators.forEachIndexed { i, operator ->
            when (operator) {
                '0' -> {
                    calculation+=numbers[i+1]
                }
                '1' -> {
                    calculation*=numbers[i+1]
                }
                else -> {
                    calculation = (calculation.toString() + numbers[i+1].toString()).toLong()
                }
            }
        }

        return calculation
    }

    val sol1 = file.sumOf { line ->
        val parts = line.split(": ")
        val value = parts[0].toLong()
        val numbers = parts[1].split(" ").map { it.toLong() }
        val binary = "1".repeat(numbers.size-1).toInt(2)

        if((0 .. binary).any { processNumbers(it, numbers, 2) == value }) {
            value
        } else {
            0L
        }
    }

    println("Part 1: $sol1")
    // 7885693428401

    val sol2 = file.sumOf { line ->
        val parts = line.split(": ")
        val value = parts[0].toLong()
        val numbers = parts[1].split(" ").map { it.toLong() }
        val binary = "2".repeat(numbers.size-1).toInt(3)

        if((0 .. binary).any { processNumbers(it, numbers, 3) == value }) {
            value
        } else {
            0L
        }
    }

    println("Part 2: $sol2")
    //348360680516005
}
