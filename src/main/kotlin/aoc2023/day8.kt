package aoc2023

import tools.*

private const val test = false

fun main() {
    val file = readFileAs<String>(if (test) "sample" else "aoc2023/day8")

    val instructions = file.first()

    val map = mutableMapOf<String,Pair<String,String>>()
    file.subList(2,file.size).forEach { line ->
        val (key, pair) = line.split(" = ")
        map[key] = pair
            .replace("(","")
            .replace(")","")
            .split(", ")
            .let{ Pair(it[0],it[1]) }
    }

    var cur = "AAA"
    var step = 0
    while (cur != "ZZZ") {
        cur =if(instructions[step % instructions.length] == 'L') {
            map[cur]!!.first
        } else {
            map[cur]!!.second
        }
        step ++
    }

    val sol1 = step
    println("Part 1: $sol1")

    step = 0
    var cur2 = map.keys.filter { it.last() == 'A' }

    while (!cur2.all { it.last() == 'Z' }  ) {
        cur2 = if(instructions[(step % instructions.length)] == 'L') {
            cur2.map{map[it]!!.first}
        } else {
            cur2.map{map[it]!!.second}
        }
        //println("Step ${step.toInt()}: ${instructions[(step % instructions.length).toInt()]}, $cur2")
        //print(instructions[(step % instructions.length).toInt()])
        step ++
    }

    val sol2 = step
    println("Part 2: $sol2")
}
