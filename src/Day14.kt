import javax.naming.OperationNotSupportedException

fun main() {
    /*
    10 = 7 - 1 - 3 = 3
    7 = 7 - 0 = 6

    */
    lateinit var polymer : String
    lateinit var rules : Map<String, Char>
    val memoization : MutableMap<Pair<String, Int>, Pair<Pair<Char, Char>, Map<Char, Long>>> = mutableMapOf()

    fun merge(
        left: Pair<Pair<Char, Char>, Map<Char, Long>>, right: Pair<Pair<Char, Char>, Map<Char, Long>>): Pair<Pair<Char, Char>, Map<Char, Long>> {
        val result = mutableMapOf<Char, Long>()
        left.second.forEach { (t, u) ->  result.merge(t, u, Long::plus)}
        right.second.forEach { (t, u) ->  result.merge(t, u, Long::plus)}
        result.merge(left.first.second, 1, Long::minus)

        return Pair(
            Pair(left.first.first, right.first.second), result.toMap()
        )
    }

    fun rStep(localPolymer: String, steps: Int) : Pair<Pair<Char, Char>, Map<Char, Long>> {
        if (localPolymer.length != 2) throw OperationNotSupportedException()
        if (!rules.containsKey(localPolymer) || steps == 0) {
            return Pair(Pair(localPolymer.first(), localPolymer.last()), localPolymer.groupBy { it }.mapValues { it.value.count().toLong() })
        }

        if (memoization.containsKey(Pair(localPolymer, steps))) return memoization.get(Pair(localPolymer, steps))!!

        val left = rStep(localPolymer.first().toString() + rules[localPolymer]!!, steps - 1)
        val right = rStep(rules[localPolymer]!! + localPolymer.last().toString(), steps - 1)

        memoization[Pair(localPolymer, steps)] = merge(left, right)

        return memoization.get(Pair(localPolymer, steps))!!
    }

    fun part1(): Long {
        memoization.clear()
        val t =  polymer.windowed(2, 1)
            .map { rStep(it, 10) }
            .reduce {acc, c -> merge(acc, c)}

        return t
            .second
            .toList()
            .fold(Pair(Long.MIN_VALUE, Long.MAX_VALUE)) {acc, p ->
                Pair(
                    (if (acc.first < p.second) p.second else acc.first),
                    (if (acc.second > p.second) p.second else acc.second))
            }
            .let { it.first - it.second}
    }

    fun part2(): Long {
        memoization.clear()
        val t =  polymer.windowed(2, 1)
            .map { rStep(it, 40) }
            .reduce {acc, c -> merge(acc, c)}

        return t
            .second
            .toList()
            .fold(Pair(Long.MIN_VALUE, Long.MAX_VALUE)) {acc, p ->
                Pair(
                    (if (acc.first < p.second) p.second else acc.first),
                    (if (acc.second > p.second) p.second else acc.second))
            }
            .let { it.first - it.second}
    }

    val testInput = readInput("Day14_test")
    polymer = testInput.first()
    rules = testInput.filter { it.contains("->") }.map { it.split("->") }.map { it[0].trim() to it[1].trim().toCharArray().first() }.toMap()
    check(part1().also { println(it) } == 1588L)

    val input = readInput("Day14")
    polymer = input.first()
    rules = input.filter { it.contains("->") }.map { it.split("->") }.map { it[0].trim() to it[1].trim().toCharArray().first() }.toMap()
    part1().also { println(it) }
    part2().also { println(it) }
}