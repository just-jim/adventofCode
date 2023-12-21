package aoc2020

import tools.*

fun main() {
    // Create the map
    val mapFile = readFileAs<String>("aoc2020/day3")
    val map = Matrix2d<Int>(mapFile[0].length, mapFile.size)
    mapFile.forEachIndexed { i, line ->
        line.forEachIndexed { i2, tile ->
            map[i2,i] = if (tile == '.') 0 else 1
        }
    }

    // Part 1
    val stepX = 3
    val stepY = 1
    var x = 0
    var trees = 0
    for (y in stepY until map.rows step stepY) {
        if (y <= map.rows) {
            x += stepX
            if (map[x % map.cols,y] == 1) {
                trees++
            }
        }
    }
    println("Part 1: $trees")

    // Part 2
    val stepList: Array<Pair<Int, Int>> = Array(5) { Pair(1, 1) }
    stepList[0] = Pair(1, 1)
    stepList[1] = Pair(3, 1)
    stepList[2] = Pair(5, 1)
    stepList[3] = Pair(7, 1)
    stepList[4] = Pair(1, 2)

    var sol = 1
    stepList.forEach { steps ->
        trees = 0
        x = 0
        for (y in steps.second until map.rows step steps.second) {
            if (y <= map.rows) {
                x += steps.first
                if (map[x % map.cols,y] == 1) {
                    trees++
                }
            }
        }
        sol *= trees
    }

    println("Part 2: $sol")
}
