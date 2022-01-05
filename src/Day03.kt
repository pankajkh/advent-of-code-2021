fun main() {
    fun part1(input: List<String>): Int {
        val zeroCountAtEachPosition = MutableList(input[0].length) {i -> 0}
        val oneCountAtEachPosition = MutableList(input[0].length) {i -> 0}
        for (line in input) {
            for (i in 0 until line.length) {
                when (line[line.length-1-i]) {
                    '0' -> {
                        zeroCountAtEachPosition[i] += 1
                    }
                    '1' -> {
                        oneCountAtEachPosition[i] += 1
                    }
                }
            }
        }

        var gammaRate = 0
        var epsilonRate = 0
        var decimalValue = 1
        for (i in 0 until input[0].length) {
            if (zeroCountAtEachPosition[i] <= oneCountAtEachPosition[i]) {
                gammaRate += 1*decimalValue
                epsilonRate += 0*decimalValue
            } else {
                gammaRate += 0*decimalValue
                epsilonRate += 1*decimalValue
            }
            decimalValue *= 2;
        }

        return gammaRate*epsilonRate
    }

    fun commonBit(input: List<String>, index: Int): Char {
        val zeros = input.map { it[index] }.count { it == '0' }
        val ones = input.map { it[index] }.count { it == '1' }
        if (zeros > ones) return '0'
        return '1'
    }

    fun part2(input: List<String>): Int {
        var mostCommonBitAtI = '1'
        var gammaRateList = input.toList()
        for (i in 0 until input[0].length) {
            mostCommonBitAtI = commonBit(gammaRateList, i);
            gammaRateList = gammaRateList.filter { it[i] == mostCommonBitAtI }
            if (gammaRateList.size == 1) break
        }
        var gammaRate = 0
        var decimalValue = 1
        for (c in gammaRateList[0].reversed()) {
            if (c == '1') gammaRate += decimalValue
            decimalValue *= 2
        }

        var leastCommonBitAtI = '0'
        var epsilonRateList = input.toList()
        for (i in 0 until input[0].length) {
            leastCommonBitAtI = if (commonBit(epsilonRateList, i) == '1') '0' else '1'
            epsilonRateList = epsilonRateList.filter { it[i] == leastCommonBitAtI }
            if (epsilonRateList.size == 1) break
        }
        var epsilonRate = 0
        decimalValue = 1
        for (c in epsilonRateList[0].reversed()) {
            if (c == '1') epsilonRate += decimalValue
            decimalValue *=2
        }
        return gammaRate*epsilonRate
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
