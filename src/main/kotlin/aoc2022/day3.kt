package aoc2022

import readFileAsStrings

fun main() {
    val test = false
    val file = readFileAsStrings(if (test) "sample" else "aoc2022/day3")

    fun Char.priority(): Int {
        val ascii = this.code
        return if (ascii <= 90) {
            ascii - 65 + 27
        } else {
            ascii - 96
        }
    }

    fun String.splitAtIndex(index: Int) = take(index) to substring(index)

    var sum = 0
    file.forEach { items ->
        val rucksacks = items.splitAtIndex(items.length / 2)
        run breaking@{
            rucksacks.first.forEach {
                if (rucksacks.second.contains(it)) {
                    sum += it.priority()
                    return@breaking
                }
            }
        }
    }
    println("Part 1: $sum")

    sum = 0
    file.chunked(3).forEach { team ->
        val uniqueItems = team.map { it.toList().distinct().toSet() }
        val badge = uniqueItems.first().intersect(uniqueItems[1]).intersect(uniqueItems[2])
        sum += badge.first().priority()
    }
    println("Part 2: $sum")
}
