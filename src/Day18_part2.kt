import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day18_part2 {
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

                fun findPathByBFS(): Boolean {
                    data class Point(val y: Int, val x: Int)

                    val directions = listOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1))

                    val visited = Array(GRID_SIZE) { BooleanArray(GRID_SIZE) { false } }
                    val dq = ArrayDeque<Point>()
                    // You and The Historians are currently in the top left corner of the memory space (at 0,0)
                    dq.addLast(Point(0, 0))
                    visited[0][0] = true
                    while (!dq.isEmpty()) {
                        val cur = dq.removeFirst()
                        if (cur.y == GRID_SIZE - 1 && cur.x == GRID_SIZE - 1) break
                        for (d in directions) {
                            val ny = cur.y + d.y
                            val nx = cur.x + d.x
                            if (ny > GRID_SIZE - 1 || ny < 0 || nx > GRID_SIZE - 1 || nx < 0) continue
                            if (grid[ny][nx] == '#') continue
                            if (!visited[ny][nx]) {
                                dq.addLast(Point(ny, nx))
                                visited[ny][nx] = true
                            }
                        }
                    }

                    return visited[GRID_SIZE - 1][GRID_SIZE - 1]
                }

                drawGrid()
                while (true) {
                    val line = reader.readLine() ?: break
                    val (x, y) = line.split(",").map { it.toInt() }
                    grid[y][x] = '#'
                    val isPathExist = findPathByBFS()
                    if (!isPathExist) {
                        writer.writeLine("$x,$y")
                        break
                    }
                }
            }
        }
    }

    private fun BufferedWriter.writeLine(v: Any) {
        this.write("$v")
        this.newLine()
    }
}
