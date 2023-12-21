package aoc2021

import tools.*

fun main() {
    val test = false
    val file = readFileAs<String>(if (test) "sample" else "aoc2021/day13")

    val cords = mutableListOf<Pair<Int, Int>>()
    val folds = mutableListOf<Pair<Char, Int>>()

    file.forEach {
        if (it.contains(',')) {
            cords.add(Pair(it.split(',')[0].toInt(), it.split(',')[1].toInt()))
        } else if (it.contains("fold along")) {
            val foldInstr = it.split(' ')[2].split('=')
            folds.add(Pair(foldInstr[0][0], foldInstr[1].toInt()))
        }
    }

    val lx = cords.maxOf { it.first }
    val ly = cords.maxOf { it.second }

    val initMap = Matrix2d<Int>(lx + 1, ly + 1).also { it.initialize(0) }

    cords.forEach {
        initMap[it.first,it.second] = 1
    }

    fun printMap(map: Matrix2d<Int>) {
        for (y in map.yRange()) {
            for (x in map.xRange()) {
                if (map[x,y] == 0) {
                    print('.')
                } else {
                    print('@')
                }
                print(' ')
            }
            println()
        }
        println()
    }

    fun fold(
        axes: Char,
        index: Int,
        map: Matrix2d<Int>,
    ): Matrix2d<Int> {
        lateinit var newMap: Matrix2d<Int>
        if (axes == 'y') {
            newMap = Matrix2d(map.cols,index)
            for (y in 0 until index)
                for (x in map.xRange())
                    newMap[x,y] = map[x,y]
            for (y in index + 1 until map.rows)
                for (x in map.xRange())
                    newMap[x,2 * index - y] = map[x,2 * index - y] + map[x,y]
        } else if (axes == 'x') {
            newMap = Matrix2d(index,map.rows)
            for (y in map.yRange())
                for (x in 0 until index)
                    newMap[x,y] = map[x,y]
            for (y in map.yRange())
                for (x in index + 1 until map.cols)
                    newMap[2 * index - x,y] = map[2 * index - x,y] + map[x,y]
        }
        return newMap
    }

    val fold1 = fold(folds[0].first, folds[0].second, initMap)

    var sol1 = 0
    fold1.forEach { if(it > 0 ) sol1++ }
    println("Part 1: $sol1")

    var map = initMap
    folds.forEach {
        map = fold(it.first, it.second, map)
    }

    println("Part 2:")
    printMap(map)
}
