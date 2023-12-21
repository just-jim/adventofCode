package aoc2023

import tools.*
import kotlin.math.min

private const val test = false

data class Translator(
    val range: LongRange,
    val offset: Long
)

fun main() {
    val file = readFileAs<String>(if (test) "sample" else "aoc2023/day5")

    val seeds = file.first().split(':').last().trim().split(' ').map { it.toLong() }

    val elements = mutableListOf<String>()
    val maps = mutableMapOf<String,MutableList<Translator>>()
    var cur = ""
    file.subList(2,file.size).filter { it.isNotEmpty() }.forEach { line ->
        if(line.last() == ':'){
            cur = line.split(' ').first().split('-').last()
            maps[cur] =mutableListOf()
            elements.add(cur)
        } else {
            val (destinationFrom, sourceFrom, n) = line.split(' ').map{ it.toLong()}
            maps[cur]?.add(Translator((sourceFrom..< sourceFrom+n),destinationFrom-sourceFrom))
        }
    }

    fun findLocation(seed: Long): Long {
        var x = seed
        elements.forEach { element ->
            run lit@{
                maps[element]?.forEach { translator ->
                    if (translator.range.contains(x)) {
                        x += translator.offset
                        return@lit
                    }
                }
            }
        }
        return x
    }

    var min = Long.MAX_VALUE
    seeds.forEach{ seed ->
        min = min(min, findLocation(seed))
    }

    val sol1 = min
    println("Part 1: $sol1")

    min = Long.MAX_VALUE
    for(i in seeds.indices step 2){
        (seeds[i] ..< seeds[i]+seeds[i+1]).forEach { seed ->
            min = min(min,findLocation(seed))
        }
    }

    val sol2 = min
    println("Part 2: $sol2")
}
