package aoc2020

import tools.*

fun main() {
    val data = readFileAs<String>("aoc2020/day8")
    val code = mutableListOf<String>()
    val visited = mutableListOf<Int>()

    data.forEach { code.add(it) }

    val codeLength = code.size
    var i = 0
    var x = 0

    while (!visited.contains(i)) {
        visited.add(i)
        val parts = code[i].split(" ")
        val instruction = parts[0]
        val value = parts[1].toInt()

        when (instruction) {
            "nop" -> i++
            "acc" -> {
                x += value
                i++
            }
            "jmp" -> i += value
        }
    }

    println("Part1: $x")

    run lit@{
        code.forEachIndexed { index, it ->
            if (it.contains("nop") || it.contains("jmp")) {
                val code2 = code.toMutableList()
                if (it.contains("nop")) {
                    code2[index] = code2[index].replace("nop", "jmp")
                } else {
                    code2[index] = code2[index].replace("jmp", "nop")
                }

                visited.clear()
                i = 0
                x = 0

                while (!visited.contains(i) && i < codeLength) {
                    visited.add(i)
                    val parts = code2[i].split(" ")
                    val instruction = parts[0]
                    val value = parts[1].toInt()

                    when (instruction) {
                        "nop" -> i++
                        "acc" -> {
                            x += value
                            i++
                        }
                        "jmp" -> i += value
                    }
                }

                if (i >= codeLength) return@lit
            }
        }
    }

    if (i >= codeLength) {
        println("Part2: $x")
    }
}
