package aoc2020

import tools.*
import kotlin.math.ceil

fun main() {
    val seats = "0".repeat(128 * 8).toCharArray()
    val data = readFileAs<String>("aoc2020/day5")
    var max = 0
    data.forEach { line ->
        var yF = 0
        var yB = 128
        var xL = 0
        var xR = 8
        line.forEach {
            when (it) {
                'F' -> yB -= ceil((((yB - yF) / 2).toDouble())).toInt()
                'B' -> yF += ceil((((yB - yF) / 2).toDouble())).toInt()
                'L' -> xR -= ceil((((xR - xL) / 2).toDouble())).toInt()
                'R' -> xL += ceil((((xR - xL) / 2).toDouble())).toInt()
            }
        }

        // Part 1
        val sol = yF * 8 + xL
        if (sol > max) {
            max = sol
        }

        // Part 2
        seats[yF * 8 + xL] = '1'
    }

    val index = seats.joinToString(separator = "").indexOf("101") + 1
    val mySeat = index + (index % 8)

    println("Part 1: $max")
    println("Part 2: $mySeat")
}
