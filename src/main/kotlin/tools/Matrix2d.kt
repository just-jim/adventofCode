package tools

import jdk.jfr.Description

@Suppress("UNCHECKED_CAST")
@Description("Cords (0,0) are in the top left corner of the matrix")
class Matrix2d<T>(var cols: Int, var rows: Int) {

    val map = Array<Array<Any?>>(cols) { Array(rows) { null } }

    fun initialize(value: T) {
        for (y in (0 until rows)) {
            for (x in (0 until cols)) {
                this[x, y] = value
            }
        }
    }

    operator fun get(x: Int, y: Int): T {
        return get(Cords(x,y))
    }

    operator fun get(x: Long, y: Long): T {
        return get(Cords(x,y))
    }

    operator fun get(cords: Cords): T {
        return if(cords.inBounds()) {
            map[cords.x.toInt()][cords.y.toInt()] as T
        } else {
            throw IndexOutOfBoundsException("Cannot get $cords. Out of bounds.")
        }
    }

    fun tryGet(x: Int, y:Int): T? {
        return try{
            this[x,y]
        } catch (e: Exception) {
            null
        }
    }

    fun tryGet(cords: Cords): T? {
        return try{
            this[cords]
        } catch (e: Exception) {
            null
        }
    }

    operator fun set(x: Int, y: Int, value: T) {
        return set(Cords(x,y),value)
    }

    operator fun set(cords: Cords, value: T){
        return if(cords.inBounds()) {
            map[cords.x.toInt()][cords.y.toInt()] = value
        } else {
            throw IndexOutOfBoundsException("Cannot set $cords. Out of bounds.")
        }
    }

    fun print() {
        for (y in (0 until rows)) {
            for (x in (0 until cols)) {
                print(this[x, y].toString() + " ")
            }
            println()
        }
        println()
    }

    private fun Cords.inBounds() = (0 until rows).contains(this.y) && (0 until cols).contains(this.x)

    fun forEach(action: (T) -> Unit) {
        for (y in (0 until rows)) {
            for (x in (0 until cols)) {
                action(this[x, y]!!)
            }
        }
    }

    fun foreachIndexed(action: (Cords, T) -> Unit) {
        for (y in (0 until rows)) {
            for (x in (0 until cols)) {
                action(Cords(x,y), this[x, y]!!)
            }
        }
    }

    fun map(action: (T) -> T) {
        for (y in (0 until rows)) {
            for (x in (0 until cols)) {
                this[x, y] = action(this[x, y]!!)
            }
        }
    }

    fun mapIndexed(action: (Cords, T) -> T) {
        for (y in (0 until rows)) {
            for (x in (0 until cols)) {
                this[x, y] = action(Cords(x,y), this[x, y]!!)
            }
        }
    }

    fun xRange() = (0 until cols)
    fun yRange() = (0 until rows)

    // Doesn't work as expected, need to implement deep clone
    fun clone() = Matrix2d<T>(cols, rows).also{ newMatrix ->
        for (y in (0 until rows)) {
            for (x in (0 until cols)) {
                newMatrix[x, y] = this[x, y]!!
            }
        }
    }

    fun row(y: Int) = (0 until cols).map { this[it, y]!! as T }
    fun col(x: Int) = (0 until rows).map { this[x, it]!! as T }

    fun rows() = (0 until rows).map { row(it) }
    fun cols() = (0 until cols).map { col(it) }

    fun diagonal(x: Int, y: Int): List<T> {
        val diagonal = mutableListOf<T>()
        var x1 = x
        var y1 = y
        while (x1 < cols && y1 < rows) {
            diagonal.add(this[x1, y1]!!)
            x1++
            y1++
        }
        return diagonal
    }

    fun diagonals() = (0 until cols).map { diagonal(it, 0) } + (1 until rows).map { diagonal(0, it) }

    fun antiDiagonal(x: Int, y: Int): List<T> {
        val diagonal = mutableListOf<T>()
        var x1 = x
        var y1 = y
        while (x1 >= 0 && y1 < rows) {
            diagonal.add(this[x1, y1]!!)
            x1--
            y1++
        }
        return diagonal
    }

    fun antiDiagonals() = (0 until cols).map { antiDiagonal(it, 0) } + (1 until rows).map { antiDiagonal(cols-1, it) }
}