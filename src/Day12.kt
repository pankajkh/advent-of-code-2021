import java.util.*

fun main() {
    val graph = mutableMapOf<String, MutableSet<String>>()
    lateinit var shouldContinue : (String, MutableMap<String, Int>) -> Boolean

    val part1ShouldContinue: (String, MutableMap<String, Int>) -> Boolean = fun (currentNode: String, visitCount: MutableMap<String, Int>): Boolean {
        if (!graph.containsKey(currentNode)
            || (currentNode.all(Char::isLowerCase) && visitCount[currentNode] == 1)) return false

        return true
    }
    val part2ShouldContinue: (String, MutableMap<String, Int>) -> Boolean = fun (currentNode: String, visitCount: MutableMap<String, Int>): Boolean {
        if (!graph.containsKey(currentNode)
            || currentNode == "start" && visitCount[currentNode] == 1
            || (currentNode.all(Char::isLowerCase) && visitCount[currentNode]!! > 0 && !currentNode.equals("start")
                    && visitCount.filter { it.key.all(Char::isLowerCase) }.any { it.value == 2 }))
            return false

        return true
    }

    fun getTotalPathCount(currentNode: String, visitCount: MutableMap<String, Int>): Int {
        if (!shouldContinue(currentNode, visitCount)) return 0
        if (currentNode.equals("end")) return 1

        visitCount[currentNode] = visitCount[currentNode]!!.plus(1)
        val neighbours = graph[currentNode] ?: emptySet()
        val paths = neighbours.sumOf { getTotalPathCount(it, visitCount) }
        visitCount[currentNode] = visitCount[currentNode]!!.minus(1)

        return paths
    }

    fun part1(input: List<String>): Int {
        shouldContinue = part1ShouldContinue
        graph.clear()
        val visitCount = mutableMapOf<String, Int>()
        input
            .map { edge -> edge.split("-")}
            .forEach { v ->
                graph.getOrPut(v[0]) { mutableSetOf()}.add(v[1])
                graph.getOrPut(v[1]) { mutableSetOf()}.add(v[0])
                visitCount.putIfAbsent(v[0], 0)
                visitCount.putIfAbsent(v[1], 0)
            }

        return getTotalPathCount("start", visitCount)
    }

    fun part2(input: List<String>): Int {
        shouldContinue = part2ShouldContinue
        graph.clear()
        val visitCount = mutableMapOf<String, Int>()
        input
            .map { edge -> edge.split("-")}
            .forEach { v ->
                graph.getOrPut(v[0]) { mutableSetOf()}.add(v[1])
                graph.getOrPut(v[1]) { mutableSetOf()}.add(v[0])
                visitCount.putIfAbsent(v[0], 0)
                visitCount.putIfAbsent(v[1], 0)
            }

        return getTotalPathCount("start", visitCount)
    }

    val testInput = readInput("Day12_test")
    check(part2(testInput).also { println(it) } == 36)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}