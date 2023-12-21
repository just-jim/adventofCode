package aoc2022

import aoc2022.BattleOutcome.Draw
import aoc2022.BattleOutcome.Lose
import aoc2022.BattleOutcome.Win
import aoc2022.RPS.Paper
import aoc2022.RPS.Rock
import aoc2022.RPS.Scissors
import tools.*

enum class RPS(val score: Int) {
    Rock(1),
    Paper(2),
    Scissors(3),
}

enum class BattleOutcome(val score: Int) {
    Win(6),
    Draw(3),
    Lose(0),
}

fun main() {
    val test = true
    val file = readFileAs<String>(if (test) "sample" else "aoc2022/day2")

    fun String.toRPS(): RPS =
        when (this) {
            "A", "X" -> Rock
            "B", "Y" -> Paper
            "C", "Z" -> Scissors
            else -> Rock
        }

    fun RPS.battle(opponent: RPS): BattleOutcome {
        return when (this) {
            Rock ->
                when (opponent) {
                    Scissors -> Win
                    Rock -> Draw
                    Paper -> Lose
                }
            Paper ->
                when (opponent) {
                    Rock -> Win
                    Paper -> Draw
                    Scissors -> Lose
                }
            Scissors ->
                when (opponent) {
                    Paper -> Win
                    Scissors -> Draw
                    Rock -> Lose
                }
        }
    }

    var sum = 0
    file.forEach { line ->
        line.split(" ").let {
            val opponent = it[0].toRPS()
            val yours = it[1].toRPS()
            sum += yours.battle(opponent).score + yours.score
        }
    }

    val sol1 = sum
    println("Part 1: $sol1")

    fun String.toBattleOutcome(): BattleOutcome =
        when (this) {
            "X" -> Lose
            "Y" -> Draw
            "Z" -> Win
            else -> Win
        }

    fun RPS.prediction(desiredOutcome: BattleOutcome): RPS {
        return when (desiredOutcome) {
            Draw -> this
            Win ->
                when (this) {
                    Rock -> Paper
                    Paper -> Scissors
                    Scissors -> Rock
                }
            Lose ->
                when (this) {
                    Rock -> Scissors
                    Paper -> Rock
                    Scissors -> Paper
                }
        }
    }

    sum = 0
    file.forEach { line ->
        line.split(" ").let {
            val opponent = it.first().toRPS()
            val yours = opponent.prediction(it[1].toBattleOutcome())
            sum += yours.battle(opponent).score + yours.score
        }
    }

    val sol2 = sum
    println("Part 2: $sol2")
}
