fun main() {

    val data = readFileAsLinesUsingUseLines("src/jim")
    val bags = mutableMapOf<String,Bag>()
    data.forEach { line ->
        val parts = line.split("bags contains")
        val bag = decodeBag(parts[0])

        bags.putIfAbsent(bag.name,bag)
        parts[0].split(",").forEach{

        }

    }
}

fun decodeBag(string: String): Bag
{
    val parts = string.split(" ")
    return Bag(parts[0]+parts[1])
}

class Bag(
    var name : String,
    var parents : MutableSet<Bag> = mutableSetOf()
)
{

}