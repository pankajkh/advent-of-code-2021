fun main() {
    val dataSegmentDisplay = mapOf<String, Int>("abcefg" to 0, "cf" to 1, "acdeg" to 2, "acdfg" to 3, "bcdf" to 4, "abdfg" to 5, "abdefg" to 6, "acf" to 7, "abcdefg" to 8, "abcdfg" to 9)
    val segmentMap = mutableMapOf<String, String>()
    val m = mapOf<Char, Int>(
        'a' to 8,
        'b' to 6,
        'c' to 8,
        'd' to 7,
        'e' to 4,
        'f' to 9,
        'g' to 7
    )

    fun getStringToDigitMap(pattern: List<String>): Map<String, String> {
        val e = ('a'..'g').first {c -> pattern.count { it.contains(c) } == 4 }

        val lengthToPatternMap = pattern.groupBy { it.length }
        val stringToDigitMap = mutableMapOf<String, String>()
        lengthToPatternMap[6]!!.filterNot { str -> lengthToPatternMap[4]!![0].all { c -> str.contains(c) } }
            .first { str -> lengthToPatternMap[3]!![0].all { c -> str.contains(c) } }.let { stringToDigitMap[it] = "0" }
        lengthToPatternMap[2]!![0].let { stringToDigitMap[it] = "1" }
        lengthToPatternMap[5]!!.filterNot { str -> lengthToPatternMap[2]!![0].all { c -> str.contains(c) } }
            .first { str -> str.contains(e) }.let { stringToDigitMap[it] = "2" }
        lengthToPatternMap[5]!!.first {str -> lengthToPatternMap[2]!![0].all {c -> str.contains(c) } }.let { stringToDigitMap[it] = "3" }
        lengthToPatternMap[4]!![0].let { stringToDigitMap[it] = "4" }
        lengthToPatternMap[5]!!.filterNot { str -> lengthToPatternMap[2]!![0].all { c -> str.contains(c) } }
            .filterNot { str -> str.contains(e) }.first().let { stringToDigitMap[it] = "5" }
        lengthToPatternMap[6]!!.filterNot { str -> lengthToPatternMap[4]!![0].all { c -> str.contains(c) } }
            .filterNot { str -> lengthToPatternMap[3]!![0].all { c -> str.contains(c) } }.first().let { stringToDigitMap[it] = "6" }
        lengthToPatternMap[3]!![0].let { stringToDigitMap[it] = "7" }
        lengthToPatternMap[7]!![0].let { stringToDigitMap[it] = "8" }
        lengthToPatternMap[6]!!.first { str -> lengthToPatternMap[4]!![0].all { c -> str.contains(c) } }.let { stringToDigitMap[it] = "9" }

        return stringToDigitMap
    }

    fun getEasyDigitsCount(split: Pair<List<String>, List<String>>): Int {
        val patterns = split.first
        val stringToDigitMap = getStringToDigitMap(patterns)

        val outputValue = split.second.map { stringToDigitMap[it] }.joinToString(separator = "")
        return outputValue.toInt()
    }

    fun part1(input: List<String>): Int {
        return input.map { entry ->
            entry.split("|")
        }.sumOf { splits ->
            splits[1].split(" ").map { it.trim() }.count { it.length in listOf(2, 3, 4, 7) }
        }
    }

    fun part2(input: List<String>): Int {
        return input.map { entry ->
            entry.split("|")
        }.map { splits ->
            Pair(splits[0].split(" ").filterNot(String::isBlank).map { it.trim().toList().sorted().joinToString(separator = "") },
                splits[1].split(" ").filterNot(String::isBlank).map { it.trim().toList().sorted().joinToString(separator = "") })
        }.sumOf(::getEasyDigitsCount)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part2(testInput).also { println(it) } == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}