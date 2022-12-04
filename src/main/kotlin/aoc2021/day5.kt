package aoc2021

import array2dOfInt
import readFileAsStrings
import java.lang.Integer.max
import java.lang.Integer.min

fun main() {
    val test = false
    val file = readFileAsStrings(if (test) "sample" else "aoc2021/day5")
    val map = array2dOfInt(1000, 1000)
    val map2 = array2dOfInt(1000, 1000)
    file.forEach { line ->
        val xy = line.split("->")
        val x1y1 = xy[0].trim().split(',')
        val x2y2 = xy[1].trim().split(',')
        val x1 = x1y1[0].toInt()
        val x2 = x2y2[0].toInt()
        val y1 = x1y1[1].toInt()
        val y2 = x2y2[1].toInt()
        if (x1 == x2 || y1 == y2) {
            for (i in min(x1, x2)..max(x1, x2))
                for (i2 in min(y1, y2)..max(y1, y2)) {
                    map[i2][i] += 1
                    map2[i2][i] += 1
                }
        } else {
            var tmpx = x1
            var tmpy = y1
            while (tmpx != x2) {
                map2[tmpy][tmpx] += 1
                tmpx += if (x1 < x2) 1 else -1
                tmpy += if (y1 < y2) 1 else -1
            }
            map2[tmpy][tmpx] += 1
        }
    }

    var count = 0
    map.forEach { count += it.filter { it >= 2 }.count() }
    println("Part 1: $count")

    count = 0
    map2.forEach { count += it.filter { it >= 2 }.count() }
    println("Part 2: $count")
}
