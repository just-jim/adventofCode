package aoc2021

import tools.*

fun main() {
    val test = false
    val file = readFileAs<String>(if (test) "sample" else "aoc2021/day11")

    val l = 10
    val map = Matrix2d<Int>(l, l)
    file.forEachIndexed { y, line ->
        line.forEachIndexed { x, point ->
            map[x,y] = point.toString().toInt()
        }
    }

    fun plusOne(
        x: Int,
        y: Int,
    ) {
        if (x in 0 until l && y in 0 until l) {
            if (map[x,y] < 10) {
                map[x,y]++
                if (map[x,y] == 10) {
                    plusOne(x - 1, y - 1)
                    plusOne(x, y - 1)
                    plusOne(x + 1, y - 1)
                    plusOne(x - 1, y)
                    plusOne(x + 1, y)
                    plusOne(x - 1, y + 1)
                    plusOne(x, y + 1)
                    plusOne(x + 1, y + 1)
                }
            }
        }
    }

    fun night(): Int {
        var flashes = 0
        for (y in 0 until l) {
            for (x in 0 until l) {
                if (map[x,y] == 10) {
                    map[x,y] = 0
                    flashes++
                }
            }
        }
        return flashes
    }

    fun day() {
        for (y in 0 until l) {
            for (x in 0 until l) {
                plusOne(x, y)
            }
        }
    }

    fun printMap() {
        for (y in 0 until l) {
            for (x in 0 until l) {
                if (map[x,y] == 0) {
                    print('.')
                } else {
                    print(map[x,y])
                }
                print(' ')
            }
            println()
        }
        println()
    }

    var flashes: Long = 0
    var flashesPerDay = 0
    var i = 0
    while (flashesPerDay != l * l) {
        i++
        day()
        flashesPerDay = night()
        flashes += flashesPerDay
        if (i == 100) {
            println("Part 1: $flashes")
        }
        // printMap()
    }
    println("Part 2: $i")
}
