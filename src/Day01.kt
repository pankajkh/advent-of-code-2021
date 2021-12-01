fun main() {
    fun part1(input: List<String>): Int {
        var totalIncreased = 0
        for (i in (1 until input.size)) {
            if (input[i].toInt() > input[i-1].toInt())
                ++totalIncreased
        }
        return totalIncreased
    }

    fun part2(input: List<String>): Int {
        var totalIncreased = 0
        for (i in (3 until input.size)) {
            if (input[i].toInt() > input[i-3].toInt())
                ++totalIncreased
        }
        return totalIncreased
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
