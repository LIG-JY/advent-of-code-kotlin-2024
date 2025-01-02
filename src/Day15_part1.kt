import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day15_part1 {
    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val input = reader.readLines()
                val grid = mutableListOf<CharArray>()
                var line = 0
                while (input[line].isNotEmpty()) {
                    grid.add(input[line].toCharArray())
                    ++line
                }
                val height = grid.size
                val width = grid[0].size

                data class Point(val y: Int, val x: Int)

                fun getDiff(direction: Char): Point {
                    when (direction) {
                        '>' -> return Point(0, 1)
                        '<' -> return Point(0, -1)
                        '^' -> return Point(-1, 0)
                        'v' -> return Point(1, 0)
                    }
                    return Point(Int.MIN_VALUE, Int.MIN_VALUE)  // UNREACHABLE
                }

                fun push(cur: Point, direction: Char): Boolean {
                    when (direction) {
                        '>' -> {
                            val empty: Int
                            for (x in cur.x + 1 until width) {
                                if (grid[cur.y][x] == '#') return false
                                if (grid[cur.y][x] == '.') {
                                    empty = x
                                    for (x2 in empty downTo cur.x + 1) {
                                        grid[cur.y][x2] = grid[cur.y][x2 - 1]
                                    }
                                    grid[cur.y][cur.x] = '.'
                                    return true
                                }
                            }
                            return false
                        }

                        '<' -> {
                            val empty: Int
                            for (x in cur.x - 1 downTo 0) {
                                if (grid[cur.y][x] == '#') return false
                                if (grid[cur.y][x] == '.') {
                                    empty = x
                                    for (x2 in empty until cur.x) {
                                        grid[cur.y][x2] = grid[cur.y][x2 + 1]
                                    }
                                    grid[cur.y][cur.x] = '.'
                                    return true
                                }
                            }
                            return false
                        }

                        '^' -> {
                            val empty: Int
                            for (y in cur.y - 1 downTo 0) {
                                if (grid[y][cur.x] == '#') return false
                                if (grid[y][cur.x] == '.') {
                                    empty = y
                                    for (y2 in empty until cur.y) {
                                        grid[y2][cur.x] = grid[y2 + 1][cur.x]
                                    }
                                    grid[cur.y][cur.x] = '.'
                                    return true
                                }
                            }
                            return false
                        }

                        'v' -> {
                            val empty: Int
                            for (y in cur.y + 1 until height) {
                                if (grid[y][cur.x] == '#') return false
                                if (grid[y][cur.x] == '.') {
                                    empty = y
                                    for (y2 in empty downTo cur.y + 1) {
                                        grid[y2][cur.x] = grid[y2 - 1][cur.x]
                                    }
                                    grid[cur.y][cur.x] = '.'
                                    return true
                                }
                            }
                            return false
                        }
                    }
                    return false
                }

                fun move(cur: Point, direction: Char): Point {
                    val d = getDiff(direction)
                    val ny = cur.y + d.y
                    val nx = cur.x + d.x
                    val newLoc = Point(ny, nx)
                    var hasMoved = false

                    if (grid[ny][nx] == '#') return cur
                    else if (grid[ny][nx] == '.') {
                        grid[cur.y][cur.x] = '.'
                        grid[ny][nx] = '@'
                        hasMoved = true
                    } else if (grid[ny][nx] == 'O') {
                        if (push(cur, direction)) hasMoved = true
                    }
                    return if (hasMoved) newLoc else cur
                }

                var loc = Point(Int.MIN_VALUE, Int.MIN_VALUE)
                for (y in grid.indices) {
                    for (x in grid[y].indices) {
                        if (grid[y][x] == '@') loc = Point(y, x)
                    }
                }

                for (i in line + 1 until input.size) {
                    val commands = input[i]
                    commands.forEach { loc = move(loc, it) }
                }

                var result = 0L
                for (y in grid.indices) {
                    for (x in grid[y].indices) {
                        if (grid[y][x] == 'O') result += (100 * y + x)
                    }
                }

                writer.writeLine(result)
            }
        }
    }

    private fun BufferedWriter.writeLine(v: Any) {
        this.write("$v")
        this.newLine()
    }
}
