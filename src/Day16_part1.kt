import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

class Day16_part1 {
    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                data class Vector(val y: Int, val x: Int, val direction: Char)

                val grid = mutableListOf<CharArray>()
                while (true) {
                    val line = reader.readLine() ?: break
                    grid.add(line.toCharArray())
                }

                val height = grid.size
                val width = grid[0].size

                fun outOfBound(y: Int, x: Int) = y >= height || y < 0 || x >= width || x < 0

                val rotate = mapOf(
                    'N' to listOf(Vector(0, 1, 'E'), Vector(0, -1, 'W')),
                    'S' to listOf(Vector(0, 1, 'E'), Vector(0, -1, 'W')),
                    'E' to listOf(Vector(1, 0, 'S'), Vector(-1, 0, 'N')),
                    'W' to listOf(Vector(1, 0, 'S'), Vector(-1, 0, 'N')),
                )

                val forward = mapOf(
                    'N' to Vector(-1, 0, 'N'),
                    'S' to Vector(1, 0, 'S'),
                    'E' to Vector(0, 1, 'E'),
                    'W' to Vector(0, -1, 'W'),
                )

                data class Node(val y: Int, val x: Int, val score: Long, val direction: Char)

                val pq = PriorityQueue(compareBy<Node> { it.score })
                val dist = Array(height) { LongArray(width) { Long.MAX_VALUE } }
                for (y in grid.indices) {
                    for (x in grid[y].indices) {
                        if (grid[y][x] == 'S') {
                            pq.add(Node(y, x, 0L, 'E'))
                            dist[y][x] = 0L
                        }
                    }
                }

                while (pq.isNotEmpty()) {
                    val cur = pq.poll()

                    if (grid[cur.y][cur.x] == 'E') {
                        writer.writeLine(cur.score)
                        break
                    }

                    val currentRotations = rotate[cur.direction]!!
                    for (i in currentRotations.indices) {
                        val nd = currentRotations[i]
                        val ny = cur.y + nd.y
                        val nx = cur.x + nd.x
                        if (outOfBound(ny, nx)) continue
                        if (grid[ny][nx] == '#') continue
                        val newDist = cur.score + 1001L
                        if (newDist < dist[ny][nx]) {
                            pq.add(Node(ny, nx, newDist, nd.direction))
                            dist[ny][nx] = newDist
                        }
                    }
                    // forward
                    val f = forward[cur.direction]!!
                    val ny = cur.y + f.y
                    val nx = cur.x + f.x
                    if (outOfBound(ny, nx)) continue
                    if (grid[ny][nx] == '#') continue
                    val newDist = cur.score + 1
                    if (newDist < dist[ny][nx]) {
                        pq.add(Node(ny, nx, newDist, cur.direction))
                        dist[ny][nx] = newDist
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
