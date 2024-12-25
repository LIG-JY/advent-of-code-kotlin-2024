import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day09_part1 {
    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val input = reader.readLine().map { it.digitToInt() }
                val sum = input.sum()
                val blocks = IntArray(sum)
                var p = 0
                var id = 0
                for (i in input.indices) {
                    val num = input[i]
                    if (i % 2 == 0) {
                        repeat(num) {
                            blocks[p++] = id
                        }
                        ++id
                    } else {
                        repeat(num) {
                            blocks[p++] = -1
                        }
                    }
                }

                val dq = ArrayDeque<Int>()
                for (i in blocks.indices) {
                    if (blocks[i] == -1) {
                        dq.addLast(i)
                    }
                }

                for (j in sum - 1 downTo 0) {
                    val v = blocks[j]
                    if (v == -1) continue
                    if (dq.isEmpty()) break
                    val i = dq.removeFirst()
                    if (i < j) {
                        blocks[j] = blocks[i].also { blocks[i] = blocks[j] }
                    }
                }

                var res = 0L
                for (i in blocks.indices) {
                    if (blocks[i] != -1) {
                        res += i * blocks[i]
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
