fun main() {
    val file = readFileAsStrings("2020/day13")
    val time = file[0].toInt()
    val times = mutableMapOf<Int,Int>()
    val map = mutableMapOf<Int,Int>()
    file[1].split(",").forEachIndexed { i, busId ->
        if(busId != "x") {
            val bus = busId.toInt()
            times[bus] = (((time / bus)+1)*bus - time)
            map[bus] = i
        }
    }

    val min = times.minByOrNull { it.value }
    val sol1 = min!!.value * min.key
    println("Part 1: $sol1")

    val max = map.maxByOrNull { it.key }
    var sol2: Long = 0
    map.remove(max!!.key)
    var i = 0L
    run lit@{
        while (true) {
            i++
            val target = max.key * i
            if (map.all {
                    (target + it.value - max.value)%it.key == 0L
                }){
                sol2 = target - max.value
                return@lit
            }
        }
    }
    println("Part 2: $sol2")
}