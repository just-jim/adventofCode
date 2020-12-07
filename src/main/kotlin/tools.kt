import java.io.File

fun readFileAsLinesUsingUseLines(fileName: String): List<String>
= File(fileName).useLines { it.toList() }

fun array2dOfInt(sizeOuter: Int, sizeInner: Int): Array<IntArray>
        = Array(sizeOuter) { IntArray(sizeInner) }

//Use URL (maybe it will come in handy)
//val expenses = URL("https://adventofcode.com/2020/day/1/input").readText()