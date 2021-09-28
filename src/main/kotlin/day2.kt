fun main() {
    val passwords = readFileAsStrings("src/passwords")
    var valid1 = 0
    var valid2 = 0
    passwords.forEach { line ->
        val info = line.split(" ")
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
}