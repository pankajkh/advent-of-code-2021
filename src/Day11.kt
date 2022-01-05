import java.lang.Math.abs
import java.util.*

fun main() {
    var met: MutableList<MutableList<Int>> = mutableListOf()

    fun flash(flashPoints: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
        val newFlashPoints = mutableListOf<Pair<Int,Int>>()
        flashPoints
            .forEach { point ->
                listOf(-1, 0, 1)
                    .forEach { r ->
                        listOf(-1, 0, 1)
                            .forEach { c ->
                                if (met.getOrNull(point.first+r)?.getOrNull(point.second+c) != null) {
                                    ++met[point.first + r][point.second + c]
                                    if (met[point.first + r][point.second + c] == 10) newFlashPoints.add(Pair(point.first + r, point.second + c))
                                }
                            }
                    }
            }
        return newFlashPoints
    }

    fun step(): Int {
        val flashPoints = mutableListOf<Pair<Int, Int>>()
        (0 until met.size).forEach { i ->
            (0 until met[0].size).forEach { j ->
                ++met[i][j]
                if (met[i][j] == 10) flashPoints.add(Pair(i, j))
            }
        }

        var tempFlashPoints = flashPoints.toList()
        while (tempFlashPoints.isNotEmpty()) {
            tempFlashPoints = flash(tempFlashPoints)
            flashPoints.addAll(tempFlashPoints)
        }
        flashPoints
            .forEach { point ->
                met[point.first][point.second] = 0
            }
        return flashPoints.size
    }

    fun part1(input: List<String>): Int {
        met = input.map { row -> row.toList().map {cell -> cell.toString().toInt() }.toMutableList() }.toMutableList()
        var totalFlashes = 0
        (1..100).forEach {
            totalFlashes += step()
        }
        return totalFlashes
    }

    fun part2(input: List<String>): Int {
        met = input.map { row -> row.toList().map {cell -> cell.toString().toInt() }.toMutableList() }.toMutableList()
        var k=0
        while (step() != met.size*met[0].size) {
            ++k
        }
        return k+1
    }

    val testInput = readInput("Day11_test")
    check(part2(testInput).also { println(it) } == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}