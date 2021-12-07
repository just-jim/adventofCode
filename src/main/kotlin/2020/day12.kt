import kotlin.math.absoluteValue
fun main() {
    class Boat{
        var orientation: Int = 0
        var x: Int = 0
        var y: Int = 0

        var wx: Int = 10
        var wy: Int = 1

        fun navigate(command:String, value:Int){
            when(command){
                "N" -> y+= value
                "E" -> x+= value
                "S" -> y-= value
                "W" -> x-= value
            }
        }

        fun navigateWaypoint(command:String, value:Int){
            when(command){
                "N" -> wy+= value
                "E" -> wx+= value
                "S" -> wy-= value
                "W" -> wx-= value
            }
        }

        fun forward(value:Int){
            // 2=left/west , 3=north , 0=right/east, 1=south
            when(orientation){
                0 -> x+= value
                1 -> y-= value
                2 -> x-= value
                3 -> y+= value
            }
        }

        fun forwardWaypoint(value:Int){
            x += value*wx
            y += value*wy
        }

        fun turn(command:String, value:Int){
            val turns = (value/90)%4
            when(command){
                "L" -> orientation -= turns
                "R" -> orientation = (orientation + turns) %4
            }
            if(orientation <0)
                orientation +=4
        }

        fun turnWaypoint(command:String, value:Int){
            val turns = (value/90)%4
            when(command){
                "L" -> for (i in 0 until turns) turnWaypointLeft()
                "R" -> for (i in 0 until turns) turnWaypointRight()
            }
        }

        fun turnWaypointLeft(){
            val tmp = wx
            wx = -wy
            wy = tmp
        }

        fun turnWaypointRight(){
            val tmp = wx
            wx = wy
            wy = -tmp
        }

        fun manhatan(): Int{
            return x.absoluteValue + y.absoluteValue
        }

        fun info(){
            println("x:$x y:$y | d:$orientation")
        }
    }

    val boat = Boat()

    val cords = readFileAsStrings("2020/day12")
    cords.forEach { line ->
        val command = line.take(1)
        val value = line.substring(1).toInt()

        if("NESW".contains(command))
            boat.navigate(command,value)
        else if( command == "F")
            boat.forward(value)
        else if( "LR".contains(command))
            boat.turn(command, value)
    }

    println("Part1: ${boat.manhatan()}")

    val boat2 = Boat()
    cords.forEach { line ->
        val command = line.take(1)
        val value = line.substring(1).toInt()

        if("NESW".contains(command))
            boat2.navigateWaypoint(command,value)
        else if( command == "F")
            boat2.forwardWaypoint(value)
        else if( "LR".contains(command))
            boat2.turnWaypoint(command, value)
    }

    println("Part2: ${boat2.manhatan()}")
}