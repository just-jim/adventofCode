package aoc2021

import array2dOfInt
import readFileAsStrings

fun main() {
    val test = false
    val file = readFileAsStrings(if(test) "jim" else "aoc2021/day13")

    val cords = mutableListOf<Pair<Int,Int>>()
    val folds = mutableListOf<Pair<Char,Int>>()

    file.forEach{
        if(it.contains(',')){
            cords.add(Pair(it.split(',')[0].toInt(),it.split(',')[1].toInt()))
        } else if(it.contains("fold along")){
            val foldInstr = it.split(' ')[2].split('=')
            folds.add(Pair(foldInstr[0][0],foldInstr[1].toInt()))
        }
    }

    val lx = cords.maxOf { it.first }
    val ly = cords.maxOf { it.second }

    val initMap = array2dOfInt(ly+1,lx+1)

    cords.forEach {
        initMap[it.second][it.first] = 1
    }

    fun printMap(map: Array<IntArray>){
        for (y in map.indices){
            for (x in map[0].indices){
                if(map[y][x] == 0)
                    print('.')
                else
                    print('@')
                print(' ')
            }
            println()
        }
        println()
    }

    fun fold(axes:Char, index:Int, map:Array<IntArray>): Array<IntArray>
    {
        lateinit var newMap:Array<IntArray>
        if(axes == 'y'){
            newMap = array2dOfInt(index,map[0].size)
            for (y in 0 until index)
                for (x in map[0].indices)
                    newMap[y][x] = map[y][x]
            for (y in index+1 until map.size)
                for(x in map[0].indices)
                    newMap[2*index-y][x] = map[2*index-y][x] + map[y][x]
        }
        else if(axes == 'x'){
            newMap = array2dOfInt(map.size,index)
            for (y in map.indices)
                for (x in 0 until index)
                    newMap[y][x] = map[y][x]
            for (y in map.indices)
                for (x in index+1 until map[0].size)
                    newMap[y][2*index-x] = map[y][2*index-x] + map[y][x]
        }
        return newMap
    }

    val fold1 = fold(folds[0].first,folds[0].second,initMap)

    val sol1 = fold1.sumOf { it.filter { it>0 }.count() }
    println("Part 1: $sol1")

    var map = initMap
    folds.forEach {
        map = fold(it.first,it.second,map)
    }

    println("Part 2:")
    printMap(map)
}