package aoc2023

import readFileAsStrings

fun main() {
    val test = false
    val file = readFileAsStrings(if (test) "sample" else "aoc2023/day1")

    var sum = 0
    /*
    file.forEach { line ->
        val numericChars = line.replace("[^\\d.]".toRegex(),"")
        val firstNumericDigit = numericChars.first().toString()
        val lastNumericDigit = numericChars.last().toString()
        sum += (firstNumericDigit+lastNumericDigit).toInt()
    }

    val sol1 = sum
    println("Part 1: $sol1")
*/
    sum = 0
    val digitWords =
        mapOf(
            "twone" to "2ne",
            "eightwo" to "8wo",
            "eighthree" to "8hree",
            "nineight" to "9ight",
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
        digitWords.keys.forEach { correctLine = correctLine.replace(it, digitWords[it]!!) }
        val numericChars = correctLine.replace("[^\\d.]".toRegex(), "")
        val numericChars2 = Regex("[\\d+]").findAll(correctLine)
        val firstNumericDigit = numericChars2.first().value
        val lastNumericDigit = numericChars2.last().value
        val num = (firstNumericDigit + lastNumericDigit).toInt()
        sum += num
        println(num)
        //println("$correctLine -> $num")
    }
    // tried: 53519

    val sol2 = sum
    println("Part 2: $sol2")
}
