package aoc2023

import tools.*
import kotlin.math.pow

private const val test = false

data class ScratchCard(
    val game: Int,
    val winningNumbers: Int,
    var n: Int
)

fun main() {
    val file = readFileAs<String>(if (test) "sample" else "aoc2023/day4")

    val scratchCards = mutableMapOf<Int,ScratchCard>()

    var sum = 0
    file.forEach { line ->
        val game =  line.split(": ").first().split(' ').last().toInt()
        val (winning, nums) = line.split(": ")[1].split(" | ").map {
            it.trim().replace("  "," ").split(' ').map { it.toInt() }
        }
        val winningNumbers = nums.count{ winning.contains(it)}
        sum += if(winningNumbers == 0) 0 else 2.0.pow(winningNumbers.toDouble()-1).toInt()
        scratchCards.getOrPut(game){ScratchCard(game,winningNumbers,1)}
    }

    val sol1 = sum
    println("Part 1: $sol1")

    scratchCards.values.forEach {
        (it.game+1 .. it.game+it.winningNumbers).forEach {i ->
            scratchCards[i]!!.n += it.n
        }
    }

    val sol2 = scratchCards.values.sumOf { it.n }
    println("Part 2: $sol2")
}
