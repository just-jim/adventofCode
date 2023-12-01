package aoc2022

import array2dOfInt
import print2dMap
import readFileAsStrings

fun main() {
    val test = false
    val file = readFileAsStrings(if (test) "sample" else "aoc2022/day8")

    val map = array2dOfInt(file.first().length, file.size)
    val l = map.size - 1

    file.forEachIndexed { y, line ->
        line.forEachIndexed { x, tree ->
            map[x][y] = tree.toString().toInt()
        }
    }

    print2dMap(map)
    println()

    fun checkIfVisible(
        x: Int,
        y: Int,
    ): String {
        // Is it on edge?
        if (x == 0 || x == l || y == 0 || y == l) return "edge"
        // Is it visible from left?
        if ((0 until x).none { map[it][y] >= map[x][y] }) return "left"
        // is visible from right?
        if ((l downTo x + 1).none { map[it][y] >= map[x][y] }) return "right"
        // is visible from top?
        if ((0 until y).none { map[x][it] >= map[x][y] }) return "top"
        // is visible from bottom?
        if ((l downTo y + 1).none { map[x][it] >= map[x][y] }) return "bottom"

        return "invisible"
    }

    fun calculateScenicScore(
        x: Int,
        y: Int,
    ): Int {
        // from left

        var left = 0
        if (x != 0) {
            run lit@{
                (x - 1 downTo 0).forEach {
                    left++
                    if (map[it][y] >= map[x][y]) return@lit
                }
            }
        }

        // from right
        var right = 0
        if (x != map.size) {
            run lit@{
                (x + 1 until map.size).forEach {
                    right++
                    if (map[it][y] >= map[x][y])return@lit
                }
            }
        }

        // from top
        var top = 0
        if (y != 0) {
            run lit@{
                (y - 1 downTo 0).forEach {
                    top++
                    if (map[x][it] >= map[x][y]) return@lit
                }
            }
        }

        // from bottom
        var bottom = 0
        if (y != map.size) {
            run lit@{
                (y + 1 until map.size).forEach {
                    bottom++
                    if (map[x][it] >= map[x][y]) {
                        return@lit
                    }
                }
            }
        }

        return left * right * top * bottom
    }

    var sum = 0
    var bestScenicScore = 0
    map.indices.forEach { y ->
        map.indices.forEach { x ->
            if (checkIfVisible(x, y) != "invisible") sum++
            val scenicScore = calculateScenicScore(x, y)
            println("[$x,$y] -> ${calculateScenicScore(x,y)}")
            if (scenicScore > bestScenicScore) bestScenicScore = scenicScore
        }
    }

    val sol1 = sum
    println("Part 1: $sol1")

    val sol2 = bestScenicScore
    println("Part 2: $sol2")
}
