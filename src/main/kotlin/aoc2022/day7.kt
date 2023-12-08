package aoc2022

import readFileAsStrings

enum class Type {
    File,
    Directory,
}

class Dir() {
    lateinit var name: String
    val files: MutableList<Dir> = mutableListOf()
    var size: Int = 0
    var dirSize: Int? = null
    private var parent: Dir? = null
    private val children = mutableMapOf<String, Dir>()

    constructor(name: String, size: Int) : this() {
        this.name = name
        this.size = size
    }

    fun addChild(dir: Dir) {
        children[dir.name] = dir
        dir.addParent(this)
    }

    private fun addParent(dir: Dir) {
        parent = dir
    }

    fun cd(input: String): Dir? {
        return when (input) {
            "/" -> null
            ".." -> parent
            else -> children[input]
        }
    }

    fun calculateTotalSize(): Int {
        if (dirSize != null) return dirSize!!

        dirSize =
            if (children.isNotEmpty()) {
                children.values.sumOf { it.calculateTotalSize() }
            } else {
                size
            }
        return dirSize!!
    }

    private fun type(): Type {
        return when (size) {
            0 -> Type.Directory
            else -> Type.File
        }
    }

    override fun toString(): String {
        return when (type()) {
            Type.File -> "$name (file, size=$size)"
            Type.Directory -> "$name (dir, size=${calculateTotalSize()})"
        }
    }

    private fun depth(): Int {
        var tmp: Dir? = this
        var count = 0
        while (tmp?.parent != null) {
            tmp = tmp.parent
            count++
        }
        return count
    }

    fun plot() {
        if (type() == Type.Directory) {
            print("  ".repeat(depth()) + "- ")
            println(this)
        }
        children.values.forEach { it.plot() }
    }
}

fun main() {
    val test = false
    val file = readFileAsStrings(if (test) "sample" else "aoc2022/day7")

    val root = Dir("/", 0)
    var curDir: Dir = root

    fun command(input: List<String>) {
        if (input.first() == "cd") {
            curDir = curDir.cd(input[1]) ?: root
        }
    }

    val allDirs: MutableList<Dir> = mutableListOf(root)
    file.forEach { line ->
        val parts = line.split(" ")
        when (parts.first()) {
            "$" -> command(parts.subList(1, parts.size))
            "dir" -> {
                val newDir = Dir(parts[1], 0)
                curDir.addChild(newDir)
                allDirs.add(newDir)
            }
            else -> curDir.addChild(Dir(parts[1], parts[0].toInt()))
        }
    }

    var sum = 0
    allDirs.forEach {
        val size = it.calculateTotalSize()
        if (size <= 100000) {
            sum += size
        }
    }

    val sol1 = sum
    println("Part 1: $sol1")

    val totalSpace = 70000000
    val usedSpace = root.calculateTotalSize()
    val unusedSpace = totalSpace - usedSpace
    val updateRequiredSpace = 30000000
    val neededSpace = updateRequiredSpace - unusedSpace

    val sol2 = allDirs.map { it.calculateTotalSize() }.sorted().first { it >= neededSpace }
    println("Part 2: $sol2")
}
