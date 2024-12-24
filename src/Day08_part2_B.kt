import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day08_part2_B {

    data class Point(val y: Int, val x: Int)

    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val input = reader.readLines()

                val map = HashMap<Char, ArrayList<Point>>()

                for (y in input.indices) {
                    for (x in input[y].indices) {
                        if (input[y][x] != '.') {
                            map.getOrPut(input[y][x]) { ArrayList() }.add(Point(y, x))
                        }
                    }
                }

                val visited = Array(input.size) { BooleanArray(input[0].length) { false } }
                var count = 0
                map.values.forEach { list ->
                    for (i in list.indices) {
                        for (j in list.indices) {
                            if (i == j) {
                                continue
                            }
                            val (y1, x1) = list[i]
                            val (y2, x2) = list[j]
                            val dy = y1 - y2
                            val dx = x1 - x2
                            var k = 0
                            while (true) {
                                val ny = y1 + k * dy
                                val nx = x1 + k * dx
                                if (ny >= input.size || ny < 0 || nx >= input[0].length || nx < 0) break
                                if (!visited[ny][nx]) {
                                    visited[ny][nx] = true
                                    ++count
                                }
                                ++k
                            }
                        }
                    }
                }

                writer.writeLine(count)
            }
        }
    }

    private fun BufferedWriter.writeLine(v: Any) {
        this.write("$v")
        this.newLine()
    }
}