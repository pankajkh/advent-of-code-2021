import java.util.*
import kotlin.math.abs
import kotlin.math.min

fun main() {
    val amphipodsCost = mapOf('A' to 1, 'B' to 10, 'C' to 100, 'D' to 1000)
    val amphipodsRoom = mapOf('A' to 3, 'B' to 5, 'C' to 7, 'D' to 9)
    val hallwayPositions = listOf(Pair(1,1), Pair(1,2), Pair(1,4), Pair(1,6), Pair(1,8), Pair(1,10), Pair(1,11))
    val amphipodPositions =  mutableListOf<Pair<Int, Int>>()
    val memoization = mutableMapOf<String, Long>()

    fun getStepsNeededToMove(mat: List<List<Char>>, source: Pair<Int, Int>, destination: Pair<Int, Int>): Long {
        val amphiPod = mat[source.first][source.second]
        var stepsNeededToMove = 0L

        if (source.first != 1) {
            for (i in source.first-1 downTo 1) {
                if (mat[i][source.second] != '.') return -1 else ++stepsNeededToMove
            }
        }

        val step = if (destination.second > source.second) 1 else -1
        (1..abs(destination.second-source.second)).forEach {
            if (mat[1][source.second+step*it] != '.') return -1 else ++stepsNeededToMove
        }

        if (destination.first != 1) {
            for (i in 2..destination.first) {
                if (mat[i][destination.second] != '.') return -1 else ++stepsNeededToMove
            }
        }
        return stepsNeededToMove
    }

    fun getArrangementMinimumCost(mat: MutableList<MutableList<Char>>): Long {
        var minimumCost = Long.MAX_VALUE
        var stepsNeededToMove: Long

        if (memoization.contains(mat.joinToString(separator = ""))) {
            return memoization[mat.joinToString(separator = "")]!!
        }

        if ((2 until mat.size-1)
                .all {
                    mat[it][amphipodsRoom['A']!!] == 'A' && mat[it][amphipodsRoom['B']!!] == 'B' && mat[it][amphipodsRoom['C']!!] == 'C' && mat[it][amphipodsRoom['D']!!] == 'D'
                }) {
            return 0L
        }

        hallwayPositions.plus(amphipodPositions).filter { mat[it.first][it.second] in amphipodsRoom.keys }
            .filterNot { it.second == amphipodsRoom[mat[it.first][it.second]]!! && (it.first + 1 until mat.size-1).all { i -> mat[i][it.second] == mat[it.first][it.second] } }
            .forEach { source ->
                val amphipod = mat[source.first][source.second]
                val cost = amphipodsCost[amphipod]!!

                hallwayPositions.plus(amphipodPositions).minus(source)
                    .filter {mat[it.first][it.second] == '.'}
                    .filterNot {source.first == 1 && it.second != amphipodsRoom[amphipod]}
                    .filterNot {source.first in (2 until mat.size-1) && source.second == it.second}
                    .filterNot {source.first in (2 until mat.size-1) && it.first != 1 && it.second != amphipodsRoom[mat[source.first][source.second]]!!}
                    .filterNot {it.second == amphipodsRoom[mat[source.first][source.second]]!!
                            && (it.first + 1 until mat.size-1).any { i -> mat[i][it.second] != mat[source.first][source.second] }}
                    .forEach { destination ->
                        stepsNeededToMove = getStepsNeededToMove(mat, source, destination)
                        if (stepsNeededToMove != -1L) {
                            mat[destination.first][destination.second] = mat[source.first][source.second]
                            mat[source.first][source.second] = '.'
                            if (minimumCost > cost*stepsNeededToMove) {
                                val temp = getArrangementMinimumCost(mat)
                                if (temp != Long.MAX_VALUE) {
                                    minimumCost = minOf(minimumCost, cost * stepsNeededToMove + temp)
                                    //println("move $amphipod from $source to $destination")
                                }
                            }
                            mat[source.first][source.second] = mat[destination.first][destination.second]
                            mat[destination.first][destination.second] = '.'
                            memoization[mat.joinToString(separator = "")] = minimumCost
                        }
                    }
            }

        return minimumCost
    }

    fun part1(input: List<String>): Long {
        val inputList = input.toMutableList()
        amphipodPositions.clear()
        (2 until input.size-1).forEach { amphipodPositions.addAll(listOf(Pair(it, 3), Pair(it, 5), Pair(it, 7), Pair(it, 9))) }
        return getArrangementMinimumCost(inputList.map(String::trim).map { if (it.length == 9) "##$it##" else it }.map { it.toMutableList() }.toMutableList())
    }

    fun part2(input: List<String>): Long {
        val inputList = input.toMutableList()
        inputList.add(3, "#D#C#B#A#")
        inputList.add(4, "#D#B#A#C#")
        memoization.clear()
        amphipodPositions.clear()
        (2 until inputList.size-1).forEach { amphipodPositions.addAll(listOf(Pair(it, 3), Pair(it, 5), Pair(it, 7), Pair(it, 9))) }
        return getArrangementMinimumCost(inputList.map(String::trim).map { if (it.length == 9) "##$it##" else it }.map { it.toMutableList() }.toMutableList())
    }

    val testInput = readInput("Day23_test")
    check(part1(testInput).also { println(it) } == 12521L)

    val input = readInput("Day23")
    println(part1(input))
    println(part2(input))
}