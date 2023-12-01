package aoc2021

import array2dOfInt
import readFileAsStrings

fun main() {
    val test = false
    val file = readFileAsStrings(if (test) "sample" else "aoc2021/day9")

    val lx = file[0].length
    val ly = file.size
    val map = array2dOfInt(lx, ly)
    file.forEachIndexed { y, line ->
        line.forEachIndexed { x, point ->
            map[x][y] = point.toString().toInt()
        }
    }
    val found = 0

    fun basinMarker(
        x: Int,
        y: Int,
    ): Int {
        var newCount = 0
        val v = map[x][y]
        // up
        if (y > 0 && map[x][y - 1] != 9 && (map[x][y - 1] > v)) {
            newCount += 1 + basinMarker(x, y - 1)
            map[x][y - 1] = found
        }
        // down
        if (y < ly - 1 && map[x][y + 1] != 9 && (map[x][y + 1] > v)) {
            newCount += 1 + basinMarker(x, y + 1)
            map[x][y + 1] = found
        }
        // left
        if (x > 0 && map[x - 1][y] != 9 && (map[x - 1][y] > v)) {
            newCount += 1 + basinMarker(x - 1, y)
            map[x - 1][y] = found
        }
        // right
        if (x < lx - 1 && map[x + 1][y] != 9 && (map[x + 1][y] > v)) {
            newCount += 1 + basinMarker(x + 1, y)
            map[x + 1][y] = found
        }

        return newCount
    }

    var count = 0
    val lowestPoints: MutableList<Pair<Int, Int>> = mutableListOf()
    for (y in 0 until ly) {
        for (x in 0 until lx) {
            val up = y == 0 || map[x][y - 1] > map[x][y]
            val down = y == ly - 1 || map[x][y + 1] > map[x][y]
            val left = x == 0 || map[x - 1][y] > map[x][y]
            val right = x == lx - 1 || map[x + 1][y] > map[x][y]

            if (up && down && left && right) {
                count += map[x][y] + 1
                lowestPoints.add(Pair(x, y))
            }
        }
    }

    println("Part 1: $count")

    val basins: MutableList<Int> = mutableListOf()
    lowestPoints.forEach {
        basins.add(1 + basinMarker(it.first, it.second))
        map[it.first][it.second] = found
    }
    basins.sortDescending()
    val count2 = basins[0] * basins[1] * basins[2]

    println("Part 2: $count2")
}
