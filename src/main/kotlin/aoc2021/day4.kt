package aoc2021

import tools.*

fun main() {
    val test = false
    val file = readFileAs<String>(if (test) "sample" else "aoc2021/day4")

    val nums = file[0].split(',')

    val bingos = mutableListOf<MutableList<MutableList<Int>>>()
    var curBingo = mutableListOf<MutableList<Int>>()
    for (i in 2 until file.size) {
        if (file[i] == "") {
            bingos.add(curBingo)
            curBingo = mutableListOf()
        } else {
            curBingo.add(file[i].split(' ').mapNotNull { it.toIntOrNull() }.toMutableList())
        }
    }

    // add vertical lines
    val bingos2 = mutableListOf<MutableList<MutableList<Int>>>()
    bingos.forEach { bingo ->
        curBingo = mutableListOf()
        for (i in 0 until 5) {
            curBingo.add(bingo.map { it[i] }.toMutableList())
        }
        bingos2.add(curBingo)
    }

    var first = false
    var lastBingo: Int
    var wonh = false
    var wonv = false
    nums.map { it.toInt() }.forEach { num ->
        bingos.forEachIndexed { i, _ ->
            bingos[i].forEach { line ->
                line.remove(num)
                if (line.size == 0) {
                    if (!first) {
                        first = true
                        val sol = bingos[i].sumOf { it.sum() } * num
                        println("Part 1: $sol")
                    }

                    wonh = true
                }
            }

            bingos2[i].forEach { line ->
                line.remove(num)
                if (line.size == 0) {
                    if (!first) {
                        first = true
                        val sol = bingos2[i].sumOf { it.sum() } * num
                        println("Part 1: $sol")
                    }
                    wonv = true
                }
            }

            if (wonh && bingos.count { it.isNotEmpty() } == 1) {
                lastBingo = bingos[i].sumOf { it.sum() } * num
                println("Part 2: $lastBingo")
            }

            if (wonv && bingos2.count { it.isNotEmpty() } == 1) {
                lastBingo = bingos2[i].sumOf { it.sum() } * num
                println("Part 2: $lastBingo")
            }

            if (wonh || wonv) {
                bingos[i] = mutableListOf()
                bingos2[i] = mutableListOf()
            }
            wonh = false
            wonv = false
        }
    }
}
