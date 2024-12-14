package tools

class Tile<T>(var cords: Cords, var v: T, val map: Matrix2d<Tile<T>>) {

    var id: Int = cords.y.toInt() * map.cols + cords.x.toInt()

    fun isLeftValid() = cords.x in 1 until map.cols
    fun isRightValid() = cords.x in 0 until map.cols
    fun isUpValid() = cords.y in 1 until map.rows
    fun isDownValid() = cords.y in 0 until map.rows

    override fun toString(): String = "[${cords.x.toInt()}, ${cords.y.toInt()}] ($v)"

    fun clone() = Tile<T>(cords.clone(), v, map)
}