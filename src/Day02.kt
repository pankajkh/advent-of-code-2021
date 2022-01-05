fun main() {
    fun part1(input: List<String>): Int {
        var position = 0
        var depth = 0

        for (command in input) {
            val commandSplit = command.split(" ")
            when (commandSplit[0]) {
                "forward"-> {
                    position += commandSplit[1].toInt()
                }
                "down"-> {
                    depth += commandSplit[1].toInt()
                }
                "up"-> {
                    depth -= commandSplit[1].toInt()
                }
            }
        }
        return position*depth;
    }

    fun part2(input: List<String>): Int {
        var position = 0
        var depth = 0
        var aim = 0

        for (command in input) {
            val commandSplit = command.split(" ")
            when (commandSplit[0]) {
                "forward" -> {
                    position += commandSplit[1].toInt()
                    depth += commandSplit[1].toInt()*aim;
                }
                "down" -> {
                    aim += commandSplit[1].toInt()
                }
                "up"-> {
                    aim -= commandSplit[1].toInt()
                }
            }
        }
        return position*depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
