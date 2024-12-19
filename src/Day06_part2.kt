import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day06_part2 {
    val dy = listOf(-1, 0, 1, 0)
    val dx = listOf(0, 1, 0, -1)

    data class Point(val y: Int, val x: Int)
    data class GuardState(val y: Int, val x: Int, val d: Int)   // 0 <= d < 4

    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val grid = mutableListOf<CharArray>()
                while (true) {
                    val line = reader.readLine() ?: break
                    grid.add(line.toCharArray())
                }

                val start = findStart(grid)!!
                var count = 0
                for (y in grid.indices) {
                    for (x in grid[y].indices) {
                        val loc = Point(y, x)
                        if (grid[y][x] == '#' || grid[y][x] == '^') {
                            continue
                        }
                        if (solve(grid = grid, start = start, post = loc)) {
//                        println(loc)  // debug
                            ++count
                        }
                    }
                }

                writer.writeLine(count)
            }
        }
    }

    private fun findStart(grid: MutableList<CharArray>): Point? {
        for (y in grid.indices) {
            for (x in grid[y].indices) {
                if (grid[y][x] == '^') {
                    return Point(y, x)
                }
            }
        }

        return null // unreachable
    }

    private fun solve(grid: MutableList<CharArray>, start: Point, post: Point): Boolean {
        // add obstacle
        grid[post.y][post.x] = '#'

        val res = isCycling(grid = grid, start = start)

        // remove obstacle
        grid[post.y][post.x] = '.'

        return res
    }

    private fun isCycling(grid: MutableList<CharArray>, start: Point): Boolean {
        val visited = mutableSetOf<GuardState>()
        var y = start.y
        var x = start.x
        var d = 0

        while (true) {
            val currentState = GuardState(y, x, d)
            if (currentState in visited) {
                return true
            }
            visited.add(currentState)

            val ny = currentState.y + dy[d]
            val nx = currentState.x + dx[d]
            if (ny >= grid.size || ny < 0 || nx >= grid[0].size || nx < 0) {
                // not a cycle
                return false
            }
            if (grid[ny][nx] == '#') {
                // change direction, location is same
                d = (d + 1) % 4
                continue
            }
            // move
            y = ny
            x = nx
        }
    }

    private fun BufferedWriter.writeLine(v: Int) {
        this.write("$v")
        this.newLine()
    }
}
