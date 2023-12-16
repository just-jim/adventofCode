package aoc2023

import readFileAsStrings

private const val test = false

fun main() {
    val file = readFileAsStrings(if (test) "sample" else "aoc2023/day9")

    var sum = 0
    var sum2 = 0
    file.forEach { line ->
        val sequence = line.split(' ').map { it.toInt() }.toMutableList()
        val listOfSequences = mutableListOf(sequence)

        while (!listOfSequences.last().all { it == 0 }) {
            val newSeq = listOfSequences
                .last()
                .subList(1, listOfSequences.last().size)
                .mapIndexed { i, thisNum ->
                    thisNum - listOfSequences.last()[i]
                }.toMutableList()
            listOfSequences.add(newSeq)
        }

        val reverseSequence = listOfSequences.reversed()
        reverseSequence.subList(1, reverseSequence.size).forEachIndexed { i, thisList ->
            thisList.add(thisList.last() + reverseSequence[i].last())
            thisList.add(0, thisList.first() - reverseSequence[i].first())
        }

        sum += reverseSequence.last().last()
        sum2 += reverseSequence.last().first()
    }

    val sol1 = sum
    println("Part 1: $sol1")

    val sol2 = sum2
    println("Part 2: $sol2")
}
