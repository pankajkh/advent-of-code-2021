fun main() {

    fun getOverlapPoints(lines: List<List<List<Int>>>): Int {
        val pointCount = mutableMapOf<Pair<Int, Int>, Int>()
        var point: Pair<Int, Int>
        for (line in lines) {
            var startX = line[0][0]
            val endX = line[1][0]
            var startY = line[0][1]
            val endY = line[1][1]

            point = Pair(startX, startY)
            pointCount[point] = pointCount.getOrDefault(point, 0).plus(1)

            while (startX != endX || startY != endY) {
                startX = if (startX == endX) startX else startX + (endX - startX) / Math.abs(endX - startX)
                startY = if (startY == endY) startY else startY + (endY - startY) / Math.abs(endY - startY)
                point = Pair(startX, startY)
                pointCount[point] = pointCount.getOrDefault(point, 0).plus(1)
            }
        }

        return pointCount.filter { it.value > 1 }.count()
    }

    fun part1(input: List<String>): Int {
        val lines = input.map { line -> line.split("->").map { point -> point.split(",").map { it.trim().toInt() }}}
            .filter {endPoints -> endPoints[0][0] == endPoints[1][0] || endPoints[0][1] == endPoints[1][1]}

        return getOverlapPoints(lines)
    }

    fun part2(input: List<String>): Int {
        val lines = input.map { line -> line.split("->").map { point -> point.split(",").map { it.trim().toInt() }}}

        return getOverlapPoints(lines)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part2(testInput).also { println(it) } == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
