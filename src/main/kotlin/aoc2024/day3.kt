package aoc2024

import tools.*

private const val test = false

fun main() {
    val file = readFileAs<String>(if (test) "sample" else "aoc2024/day3")
    val memory = file.joinToString { it }

    val regex = """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()

    val sol1 = regex.findAll(memory).sumOf {
        val (x, y) = it.destructured
        x.toInt() * y.toInt()
    }

    println("Part 1: $sol1")

    val doRegex = """do\(\)""".toRegex()
    val dontRegex = """don't\(\)""".toRegex()

    val indexesOfDo = doRegex.findAll(memory).map { it.range.first }.toMutableList().also { it.add(0, 0) }
    val indexesOfDont = dontRegex.findAll(memory).map { it.range.first }.toMutableList().also { it.add(memory.length) }

    val validRanges = indexesOfDo.map{ doIndex ->
        doIndex .. indexesOfDont.first { it >= doIndex }
    }

    val sol2 = regex.findAll(memory).sumOf {
        if(validRanges.any { range -> range.contains(it.range.first) }){
            val (x, y) = it.destructured
            x.toInt() * y.toInt()
        } else {
            0
        }
    }

    println("Part 2: $sol2")

    // Alternative solution

    var i = 0;
    var nextDo = 0
    var nextDont = 0;
    var nextMul: MatchResult? = null;

    var sum = 0

    while (i < memory.length) {
        nextDont = dontRegex.find(memory, i)?.range?.last ?: memory.length
        nextMul = regex.find(memory, i)

        if(nextMul != null){
            if(nextDont < nextMul.range.last){
                nextDo = doRegex.find(memory, nextDont)?.range?.last ?: memory.length
                i = nextDo
                continue
            }

            val (x, y) = nextMul.destructured
            sum += x.toInt() * y.toInt()
            i = nextMul.range.last
            continue
        } else {
            break
        }
    }

    val sol2b = sum

    println("Part 2b: $sol2b")
}