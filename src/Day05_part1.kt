import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day05_part1 {
    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val orderMap = mutableMapOf<Int, MutableSet<Int>>()
                while (true) {
                    val input = reader.readLine()

                    if (input.isEmpty()) {
                        break
                    }

                    val (k, v) = input.split("|").map { it.toInt() }
                    orderMap.getOrPut(k) { mutableSetOf() }.add(v)
                }

                var result = 0
                while (true) {
                    val input = reader.readLine()

                    if (input.isNullOrBlank()) {
                        break
                    }

                    val list = input.split(",").map { it.toInt() }
                    if (isCorrectOrder(list, orderMap)) {
                        result += list[(list.size - 1) / 2]
                    }
                }
                writer.writeLine(result)
            }
        }
    }

    private fun isCorrectOrder(list: List<Int>, orderMap: MutableMap<Int, MutableSet<Int>>): Boolean {
        for (i in list.indices) {
            val current = list[i]
            for (j in i + 1 until list.size) {
                val orderList = orderMap[list[j]]
                if (orderList != null) {
                    if (orderList.contains(current)) {
                        return false
                    }
                }
            }
        }
        return true
    }

    private fun BufferedWriter.writeLine(v: Int) {
        this.write("$v")
        this.newLine()
    }
}
