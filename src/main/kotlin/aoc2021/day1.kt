package aoc2021

import readFileAsInt

fun main() {
    val test = false
    val file = readFileAsInt(if(test) "sample" else "aoc2021/day1")
    var count = 0
    file.forEachIndexed{i,v ->
        if(i==0) return@forEachIndexed
        if(v>file[i-1]) count++
    }
    println("Part 1: $count")

    count = 0
    file.forEachIndexed{i,v ->
        if(i>=file.size-3) return@forEachIndexed
        if(v+file[i+1]+file[i+2]<file[i+1]+file[i+2]+file[i+3]) count++
    }
    println("Part 2: $count")
}