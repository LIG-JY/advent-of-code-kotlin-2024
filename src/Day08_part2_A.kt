import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day08_part2_A {

    companion object {
        data class Point(val y: Int, val x: Int)
    }

    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val input = reader.readLines()

                val map = HashMap<Char, ArrayList<Point>>()

                val visited = Array(input.size) { BooleanArray(input[0].length) { false } }
                var count = 0
                for (y in input.indices) {
                    for (x in input[y].indices) {
                        if (input[y][x] != '.') {
                            map.getOrPut(input[y][x]) { ArrayList() }.add(Point(y, x))
                            ++count
                            visited[y][x] = true
                        }
                    }
                }

                fun masking(y: Int, x: Int) {
                    if (visited[y][x]) return
                    visited[y][x] = true
                    ++count
                }

                map.values.forEach { list ->
                    for (i in list.indices) {
                        for (j in i + 1 until list.size) {
                            val (y1, x1) = list[i]
                            val (y2, x2) = list[j]
                            var k1 = 2
                            while (true) {
                                val ny = y1 - k1 * (y1 - y2)
                                val nx = x1 - k1 * (x1 - x2)
                                if (ny >= input.size || ny < 0 || nx >= input[0].length || nx < 0) break
                                masking(ny, nx)
                                ++k1
                            }

                            var k2 = 2
                            while (true) {
                                val ny = y2 - k2 * (y2 - y1)
                                val nx = x2 - k2 * (x2 - x1)
                                if (ny >= input.size || ny < 0 || nx >= input[0].length || nx < 0) break
                                masking(ny, nx)
                                ++k2
                            }

                            // (5,5) (7,6)
                            // dy == 2, dx == 1
                            // (9,7)
                            // 5 - 2(5 - 7), 5 - 2(5 - 6)
                            // (3,4)
                            // 7 - 2(7 - 5), 6 - 2(6 - 5)
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