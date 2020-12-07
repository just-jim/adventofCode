fun main() {

    // Part 1 + 2
    val expenses = readFileAsLinesUsingUseLines("src/expenses")
    expenses.forEach { a ->
        expenses.forEach { b ->
            expenses.forEach { c ->
                if (a.toInt() + b.toInt() + c.toInt() == 2020) {
                    println("Day 1 Part 2:" + (a.toInt() * b.toInt() * c.toInt()))
                }
            }
        }
    }
}