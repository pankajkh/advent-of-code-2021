import javax.naming.OperationNotSupportedException
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sqrt

fun main() {
    /*
n*(n+1)/2 - (n-k-1)*(n-k)/2 = sum of from n-k to n
1/2*((2*n - k)*(1+ k))

  y.first <= (i*(i+1)/2) - (k*(k+1)/2) <= y.second

     */
    fun getMaxStepsThroughX(xlimit: Pair<Int, Int>): Int {
        val limit = if (abs(xlimit.first) > abs(xlimit.second)) abs(xlimit.first) else abs(xlimit.second)

        return (0..sqrt(2*limit.toDouble()).roundToInt()).last { it*(it+1) <= 2*limit }
    }

    fun getMaxStepsThroughY(ylimit: Pair<Int, Int>): Int {
        var (i, k) = listOf(0, 0)
        var ans = 0
        while (i+1 < abs(ylimit.first)) {
            ++i
            val height = i*(i+1)/2
            k = 0
            while ((height - (k*(k+1)/2)) > ylimit.second) {
                ++k
                if (ylimit.first <= (height - (k*(k+1)/2)) && (height - (k*(k+1)/2)) <= ylimit.second) {
                    ans = i
                }
            }
        }
        return ans
    }

    fun part1(input: List<String>): Int {
        val targetArea = input[0]
            .substringAfter("target area: ")
            .split(", ")
            .map { it.split("=") }
            .map { it[0] to it[1].split("..") }
            .associate { it.first to Pair(it.second[0].trim().toInt(), it.second[1].trim().toInt()) }

        val maxSteps = getMaxStepsThroughY(targetArea["y"]!!)
        val maxHeightUpSide = if (targetArea["y"]!!.second > 0) targetArea["y"]!!.second*(targetArea["y"]!!.second+1)/2 else 0
        val maxHeightDownSide = maxSteps*(maxSteps+1)/2

        return maxOf(maxHeightUpSide, maxHeightDownSide)
    }

    fun part2(input: List<String>): Int {
        return 0
    }

//    val testInput = readInput("Day17_test")
//    check(part1(testInput).also { println(it) } == 45)

    val input = readInput("Day17")
    part1(input).also { println(it) }
    part2(input).also { println(it) }
}