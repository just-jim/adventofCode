package aoc2024

import tools.*

private const val test = false

fun main() {
    val file = readFileAs<String>(if (test) "sample" else "aoc2024/day6e")

    class Step(var cords: Cords, var direction: Direction){
        override fun toString() : String{
            return "$cords | ${direction.symbol} "
        }

        override fun equals(other: Any?): Boolean {
            if (other is Step) {
                return cords == other.cords && direction == other.direction
            }
            return false
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    fun Direction.rotateRight(): Direction {
        return Direction.entries[(this.ordinal + 1) % Direction.entries.size]
    }

    class Guard(initialCords: Cords, var map : Matrix2d<Int> , var direction: Direction = Direction.UP) {
        var cords = initialCords.clone()
        var path = mutableListOf<Step>()
        var possibleLoopsCords = mutableListOf<Cords>()

        fun canMove() : Boolean {
            return cordsAhead().x in 0 until map.cols && cordsAhead().y in 0 until map.rows
        }

        fun blockAhead() : Boolean {
            return map[cordsAhead()] == 1
        }

        fun move() {
            if(blockAhead()){
                turn()
                return
            }
            path.add(Step(cords, direction))
            cords = cordsAhead()
        }

        fun turn() {
            path.add(Step(cords, direction))
            direction = direction.rotateRight()
        }

        fun beenHereBefore() : Boolean {
            return path.contains(Step(cords, direction))
        }

        fun cordsAhead() : Cords {
            return when (direction) {
                Direction.RIGHT -> Cords(cords.x+1, cords.y)
                Direction.DOWN -> Cords(cords.x, cords.y+1)
                Direction.LEFT -> Cords(cords.x-1, cords.y)
                Direction.UP -> Cords(cords.x, cords.y-1)
            }
        }

        fun info() {
            println("x:$cords.x y:$cords.y | d:${direction.name}")
        }
    }

    fun printMap(guard: Guard, map: Matrix2d<Int>){
        val mapClone = map.clone()
        guard.path.forEachIndexed { i, step ->
            if(mapClone[step.cords] >= 10){
                mapClone[step.cords] = 14
            } else {
                mapClone[step.cords] = 10 + step.direction.ordinal
            }
        }

        guard.possibleLoopsCords.forEach {
            mapClone[it] = 3
        }

        mapClone[guard.path.first().cords] = 2

        for (y in (0 until mapClone.rows)) {
            for (x in (0 until mapClone.cols)) {
                // 0=right , 1=down , 2=left , 3=up
                val printMap = mapOf(
                    0 to ".",
                    1 to "#",
                    2 to "x",
                    3 to "o",
                    10 to ">", // >
                    11 to "v", // v
                    12 to "<", // <
                    13 to "^", // ^
                    14 to "+"
                )
                print(printMap[mapClone[x, y]] + "")
            }
            println()
        }
        println()
    }

    val map = Matrix2d<Int>(file[0].length, file.size)
    var initGuardPosition = Cords(0,0)
    file.forEachIndexed { y, line ->
        line.forEachIndexed { x, tile ->
            map[x,y] = if (tile == '#') 1 else 0
            if(tile == '^'){
                initGuardPosition = Cords(x,y)
            }
        }
    }

    var guard = Guard(initGuardPosition, map)
    while(guard.canMove()){
        guard.move()
    }

    val sol1 = guard.path.map { it.cords }.distinct().size + 1
    println("Part 1: $sol1")

    val loops = mutableListOf<Cords>()
    guard = Guard(initGuardPosition, map)
    while (guard.canMove()) {
        // Simulate possible loop
        val possibleLoopCord = guard.cordsAhead()
        if (map[possibleLoopCord] != 1) {
            var loop = false;
            val tmpMap = map.clone()
            tmpMap[possibleLoopCord] = 1
            val tmpGuard = Guard(guard.cords, tmpMap, guard.direction)
            while (tmpGuard.canMove() && !loop) {
                tmpGuard.move()
                if (tmpGuard.beenHereBefore()) {
                    loops.add(possibleLoopCord)
                    tmpGuard.possibleLoopsCords.add(possibleLoopCord)
                    //printMap(tmpGuard, tmpMap)
                    loop = true
                }
            }
        }

        // Actually move
        guard.move()
    }

    val sol2 = loops.distinct().size

    println("Part 2: $sol2")

    //printMap(guard,map)

    // original (github)
    // 630 - too low
    // 1648 - wrong (possibly higher)
    // 1650 - wrong (??)
    // 1661 - wrong (??)
    // 1769 ?? (haven't submitted yet)

    // test b (twitter)
    // 667 - too low
    // 1580 - too high
    // 1582 - too high

    // test c  (just.jim.trace@gmail.cm
    // 775 - too low
    // 1781 - wrong (??)
    // 1802 - too high
    // 1843 - too high
    // 1929 - too high

    // test d (reddit)
    // 2088 - too high

    // test e (just.jim.d.k@gmail.com)
    // 1912 - too high
    // 1913 - too high
}
