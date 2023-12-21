package aoc2023

import tools.*

fun main() {
    val test = false
    val file = readFileAs<String>(if (test) "sample" else "aoc2023/day1")

    var sum = 0

    file.forEach { line ->
        val numericChars = line.filter { it.isDigit() }
        val firstNumericDigit = numericChars.first().toString()
        val lastNumericDigit = numericChars.last().toString()
        sum += (firstNumericDigit + lastNumericDigit).toInt()
    }

    val sol1 = sum
    println("Part 1: $sol1")

    sum = 0
    val digitWords =
        mapOf(
            "oneight" to "18",
            "threeight" to "38",
            "fiveight" to "58",
            "sevenine" to "79",
            "twone" to "21",
            "eightwo" to "82",
            "eighthree" to "83",
            "nineight" to "98",
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9",
        )

    file.forEach { line ->
        var correctLine = line
        digitWords.forEach {
            correctLine = correctLine.replace(it.key, it.value)
        }
        val numericChars = correctLine.filter { it.isDigit() }
        val firstNumericDigit = numericChars.first().toString()
        val lastNumericDigit = numericChars.last().toString()
        val num = (firstNumericDigit + lastNumericDigit).toInt()
        sum += num
    }

    val sol2 = sum
    println("Part 2: $sol2")
}
