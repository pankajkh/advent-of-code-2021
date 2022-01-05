fun main() {
    val memoization = mutableMapOf<Pair<Long, Long>, Long>()
    fun getLanternfishCount(timer: Long, days: Long): Long {
        if (timer >= days) return 0
        return 1 +
                memoization.getOrPut(Pair(6, days-timer-1)){ getLanternfishCount(6, days-timer-1)} +
                memoization.getOrPut(Pair(8, days-timer-1)){ getLanternfishCount(8, days-timer-1) }
    }

    fun part1(input: List<String>): Long {
        val days = 80L
        return input[0].split(',').map { it.trim().toLong() }.fold(0) {acc, timer -> acc + 1 + memoization.getOrPut(Pair(timer, days)) {getLanternfishCount(timer, days)}}
    }

    fun part2(input: List<String>): Long {
        val days = 256L
        return input[0].split(',').map { it.trim().toLong() }.fold(0) {acc, timer -> acc + 1 + memoization.getOrPut(Pair(timer, days)) {getLanternfishCount(timer, days)}}
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part2(testInput).also { println(it) } == 26984457539L)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}