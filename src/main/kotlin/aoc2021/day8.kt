package aoc2021

import readFileAsStrings

fun main() {
    val test = false
    val file = readFileAsStrings(if(test) "sample" else "aoc2021/day8")

    var count = 0
    var count2 = 0
    file.forEach{ line ->
        val outcome = line.split(" | ")[1]
        val signals = line.split(" | ")[0].split(' ').map { it.toCharArray().toList() }.groupBy { it.size }
        val digitLengths = outcome.split(' ').map { it.length }
        count += digitLengths.filter { linesToNumber(it).size == 1 }.size

        var sol = ""
        outcome.split(' ').map { it.toCharArray().toList() }.forEach { num ->
            sol += guessNum(num,signals).toString()
        }
        count2 += sol.toInt()
    }

    println("Part 1: $count")
    println("Part 2: $count2")
}

fun linesToNumber(lines:Int):List<Int>{
    return when(lines){
        2 -> listOf(1)
        3 -> listOf(7)
        4 -> listOf(4)
        5 -> listOf(2,3,5)
        6 -> listOf(6,9)
        7 -> listOf(8)
        else -> listOf()
    }
}

fun guessNum(num: List<Char>,signals: Map<Int, List<List<Char>>>): Int{
    return when(num.size){
        2 -> 1
        3 -> 7
        4 -> 4
        5 -> {
            val p = num.toMutableList()
            p.removeAll(signals[4]!![0])
            val p3 = num.toMutableList()
            p3.removeAll(signals[2]!![0])
            if(p3.size == 3) 3
            else if(p.size == 2) 5
            else 2
        }
        6 -> {
            val p9 = num.toMutableList()
            p9.removeAll(signals[4]!![0])
            val p6 = num.toMutableList()
            p6.removeAll(signals[3]!![0])
            if(p9.size == 2) 9
            else if(p6.size == 4) 6
            else 0
        }
        7 -> 8
        else -> -1
    }
}