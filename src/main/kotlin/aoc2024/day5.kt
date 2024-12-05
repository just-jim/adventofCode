package aoc2024

import tools.*

private const val test = false

fun main() {
    val file = readFileAs<String>(if (test) "sample" else "aoc2024/day5")

    val rulesList = file.subList(0, file.indexOf(""))
    val updatesList = file.subList(file.indexOf("") + 1, file.size)

    val rules = mutableMapOf<Int, MutableList<Int>>()
    rulesList.forEach { rule ->
        val (key, value) = rule.split("|").map { it.toInt() }
        if (rules.containsKey(key)) {
            rules[key]?.add(value)
        } else {
            rules[key] = mutableListOf(value)
        }
    }

    val updates = updatesList.map { update ->
        update.split(",").map { it.toInt() }
    }

    val sol1 = updates.sumOf { pages ->
        if (pages.all { page ->
                rules.getOrDefault(page, listOf()).all { mustBeBefore ->
                    pages.indexOf(page) < (if (pages.indexOf(mustBeBefore) != -1) pages.indexOf(mustBeBefore) else pages.size)
                }
            }) {
            pages[pages.size / 2]
        } else {
            0
        }
    }

    println("Part 1: $sol1")

    val sol2 = updates.sumOf { pages ->
        if (!pages.all { page ->
                rules.getOrDefault(page, listOf()).all { mustBeBefore ->
                    pages.indexOf(page) < (if (pages.indexOf(mustBeBefore) != -1) pages.indexOf(mustBeBefore) else pages.size)
                }
            }) {
            val newPages = mutableListOf<Int>()
            pages.forEach { page ->
                run lit@{
                    if (newPages.isEmpty()) {
                        newPages.add(page)
                        return@lit
                    }
                    newPages.reversed().forEach { comparePage ->
                        if (rules.getOrDefault(comparePage, listOf()).contains(page)) {
                            newPages.add(newPages.indexOf(comparePage) + 1, page)
                            return@lit
                        }
                    }
                    newPages.add(0, page)
                }
            }
            newPages[newPages.size / 2]
        } else {
            0
        }
    }

    println("Part 2: $sol2")
}
