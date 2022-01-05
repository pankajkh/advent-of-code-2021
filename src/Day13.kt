fun main() {
    /*
    10 = 7 - 1 - 3 = 3
    7 = 7 - 0 = 6

    */
    lateinit var points : List<Pair<Int, Int>>
    lateinit var folds : List<Pair<String, Int>>

    val foldThePoints = fun (localPoints: List<Pair<Int, Int>>, fold: Pair<String, Int>): List<Pair<Int, Int>> {
        return if (fold.first == "y")
            localPoints
                .filter { it.second != fold.second }
                .groupBy { Pair(it.first, if (it.second > fold.second) (fold.second - it.second%fold.second)%fold.second else it.second) }
                .map { it.key }
        else
            localPoints
                .filter { it.first != fold.second }
                .groupBy { Pair(if (it.first > fold.second) (fold.second - it.first%fold.second)%fold.second else it.first, it.second) }
                .map { it.key }
    }

    fun part1(input: List<String>): Int {
        return folds.first().let { foldThePoints(points, it).count() }
    }

    fun part2(input: List<String>): Int {
        val result = folds
            .fold(points) {acc, it ->  foldThePoints(acc, it) }
                val limit = result.fold(Pair(Int.MIN_VALUE, Int.MIN_VALUE)) {acc, it -> Pair(maxOf(acc.first, it.first), maxOf(acc.second, it.second))}
        (0..limit.second+1)
            .map { y ->
                (0..limit.first+1)
                    .fold("") {acc, x->
                        if (result.contains(Pair(x, y))) "$acc#" else "$acc."
                    }
            }.forEach(::println)
        return result.size
    }

    val testInput = readInput("Day13_test")
    points = testInput.filter { it.contains(",") }.map { it.split(",") }.map { Pair(it[0].toInt(), it[1].toInt()) }
    folds = testInput.filter { it.contains("fold") }.map { it.split(" ").last().split("=") }.map { Pair(it[0], it[1].toInt()) }
    check(part1(testInput).also { println(it) } == 17)

    val input = readInput("Day13")
    points = input.filter { it.contains(",") }.map { it.split(",") }.map { Pair(it[0].toInt(), it[1].toInt()) }
    folds = input.filter { it.contains("fold") }.map { it.split(" ").last().split("=") }.map { Pair(it[0], it[1].toInt()) }
    part1(input).also { println(it) }
    part2(input).also { println(it) }
}