package aoc2023

import array2dOfChar
import readFileAsStrings
import x
import y

private const val test = false

data class Gear(
    var adjacentNums: Int,
    var sum: Int,
)

fun Array<CharArray>.checkIfPartNumberIsValid(y: Int, x: Int, length: Int, num: Int, gears: MutableMap<Pair<Int, Int>, Gear>): Boolean {
    var flag = false
    (y - 1..y + 1).forEach { yy ->
        if (yy.validIndex(this.y())) {
            (x - 1..x + length).forEach { xx ->
                if (xx.validIndex(this.x()) && this[xx][yy].isSymbol()) {
                    gears.getOrPut(Pair(xx, yy)) {
                        Gear(0, 1)
                    }.let {
                        it.adjacentNums += 1
                        it.sum *= num
                    }
                    flag = true
                }
            }
        }
    }
    return flag
}

fun Char.isSymbol() = this != '.' && !this.isDigit()

fun Int.validIndex(max: Int) = this in 0..<max

fun extractNumbersWithIndex(input: String): Pair<List<Int>, List<Int>> {
    val regex = Regex("\\d+")
    val matches = regex.findAll(input)
    return Pair(
        matches.map { it.value.toInt() }.toList(),
        matches.map { it.range.first }.toList(),
    )
}

fun main() {
    val file = readFileAsStrings(if (test) "sample" else "aoc2023/day3")

    val schematic = array2dOfChar(file[0].length, file.size)
    var sum = 0

    file.forEachIndexed { i, line ->
        line.forEachIndexed { i2, char ->
            schematic[i2][i] = char
        }
    }

    val gears = mutableMapOf<Pair<Int, Int>, Gear>()

    file.forEachIndexed { y, line ->
        val (numbers, indexes) = extractNumbersWithIndex(line)
        numbers.forEachIndexed { i, it ->
            val length = it.toString().length
            if (schematic.checkIfPartNumberIsValid(y, indexes[i], length, it, gears)) {
                sum += it
            }
        }
    }

    val sol1 = sum
    println("Part 1: $sol1") // 527364

    val sol2 = gears.filter { it.value.adjacentNums == 2 }.values.sumOf{it.sum}
    println("Part 2: $sol2") // 79026871
}
