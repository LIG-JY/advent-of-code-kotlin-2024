import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*
import kotlin.collections.ArrayDeque

class Day16_part2 {
    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                data class Vector(val y: Int, val x: Int, val direction: Char)

                // 격자 초기화
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
                // 방향에 대한 자료구조
                val directions = listOf('N', 'S', 'E', 'W')
                val dirIndex = directions.withIndex().associate { it.value to it.index }
                // 3D 상태(y, x, 방향)
                val dist = Array(height) { Array(width) { LongArray(4) { Long.MAX_VALUE } } }
                val previous = Array(height) { Array(width) { Array(4) { mutableListOf<Vector>() } } }
                for (y in grid.indices) {
                    for (x in grid[y].indices) {
                        if (grid[y][x] == 'S') {
                            pq.add(Node(y, x, 0L, 'E'))
                            dist[y][x][dirIndex['E']!!] = 0L
                        }
                    }
                }

                var endY = Int.MIN_VALUE
                var endX = Int.MIN_VALUE
                var shortestDist = Long.MAX_VALUE
                while (pq.isNotEmpty()) {
                    val cur = pq.poll()

                    if (cur.score > dist[cur.y][cur.x][dirIndex[cur.direction]!!]) continue

                    if (grid[cur.y][cur.x] == 'E') {
                        shortestDist = cur.score
                        endY = cur.y
                        endX = cur.x
                        writer.writeLine("shortest distance: $shortestDist")
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
                        val ndirIdx = dirIndex[nd.direction]!!
                        if (newDist < dist[ny][nx][ndirIdx]) {
                            // 비용을 갱신하는 경우만 탐색(우선순위 큐에 저장)
                            pq.add(Node(ny, nx, newDist, nd.direction))
                            dist[ny][nx][ndirIdx] = newDist
                            // 새로운 경로가 더 빠를 경우 이전 경로를 초기화
                            previous[ny][nx][ndirIdx].clear()
                            previous[ny][nx][ndirIdx].add(Vector(cur.y, cur.x, cur.direction))
                        } else if (newDist == dist[ny][nx][ndirIdx]) {
                            // 같은 최단 경로가 존재하면 경로를 추가
                            previous[ny][nx][ndirIdx].add(Vector(cur.y, cur.x, cur.direction))
                        }
                    }
                    // forward
                    val f = forward[cur.direction]!!
                    val ny = cur.y + f.y
                    val nx = cur.x + f.x
                    if (outOfBound(ny, nx)) continue
                    if (grid[ny][nx] == '#') continue
                    val newDist = cur.score + 1
                    val dirIdx = dirIndex[cur.direction]!!
                    if (newDist < dist[ny][nx][dirIdx]) {
                        // 비용을 갱신하는 경우만 탐색(우선순위 큐에 저장)
                        pq.add(Node(ny, nx, newDist, cur.direction))
                        dist[ny][nx][dirIdx] = newDist
                        // 새로운 경로가 더 빠를 경우 이전 경로를 초기화
                        previous[ny][nx][dirIdx].clear()
                        previous[ny][nx][dirIdx].add(Vector(cur.y, cur.x, cur.direction))
                    } else if (newDist == dist[ny][nx][dirIdx]) {
                        // 같은 최단 경로가 존재하면 경로를 추가
                        previous[ny][nx][dirIdx].add(Vector(cur.y, cur.x, cur.direction))
                    }
                }

                // 격자의 방문여부를 나타내는 2D 상태(y, x)
                val visited = Array(height) { BooleanArray(width) { false } }
                val dq = ArrayDeque<Vector>()
                var count = 0
                dist[endY][endX].forEachIndexed { index, _ ->
                    if (dist[endY][endX][index] == shortestDist) {
                        // 최단 경로에 마킹
                        grid[endY][endX] = 'O'
                        if (!visited[endY][endX]) ++count
                        visited[endY][endX] = true
                        dq.addLast(Vector(endY, endX, directions[index]))
                    }
                }

                while (dq.isNotEmpty()) {
                    val cur = dq.removeFirst()
                    previous[cur.y][cur.x][dirIndex[cur.direction]!!].forEach {
                        // 최단 경로에 마킹
                        grid[it.y][it.x] = 'O'
                        if (!visited[it.y][it.x]) ++count
                        visited[it.y][it.x] = true
                        dq.addLast(it)
                    }
                }
                writer.writeLine("tiles: $count")

                // DEBUG
                writer.writeLine(grid.joinToString(separator = System.lineSeparator()) { it.joinToString(separator = "") })
            }
        }
    }

    private fun BufferedWriter.writeLine(v: Any) {
        this.write("$v")
        this.newLine()
    }
}
