import com.sun.tools.javac.tree.TreeMaker
import java.time.Instant
import java.util.*

fun main() {
    fun winds(input: List<String>): List<Int> {
        val drawNumbers = input[0].split(',').map { it.toInt() }
        val boards = mutableListOf<Array<Array<Int>>>()
        val valueToBoardsCellMap = mutableMapOf<Int, List<Pair<Int, Cell>>>()
        var boardRowCount = 0
        for (i in (1 until input.size)) {
            if (input[i].isNotBlank()) {
                if (boardRowCount.rem(5).compareTo(0) == 0) {
                    boards.add(Array(5) { Array(5) { 0 } })
                }
                input[i].split(' ').filter(String::isNotBlank).map(String::toInt).forEachIndexed { index, key ->
                    boards[boardRowCount / 5][boardRowCount % 5][index] = key
                    val updatedValue = valueToBoardsCellMap.getOrDefault(key, listOf()).plus(Pair(boardRowCount / 5, Cell(boardRowCount % 5, index)))
                    valueToBoardsCellMap[key] = updatedValue
                }
                ++boardRowCount
            }
        }

        val boardRows = Array(boards.size) { Array(5) { 0 } }
        val boardColumns = Array(boards.size) { Array(5) { 0 } }
        val wins = mutableMapOf<Int, Pair<Int, Int>>()
        for (i in drawNumbers) {
            if (valueToBoardsCellMap.contains(i)) {
                for (boardCell in valueToBoardsCellMap[i]!!) {
                    if (wins.contains(boardCell.first)) {
                        continue
                    }
                    boards[boardCell.first][boardCell.second.row][boardCell.second.column] *= -1
                    ++boardRows[boardCell.first][boardCell.second.row]
                    ++boardColumns[boardCell.first][boardCell.second.column]
                    if (boardRows[boardCell.first][boardCell.second.row] == 5 || boardColumns[boardCell.first][boardCell.second.column] == 5) {
                        wins[boardCell.first] = Pair(Instant.now().nano, i * boards[boardCell.first].fold(0) { acc, row -> acc + row.filter { value -> value > 0 }.sum() })
                    }
                }
            }
        }
        return wins.values.sortedBy { it.first }.map { it.second }
    }

    fun part1(input: List<String>): Int {
        return winds(input).first()
    }

    fun part2(input: List<String>): Int {
        return winds(input).last()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
          check(part1(testInput).also { println(it) } == 4512)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

data class Cell(val row: Int, val column: Int)