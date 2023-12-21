package aoc2021

import tools.*
import kotlin.math.absoluteValue

fun main() {
    class Submarine {
        var x: Int = 0
        var y: Int = 0
        var aim: Int = 0

        fun navigate(
            command: String,
            value: Int,
        ) {
            when (command) {
                "forward" -> x += value
                "down" -> y += value
                "up" -> y -= value
            }
        }

        fun navigate2(
            command: String,
            value: Int,
        ) {
            when (command) {
                "forward" -> {
                    x += value
                    y += value * aim
                }
                "down" -> aim += value
                "up" -> aim -= value
            }
        }

        fun sol(): Int {
            return x.absoluteValue * y.absoluteValue
        }
    }

    val submarine = Submarine()
    val submarine2 = Submarine()

    val test = false
    val cords = readFileAs<String>(if (test) "sample" else "aoc2021/day2")
    cords.forEach { line ->
        val command = line.split(" ")[0]
        val value = line.split(" ")[1].toInt()
        submarine.navigate(command, value)
        submarine2.navigate2(command, value)
    }

    println("Part1: ${submarine.sol()}")
    println("Part2: ${submarine2.sol()}")
}
