package aoc2022

import readFileAsStrings
import kotlin.math.abs

class Rope() {
    val knots: MutableList<Knot> = mutableListOf()
    val tailBeen: MutableList<Pair<Int, Int>> = mutableListOf(Pair(0, 0))
    constructor(n: Int) : this() {
        repeat(n) { i -> this.knots.add(Knot(this, i)) }
    }

    fun moveHead(direction: String) {
        val head = knots.first()
        when (direction) {
            "U" -> head.y++
            "L" -> head.x--
            "D" -> head.y--
            "R" -> head.x++
        }
        moveKnots()
    }

    private fun moveKnot(knot: Knot) {
        if (knot.index == 0) return

        val previousKnot = knot.parent.knots[knot.index - 1]
        if (abs(previousKnot.x - knot.x) > 1 || abs(previousKnot.y - knot.y) > 1) {
            if (previousKnot.x > knot.x) {
                knot.x++
            } else if (previousKnot.x < knot.x) {
                knot.x--
            }
            if (previousKnot.y > knot.y) {
                knot.y++
            } else if (previousKnot.y < knot.y) {
                knot.y--
            }
        }
    }

    private fun registerTailPosition() {
        val tail = knots.last()
        tailBeen.add(Pair(tail.x, tail.y))
    }

    private fun moveKnots() {
        knots.forEach {
            moveKnot(it)
        }
        registerTailPosition()
    }
}

class Knot() {
    var x = 0
    var y = 0
    var index = 0

    lateinit var parent: Rope
    constructor(parent: Rope, index: Int) : this() {
        this.parent = parent
        this.index = index
    }

    override fun toString(): String =
        when (index) {
            0 -> "Head"
            parent.knots.size - 1 -> "Tail"
            else -> index.toString()
        }
}

fun main() {
    val test = false
    val file = readFileAsStrings(if (test) "sample" else "aoc2022/day9")

    val rope = Rope(10) // Part 1: n=2 , Part 2: n=10

    file.forEach { line ->
        val parts = line.split(" ")
        val direction = parts.first()
        val n = parts[1].toInt()

        println(line)
        repeat(n) {
            rope.moveHead(direction)
        }
    }

    val sol = rope.tailBeen.distinct().count()
    println("Part 1: $sol")
}
