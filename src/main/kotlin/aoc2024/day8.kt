package aoc2024

import tools.*

private const val test = false

fun main() {
    val file = readFileAs<String>(if (test) "sample" else "aoc2024/day8")
    val map = Matrix2d<Char>(file[0].length, file.size)

    val antennas = mutableMapOf<Char, MutableList<Cords>>()

    file.forEachIndexed { y, line ->
        line.forEachIndexed { x, tile ->
            map[x, y] = tile
            if (tile != '.') {
                if (antennas.containsKey(tile)) {
                    antennas[tile]?.add(Cords(x, y))
                } else {
                    antennas[tile] = mutableListOf(Cords(x, y))
                }
            }
        }
    }

    val antinodeMap = Matrix2d<Int>(file[0].length, file.size)
    antinodeMap.initialize(0)

    antennas.values.forEach { cords ->
        cords.forEach { originCord ->
            cords.forEach { cord ->
                if (originCord != cord) {
                    val xOffset = originCord.x - cord.x
                    val yOffset = originCord.y - cord.y
                    val antinodeX = originCord.x + xOffset
                    val antinodeY = originCord.y + yOffset
                    if (antinodeX in 0 until map.cols && antinodeY in 0 until map.rows) {
                        antinodeMap[Cords(antinodeX, antinodeY)] = 1
                    }
                }
            }
        }
    }

    val sol1 = antinodeMap.rows().sumOf { it.sum() }
    println("Part 1: $sol1")

    val enhancedAntinodeMap = Matrix2d<Int>(file[0].length, file.size)
    enhancedAntinodeMap.initialize(0)

    antennas.values.forEach { cords ->
        cords.forEach { originCord ->
            cords.forEach { cord ->
                if (originCord != cord) {
                    var i = 0
                    do {
                        val xOffset = originCord.x - cord.x
                        val yOffset = originCord.y - cord.y
                        val antinodeX = originCord.x + xOffset * i
                        val antinodeY = originCord.y + yOffset * i
                        if (antinodeX in 0 until map.cols && antinodeY in 0 until map.rows) {
                            enhancedAntinodeMap[Cords(antinodeX, antinodeY)] = 1
                        }
                        i++
                    } while (antinodeX in 0 until map.cols || antinodeY in 0 until map.rows)
                }
            }
        }
    }

    val sol2 = enhancedAntinodeMap.rows().sumOf { it.sum() }
    println("Part 2: $sol2")
}
