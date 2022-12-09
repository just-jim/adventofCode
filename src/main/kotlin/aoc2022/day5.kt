package aoc2022

import readFileAsStrings
import kotlin.collections.ArrayDeque

fun main() {
    val test = false
    val file = readFileAsStrings(if (test) "sample" else "aoc2022/day5")

    val numOfStacks = if (test) 3 else 9
    val stacks: MutableList<ArrayDeque<String>> = mutableListOf()
    val stacks2: MutableList<ArrayDeque<String>> = mutableListOf()
    for (i in 0 until numOfStacks) {
        stacks.add(ArrayDeque())
        stacks2.add(ArrayDeque())
    }

    file.forEach { line ->
        if (line.isEmpty() || line[1].isDigit()) { return@forEach } else if (line[0] == '[' || line[0] == ' ') {
            line.chunked(4).forEachIndexed { i, crate ->
                if (crate[0] == '[') {
                    stacks[i].addLast(crate[1].toString())
                    stacks2[i].addLast(crate[1].toString())
                }
            }
        } else {
            line.split(" ").let { orders ->
                val n = orders[1].toInt()
                val from = orders[3].toInt()
                val to = orders[5].toInt()

                // Move crates for part 1
                (1..n)
                    .map { stacks[to - 1].addFirst(stacks[from - 1].removeFirst()) }

                // Move crates for task 2
                (0 until n)
                    .map { stacks2[from - 1].removeFirst() }
                    .reversed()
                    .map { stacks2[to - 1].addFirst(it) }
            }
        }
    }

    var sol1 = ""
    stacks.forEach { sol1 += it.first() }
    println("Part 1: $sol1")

    var sol2 = ""
    stacks2.forEach { sol2 += it.first() }
    println("Part 2: $sol2")
}
