package aoc2024

import tools.*
import kotlin.math.min
import kotlinx.coroutines.*
import java.util.*

private const val test = false

fun main() {
    val file = readFileAs<String>(if (test) "sample" else "aoc2024/day6")
    val map = Matrix2d<Int>(file[0].length, file.size)

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

    fun Direction.rotateLeft(): Direction {
        return Direction.entries[(this.ordinal - 1 + Direction.entries.size) % Direction.entries.size]
    }

    fun Direction.rotateRight(): Direction {
        return Direction.entries[(this.ordinal + 1) % Direction.entries.size]
    }

    class Guard(initialCords: Cords,var direction: Direction = Direction.UP) {
        var loopFlag = true;
        var cords = initialCords.clone()
        var path = mutableListOf(Step(cords.clone(),direction))
        var possibleLoopsCords = mutableListOf<Cords>()

        fun set( newPath: MutableList<Step>, newDirection : Direction){
            path = newPath
            direction = newDirection
            loopFlag = false
        }

        fun move(value: Int = 1) : Boolean {
            when (direction) {
                Direction.RIGHT -> cords.x += value
                Direction.DOWN -> cords.y += value
                Direction.LEFT -> cords.x -= value
                Direction.UP -> cords.y -= value
            }
            val beenHere = beenHereBefore()
            path.add(Step(cords.clone(), direction))
            return beenHere
        }

        fun turn(command: String = "R") : Boolean {

            direction = calculateTurnOrientation(command)
            val beenHere = beenHereBefore()
            path.add(Step(cords.clone(), direction))
            return beenHere
        }

        fun beenHereBefore() : Boolean {
            return path.contains(Step(cords, direction))
        }

        fun calculateTurnOrientation(
            command: String = "R"
        ) : Direction {
            return when (command) {
                "L" -> direction.rotateLeft()
                "R" -> direction.rotateRight()
                else -> direction
            }
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

    var initGuardPosition = Cords(0,0)
    file.forEachIndexed { y, line ->
        line.forEachIndexed { x, tile ->
            map[x,y] = if (tile == '.' || tile == '^') 0 else 1
            if(tile == '^'){
                initGuardPosition = Cords(x,y)
            }
        }
    }

    fun printMap(guard: Guard, map: Matrix2d<Int>){
        val mapClone = map.clone()
        guard.path.forEachIndexed { i, step ->
            if(mapClone[step.cords] >= 10 || guard.path[min(i+1,guard.path.size-1)].direction != step.direction){
                mapClone[step.cords] = 14
            } else {
                mapClone[step.cords] = 10 + step.direction.ordinal
            }
        }

        guard.possibleLoopsCords.forEach {
            mapClone[it] = 3
        }

        mapClone[initGuardPosition] = 2

        for (y in (0 until mapClone.rows)) {
            for (x in (0 until mapClone.cols)) {
                // 0=right , 1=down , 2=left , 3=up
                val printMap = mapOf(
                    0 to " ",
                    1 to "#",
                    2 to "x",
                    3 to "o",
                    10 to ">", // >
                    11 to "v", // v
                    12 to "<", // <
                    13 to "^",  // ^
                    14 to "+"
                )
                print(printMap[mapClone[x, y]] + " ")
            }
            println()
        }
        println()
    }

    val guard = Guard(initGuardPosition)
    while(guard.cordsAhead().x in 0 until map.cols && guard.cordsAhead().y in 0 until map.rows){
        while(map[guard.cordsAhead()] == 1){
            guard.turn()
        }
        guard.move()
    }

    val sol1 = guard.path.map { it.cords }.distinct().size
    println("Part 1: $sol1")

    val loops = Collections.synchronizedList(mutableListOf<Cords>())
    runBlocking(Dispatchers.Default) {
        guard.path.mapIndexed { i, step ->
            async {
                val tmpGuard = Guard(step.cords)
                tmpGuard.set(guard.path.subList(0, i).toMutableList(), step.direction)
                if (tmpGuard.cordsAhead().x in 0 until map.cols && tmpGuard.cordsAhead().y in 0 until map.rows) {
                    val possibleLoopCord = tmpGuard.cordsAhead()
                    if(map[possibleLoopCord] == 1) {
                        return@async
                    }
                    tmpGuard.possibleLoopsCords.add(possibleLoopCord)
                    tmpGuard.turn()
                    while (tmpGuard.cordsAhead().x in 0 until map.cols && tmpGuard.cordsAhead().y in 0 until map.rows) {
                        while (map[tmpGuard.cordsAhead()] == 1) {
                            if(tmpGuard.turn()){
                                loops.add(possibleLoopCord)
                                return@async
                            }
                        }
                        if(tmpGuard.move()){
                            loops.add(possibleLoopCord)
                            return@async
                        }
                    }
                }
            }
        }.awaitAll()
    }
    val sol2 = loops.distinct().size

    println("Part 2: $sol2")

    //printMap(guard,map)

    // original
    // 630 - too low
    // 1648 - wrong (possibly higher)

    // test b
    // 667 - too low
    // 1580 - too high
    // 1582 - too high
}
