package aoc2020

import tools.*

fun main() {
    val expenses = readFileAs<String>("aoc2020/day1")
    expenses.forEach { a ->
        expenses.forEach { b ->
            expenses.forEach { c ->
                if (a.toInt() + b.toInt() + c.toInt() == 2020) {
                    println("Part 1+2:" + (a.toInt() * b.toInt() * c.toInt()))
                }
            }
        }
    }
}
