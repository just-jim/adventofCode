package aoc2023

import tools.*

fun processMap(file: List<String>){
    val map = file.toMutableList().toMatrix<Char>()

    val rows = map.rows().map{ it.joinToString("")}
    for(i in (0..rows.size-2)) {
        if(rows[i] == rows[i+1]) {
            println("Found possible symmetry at y: $i")
        }
    }

    val cols = map.cols().map{ it.joinToString("")}
    for(i in (0..cols.size-2)) {
        if(cols[i] == cols[i+1]) {
            println("Found possible symmetry at x: $i")
        }
    }
}

private const val test = true

fun main() {
    val file = readFileAs<String>(if (test) "sample" else "aoc2023/day13")

    // Split maps
    val maps = mutableListOf<MutableList<String>>()
    val currentList = mutableListOf<String>()

    for (element in file) {
        if (element == "") {
            if (currentList.isNotEmpty()) {
                maps.add(currentList.toMutableList())
                currentList.clear()
            }
        } else {
            currentList.add(element)
        }
    }
    if (currentList.isNotEmpty()) {
        maps.add(currentList)
    }

    maps.forEach { processMap(it) }

    var sum = 0

    val sol1 = sum
    println("Part 1: $sol1")

    sum = 0

    val sol2 = sum
    println("Part 2: $sol2")
}
