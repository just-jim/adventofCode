package tools

import kotlin.math.absoluteValue

data class Cords(var x: Long, var y: Long) {

    constructor(x: Int, y: Int) : this(x.toLong(), y.toLong())

    fun up() = Cords(x, y-1)
    fun right() = Cords(x+1, y)
    fun down() = Cords(x, y+1)
    fun left() = Cords(x-1, y)

    fun manhatanTo(other: Cords): Long {
        val dx = this.x - other.x
        val dy = this.y - other.y
        return dx.absoluteValue + dy.absoluteValue
    }

    override fun toString(): String = "[${x.toInt()}, ${y.toInt()}]"

    fun clone() = Cords(x, y)
}