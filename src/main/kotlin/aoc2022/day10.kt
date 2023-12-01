package aoc2022

import readFileAsStrings
import kotlin.math.abs

fun main() {
    val test = false
    val file = readFileAsStrings(if (test) "sample" else "aoc2022/day10")

    val commandTime: Map<String, Int> = mapOf(Pair("addx", 2), Pair("noop", 1))
    var x = 1
    var t = 0
    var crtLine = ""
    val crt: MutableList<String> = mutableListOf()

    var sum = 0
    file.forEach { line ->
        val parts = line.split(" ")
        val command = parts.first()
        repeat(commandTime[command]!!) {
            // Increase clock
            t++

            // Calculation for Part 1
            if ((t - 20) % 40 == 0) {
                sum += x * t
            }

            // Calculate pixel value for Part2
            val pixel = if (abs(x - (t % 40 - 1)) < 2) "#" else "."

            // Add pixel to line
            crtLine += pixel

            // Complete the line every 40 ticks add it in the crt and reset it
            if (t % 40 == 0) {
                crt.add(crtLine)
                crtLine = ""
            }
        }

        // Increase the register X after step completion
        if (command == "addx") {
            x += parts[1].toInt()
        }
    }

    val sol1 = sum
    println("Part 1: $sol1")

    println("Part 2:")
    crt.forEach { println(it) } // EGJBGCFK
}
