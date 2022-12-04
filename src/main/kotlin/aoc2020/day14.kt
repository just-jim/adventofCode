package aoc2020

import readFileAsStrings

fun main() {
    val file = readFileAsStrings("aoc2020/day14")

    var mask = ""
    val dic: MutableMap<Int, Long> = mutableMapOf()
    val editor: MutableMap<Int, Char> = mutableMapOf()

    // Part 1
    file.forEach {
        if (it.contains("mask")) {
            editor.clear()
            mask = it.split(" = ")[1]
            mask.forEachIndexed { i, c -> if (c != 'X') editor[i] = c }
        } else {
            val parts = it.split(" = ")
            val memIndex = parts[0].removePrefix("mem[").removeSuffix("]").toInt()
            val binary = parts[1].toLong().toString(2).padStart(36, '0').toCharArray()
            editor.forEach { (k, v) -> binary[k] = v }
            dic[memIndex] = String(binary).toLong(2)
        }
    }
    val sol1 = dic.values.sum()
    println("Part 1: $sol1")

    dic.clear()
    // Part 2
    file.forEach {
        if (it.contains("mask")) {
            editor.clear()
            mask = it.split(" = ")[1]
            mask.forEachIndexed { i, c -> if (c != '0') { editor[i] = c } }
        } else {
            val parts = it.split(" = ")
            val memIndex = parts[0].removePrefix("mem[").removeSuffix("]").toInt()
            val value = parts[1]
            val binary = memIndex.toLong().toString(2).padStart(36, '0').toCharArray()
            editor.forEach { (k, v) -> binary[k] = v }

            // create and call a recursive function to find all the permutations
            while (binary.contains('X')) {
                binary[binary.indexOf('X')]
            }

            // String(binary).toInt(2)

            val memIndices: List<Int> = listOf()
            memIndices.forEach {
                dic[it] = value.toLong()
            }
        }
    }

    val sol2 = dic.values.sum()
    println("Part 2: $sol2")
}
