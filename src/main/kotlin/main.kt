import java.io.File

fun main(args: Array<String>) {
    //Use URL (maybe it will come in handy)
    //val expenses = URL("https://adventofcode.com/2020/day/1/input").readText()

    /*// Day 1
    // Part 1 + 2
    val expenses = readFileAsLinesUsingUseLines("src/expenses")
    expenses.forEach { a ->
        expenses.forEach {b->
            expenses.forEach {c->
                if (a.toInt() + b.toInt() + c.toInt() == 2020) {
                    println("Day 1 Part 2"+(a.toInt() * b.toInt() * c.toInt()))
                }
            }
        }
    }
    */

    /*// Day 2
    val passwords = readFileAsLinesUsingUseLines("src/passwords")
    var valid1 = 0
    var valid2 = 0
    passwords.forEach {
        val info = it.split(" ")
        val range = info[0].split("-")
        val char = info[1][0]
        val password = info[2]

        // Part 1
        val charCount = password.count{char.toString().contains(it)}
        if(charCount in range[0].toInt()..range[1].toInt())
            valid1++

        // Part 2
        if((password[range[0].toInt()-1] == char).xor(password[range[1].toInt()-1] == char))
            valid2++
    }

    println("Day 2 Part 1 solution: $valid1")
    println("Day 2 Part 2 solution: $valid2")
    */

    /*// Day 3
    // Create the map
    val mapFile = readFileAsLinesUsingUseLines("src/map")
    val map = array2dOfInt(mapFile[0].length,mapFile.size)
    mapFile.forEachIndexed { i, line->
        line.forEachIndexed { i2, tile ->
          map[i2][i] = if( tile == '.') 0 else 1
        }
    }

    // Part 1
    val stepX = 3
    val stepY = 1
    var x = 0
    var trees = 0
    for (y in stepY until map[0].size step stepY) {
        if(y <= map[0].size) {
            x += stepX
            if (map[x % map.size][y] == 1)
                trees++
        }
    }
    println("Day 3 Part 1 solution: $trees")

    // Part 2
    val stepList : Array<Pair<Int,Int>> = Array(5) {Pair(1,1)}
    stepList[0]= Pair(1,1)
    stepList[1]= Pair(3,1)
    stepList[2]= Pair(5,1)
    stepList[3]= Pair(7,1)
    stepList[4]= Pair(1,2)

    var sol = 1
    stepList.forEach {steps->
        trees = 0
        x = 0
        for (y in steps.second until map[0].size step steps.second) {
            if(y <= map[0].size) {
                x += steps.first
                if (map[x % map.size][y] == 1)
                    trees++
            }
        }
        println(trees)
        sol *= trees
    }

    println("Day 3 Part 1 $sol")
    */

    /*// Day 4 */
    val data = readFileAsLinesUsingUseLines("src/passports")
    var passports :MutableList<Passport> = mutableListOf()
    var curPassport: Passport? = null
    data.forEach {
        if(it == "" && curPassport != null){
            passports.add(curPassport)
        }
        it.split(" ").forEach {  }
    }
}

class Passport{
    val byr : String? = null
    val iyr : String? = null
    val eyr : String? = null
    val hgt : String? = null
    val hcl : String? = null
    val ecl : String? = null
    val pid : String? = null
    val cid : String? = null

    fun isValid(): Boolean
    {
        return listOfNotNull(byr, iyr, eyr, hgt, hcl, ecl, pid).size == 7
    }
}

fun readFileAsLinesUsingUseLines(fileName: String): List<String>
= File(fileName).useLines { it.toList() }

fun array2dOfInt(sizeOuter: Int, sizeInner: Int): Array<IntArray>
    = Array(sizeOuter) { IntArray(sizeInner) }
