package tools

import java.io.File

const val path = "src/main/resources/"

inline fun <reified T> readFileAs(fileName: String): MutableList<T> =
    File(path + fileName).useLines { lines ->
        lines.map {
            when (T::class) {
                Int::class -> it.toInt()
                Long::class -> it.toLong()
                String::class -> it
                else -> throw Exception("Cannot read file as ${T::class.simpleName}")
            } as T
        }.toMutableList()
    }

fun <T> List<T>.print() {
    print("[")
    print(this.joinToString(","))
    println("]")
}

fun <T> List<T>.printEachElement() {
    this.forEach { println(it) }
    println()
}


fun <K, V> Map<K, V>.print() {
    this.forEach { println("${it.key} : ${it.value}") }
}

inline fun <reified T>MutableList<String>.toMatrix(): Matrix2d<T> {
    val matrix = Matrix2d<T>(this[0].length, this.size)
    for (y in this.indices) {
        for (x in (0 until this[0].length)) {
            matrix[x,y] = when(T::class){
                Int::class -> this[y][x].toString().toInt()
                Long::class -> this[y][x].toString().toLong()
                Char::class -> this[y][x]
                else -> throw Exception("Cannot convert file to Matrix of ${T::class.simpleName}")
            } as T
        }
    }
    return matrix
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T>MutableList<String>.toTileMatrix(): Matrix2d<Tile<T>> {
    val matrix = Matrix2d<Tile<T>>(this[0].length, this.size)
    for (y in this.indices) {
        for (x in (0 until this[0].length)) {
            matrix[x,y] = when(T::class){
                Int::class -> Tile(Cords(x,y), this[y][x].toString().toInt(), matrix as Matrix2d<Tile<Int>>)
                Long::class ->  Tile(Cords(x,y), this[y][x].toString().toLong(), matrix as Matrix2d<Tile<Long>>)
                Char::class ->  Tile(Cords(x,y), this[y][x], matrix as Matrix2d<Tile<Char>>)
                else -> throw Exception("Cannot convert file to Tile Matrix of ${T::class.simpleName}")
            } as Tile<T>
        }
    }
    return matrix
}

enum class Direction(val symbol: String) {
    RIGHT(">"),
    DOWN("v"),
    LEFT("<"),
    UP("^");
}

// Use URL (maybe it will come in handy)
// val file = URL("https://adventofcode.com/2020/day/1/input").readText()
