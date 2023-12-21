package aoc2021

import tools.*
import java.lang.Integer.max
import java.lang.Integer.min

fun main() {
    val test = false
    val file = readFileAs<String>(if (test) "sample" else "aoc2021/day5")
    val map = Matrix2d<Int>(1000, 1000).also { it.initialize(0) }
    val map2 = Matrix2d<Int>(1000, 1000).also { it.initialize(0) }
    file.forEach { line ->
        val xy = line.split("->")
        val (x1,y1) = xy[0].trim().split(',').map { it.toInt() }
        val (x2,y2) = xy[1].trim().split(',').map { it.toInt() }
        if (x1 == x2 || y1 == y2) {
            for (y in min(y1, y2)..max(y1, y2))
                for (x in min(x1, x2)..max(x1, x2)){
                    map[x,y] += 1
                    map2[x,y] += 1
                }
        } else {
            var tmpx = x1
            var tmpy = y1
            while (tmpx != x2) {
                map2[tmpx,tmpy] += 1
                tmpx += if (x1 < x2) 1 else -1
                tmpy += if (y1 < y2) 1 else -1
            }
            map2[tmpx,tmpy] += 1
        }
    }

    var count = 0
    map.forEach { if( it>= 2) count++ }
    println("Part 1: $count")

    count = 0
    map2.forEach { if( it>= 2) count++ }
    println("Part 2: $count")
}
