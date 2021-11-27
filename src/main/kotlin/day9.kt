fun main() {
    val preambleSize = 25
    val data = readFileAsLong("day9")
    val preamble = mutableListOf<Long>()

    var solution1 = 0L

    fun allPossibleSums(list: List<Long>): Set<Long> {
        val sums = mutableSetOf<Long>()
        val n = list.size
        for (i in 0 until n) {
            for (j in 0 until n) {
                sums.add(list[i] + list[j])
            }
        }
        return sums
    }

    run lit@{
        data.forEach {
            if (preamble.size < preambleSize)
                preamble.add(it)
            else {
                val allSums = allPossibleSums(preamble)
                if (!allSums.contains(it)) {
                    solution1 = it
                    println("Part1: $it")
                    return@lit
                }

                preamble.removeFirst()
                preamble.add(it)
            }
        }
    }

    var sum = 0L
    val miniList = mutableListOf<Long>()
    run lit@{
        data.forEach {
            if (sum > solution1) {
                while(sum > solution1) {
                    sum -=  miniList.removeFirst()
                }
            }
            if (sum == solution1) {
                val solution2 = miniList.minOrNull()!! + miniList.maxOrNull()!!
                print("Part2: $solution2")
                return@lit
            }

            sum += it
            miniList.add(it)
        }
    }
}