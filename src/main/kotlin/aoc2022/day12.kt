package aoc2022

import array2dOfInt
import io.uuddlrlrba.ktalgs.graphs.directed.weighted.DWGraph
import io.uuddlrlrba.ktalgs.graphs.directed.weighted.Dijkstra
import readFileAsStrings

// Get Pair matrix coordinates from index
fun Int.toPair(forMap: Array<IntArray>) = Pair(this % forMap.size, this / forMap.size)
fun Pair<Int, Int>.toIndex(forMap: Array<IntArray>) = this.second * forMap.size + this.first

// Get amount of nodes from matrix
fun Array<IntArray>.nodes() = this.size * this[0].size

// Check cord validity
fun Int.inBounds(forMap: Array<IntArray>) = this in 0 until forMap.nodes()
fun Int.validLeft(forMap: Array<IntArray>) = this.inBounds(forMap) && this % (forMap.size) != forMap.size - 1
fun Int.validRight(forMap: Array<IntArray>) = this.inBounds(forMap) && this % forMap.size != 0
fun Int.validUp(forMap: Array<IntArray>) = this.inBounds(forMap)
fun Int.validDown(forMap: Array<IntArray>) = this.inBounds(forMap)

fun Int.canGo(forMap: Array<IntArray>, current: Int): Boolean {
    val targetHeight = forMap[this.toPair(forMap).first][this.toPair(forMap).second]
    val currentHeight = forMap[current.toPair(forMap).first][current.toPair(forMap).second]
    return targetHeight <= currentHeight + 1
}

// Graph creation functions
fun addEdge(from: Int, to: Int, weight: Int, forGraph: DWGraph) {
    forGraph.addEdge(from, to, weight.toDouble())
}

fun addEdges(fromMap: Array<IntArray>, forGraph: DWGraph) {
    for (i in 0 until fromMap.nodes()) {
        val left = i - 1
        val right = i + 1
        val up = i - fromMap.size
        val down = i + fromMap.size

        if (left.validLeft(fromMap) && left.canGo(fromMap, i)) addEdge(i, left, 1, forGraph)
        if (right.validRight(fromMap) && right.canGo(fromMap, i)) addEdge(i, right, 1, forGraph)
        if (up.validUp(fromMap) && up.canGo(fromMap, i)) addEdge(i, up, 1, forGraph)
        if (down.validDown(fromMap) && down.canGo(fromMap, i)) addEdge(i, down, 1, forGraph)
    }
}

fun main() {
    val test = false
    val file = readFileAsStrings(if (test) "sample" else "aoc2022/day12")

    // Create the map
    var start: Pair<Int, Int> = Pair(0, 0)
    var target: Pair<Int, Int> = Pair(0, 0)
    val starts: MutableList<Pair<Int, Int>> = mutableListOf()
    val map = array2dOfInt(file[0].length, file.size)
    file.forEachIndexed { y, line ->
        line.forEachIndexed { x, tile ->
            map[x][y] = when (tile) {
                'S' -> {
                    start = Pair(x, y)
                    'a'.code - 97
                }
                'E' -> {
                    target = Pair(x, y)
                    'z'.code - 97
                }
                'a' -> {
                    starts.add(Pair(x, y))
                    tile.code - 97
                }
                else -> {
                    tile.code - 97
                }
            }
        }
    }

    // Create the graph
    val graph = DWGraph(map.nodes())
    addEdges(map, graph)

    // Calculate solution
    val sol1 = Dijkstra(graph, start.toIndex(map)).distTo(target.toIndex(map)).toInt()
    println("Part 1: $sol1")

    val allPaths = starts.map { Dijkstra(graph, it.toIndex(map)).distTo(target.toIndex(map)).toInt() }
    val sol2 = allPaths.minOf { it }
    println("Part 2: $sol2")
}
