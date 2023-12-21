package aoc2023

import tools.*

fun main() {
    val test = false
    val file = readFileAs<String>(if (test) "sample" else "aoc2023/day2")

    val limitations = mapOf("red" to 12, "green" to 13, "blue" to 14)

    var sum = 0

    file.forEach { line ->
        val id = line.split(':').first().split(' ')[1].toInt()
        val parts = line.split(':')[1].trim().split(';')
        run lit@{
            parts.forEach {
                it.split(',').forEach { batch ->
                    val (n, color) = batch.trim().split(' ')
                    if (n.toInt() > limitations[color]!!) {
                        return@lit
                    }
                }
            }
            sum += id
        }
    }

    val sol1 = sum
    println("Part 1: $sol1")

    sum = 0
    var maxOfColor: MutableMap<String, Int>

    file.forEach { game ->
        val sets = game.split(':')[1].trim().split(';')

        maxOfColor = mutableMapOf("red" to 0, "green" to 0, "blue" to 0)
        sets.forEach { set ->
            set.split(',').forEach { batch ->
                val (n, color) = batch.trim().split(' ')
                if (n.toInt() > maxOfColor[color]!!) {
                    maxOfColor[color] = n.toInt()
                }
            }
        }
        sum += maxOfColor["red"]!! * maxOfColor["green"]!! * maxOfColor["blue"]!!
    }

    val sol2 = sum
    println("Part 2: $sol2")
}
