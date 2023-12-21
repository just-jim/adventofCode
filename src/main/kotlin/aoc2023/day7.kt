package aoc2023

import tools.*

enum class Poker(val score: Int) {
    HighCard(0),
    OnePair(1),
    TwoPair(2),
    ThreeOfAKind(3),
    FullHouse(4),
    FourOfAKind(5),
    FiveOfAKind(6)
}

fun Char.score(jokerRules: Boolean): Int {
    if (this.isDigit())
        return this.toString().toInt()
    return when (this) {
        'T' -> 10
        'J' -> if (jokerRules) 1 else 11
        'Q' -> 12
        'K' -> 13
        'A' -> 14
        else -> 0
    }
}

fun String.countCards(): Map<Char, Int> {
    val characterCount = mutableMapOf<Char, Int>()
    for (char in this) {
        if (!characterCount.containsKey(char)) {
            characterCount[char] = 1
        } else {
            characterCount[char] = characterCount[char]!! + 1
        }
    }
    return characterCount
}

fun String.classify(jokerRules: Boolean):Poker {
    val cards = this.countCards()
    val jokers = cards['J'] ?: 0
    if(jokerRules && jokers > 0){
        if(cards.keys.size == 2)
            return Poker.FiveOfAKind

        return when (jokers) {
            5 -> Poker.FiveOfAKind
            4 -> Poker.FiveOfAKind
            3 -> Poker.FourOfAKind
            2 -> if(cards.keys.size == 3)
                    Poker.FourOfAKind
                else
                    Poker.ThreeOfAKind
            1 -> if (cards.keys.size == 3) {
                    if(cards.values.any{ it == 3}){
                        Poker.FourOfAKind
                    } else {
                        Poker.FullHouse
                    }
                } else if (cards.keys.size == 4) {
                    Poker.ThreeOfAKind
                } else {
                    Poker.OnePair
                }
            else -> Poker.HighCard
        }
    } else {
        return when (cards.values.max()) {
            5 -> Poker.FiveOfAKind
            4 -> Poker.FourOfAKind
            3 -> if (cards.values.any { it == 2 }) Poker.FullHouse else Poker.ThreeOfAKind
            2 -> if (cards.values.count { it == 2 } == 2) Poker.TwoPair else Poker.OnePair
            else -> Poker.HighCard
        }
    }
}

fun String.win(other: String,jokerRules: Boolean):Boolean {
    val thisPoker = this.classify(jokerRules)
    val otherPoker = other.classify(jokerRules)

    if(thisPoker == otherPoker){
        this.forEachIndexed { i, it->
            if(it.score(jokerRules) != other[i].score(jokerRules)){
                return it.score(jokerRules) > other[i].score(jokerRules)
            }
        }
    }
    return thisPoker > otherPoker
}

fun Boolean.toCompareInt() = if(this) 1 else -1

data class Hand(
    val cards: String,
    val bid: Int
)

private const val test = false

fun main() {
    val file = readFileAs<String>(if (test) "sample" else "aoc2023/day7")

    var sum = 0.0
    val hands = file.map { line ->
        val (cards, bid) = line.split(' ')
        Hand(cards,bid.toInt())
    }

    hands.sortedWith { a, b ->
        a.cards.win(b.cards,false).toCompareInt()
    }.forEachIndexed { i, hand ->
        sum += hand.bid * (i+1)
    }

    val sol1 = sum.toInt()
    println("Part 1: $sol1")

    sum = 0.0
    hands.sortedWith { a, b ->
        a.cards.win(b.cards,true).toCompareInt()
    }.forEachIndexed { i, hand ->
        sum += hand.bid * (i+1)
    }

    val sol2 = sum.toInt()
    println("Part 2: $sol2")
}
