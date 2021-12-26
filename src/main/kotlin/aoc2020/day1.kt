package aoc2020

import readFileAsStrings

fun main() {
    val expenses = readFileAsStrings("aoc2020/day1")
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