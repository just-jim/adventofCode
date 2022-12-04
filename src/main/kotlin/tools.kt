import java.io.File

const val path = "src/main/resources/"

fun readFileAsStrings(fileName: String): MutableList<String> =
    File(path + fileName).useLines { it.toMutableList() }

fun readFileAsInt(fileName: String): MutableList<Int> =
    File(path + fileName).useLines { lines -> lines.map { it.toInt() }.toMutableList() }

fun readFileAsLong(fileName: String): MutableList<Long> =
    File(path + fileName).useLines { lines -> lines.map { it.toLong() }.toMutableList() }

fun array2dOfInt(sizeOuter: Int, sizeInner: Int): Array<IntArray> =
    Array(sizeOuter) { IntArray(sizeInner) }

fun array2dOfChar(sizeOuter: Int, sizeInner: Int): Array<CharArray> =
    Array(sizeOuter) { CharArray(sizeInner) }

// Use URL (maybe it will come in handy)
// val file = URL("https://adventofcode.com/2020/day/1/input").readText()
