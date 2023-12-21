package tools

data class Cords(var x: Int, var y: Int) {
    fun up() = Cords(x, y-1)
    fun right() = Cords(x+1, y)
    fun down() = Cords(x, y+1)
    fun left() = Cords(x-1, y)

    override fun toString(): String = "[$x, $y]"
}