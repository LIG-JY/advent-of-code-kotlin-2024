import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day14_part1 {
    companion object {
        const val HEIGHT = 103
        const val WIDTH = 101
    }

    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                data class Robot(val y: Int, val x: Int, val dy: Int, val dx: Int)

                val grid = Array(HEIGHT) { IntArray(WIDTH) { 0 } }
                val q = ArrayDeque<Robot>()
                while (true) {
                    val l = reader.readLine() ?: break
                    val (x, y, dx, dy) = l.split("p=", ",", " v=").mapNotNull { it.toIntOrNull() }
                    val robot = Robot(y, x, dy, dx)
                    grid[y][x]++
                    q.addLast(robot)
                }

                fun move(r: Robot): Robot {
                    val ny = if (r.y + r.dy < 0) r.y + r.dy + HEIGHT else (r.y + r.dy) % HEIGHT
                    val nx = if (r.x + r.dx < 0) r.x + r.dx + WIDTH else (r.x + r.dx) % WIDTH
                    grid[r.y][r.x]--
                    grid[ny][nx]++

                    return Robot(ny, nx, r.dy, r.dx)
                }

                repeat(100) {
                    repeat(q.size) {
                        val cur = q.removeFirst()
                        q.addLast(move(cur))
                    }
                }

                val halfHeight = HEIGHT / 2
                val halfWidth = WIDTH / 2

                var quad1 = 0
                for (y in 0 until halfHeight) {
                    for (x in 0 until halfWidth) {
                        quad1 += grid[y][x]
                    }
                }

                var quad2 = 0
                for (y in 0 until halfHeight) {
                    for (x in halfWidth + 1 until WIDTH) {
                        quad2 += grid[y][x]
                    }
                }

                var quad3 = 0
                for (y in halfHeight + 1 until HEIGHT) {
                    for (x in 0 until halfWidth) {
                        quad3 += grid[y][x]
                    }
                }

                var quad4 = 0
                for (y in halfHeight + 1 until HEIGHT) {
                    for (x in halfWidth + 1 until WIDTH) {
                        quad4 += grid[y][x]
                    }
                }

                writer.writeLine(quad1 * quad2 * quad3 * quad4)
            }
        }
    }

    private fun BufferedWriter.writeLine(v: Any) {
        this.write("$v")
        this.newLine()
    }
}
