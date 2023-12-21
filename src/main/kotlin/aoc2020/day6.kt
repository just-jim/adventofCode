package aoc2020

import tools.*

fun main() {
    val data = readFileAs<String>("aoc2020/day6")
    var group = mutableSetOf<Char>()
    var group2 = mutableMapOf<Char, Int> ()
    var count = 0
    var count2 = 0
    var groupMembers = 0
    data.forEach { line ->
        if (line == "") {
            count += group.size
            group = mutableSetOf()

            group2.forEach {
                if (it.value == groupMembers) {
                    count2++
                }
            }
            group2 = mutableMapOf()
            groupMembers = 0
        } else {
            groupMembers++
            line.toCharArray().forEach {
                group.add(it)
                group2.putIfAbsent(it, 0)
                group2.merge(it, 1, Int::plus)
            }
        }
    }

    println("Part 1: $count")
    println("Part 2: $count2")
}
