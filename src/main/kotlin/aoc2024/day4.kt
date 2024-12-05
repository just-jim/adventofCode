package aoc2024

import tools.*

private const val test = false

fun main() {
    val file = readFileAs<String>(if (test) "sample" else "aoc2024/day4")
    val map = file.toMatrix<Char>()

    val horizontalLines = map.rows().map { it.joinToString("") }.joinToString(" ")
    val verticalLines = map.cols().map { it.joinToString("") }.joinToString(" ")
    val diagonalLines = map.diagonals().map { it.joinToString("") }.joinToString(" ")
    val antiDiagonalLines = map.antiDiagonals().map { it.joinToString("") }.joinToString(" ")

    val all = "$horizontalLines $verticalLines $diagonalLines $antiDiagonalLines"
    val allReverse = all.reversed()

    val xmasRegex = """XMAS""".toRegex()

    val sol1 = xmasRegex.findAll("$all $allReverse").count()

    println("Part 1: $sol1")

    val validXmases = listOf("MMSS", "MSSM", "SSMM", "SMMS")
    fun aroundIsXmas(x: Int, y: Int) = validXmases.contains("${map[x-1, y-1]}${map[x+1, y-1]}${map[x+1, y+1]}${map[x-1, y+1]}")

    var sum = 0
    for (y in 1 until map.rows-1) {
        for (x in 1 until map.cols-1) {
            if (map[x,y] == 'A' && aroundIsXmas(x,y)) {
                sum += 1
            }
        }
    }

    val sol2 = sum
    println("Part 2: $sol2")
}
