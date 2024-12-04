package aoc2024

import tools.*

private const val test = false

fun main() {
    val file = readFileAs<String>(if (test) "sample" else "aoc2024/day2")

    fun List<Int>.isValid() : Boolean =
        this.mapIndexed { index, x ->
            if(index < this.size-1){
                this[index+1]-x
            } else {
                0
            }
        }.dropLast(1).let{ line ->
            line.max() <=3 &&  line.min() >= -3 && (line.all { it > 0 } || line.all { it < 0})
        }

    val lines = file.map { line ->
        line.split(" ").map { it.toInt() }
    }

    val sol1 = lines.count { line ->
        line.isValid()
    }

    println("Part 1: $sol1")

    val sol2 = lines.count{ line ->
        List(line.size) { index ->
            val newLine = line.toMutableList()
            newLine.removeAt(index)
            newLine
        }.any { it.isValid() }
    }

    println("Part 2: $sol2")
}

