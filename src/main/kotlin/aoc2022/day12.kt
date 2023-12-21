package aoc2022

import tools.*
import io.uuddlrlrba.ktalgs.graphs.directed.weighted.DWGraph
import io.uuddlrlrba.ktalgs.graphs.directed.weighted.Dijkstra

// Get Pair matrix coordinates from index
fun Int.toPair(forMap: Matrix2d<Int>) = Pair(this % forMap.cols, this / forMap.cols)

fun Pair<Int, Int>.toIndex(forMap: Matrix2d<Int>) = this.second * forMap.cols + this.first

// Get amount of nodes from matrix
fun Matrix2d<Int>.nodes() = this.cols * this.rows

// Check cord validity
fun Int.inBounds(forMap: Matrix2d<Int>) = this in 0 until forMap.nodes()

fun Int.validLeft(forMap: Matrix2d<Int>) = this.inBounds(forMap) && this % (forMap.cols) != forMap.cols - 1

fun Int.validRight(forMap: Matrix2d<Int>) = this.inBounds(forMap) && this % forMap.cols != 0

fun Int.validUp(forMap: Matrix2d<Int>) = this.inBounds(forMap)

fun Int.validDown(forMap: Matrix2d<Int>) = this.inBounds(forMap)

fun Int.canGo(
    forMap: Matrix2d<Int>,
    current: Int,
): Boolean {
    val targetHeight = forMap[this.toPair(forMap).first,this.toPair(forMap).second]
    val currentHeight = forMap[current.toPair(forMap).first,current.toPair(forMap).second]
    return targetHeight <= currentHeight + 1
}

// Graph creation functions
fun addEdge(
    from: Int,
    to: Int,
    weight: Int,
    forGraph: DWGraph,
) {
    forGraph.addEdge(from, to, weight.toDouble())
}

fun addEdges(
    fromMap: Matrix2d<Int>,
    forGraph: DWGraph,
) {
    for (i in 0 until fromMap.nodes()) {
        val left = i - 1
        val right = i + 1
        val up = i - fromMap.cols
        val down = i + fromMap.cols

        if (left.validLeft(fromMap) && left.canGo(fromMap, i)) addEdge(i, left, 1, forGraph)
        if (right.validRight(fromMap) && right.canGo(fromMap, i)) addEdge(i, right, 1, forGraph)
        if (up.validUp(fromMap) && up.canGo(fromMap, i)) addEdge(i, up, 1, forGraph)
        if (down.validDown(fromMap) && down.canGo(fromMap, i)) addEdge(i, down, 1, forGraph)
    }
}

fun main() {
    val test = false
    val file = readFileAs<String>(if (test) "sample" else "aoc2022/day12")

    // Create the map
    var start: Pair<Int, Int> = Pair(0, 0)
    var target: Pair<Int, Int> = Pair(0, 0)
    val starts: MutableList<Pair<Int, Int>> = mutableListOf()
    val map = Matrix2d<Int>(file[0].length, file.size)
    file.forEachIndexed { y, line ->
        line.forEachIndexed { x, tile ->
            map[x,y] =
                when (tile) {
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
