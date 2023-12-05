import java.io.File

const val path = "src/main/resources/"

fun readFileAsStrings(fileName: String): MutableList<String> =
    File(path + fileName).useLines { it.toMutableList() }

fun readFileAsInt(fileName: String): MutableList<Int> =
    File(path + fileName).useLines { lines -> lines.map { it.toInt() }.toMutableList() }

fun readFileAsLong(fileName: String): MutableList<Long> =
    File(path + fileName).useLines { lines -> lines.map { it.toLong() }.toMutableList() }

fun array2dOfInt(
    sizeOuter: Int,
    sizeInner: Int,
): Array<IntArray> = Array(sizeOuter) { IntArray(sizeInner) }

fun array2dOfChar(
    sizeOuter: Int,
    sizeInner: Int,
): Array<CharArray> = Array(sizeOuter) { CharArray(sizeInner) }

fun array2dOfBool(
    sizeOuter: Int,
    sizeInner: Int,
): Array<BooleanArray> = Array(sizeOuter) { BooleanArray(sizeInner) }

fun Array<IntArray>.print() {
    for (y in this.indices) {
        for (x in 0 until this[0].size) {
            print(this[x][y].toString() + " ")
        }
        println()
    }
    println()
}

fun Array<CharArray>.x() = this.size
fun Array<CharArray>.y() = this.first().size

fun List<String>.print() {
    this.forEach { println(it) }
    println()
}

fun MutableMap<String, Int>.print() {
    this.forEach { println("${it.key} : ${it.value}") }
}

// Use URL (maybe it will come in handy)
// val file = URL("https://adventofcode.com/2020/day/1/input").readText()
