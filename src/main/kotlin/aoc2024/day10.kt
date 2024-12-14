package aoc2024

import org.jgrapht.Graph
import tools.*
import org.jgrapht.alg.shortestpath.AllDirectedPaths
import org.jgrapht.alg.shortestpath.DijkstraShortestPath
import org.jgrapht.graph.DefaultDirectedGraph
import org.jgrapht.graph.DefaultEdge


private const val test = true

fun Tile<Int>.canGo(
    target: Tile<Int>
): Boolean {
    val targetHeight = map[target.cords].v
    val currentHeight = map[cords].v
    return targetHeight == currentHeight + 1
}

fun main() {
    val file = readFileAs<String>(if (test) "sample" else "aoc2024/day10").map { it.replace(".", "8") }.toMutableList()
    val map = file.toTileMatrix<Int>()

    // Find the starts and the peaks
    val starts: MutableList<Tile<Int>> = mutableListOf()
    val peaks: MutableList<Tile<Int>> = mutableListOf()

    map.forEach { tile ->
        when (tile.v) {
            0 -> starts.add(tile)
            9 -> peaks.add(tile)
        }
    }

    // Create the graph
    val graph: Graph<Int, DefaultEdge> = DefaultDirectedGraph(DefaultEdge::class.java)
    map.forEach { tile ->
        graph.addVertex(tile.id)
    }
    map.forEach { tile ->
        map.tryGet(tile.cords.left())?.let { if (tile.isLeftValid() && tile.canGo(it)) graph.addEdge(tile.id, it.id) }
        map.tryGet(tile.cords.right())?.let { if (tile.isRightValid() && tile.canGo(it)) graph.addEdge(tile.id, it.id) }
        map.tryGet(tile.cords.up())?.let { if (tile.isUpValid() && tile.canGo(it)) graph.addEdge(tile.id, it.id) }
        map.tryGet(tile.cords.down())?.let { if (tile.isDownValid() && tile.canGo(it)) graph.addEdge(tile.id, it.id) }
    }

    val dijkstraAlg = DijkstraShortestPath(graph)
    val sol1 = starts.sumOf { start ->
        peaks.count { target ->
            dijkstraAlg.getPath(start.id, target.id) != null
        }.also { println("$start: $it") }
    }
    println("Part 1: $sol1")

    val allDirectionsAlg = AllDirectedPaths(graph)
    val sol2 = starts.sumOf { start ->
        peaks.sumOf { target ->
            allDirectionsAlg.getAllPaths(start.id, target.id, true, null).count()
        }.also { println("$start: $it") }
    }
    println("Part 2: $sol2")
}
