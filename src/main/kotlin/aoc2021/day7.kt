package aoc2021

import readFileAsStrings
import java.util.Collections.max
import kotlin.math.abs

fun main() {
    val test = false
    val file = readFileAsStrings(if(test) "jim" else "aoc2021/day7")
    val crabs = file[0].split(',').map {it.toInt()}

    fun triangular(x: Int) : Int{
        return x * (x+1) / 2
    }

    var fuel = -1
    var fuel2 = -1
    val maxCrab = max(crabs)
    (0 until maxCrab).iterator().forEach{
        var tmpFuel = 0
        var tmpFuel2 = 0
        crabs.forEach { crab ->
            tmpFuel += abs(crab-it)
            tmpFuel2 += triangular(abs(crab-it))
        }
        if(fuel == -1)
            fuel = tmpFuel
        if(tmpFuel < fuel)
            fuel = tmpFuel

        if(fuel2 == -1)
            fuel2 = tmpFuel2
        if(tmpFuel2 < fuel2)
            fuel2 = tmpFuel2
    }

    println("Part 1: $fuel")
    println("Part 2: $fuel2")
}