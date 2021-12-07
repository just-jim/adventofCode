fun main() {
    // Create the map
    val mapFile = readFileAsStrings("2020/day3")
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
    println("Part 1: $trees")

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

    println("Part 2: $sol")
}