import java.util.*

fun main() {
    fun getSyntaxErrorScore(line: List<Char>, score: Map<Char, Int>): Int {
        if (line.isEmpty()) return 0
        val stack = Stack<Char>()
        var i = 0
        while (i < line.size) {
            when (line[i]) {
                ')' -> if (stack.isEmpty() || stack.pop() != '(') return score[')']!!
                ']' -> if (stack.isEmpty() || stack.pop() != '[') return score[']']!!
                '}' -> if (stack.isEmpty() || stack.pop() != '{') return score['}']!!
                '>' -> if (stack.isEmpty() || stack.pop() != '<') return score['>']!!
                else -> {
                    stack.push(line[i])
                }
            }
            ++i
        }
        return 0
    }

    fun part1(input: List<String>): Int {
        val score = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
        return input.map { it.toList() }.map {getSyntaxErrorScore(it, score) }.sum()
    }

    fun getIncompleteScore(line: List<Char>, score: Map<Char, Long>): Long {
        if (line.isEmpty()) return 0
        val stack = Stack<Char>()
        var i = 0
        while (i < line.size) {
            when (line[i]) {
                ')' -> if (stack.isEmpty() || stack.pop() != '(') return 0
                ']' -> if (stack.isEmpty() || stack.pop() != '[') return 0
                '}' -> if (stack.isEmpty() || stack.pop() != '{') return 0
                '>' -> if (stack.isEmpty() || stack.pop() != '<') return 0
                else -> {
                    stack.push(line[i])
                }
            }
            ++i
        }
        var incompleteScore : Long = 0
        while (stack.isNotEmpty()) {
            incompleteScore *= 5
            incompleteScore += when(stack.pop()) {
                '(' -> score[')']!!
                '[' -> score[']']!!
                '{' -> score['}']!!
                '<' -> score['>']!!
                else -> 0
            }
        }
        return incompleteScore
    }

    fun part2(input: List<String>): Long {
        val score = mapOf<Char, Long>(')' to 1, ']' to 2, '}' to 3, '>' to 4)
        return input.map { it.toList() }.map {getIncompleteScore(it, score)}.filter {it != 0L}.sorted().also { println(it) }.let { it[it.size/2] }
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput).also { println(it) } == 26397)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}