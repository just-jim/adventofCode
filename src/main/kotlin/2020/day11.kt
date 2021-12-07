fun main() {
    data class Chair(
        var status: Char,
        var cords: Pair<Int,Int>,
        var u: Chair? = null,
        var d: Chair? = null,
        var l:Chair? = null,
        var r:Chair? = null,
        var ul:Chair? = null,
        var ur:Chair? = null,
        var dl:Chair? = null,
        var dr:Chair? = null,
        var nextStatus: Char = '-'
    ){
        override fun toString(): String = "$cords : $status"

        fun countNeighbourghs(extended: Boolean) : Int{
            var count = 0
            if(!extended){
                u?.let { if(it.status == '#') count++ }
                ul?.let { if(it.status == '#') count++ }
                ur?.let { if(it.status == '#') count++ }
                l?.let { if(it.status == '#') count++ }
                r?.let { if(it.status == '#') count++ }
                d?.let { if(it.status == '#') count++ }
                dl?.let { if(it.status == '#') count++}
                dr?.let { if(it.status == '#') count++ }
                return count
            } else{
                var tmp = u
                while(tmp != null && tmp.status == '.') tmp = tmp.u
                tmp?.let { if(it.status == '#') count++ }

                tmp = ul
                while(tmp != null && tmp.status == '.') tmp = tmp.ul
                tmp?.let { if(it.status == '#') count++ }

                tmp = ur
                while(tmp != null && tmp.status == '.') tmp = tmp.ur
                tmp?.let { if(it.status == '#') count++ }

                tmp = l
                while(tmp != null && tmp.status == '.') tmp = tmp.l
                tmp?.let { if(it.status == '#') count++ }

                tmp = r
                while(tmp != null && tmp.status == '.') tmp = tmp.r
                tmp?.let { if(it.status == '#') count++ }

                tmp = d
                while(tmp != null && tmp.status == '.') tmp = tmp.d
                tmp?.let { if(it.status == '#') count++ }

                tmp = dl
                while(tmp != null && tmp.status == '.') tmp = tmp.dl
                tmp?.let { if(it.status == '#') count++ }

                tmp = dr
                while(tmp != null && tmp.status == '.') tmp = tmp.dr
                tmp?.let { if(it.status == '#') count++ }

                return count
            }
        }

        fun calculateNextGen(extended: Boolean){
            val neighbours = countNeighbourghs(extended)

            nextStatus = if (status == '#' && neighbours >= (if(extended) 5 else 4)) 'L'
            else if (status == 'L' && neighbours == 0) '#'
            else status
        }

        fun unChanged(): Boolean{
            return nextStatus == status
        }

        fun nextGen(){
            status = nextStatus
        }
    }

    data class Chairs(
        var map: Array<CharArray>,
        var dimensions: Pair<Int,Int>,
        val dic : MutableMap<Pair<Int,Int>, Chair> = mutableMapOf()

    ){
        override fun toString(): String {
            var text = ""
            for(i in 0 until dimensions.first){
                for (i2 in 0 until dimensions.second){
                    text += dic[Pair(i2,i)]?.status.toString()
                }
                text += "\n"
            }
            return text
        }

        fun print(){
            println(this.toString())
        }

        fun populateChairs(mapFile: MutableList<String>){
            mapFile.forEachIndexed { i, line->
                line.forEachIndexed { i2, tile ->
                    map[i2][i] = tile
                    dic[Pair(i2,i)] = Chair(map[i2][i],Pair(i2,i))
                }
            }

            for(i in 0 until dimensions.first){
                for (i2 in 0 until dimensions.second){
                    // up
                    dic[Pair(i2,i)]?.u = dic.get(Pair(i2,i-1))
                    // up left
                    dic[Pair(i2,i)]?.ul = dic.get(Pair(i2-1,i-1))
                    // up right
                    dic[Pair(i2,i)]?.ur = dic.get(Pair(i2+1,i-1))
                    // left
                    dic[Pair(i2,i)]?.l = dic.get(Pair(i2-1,i))
                    // right
                    dic[Pair(i2,i)]?.r = dic.get(Pair(i2+1,i))
                    // down
                    dic[Pair(i2,i)]?.d = dic.get(Pair(i2,i+1))
                    // down left
                    dic[Pair(i2,i)]?.dl = dic.get(Pair(i2-1,i+1))
                    // down right
                    dic[Pair(i2,i)]?.dr = dic.get(Pair(i2+1,i+1))
                }
            }
        }

        fun nextGen(extended: Boolean) : Int {
            var count = 0
            for(i in 0 until dimensions.first) {
                for (i2 in 0 until dimensions.second) {
                    dic[Pair(i2,i)]?.calculateNextGen(extended)
                    dic[Pair(i2,i)]?.unChanged()?.let { if(!it) count++ }
                }
            }
            for(i in 0 until dimensions.first) {
                for (i2 in 0 until dimensions.second) {
                    dic[Pair(i2,i)]?.nextGen()
                }
            }
            return count
        }

        fun occupiedSeats() : Int{
            var count : Int = 0
            for(i in 0 until dimensions.first) {
                for (i2 in 0 until dimensions.second) {
                    if(dic[Pair(i2,i)]?.status == '#')
                        count++
                }
            }
            return count
        }
    }

    // Create the map
    val mapFile = readFileAsStrings("2020/day11")
    val chairs = Chairs(array2dOfChar(mapFile[0].length,mapFile.size),Pair(mapFile.size,mapFile[0].length))
    chairs.populateChairs(mapFile)

    var changes = 1
    var prevChanges = 2
    while ( changes != prevChanges)
    {
        prevChanges = changes
        changes = chairs.nextGen(false)
    }

    println("Part1: ${chairs.occupiedSeats()}")

    val chairs2 = Chairs(array2dOfChar(mapFile[0].length,mapFile.size),Pair(mapFile.size,mapFile[0].length))
    chairs2.populateChairs(mapFile)

    changes = 1
    prevChanges = 2
    while ( changes != prevChanges)
    {
        prevChanges = changes
        changes = chairs2.nextGen(true)
    }
    println("Part2: ${chairs2.occupiedSeats()}")
}