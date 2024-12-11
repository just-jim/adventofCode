package aoc2024

import tools.*

private const val test = false

fun main() {
    val file = readFileAs<String>(if (test) "sample" else "aoc2024/day9")

    val disk = file[0].map { it.toString().toInt() }
    val diskMap = mutableListOf<Int?>()
    var index = 0
    var fileIndex = 0
    disk.forEach { size ->
        (0 until size).forEach { _ ->
            if (index % 2 == 0) {
                diskMap.add(fileIndex)
            } else {
                diskMap.add(null)
            }
        }
        if (index % 2 == 0) {
            fileIndex++
        }
        index++
    }

    fun printDisk(theDisk: MutableList<Int?>, replaceNumbers: String?) {
        theDisk.forEach { if (it != null) print(replaceNumbers ?: it) else print(".") }
        println()
    }

    fun formatDisk(theDisk: MutableList<Int?>) {
        theDisk.forEachIndexed { i, fileId ->
            if (fileId == null) {
                if (theDisk.subList(i, theDisk.size).all { it == null }) {
                    println("Done at index $i")
                    return
                }
                val indexOfLastFilePart = theDisk.size - 1 - (theDisk.reversed().indexOfFirst { it != null })
                theDisk[i] = theDisk[indexOfLastFilePart]
                theDisk[indexOfLastFilePart] = null
            }
        }
    }

    val diskClone = diskMap.toMutableList()
    formatDisk(diskClone)

    fun checkSum(theDisk: MutableList<Int?>): Long {
        return theDisk.mapIndexed { i, fileId -> i * (fileId ?: 0) }.sumOf { it.toLong() }
    }

    val sol1 = checkSum(diskClone)
    println("Part 1: $sol1")

    val diskClone2 = diskMap.toMutableList()

    fileIndex--

    (fileIndex downTo 1).forEach { fileId ->
        val fileSize = diskClone2.count { it == fileId }
        val regex = "x{$fileSize}".toRegex()
        val firstOccurrenceOfFileId = diskClone2.indexOf(fileId)

        if (firstOccurrenceOfFileId == -1) {
            println(fileId)
        }

        val diskString = diskClone2.subList(0, firstOccurrenceOfFileId).joinToString("") { it?.let { "o" } ?: "x" }
        regex.find(diskString)?.range?.first?.let { indexOfFirstEmptySlot ->
            diskClone2.forEachIndexed { i, tmpFileId ->
                if (tmpFileId == fileId) {
                    diskClone2[i] = null
                }
            }

            (indexOfFirstEmptySlot until indexOfFirstEmptySlot + fileSize).forEach {
                diskClone2[it] = fileId
            }
        }
    }

    val sol2 = checkSum(diskClone2)
    println("Part 2: $sol2")
}
