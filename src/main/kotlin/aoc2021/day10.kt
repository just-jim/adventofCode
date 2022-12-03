package aoc2021

import readFileAsStrings

fun main() {
    val test = false
    val file = readFileAsStrings(if(test) "sample" else "aoc2021/day10")

    val start = listOf('<','[','{','(')
    val opposite : Map<Char,Char> = mapOf(Pair('>','<'),Pair(']','['),Pair('}','{'),Pair(')','('),Pair('<','>'),Pair('[',']'),Pair('{','}'),Pair('(',')'))

    val scores : Map<Char,Int> = mapOf(Pair('>',25137),Pair(']',57),Pair('}',1197),Pair(')',3))
    val scores2 : Map<Char,Int> = mapOf(Pair('>',4),Pair(']',2),Pair('}',3),Pair(')',1))
    val finalScores : MutableList<Long> = mutableListOf()
    var count = 0
    file.forEach{ line ->
        val chain: MutableList<Char> = mutableListOf()
        run lit@{
            line.forEach {
                if (start.contains(it))
                    chain.add(it)
                else {
                    if (chain.last() == opposite[it])
                        chain.removeLast()
                    else {
                        count += scores[it]!!
                        return@lit
                    }
                }
            }
            if(chain.size != 0) {
                var score:Long = 0
                chain.map { num -> opposite[num] }.reversed().forEach { pol ->
                    score = (score * 5) + scores2[pol]!!
                }
                finalScores.add(score)
            }
        }
    }
    println("Part 1: $count")

    finalScores.sort()
    val sol2 = finalScores[finalScores.size/2]

    println("Part 2: $sol2")
}