import kotlin.math.abs

fun main() {

    fun part1(input: List<String>): Int {
        fun cost(chosenPosition: Int, position: Int) = abs(chosenPosition - position)

        val inputValueList = input[0].split(',').map { it.toInt() }
        val limit = inputValueList.fold(Pair(Int.MAX_VALUE, Int.MIN_VALUE)) { acc, value -> Pair(minOf(acc.first, value), maxOf(acc.second, value)) }

        val chosenPair = (limit.first..limit.second).map { chosenPosition -> Pair(chosenPosition, inputValueList.sumOf { position -> cost(chosenPosition, position) }) }
            .minByOrNull { it.second }

        return chosenPair!!.second
    }


    fun part2(input: List<String>): Int {
        fun cost(chosenPosition: Int, position: Int) = abs(chosenPosition - position)*(abs(chosenPosition - position) + 1)/2

        val inputValueList = input[0].split(',').map { it.toInt() }
        val limit = inputValueList.fold(Pair(Int.MAX_VALUE, Int.MIN_VALUE)) { acc, value -> Pair(minOf(acc.first, value), maxOf(acc.second, value)) }

        val chosenPair = (limit.first..limit.second).map { chosenPosition -> Pair(chosenPosition, inputValueList.sumOf { position -> cost(chosenPosition, position) }) }
            .minByOrNull { it.second }

        return chosenPair!!.second
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}