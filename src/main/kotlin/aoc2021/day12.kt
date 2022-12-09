package aoc2021

import readFileAsStrings

fun main() {
    val test = false
    val file = readFileAsStrings(if (test) "sample" else "aoc2021/day12")

    class Node() {
        lateinit var name: String
        val nodes: MutableList<Node> = mutableListOf()
        var big: Boolean = false
        var isStart: Boolean = false
        var isEnd: Boolean = false
        var revisit: Boolean = false

        constructor(name: String) : this() {
            this.name = name
            this.big = name[0].isUpperCase()
            this.isStart = name == "start"
            this.isEnd = name == "end"
        }

        fun addNode(node: Node) {
            nodes.add(node)
        }

        override fun toString(): String {
            return name
        }
    }

    val nodes: MutableMap<String, Node> = mutableMapOf()

    file.forEach { line ->
        val nodeNames = line.split('-')
        val node1 = nodes.getOrElse(nodeNames[0]) {
            nodes[nodeNames[0]] = Node(nodeNames[0])
            nodes[nodeNames[0]]!!
        }
        val node2 = nodes.getOrElse(nodeNames[1]) {
            nodes[nodeNames[1]] = Node(nodeNames[1])
            nodes[nodeNames[1]]!!
        }

        node1.addNode(node2)
        node2.addNode(node1)
    }

    fun findExit(node: Node, path: List<Node>): Int {
        if (node.isEnd) {
            // println((path+listOf(node)).joinToString(","))
            return 1
        }

        if (!node.big && !node.revisit && path.contains(node)) {
            return 0
        }

        if (!node.big && node.revisit && path.count { it == node } == 2) {
            return 0
        }

        var paths = 0
        node.nodes.forEach {
            paths += findExit(it, path + listOf(node))
        }

        return paths
    }

    val sol1 = findExit(nodes["start"]!!, listOf())
    println("Part 1: $sol1")

    var sol2 = sol1
    val small = nodes.values.filter { !it.big && !it.isStart && !it.isEnd }
    small.forEach {
        it.revisit = true
        val extraPaths = findExit(nodes["start"]!!, listOf()) - sol1
        sol2 += extraPaths
        it.revisit = false
    }
    println("Part 2: $sol2")
}
