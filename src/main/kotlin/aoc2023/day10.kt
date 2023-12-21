package aoc2023

import tools.*
import kotlin.math.min

private const val test = true

fun main() {
    data class Tile (
        val map: Matrix2d<Tile>,
        var cords: Cords,
        var type: Char,
        val directions: MutableSet<Int> = mutableSetOf()
    ){
        override fun toString(): String {
            return type.toString()
        }

        fun print() {
            println("$type: $cords")
        }
    }

    fun Tile.exits(): List<Cords> {
        return when(type) {
            '-' -> listOf(Cords(cords.x-1,cords.y), Cords(cords.x+1,cords.y))
            '7' -> listOf(Cords(cords.x-1,cords.y), Cords(cords.x,cords.y+1))
            'J' -> listOf(Cords(cords.x,cords.y-1), Cords(cords.x-1,cords.y))
            '|' -> listOf(Cords(cords.x,cords.y-1), Cords(cords.x,cords.y+1))
            'L' -> listOf(Cords(cords.x,cords.y-1), Cords(cords.x+1,cords.y))
            'F' -> listOf(Cords(cords.x,cords.y+1), Cords(cords.x+1,cords.y))
            else -> listOf()
        }
    }

    fun Tile.next(comingFrom: Tile): Tile? = this.exits().firstOrNull { it != comingFrom.cords }?.let { map[it] }

    val file = readFileAs<String>(if (test) "sample" else "aoc2023/day10")

    val map = Matrix2d<Tile>(file[0].length, file.size)

    var start = Tile(map, Cords(0,0), '0')
    file.forEachIndexed { y, line ->
        line.forEachIndexed { x, type ->
            map[x,y] = Tile(map, Cords(x,y), type)
            if(type == 'S') start = map[x,y]
        }
    }

    val possibleStartingPoints = listOf(
        map.tryGet(start.cords.up()),
        map.tryGet(start.cords.right()),
        map.tryGet(start.cords.down()),
        map.tryGet(start.cords.left())
    ).filter { it?.exits()?.contains(start.cords) ?: false }

    var previous = start
    var cur = possibleStartingPoints.firstOrNull()
    val path = mutableListOf<Tile>()

    while (cur != start) {
        path.add(cur!!)
        val tmp = cur
        cur = cur.next(previous)
        previous = tmp
    }

    val sol1 = (path.size/2) + 1
    println("Part 1: $sol1")

    var sum = 0

    // Add start to the path
    path.add(start)

    // Clean up the map
    map.forEach { tile ->
        if (tile !in path) {
            tile.type = '.'
        }
    }

    // Identify and set the type of the start tile
    val upPossibilities = if(setOf('7','|','F').contains(map.tryGet(start.cords.up())?.type)) setOf('J','|','L') else setOf()
    val downPossibilities = if(setOf('J','|','L').contains(map.tryGet(start.cords.down())?.type)) setOf('7','|','F') else setOf()
    val leftPossibilities = if(setOf('F','-','L').contains(map.tryGet(start.cords.left())?.type)) setOf('J','-','7') else setOf()
    val rightPossibilities = if(setOf('7','-','J').contains(map.tryGet(start.cords.right())?.type)) setOf('F','-','L') else setOf()
    start.type = listOf(upPossibilities,downPossibilities,leftPossibilities,rightPossibilities)
        .filter { it.isNotEmpty() }
        .reduce { acc, it -> acc.intersect(it)}
        .first()

    val cornerWalls = listOf('7','J','L','F')
    val verticalWalls = cornerWalls + listOf('|')
    val horizontalWalls = cornerWalls + listOf('-')
    map.forEach { tile ->
        if (tile.type == '.') {
            val leftWalls = (0 until tile.cords.x)
                .map{ map[it,tile.cords.y] }
                .filter { verticalWalls.contains(it.type) }
                .groupingBy { it.type }
                .eachCount()

            val rightWalls = (tile.cords.x+1 until map.cols)
                .map{ map[it,tile.cords.y] }
                .filter { verticalWalls.contains(it.type) }
                .groupingBy { it.type }
                .eachCount()

            val upWalls = (0 until tile.cords.y)
                .map{ map[tile.cords.x,it] }
                .filter { horizontalWalls.contains(it.type) }
                .groupingBy { it.type }
                .eachCount()

            val downWalls = (tile.cords.y+1 until map.rows)
                .map{ map[tile.cords.x,it] }
                .filter { horizontalWalls.contains(it.type) }
                .groupingBy { it.type }
                .eachCount()

            if((leftWalls.isEmpty() || rightWalls.isEmpty() || upWalls.isEmpty() || downWalls.isEmpty()))
                return@forEach

            val leftCount = leftWalls.getOrDefault('|',0) + min(leftWalls.getOrDefault('L',0),leftWalls.getOrDefault('7',0)) + min(leftWalls.getOrDefault('F',0),leftWalls.getOrDefault('J',0))
            val rightCount = rightWalls.getOrDefault('|',0) + min(rightWalls.getOrDefault('L',0),rightWalls.getOrDefault('7',0)) + min(rightWalls.getOrDefault('F',0),rightWalls.getOrDefault('J',0))
            val upCount = upWalls.getOrDefault('-',0) + min(upWalls.getOrDefault('F',0),upWalls.getOrDefault('J',0)) + min(upWalls.getOrDefault('L',0),upWalls.getOrDefault('7',0))
            val downCount = downWalls.getOrDefault('-',0) + min(downWalls.getOrDefault('F',0),downWalls.getOrDefault('J',0)) + min(downWalls.getOrDefault('L',0),downWalls.getOrDefault('7',0))

            val counts = listOf(leftCount,rightCount,upCount,downCount)

            if (counts.any{ it % 2 == 1}) {
                tile.type = 'X'
                sum ++
            }

            //println(" ${tile.cords} -> left: ${leftWalls.size}, right: ${rightWalls.size}, up: ${upWalls.size}, down: ${downWalls.size}")
        }
    }

    map.forEach { tile ->
        when(tile.type) {
            'F' -> tile.type = '┏'//'╔'
            '7' -> tile.type = '┓'//'╗'
            'L' -> tile.type = '┗'//'╚'
            'J' -> tile.type = '┛'//'╝'
            '-' -> tile.type = '━'//'═'
            '|' -> tile.type = '┃'//'║'
        }
    }
    map.print()

    val sol2 = sum
    println("Part 2: $sol2")
}


