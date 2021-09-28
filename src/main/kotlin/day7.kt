fun main() {

    val data = readFileAsStrings("src/day7")
    val bags = mutableMapOf<String,Bag>()

    fun registerBag(bagName:String,parent:Bag? = null)
    {
        if(!bags.containsKey(bagName))
            bags[bagName] = Bag(bagName)

        if(parent != null)
            bags[bagName]?.parents?.add(parent)
    }

    data.forEach { line ->
        val parts = line.split(" bags contain ")

        val bagThatContainsName = parts[0].split(" ")[0]+parts[0].split(" ")[1]
        registerBag(bagThatContainsName)

        parts[1].split(", ").forEach{
            if(it != "no other bags.") {
                val containingBagInfo = it.split(" ")
                val bagName = containingBagInfo[1] + containingBagInfo[2]
                registerBag(bagName, bags[bagThatContainsName])

                bags[bagThatContainsName]?.children?.set(bags[bagName]!!, containingBagInfo[0].toInt())
            }
        }
    }

    val goldBag = bags["shinygold"]
    println("Part1: "+goldBag?.countParents(mutableSetOf())?.size)
    println("Part2: "+goldBag?.countChildrenBags())
}


class Bag(
    var name : String,
    var parents : MutableSet<Bag> = mutableSetOf(),
    var children : MutableMap<Bag,Int> = mutableMapOf(),
)
{
    fun countParents(listOfUniqueParents: MutableSet<Bag>): MutableSet<Bag> {
        parents.forEach {
            listOfUniqueParents.add(it)
            it.countParents(listOfUniqueParents)
        }
        return listOfUniqueParents
    }

    fun countChildrenBags():Int
    {
        var bagsCount = 0
        children.forEach{(child, count) ->
            bagsCount += count + (count * child.countChildrenBags())
        }
        return bagsCount
    }
}
