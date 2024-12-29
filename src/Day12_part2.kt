import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day12_part2 {
    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val input = reader.readText().split(System.lineSeparator()).map { it.toCharArray() }
                val height = input.size
                val width = input[0].size
                val visited = Array(height) { BooleanArray(width) { false } }


                data class Point(val y: Int, val x: Int)

                val directions = listOf(Point(1, 0), Point(0, 1), Point(-1, 0), Point(0, -1))
                // URDL

                fun getSide(input: Array<MutableList<Point>>): Long {
                    var sideCount = 0L
                    for (i in directions.indices) {
                        val group = mutableMapOf<Int, MutableList<Point>>()
                        var selector: (Point) -> Int
                        if (i % 2 == 0) {
                            // Up, Down
                            input[i].groupByTo(group) { it.y }
                            selector = { it.x }
                        } else {
                            // Right, Left
                            input[i].groupByTo(group) { it.x }
                            selector = { it.y }
                        }

                        for (v in group.values) {
                            var prev: Int? = null
                            for (p in v.sortedBy(selector)) {
                                val cur = selector(p)
                                if (prev == null || cur - prev > 1) ++sideCount
                                prev = cur
                            }
                        }
                    }

                    return sideCount
                }

                fun getPrice(p: Point): Long {
                    val edges = Array(4) { mutableListOf<Point>() } // URDL
                    var count = 0L
                    val dq = ArrayDeque<Point>()
                    visited[p.y][p.x] = true
                    dq.addLast(p)
                    while (dq.isNotEmpty()) {
                        val cur = dq.removeFirst()
                        ++count
                        for (i in directions.indices) {
                            val d = directions[i]
                            val ny = cur.y + d.y
                            val nx = cur.x + d.x

                            if (ny >= height || ny < 0 || nx >= width || nx < 0 || input[ny][nx] != input[cur.y][cur.x]) {
                                edges[i].add(Point(ny, nx))
                                continue
                            }

                            if (!visited[ny][nx]) {
                                visited[ny][nx] = true
                                dq.addLast(Point(ny, nx))
                            }
                        }
                    }

                    return getSide(edges) * count
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
