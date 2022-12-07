package aoc2021

import readFileAsStrings

fun main() {
    val test = false
    val file = readFileAsStrings(if (test) "sample" else "aoc2021/day3")
    val num = IntArray(file[0].length) { 0 }
    val num2 = IntArray(file[0].length) { 0 }
    file.forEach { line ->
        line.forEachIndexed { i, v ->
            num[i] += v.toString().toInt()
        }
    }

    num.forEachIndexed { i, _ ->
        num[i] = if (num[i] >= file.size / 2) 1 else 0
        num2[i] = if (num[i] == 1) 0 else 1
    }

    var binary = num.joinToString("").toInt(2)
    var invBinary = num2.joinToString("").toInt(2)
    val sol = binary * invBinary
    println("Part 1: $sol")

    val file2 = file.toMutableList()
    val file3 = file.toMutableList()

    var num3: Int
    var num4: Int

    for (i in 0 until file[0].length) {
        num3 = if (file2.filter { it[i] == '1' }.count() >= file2.filter { it[i] == '0' }.count()) 1 else 0
        if (file2.size > 1) {
            file2.removeIf { x -> x[i].toString().toInt() != num3 }
        }
        num4 = if (file3.filter { it[i] == '1' }.count() < file3.filter { it[i] == '0' }.count()) 1 else 0
        if (file3.size > 1) {
            file3.removeIf { x -> x[i].toString().toInt() != num4 }
        }
        if (file2.size == 1 && file3.size == 1) {
            break
        }
    }

    binary = file2[0].toInt(2)
    invBinary = file3[0].toInt(2)
    val sol2 = binary * invBinary
    println("Part 2: $sol2")
}