fun main() {
    val expenses = readFileAsStrings("day1")
    expenses.forEach { a ->
        expenses.forEach { b ->
            expenses.forEach { c ->
                if (a.toInt() + b.toInt() + c.toInt() == 2020) {
                    println("Part 1+2:" + (a.toInt() * b.toInt() * c.toInt()))
                }
            }
        }
    }
}