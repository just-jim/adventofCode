package aoc2020

import readFileAsStrings

fun main() {
    val data = readFileAsStrings("aoc2020/day4")
    val passports: MutableList<Passport> = mutableListOf()
    val passports2: MutableList<Passport> = mutableListOf()
    var curPassport: Passport? = Passport()
    data.forEach { line ->
        if (line == "") {
            // Part 1
            if (curPassport!!.isValid()) {
                passports.add(curPassport!!)
            }
            // Part 2
            if (curPassport!!.isValid2()) {
                passports2.add(curPassport!!)
            }
            curPassport = Passport()
        } else {
            line.split(" ").forEach {
                val info = it.split(":")
                when (info[0].toLowerCase()) {
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
    println("Part 1: ${passports.size}")
    println("Part 2: ${passports2.size}")
}

class Passport {
    var byr: String? = null
    var iyr: String? = null
    var eyr: String? = null
    var hgt: String? = null
    var hcl: String? = null
    var ecl: String? = null
    var pid: String? = null
    var cid: String? = null

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
        var v8: Boolean = false
        if (hgt?.takeLast(2) == "cm") {
            v8 = hgt?.dropLast(2)?.toIntOrNull() in 150..193
        } else if (hgt?.takeLast(2) == "in") {
            v8 = hgt?.dropLast(2)?.toIntOrNull() in 59..76
        }

        return isValid() && v1 && v2 && v3 && v4 == true && v5 == true && v6 == true && v7 && v8
    }

    override fun toString() = isValid2().toString() + ": byr=$byr iyr=$iyr eyr=$eyr hgt=$hgt hcl=$hcl ecl=$ecl pid=$pid cid=$cid"
}