import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day11_part2 {

    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val input = reader.readLine().split(" ")

                var currentMap = input.groupBy { it }.mapValues { it.value.size.toLong() }.toMutableMap()
                var nextMap = mutableMapOf<String, Long>()

                fun String.removePrefixZero(): String {
                    val res = this.dropWhile { it == '0' }
                    return res.ifEmpty { "0" }
                }

                fun MutableMap<String, Long>.customAdd(key: String, num: Long) {
                    this.merge(key, num, Long::plus)
                }

                fun solveRecursive(curDepth: Int, maxDepth: Int) {
                    if (curDepth == maxDepth) return

                    nextMap.clear()
                    for ((k, v) in currentMap) {
                        if (k == "0") {
                            nextMap.customAdd("1", v)
                        } else if (k.length % 2 == 0) {
                            val left = k.substring(0, k.length / 2)
                            nextMap.customAdd(left, v)

                            val right = (k.substring(k.length / 2)).removePrefixZero()
                            nextMap.customAdd(right, v)
                        } else {
                            val num = (k.toLong() * 2024).toString()
                            nextMap.customAdd(num, v)
                        }
                    }

                    nextMap = currentMap.also { currentMap = nextMap }
                    solveRecursive(curDepth + 1, maxDepth)
                }

                solveRecursive(0, 75)

                writer.writeLine(currentMap.values.sum())
            }
        }
    }

    private fun BufferedWriter.writeLine(v: Any) {
        this.write("$v")
        this.newLine()
    }
}