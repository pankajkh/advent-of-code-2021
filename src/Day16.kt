import javax.naming.OperationNotSupportedException

fun main() {
    val hexToBinary = mapOf<Char, String>(
        '0' to "0000",
        '1' to "0001",
        '2' to "0010",
        '3' to "0011",
        '4' to "0100",
        '5' to "0101",
        '6' to "0110",
        '7' to "0111",
        '8' to "1000",
        '9' to "1001",
        'A' to "1010",
        'B' to "1011",
        'C' to "1100",
        'D' to "1101",
        'E' to "1110",
        'F' to "1111"
    )

    lateinit var binary: String

    data class Packet(val version: Int, val typeId: Int) {
        var literal: Long? = null

        var lengthTypeId: Char? = null
        var packets: List<Packet>? = null

        constructor(version:Int, typeId:Int, literal:Long) : this(version, typeId) {
            this.literal = literal
        }

        constructor(version:Int, typeId:Int, lengthTypeId:Char, packets: List<Packet>) : this(version, typeId) {
            this.lengthTypeId = lengthTypeId
            this.packets = packets
        }

    }

    fun getLiteral(start: Int, end: Int): Pair<Pair<Int, Int>, Long> {
        var literal = ""
        var i = start
        do {
            literal += binary.substring(i + 1 until i + 5)
            i += 5
        } while (binary[i-5] == '1')
        return Pair(Pair(start, i), literal.toDecimal())
    }

    fun getPackets(start: Int, end:Int, count: Int): Pair<Pair<Int, Int>, List<Packet>> {
        val result = mutableListOf<Packet>()
        var ls = start
        var c = 0
        while (ls < end && c < count) {
            if (binary.substring(ls until end).toDecimal() == 0L) break;
            val version = binary.substring(ls until ls + 3).toDecimal().toInt()
            ls += 3
            val typeId = binary.substring(ls until ls + 3).toDecimal().toInt()
            ls += 3
            when (typeId) {
                4 -> {
                    val literal = getLiteral(ls, end)
                    result.add(Packet(version = version, typeId = typeId, literal = literal.second))
                    ls = literal.first.second
                    ++c
                }
                else -> {
                    val translation: Pair<Pair<Int, Int>, List<Packet>>
                    val lengthTypeId = binary[ls]
                    if (lengthTypeId == '0') {
                        ls += 1
                        val totalLengthInBits = binary.substring(ls until ls + 15).toDecimal().toInt()
                        ls += 15
                        translation = getPackets(ls, ls + totalLengthInBits, Int.MAX_VALUE)
                    } else {
                        ls += 1
                        val numberOfSubPackets = binary.substring(ls until ls + 11).toDecimal().toInt()
                        ls += 11
                        translation = getPackets(ls, end, numberOfSubPackets)
                    }
                    result.add(Packet(version = version, typeId = typeId, lengthTypeId = lengthTypeId, packets = translation.second))
                    ls  = translation.first.second
                    ++c
                }
            }
        }
        return Pair(Pair(start, ls), result)
    }

    fun addAllVersions(packets: List<Packet>): Int {
        return packets.sumOf { it.version.plus(it.packets?.let { it1 -> addAllVersions(it1) } ?: 0) }
    }

    fun Packet.calculate(): Long {
        return when(typeId) {
            0 -> packets!!.fold(0) { acc, p ->  acc+p.calculate() }
            1 -> packets!!.fold(1) { acc, p -> acc*p.calculate() }
            2 -> packets!!.fold(Long.MAX_VALUE) { acc, p -> minOf(acc, p.calculate()) }
            3 -> packets!!.fold(Long.MIN_VALUE) { acc, p -> maxOf(acc, p.calculate()) }
            4 -> literal!!
            5 -> packets!!.map { p -> p.calculate() }.windowed(2).map { (a, b) -> if (a > b) 1L else 0L }.first()
            6 -> packets!!.map { p -> p.calculate() }.windowed(2).map { (a, b) -> if (a < b) 1L else 0L }.first()
            7 -> packets!!.map { p -> p.calculate() }.windowed(2).map { (a, b) -> if (a==b) 1L else 0L }.first()
            else -> 0L.also { println("error") }
        }
    }

    fun part1(): Int {
        val t = getPackets(0, binary.length, Int.MAX_VALUE)
        return addAllVersions(t.second)
    }

    fun part2(): Long {
        val t = getPackets(0, binary.length, Int.MAX_VALUE)
        return t.second[0].calculate()
    }

    val testInput = readInput("Day16_test")
    binary = testInput[0].map { hex -> hexToBinary[hex]}.joinToString(separator = "")
    check(part2().also { println(it) } == 1L)

    val input = readInput("Day16")
    binary = input[0].map { hex -> hexToBinary[hex]}.joinToString(separator = "")
    part1().also { println(it) }
    part2().also { println(it) }
}

private fun String.toDecimal(): Long {
    return this.fold(0) {acc, c -> acc*2 + c.digitToInt()}
}
