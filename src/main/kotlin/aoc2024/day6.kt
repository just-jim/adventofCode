package aoc2024

import tools.*

private const val test = false

fun main() {
    val file = readFileAs<String>(if (test) "sample" else "aoc2024/day6")
    val map = Matrix2d<Int>(file[0].length, file.size)

    class Guard(var orientation: Int, cords: Cords) {
        var x = cords.x
        var y = cords.y
        var path = mutableListOf<Cords>(cords)

        fun move(value: Int = 1) {
            // 0=right , 1=down , 2=left , 3=up
            when (orientation) {
                0 -> x += value
                1 -> y += value
                2 -> x -= value
                3 -> y -= value
            }
            path.add(Cords(x, y))
        }

        fun turn(
            command: String,
            value: Int = 90,
        ) {
            val turns = (value / 90) % 4
            when (command) {
                "L" -> orientation -= turns
                "R" -> orientation = (orientation + turns) % 4
            }
            if (orientation < 0) {
                orientation += 4
            }
        }

        fun CordsAhead() : Cords {
            return when (orientation) {
                0 -> Cords(x+1, y)
                1 -> Cords(x, y+1)
                2 -> Cords(x-1, y)
                3 -> Cords(x, y-1)
                else -> Cords(x, y)
            }
        }

        fun info() {
            println("x:$x y:$y | d:$orientation")
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
        guard.path.forEach { cords ->
            mapClone[cords.x.toInt(), cords.y.toInt()] = 8
        }
        mapClone.print()
        println()
    }

    //map.print()
    var guard = Guard(3, initGuardPosition)
    while(guard.CordsAhead().x < map.cols && guard.CordsAhead().y < map.rows){
        while(map[guard.CordsAhead().x, guard.CordsAhead().y] == 1){
            guard.turn("R")
        }
        guard.move()
        //printMap(guard,map);
    }

    val sol1 = guard.path.distinct().size
    println("Part 1: $sol1")

    val sum = 0

    val sol2 = sum
    println("Part 2: $sol2")
}
