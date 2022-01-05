import javax.naming.OperationNotSupportedException

fun main() {
    lateinit var matz: List<List<Int>>
    lateinit var mat: List<List<Int>>
    lateinit var memoization: MutableList<MutableList<Int>>

    val computeLowestRiskPath = fun () {
        (mat.size-1 downTo 0).forEach { r ->
            (mat.size-1 downTo 0).forEach { c ->
                if (memoization.getOrNull(r)?.getOrNull(c+1) != null) memoization[r][c+1] = minOf(memoization[r][c+1], memoization[r][c] + mat[r][c])
                if (memoization.getOrNull(r+1)?.getOrNull(c) != null) memoization[r+1][c] = minOf(memoization[r+1][c], memoization[r][c] + mat[r][c])
                if (memoization.getOrNull(r-1)?.getOrNull(c) != null) memoization[r-1][c] = minOf(memoization[r-1][c], memoization[r][c] + mat[r][c])
                if (memoization.getOrNull(r)?.getOrNull(c-1) != null) memoization[r][c-1] = minOf(memoization[r][c-1], memoization[r][c] + mat[r][c])
            }
        }
    }

    fun part1(): Int {
        mat = matz.map { r -> r.map { c -> c } }
        memoization = mat.map { row -> row.map {Int.MAX_VALUE}.toMutableList() }.toMutableList()
        memoization[mat.size-1][mat.size-1] = 0
        computeLowestRiskPath()
        return memoization[0][0]
    }

    fun part2(): Int {
        mat = (0 until matz.size*5).map { r->
            (0 until matz.size*5).map { c ->
                if (matz[r%matz.size][c%matz.size] + (r/matz.size+c/matz.size) > 9) (matz[r%matz.size][c%matz.size] + (r/matz.size+c/matz.size))%9
                else (matz[r%matz.size][c%matz.size] + (r/matz.size+c/matz.size))
            }
        }

        memoization = mat.map { row -> row.map {Int.MAX_VALUE}.toMutableList() }.toMutableList()
        memoization[mat.size-1][mat.size-1] = 0
        computeLowestRiskPath()
        return memoization[0][0]
    }

    val testInput = readInput("Day15_test")
    matz = testInput.map { row -> row.map { it.digitToInt() } }
    check(part2().also { println(it) } == 315)

    val input = readInput("Day15")
    matz = input.map { row -> row.map { it.digitToInt() } }
    part1().also { println(it) }
    part2().also { println(it) }
}