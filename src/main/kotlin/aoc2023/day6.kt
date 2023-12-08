package aoc2023

import readFileAsStrings
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

private const val test = false

// buttonTimeToDuration -> //-x^2+nx

fun durationToMinButtonTime(raceTime:Long, duration: Long):Double{
    //(n - sqrt(n^2 - 4*y))
    return (raceTime - sqrt(raceTime * raceTime - 4.0 * duration)) / 2
}

fun durationToMaxButtonTime(raceTime:Long, duration: Long):Double{
    //(n + sqrt(n^2 - 4*y))
    return (raceTime + sqrt(raceTime * raceTime - 4.0 * duration)) / 2
}

fun main() {
    val file = readFileAsStrings(if (test) "sample" else "aoc2023/day6")

    val raceTimes = Regex("\\d+").findAll(file.first()).map { it.value.toInt() }.toList()
    val highScores = Regex("\\d+").findAll(file.last()).map { it.value.toInt() }.toList()

    var sum = 1.0
    raceTimes.forEachIndexed{ i, raceTime->
       val minButtonTime = ceil(durationToMinButtonTime(raceTime.toLong(),(highScores[i]+1).toLong())).toInt()
       val maxButtonTime = floor(durationToMaxButtonTime(raceTime.toLong(),(highScores[i]+1).toLong())).toInt()
       sum *= maxButtonTime-minButtonTime+1
    }

    val sol1 = sum.toInt()
    println("Part 1: $sol1")

    val raceTime = raceTimes.joinToString("").toLong()
    val highScore = highScores.joinToString("").toLong()

    val minButtonTime = ceil(durationToMinButtonTime(raceTime,highScore+1)).toInt()
    val maxButtonTime = floor(durationToMaxButtonTime(raceTime,highScore+1)).toInt()

    val sol2 = maxButtonTime-minButtonTime+1
    println("Part 2: $sol2")
}
