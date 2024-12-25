import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day09_part2 {
    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val input = reader.readLine().map { it.digitToInt() }
                val sum = input.sum()
                val blocks = IntArray(sum)
                val idMap = mutableMapOf<Int, Pair<Int, Int>>() // key: id, value: start to size
                var p = 0
                var id = 0
                for (i in input.indices) {
                    val num = input[i]
                    if (i % 2 == 0) {
                        val start = p
                        repeat(num) {
                            blocks[p++] = id
                        }
                        idMap[id++] = start to num
                    } else {
                        repeat(num) {
                            blocks[p++] = -1
                        }
                    }
                }

                for (j in id - 1 downTo 0) {
                    idMap[j]?.let {
                        val size = it.second
                        val start = it.first
                        var k = 0
                        while (k < start) {
                            if (blocks[k] == -1) {
                                val spaceStart = k
                                var spaceSize = 0
                                while (blocks[k] == -1) {
                                    spaceSize++
                                    k++
                                }
                                if (spaceSize >= size) {
                                    for (l in 0 until size) {
                                        blocks[spaceStart + l] = j
                                        blocks[start + l] = -1
                                    }
                                    break
                                }
                            } else {
                                k++
                            }
                        }
                    }
                }

                var res = 0L
                for (k in blocks.indices) {
                    if (blocks[k] != -1) {
                        res += k * blocks[k]
                    }
                }
                writer.writeLine(res)
            }
        }
    }

    private fun BufferedWriter.writeLine(v: Any) {
        this.write("$v")
        this.newLine()
    }
}