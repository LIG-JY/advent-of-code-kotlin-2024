import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day18_part1 {

    companion object {
        const val GRID_SIZE = 71
        const val BYTES = 1024
    }

    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val grid = Array(GRID_SIZE) { CharArray(GRID_SIZE) { '.' } }

                fun drawGrid() {
                    repeat(BYTES) {
                        val line = reader.readLine() ?: return
                        val (x, y) = line.split(",").map { it.toInt() }
                        grid[y][x] = '#'
                    }
                }

                fun calculateStepsByBFS(): Int {
                    data class Point(val y: Int, val x: Int)

                    val directions = listOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1))

                    val dist = Array(GRID_SIZE) { IntArray(GRID_SIZE) { -1 } }
                    val dq = ArrayDeque<Point>()
                    // You and The Historians are currently in the top left corner of the memory space (at 0,0)
                    dq.addLast(Point(0, 0))
                    dist[0][0] = 0
                    while (!dq.isEmpty()) {
                        val cur = dq.removeFirst()
                        if (cur.y == GRID_SIZE - 1 && cur.x == GRID_SIZE - 1) break
                        for (d in directions) {
                            val ny = cur.y + d.y
                            val nx = cur.x + d.x
                            if (ny > GRID_SIZE - 1 || ny < 0 || nx > GRID_SIZE - 1 || nx < 0) continue
                            if (grid[ny][nx] == '#') continue
                            if (dist[ny][nx] == -1) {
                                dq.addLast(Point(ny, nx))
                                dist[ny][nx] = dist[cur.y][cur.x] + 1
                            }
                        }
                    }

                    return dist[GRID_SIZE - 1][GRID_SIZE - 1]
                }

                drawGrid()
                val steps = calculateStepsByBFS()
                writer.writeLine(steps)
            }
        }
    }

    private fun BufferedWriter.writeLine(v: Any) {
        this.write("$v")
        this.newLine()
    }
}
