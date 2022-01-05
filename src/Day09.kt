fun main() {
    fun getLowPoints(mat: List<List<Int>>): List<Pair<Int, Int>> {
        return mat
            .flatMapIndexed { i, row ->
                row.mapIndexed { j, height ->
                    if (height < minOf(mat[i].getOrNull(j-1) ?: 10, mat[i].getOrNull(j+1) ?: 10, mat.getOrNull(i-1)?.get(j) ?: 10, mat.getOrNull(i+1)?.get(j) ?: 10)) Pair(i, j)
                    else Pair(-1, -1)
                }.filter { it.first != -1 }
            }
    }

    fun part1(input: List<String>): Int {
        val mat: List<List<Int>> = input.map { it.map { c -> c.toString().toInt() } }
        return getLowPoints(mat).map { mat[it.first][it.second] + 1 }.sum()
    }

    fun getSizeOfBasin(lowPoint: Pair<Int, Int>, mat: List<List<Int>>, visited: Array<Array<Int>>): Int {
        if (lowPoint.first < 0 || lowPoint.first >= mat.size || lowPoint.second < 0 || lowPoint.second >= mat[0].size) return 0
        if (visited[lowPoint.first][lowPoint.second] == 1 || mat[lowPoint.first][lowPoint.second] == 9) {
            visited[lowPoint.first][lowPoint.second] = 1
            return 0
        }
        var sizeOfBasin = 1
        visited[lowPoint.first][lowPoint.second] = 1
        sizeOfBasin += getSizeOfBasin(Pair(lowPoint.first-1, lowPoint.second), mat, visited)
        sizeOfBasin += getSizeOfBasin(Pair(lowPoint.first+1, lowPoint.second), mat, visited)
        sizeOfBasin += getSizeOfBasin(Pair(lowPoint.first, lowPoint.second - 1), mat, visited)
        sizeOfBasin += getSizeOfBasin(Pair(lowPoint.first, lowPoint.second + 1), mat, visited)

        return sizeOfBasin
    }

    fun part2(input: List<String>): Int {
        val mat: List<List<Int>> = input.map { it.map { c -> c.toString().toInt() } }
        val visited = Array(mat.size) {Array(mat[0].size) {0} }
        return getLowPoints(mat).map {getSizeOfBasin(it, mat, visited)}.sortedDescending().take(3).reduce {acc, value -> acc*value}
    }

    val testInput = readInput("Day09_test")
    check(part2(testInput).also { println(it) } == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}