import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import kotlin.math.log2

class Day14_part2 {
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

                // Entropy calculates the entropy of a 2D grid
                // using Shannon's Entropy.
                // https://en.wikipedia.org/wiki/Entropy_(information_theory)
                fun calculateEntropy(grid: Array<IntArray>): Double {
                    // Flatten the image into a single slice
                    val flatGrid = grid.flatMap { it.asIterable() }

                    // Calculate each frequencies
                    val valueCounts = mutableMapOf<Int, Int>()
                    for (value in flatGrid) {
                        valueCounts[value] = valueCounts.getOrDefault(value, 0) + 1
                    }

                    // Total cell count of grid
                    val totalCells = flatGrid.size.toDouble()

                    // Calculate probability distributions and calculate entropy
                    var entropy = 0.0
                    for (count in valueCounts.values) {
                        val probability = count / totalCells
                        entropy -= probability * log2(probability)
                    }

                    return entropy
                }

                var minEntropy = Double.MAX_VALUE
                var bestTime = 0
                for (t in 1..WIDTH * HEIGHT) {
                    repeat(q.size) {
                        val cur = q.removeFirst()
                        q.addLast(move(cur))
                    }
                    val entropy = calculateEntropy(grid)
                    if (entropy < minEntropy) {
                        minEntropy = entropy
                        bestTime = t
                    }
                }
                writer.writeLine(bestTime)
            }
        }
    }

    private fun BufferedWriter.writeLine(v: Any) {
        this.write("$v")
        this.newLine()
    }
}
