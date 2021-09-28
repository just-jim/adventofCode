import java.io.File

fun readFileAsStrings(fileName: String): MutableList<String>
= File(fileName).useLines { it.toMutableList() }

fun readFileAsInt(fileName: String): MutableList<Int>
= File(fileName).useLines { lines -> lines.map{it.toInt()}.toMutableList() }

fun readFileAsLong(fileName: String): MutableList<Long>
= File(fileName).useLines { lines -> lines.map{it.toLong()}.toMutableList() }

fun array2dOfInt(sizeOuter: Int, sizeInner: Int): Array<IntArray>
= Array(sizeOuter) { IntArray(sizeInner) }

//Use URL (maybe it will come in handy)
//val expenses = URL("https://adventofcode.com/2020/day/1/input").readText()