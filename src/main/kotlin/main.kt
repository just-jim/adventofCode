import java.io.File
import kotlin.math.ceil

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

    println("Day 2 Part 1: $valid1")
    println("Day 2 Part 2: $valid2")
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
    println("Day 3 Part 1: $trees")

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

    println("Day 3 Part 1: $sol")
    */

    /*// Day 4
    val data = readFileAsLinesUsingUseLines("src/passports")
    var passports :MutableList<Passport> = mutableListOf()
    var passports2 :MutableList<Passport> = mutableListOf()
    var curPassport: Passport? = Passport()
    data.forEach { line ->
        if(line == ""){
            // Part 1
            if(curPassport!!.isValid()) {
                passports.add(curPassport!!)
            }
            // Part 2
            if(curPassport!!.isValid2()){
                passports2.add(curPassport!!)
            }
            curPassport = Passport()
        }
        else
        {
            line.split(" ").forEach {
                val info = it.split(":")
                when(info[0].toLowerCase()){
                    "byr" -> curPassport?.byr = info[1]
                    "iyr" -> curPassport?.iyr = info[1]
                    "eyr" -> curPassport?.eyr = info[1]
                    "hgt" -> curPassport?.hgt = info[1]
                    "hcl" -> curPassport?.hcl = info[1]
                    "ecl" -> curPassport?.ecl = info[1]
                    "pid" -> curPassport?.pid = info[1]
                    "cid" -> curPassport?.cid = info[1]

                }
            }
        }
    }
    println("Day 4 Part 1: ${passports.size}")
    println("Day 4 Part 2: ${passports2.size}")
    */

    /*// Day 5
    var seats = "0".repeat(128*8).toCharArray()
    val data = readFileAsLinesUsingUseLines("src/seats")
    var max = 0
    data.forEach { line ->
        var yF = 0
        var yB = 128
        var xL = 0
        var xR = 8
        line.forEach {
            when(it){
                'F'-> yB -= ceil((((yB - yF) / 2).toDouble())).toInt()
                'B'-> yF += ceil((((yB - yF) / 2).toDouble())).toInt()
                'L'-> xR -= ceil((((xR - xL) / 2).toDouble())).toInt()
                'R'-> xL += ceil((((xR - xL) / 2).toDouble())).toInt()
            }
        }

        // Part 1
        val sol = yF*8+xL
        if(sol > max){
            max = sol
        }

        // Part 2
        seats[yF*8+xL] = '1'
    }

    val index = seats.joinToString(separator = "").indexOf("101")+1
    val mySeat = index+(index%8)

    println("Day 5 Part 1: $max")
    println("Day 6 Part 2: $mySeat")
    */

    /*// Day 6
    val data = readFileAsLinesUsingUseLines("src/customs")
    var group: MutableSet<Char> = mutableSetOf()
    var group2: MutableMap<Char,Int> = mutableMapOf()
    var count = 0
    var count2 = 0
    var groupMembers = 0
    data.forEach { line ->
        if(line == ""){
            count += group.size
            group = mutableSetOf()

            group2.forEach{
                if (it.value == groupMembers)
                    count2++
            }
            group2 = mutableMapOf()
            groupMembers = 0
        }
        else
        {
            groupMembers++
            line.toCharArray().forEach {
                group.add(it)
                group2.putIfAbsent(it,0)
                group2.merge(it,1, Int::plus)
            }
        }

    }

    println("Day 5 Part 1: $count")
    println("Day 5 Part 2: $count2")
    */

    /*// Day 7 */
    val data = readFileAsLinesUsingUseLines("src/customs")
    data.forEach { line ->

    }
}

class Passport{
var byr : String? = null
var iyr : String? = null
var eyr : String? = null
var hgt : String? = null
var hcl : String? = null
var ecl : String? = null
var pid : String? = null
var cid : String? = null

fun isValid(): Boolean {
    return listOfNotNull(byr, iyr, eyr, hgt, hcl, ecl, pid).size == 7
}

fun isValid2(): Boolean {
    val v1 = byr?.toIntOrNull() in 1920..2002
    val v2 = iyr?.toIntOrNull() in 2010..2020
    val v3 = eyr?.toIntOrNull() in 2020..2030
    val v4 = hgt?.matches("\\d+\\s*(?:cm|in)".toRegex())
    val v5 = hcl?.matches("^#([a-fA-F0-9]{6})\$".toRegex())
    val v6 = ecl?.matches("\\b(?:amb|blu|brn|gry|grn|hzl|oth)\\b".toRegex())
    val v7 = pid?.length == 9 && pid?.matches("^\\d{9}\$".toRegex()) == true
    var v8:Boolean = false
    if(hgt?.takeLast(2) == "cm")
        v8 = hgt?.dropLast(2)?.toIntOrNull() in 150..193
    else if(hgt?.takeLast(2) == "in")
        v8 =hgt?.dropLast(2)?.toIntOrNull() in 59..76

    return isValid()&&v1&&v2&&v3&& v4 == true && v5 == true && v6 == true && v7 && v8
}

override fun toString() = isValid2().toString()+": byr=$byr iyr=$iyr eyr=$eyr hgt=$hgt hcl=$hcl ecl=$ecl pid=$pid cid=$cid"
}

fun readFileAsLinesUsingUseLines(fileName: String): List<String>
= File(fileName).useLines { it.toList() }

fun array2dOfInt(sizeOuter: Int, sizeInner: Int): Array<IntArray>
= Array(sizeOuter) { IntArray(sizeInner) }
