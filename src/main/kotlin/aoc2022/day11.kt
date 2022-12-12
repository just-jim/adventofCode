package aoc2022

import readFileAsStrings

data class Item(
    var worry: Long
)

class Monkey(
    var id: Int,
    var items: MutableList<Item>,
    var operationSign: String,
    var operationValueStr: String,
    var divisibleBy: Long,
    var trueMonkeyId: Int,
    var falseMonkeyId: Int
) {
    private lateinit var trueMonkey: Monkey
    private lateinit var falseMonkey: Monkey
    var inspections: Long = 0L
    fun init(monkeys: List<Monkey>) {
        trueMonkey = monkeys[trueMonkeyId]
        falseMonkey = monkeys[falseMonkeyId]
    }

    fun turn() {
        println("Monkey: $id:")
        items.forEach {
            println("  Monkey inspects an item with a worry level of ${it.worry}.")
            inspect(it)
            bored(it)
            val targetMonkey = if (test(it)) trueMonkey else falseMonkey
            passItem(it, targetMonkey)
        }
        items.clear()
    }

    private fun inspect(item: Item) {
        print("    Worry level ")
        val operationValue = operationValue(item)
        item.worry = when (operationSign) {
            "+" -> {
                print("increases ")
                item.worry + operationValue
            }
            "*" -> {
                print("is multiplied ")
                item.worry * operationValue
            }
            else -> item.worry
        }
        println("by $operationValue to ${item.worry}.")

        inspections++
    }

    private fun bored(item: Item) {
        item.worry = item.worry.floorDiv(3)
        println("    Monkey gets bored with item. Worry level is divided by 3 to ${item.worry}.")
    }

    private fun test(item: Item): Boolean {
        val isDivisible = item.worry % divisibleBy == 0L
        if (isDivisible) {
            println("    Current worry level is divisible by $divisibleBy.")
        } else {
            println("    Current worry level is not divisible by $divisibleBy.")
        }

        return isDivisible
    }

    private fun passItem(item: Item, monkey: Monkey) {
        monkey.items.add(item)
        println("    Item with worry level ${item.worry} is thrown to monkey ${monkey.id}.")
    }

    private fun operationValue(item: Item): Long {
        return if (operationValueStr == "old") {
            item.worry
        } else {
            operationValueStr.toLong()
        }
    }
}

fun main() {
    val test = true
    val file = readFileAsStrings(if (test) "sample" else "aoc2022/day11")

    var sum = 0

    val monkeys = file.chunked(7).mapIndexed { i, monkeyData ->
        Monkey(
            id = i,
            items = monkeyData[1].split(":")[1].replace(" ", "").split(",").map { Item(it.toLong()) }.toMutableList(),
            operationSign = monkeyData[2].trim().split(" ")[4],
            operationValueStr = monkeyData[2].trim().split(" ").last(),
            divisibleBy = monkeyData[3].trim().split(" ").last().toLong(),
            trueMonkeyId = monkeyData[4].trim().split(" ").last().toInt(),
            falseMonkeyId = monkeyData[5].trim().split(" ").last().toInt()
        )
    }

    // Initialize monkeys
    monkeys.forEach { it.init(monkeys) }

    // Run rounds
    repeat(20) {
        monkeys.forEach { it.turn() }
    }

    monkeys.forEach { println("Monkey ${it.id} inspected items ${it.inspections} times.") }

    val inspectionsOrdered = monkeys.map { it.inspections }.sortedDescending()
    val monkeyBusiness = inspectionsOrdered[0] * inspectionsOrdered[1]
    println("Part 1: $monkeyBusiness")

    val sol2 = 0
    println("Part 2: $sol2")
}
