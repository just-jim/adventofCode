package aoc2021

import array2dOfInt
import io.uuddlrlrba.ktalgs.graphs.directed.weighted.DWGraph
import io.uuddlrlrba.ktalgs.graphs.directed.weighted.Dijkstra
import readFileAsStrings

// Get Pair matrix coordinates from index
fun Int.toPair(forMap: Array<IntArray>) = Pair(this % forMap.size, this / forMap.size)

// Get amount of nodes from matrix
fun Array<IntArray>.nodes() = this.size * this[0].size

// Check cord validity
fun Int.inBounds(forMap: Array<IntArray>) = this in 0 until forMap.nodes()

fun Int.validLeft(forMap: Array<IntArray>) = this.inBounds(forMap) && this % (forMap.size) != forMap.size - 1

fun Int.validRight(forMap: Array<IntArray>) = this.inBounds(forMap) && this % forMap.size != 0

fun Int.validUp(forMap: Array<IntArray>) = this.inBounds(forMap)

fun Int.validDown(forMap: Array<IntArray>) = this.inBounds(forMap)

// Get weight for a map given teh index of the node
fun Int.weight(fromMap: Array<IntArray>) = fromMap[this.toPair(fromMap).first][this.toPair(fromMap).second]

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
    fromMap: Array<IntArray>,
    forGraph: DWGraph,
) {
    for (i in 0 until fromMap.nodes()) {
        val left = i - 1
        val right = i + 1
        val up = i - fromMap.size
        val down = i + fromMap.size

        if (left.validLeft(fromMap)) addEdge(i, left, left.weight(fromMap), forGraph)
        if (right.validRight(fromMap)) addEdge(i, right, right.weight(fromMap), forGraph)
        if (up.validUp(fromMap)) addEdge(i, up, up.weight(fromMap), forGraph)
        if (down.validDown(fromMap)) addEdge(i, down, down.weight(fromMap), forGraph)
    }
}

// Shift weight (with reset to 1 after 9)
fun Int.shiftN(n: Int): Int {
    var x = this
    for (i in 1..n) {
        x++
        if (x % 10 == 0) x = 1
    }
    return x
}

fun main() {
    val test = false
    val file = readFileAsStrings(if (test) "sample" else "aoc2021/day15")

    // Create the map
    val map = array2dOfInt(file[0].length, file.size)
    file.forEachIndexed { y, line ->
        line.forEachIndexed { x, tile ->
            map[x][y] = tile.toString().toInt()
        }
    }

    // Create the graph
    val graph = DWGraph(map.nodes())
    addEdges(map, graph)

    // Calculate solution
    val sol1 = Dijkstra(graph, 0).distTo(map.nodes() - 1).toInt()
    println("Part 1: $sol1")

    // Create second map
    val map2 = array2dOfInt(file[0].length * 5, file.size * 5)
    for (y2 in 0 until 5) {
        for (x2 in 0 until 5) {
            file.forEachIndexed { y, line ->
                line.forEachIndexed { x, tile ->
                    val risk = tile.toString().toInt()
                    map2[(x2 * file.size) + x][(y2 * file.size) + y] = risk.shiftN(x2 + y2)
                }
            }
        }
    }

    val graph2 = DWGraph(map2.nodes())
    addEdges(map2, graph2)
    val sol2 = Dijkstra(graph2, 0).distTo(map2.nodes() - 1).toInt()
    println("Part 2: $sol2")
}
