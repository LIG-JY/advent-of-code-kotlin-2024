import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day12_part1 {
    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val input = reader.readText().split(System.lineSeparator()).map { it.toCharArray() }
                val height = input.size
                val width = input[0].size
                val visited = Array(height) { BooleanArray(width) { false } }


                data class Point(val y: Int, val x: Int)

                val directions = listOf(Point(1, 0), Point(-1, 0), Point(0, 1), Point(0, -1))

                fun getPrice(p: Point): Long {
                    var perimeter = 0L
                    var count = 0L
                    val dq = ArrayDeque<Point>()
                    visited[p.y][p.x] = true
                    dq.addLast(p)
                    while (dq.isNotEmpty()) {
                        val cur = dq.removeFirst()
                        ++count
                        for (d in directions) {
                            val ny = cur.y + d.y
                            val nx = cur.x + d.x
                            if (ny >= height || ny < 0 || nx >= width || nx < 0) {
                                ++perimeter
                                continue
                            }
                            if (input[ny][nx] != input[cur.y][cur.x]) {
                                ++perimeter
                                continue
                            }
                            if (!visited[ny][nx]) {
                                visited[ny][nx] = true
                                dq.addLast(Point(ny, nx))
                            }
                        }
                    }
                    return perimeter * count
                }

                var res = 0L
                for (y in 0 until height) {
                    for (x in 0 until width) {
                        if (!visited[y][x]) {
                            res += getPrice(Point(y, x))
                        }
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
