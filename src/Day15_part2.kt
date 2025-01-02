import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day15_part2 {
    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val input = reader.readLines()
                var grid = mutableListOf<CharArray>()
                var line = 0
                while (input[line].isNotEmpty()) {
                    grid.add(input[line].toCharArray())
                    ++line
                }

                fun resizeRow(row: CharArray): CharArray {
                    val result = CharArray(row.size * 2)
                    row.forEachIndexed { index, c ->
                        when (c) {
                            '#' -> {
                                result[2 * index] = '#'
                                result[2 * index + 1] = '#'
                            }

                            '.' -> {
                                result[2 * index] = '.'
                                result[2 * index + 1] = '.'
                            }

                            'O' -> {
                                result[2 * index] = '['
                                result[2 * index + 1] = ']'
                            }

                            '@' -> {
                                result[2 * index] = '@'
                                result[2 * index + 1] = '.'
                            }
                        }
                    }
                    return result
                }

                grid = grid.map { resizeRow(it) }.toMutableList()

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
                            for (x in cur.x + 1 until width) {
                                if (grid[cur.y][x] == '#') return false
                                if (grid[cur.y][x] == '.') {
                                    for (x2 in x downTo cur.x + 1) {
                                        grid[cur.y][x2] = grid[cur.y][x2 - 1]
                                    }
                                    grid[cur.y][cur.x] = '.'
                                    return true
                                }
                            }
                            return false
                        }

                        '<' -> {
                            for (x in cur.x - 1 downTo 0) {
                                if (grid[cur.y][x] == '#') return false
                                if (grid[cur.y][x] == '.') {
                                    for (x2 in x until cur.x) {
                                        grid[cur.y][x2] = grid[cur.y][x2 + 1]
                                    }
                                    grid[cur.y][cur.x] = '.'
                                    return true
                                }
                            }
                            return false
                        }

                        '^' -> {
                            // key: y, value: Set of x coordinates of the box
                            val toBeMoved = mutableMapOf<Int, MutableSet<Int>>()
                            toBeMoved.getOrPut(cur.y) { mutableSetOf() }.add(cur.x)

                            for (y in cur.y - 1 downTo 0) {
                                val boxes = toBeMoved[y + 1]!!   // Set of x coordinates of the box to be moved
                                // found empty space
                                if (boxes.all { grid[y][it] == '.' }) {
                                    // push box
                                    // move robot
                                    for (j in y until cur.y) {
                                        for (x in toBeMoved[j + 1]!!) {
                                            assert(grid[j + 1][x] == '[' || grid[j + 1][x] == ']' || grid[j + 1][x] == '@')
                                            grid[j][x] = grid[j + 1][x]
                                            grid[j + 1][x] = '.'
                                        }
                                    }
                                    return true
                                }
                                for (x in boxes) {
                                    if (grid[y][x] == '[') toBeMoved.getOrPut(y) { mutableSetOf() }
                                        .addAll(listOf(x, x + 1))
                                    if (grid[y][x] == ']') toBeMoved.getOrPut(y) { mutableSetOf() }
                                        .addAll(listOf(x, x - 1))
                                    if (grid[y][x] == '#') {
                                        return false
                                    }
                                }
                            }
                            return false
                        }

                        'v' -> {
                            // key: y, value: Set of x coordinates of the box
                            val toBeMoved = mutableMapOf<Int, MutableSet<Int>>()
                            toBeMoved.getOrPut(cur.y) { mutableSetOf() }.add(cur.x)

                            for (y in cur.y + 1 until height) {
                                val boxes = toBeMoved[y - 1]!!   // Set of x coordinates of the box to be moved
                                // found empty space
                                if (boxes.all { grid[y][it] == '.' }) {
                                    // push box
                                    // move robot
                                    for (j in y downTo cur.y + 1) {
                                        for (x in toBeMoved[j - 1]!!) {
                                            assert(grid[j - 1][x] == '[' || grid[j - 1][x] == ']' || grid[j - 1][x] == '@')
                                            grid[j][x] = grid[j - 1][x]
                                            grid[j - 1][x] = '.'
                                        }
                                    }
                                    return true
                                }
                                for (x in boxes) {
                                    if (grid[y][x] == '[') toBeMoved.getOrPut(y) { mutableSetOf() }
                                        .addAll(listOf(x, x + 1))
                                    if (grid[y][x] == ']') toBeMoved.getOrPut(y) { mutableSetOf() }
                                        .addAll(listOf(x, x - 1))
                                    if (grid[y][x] == '#') {
                                        return false
                                    }
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
                    } else if (grid[ny][nx] == '[' || grid[ny][nx] == ']') {
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

                // DEBUG
                var turn = 0
                writer.writeLine("turn: $turn")
                writer.writeLine(printGrid(grid))

                for (i in line + 1 until input.size) {
                    val commands = input[i]
                    commands.forEach {
                        loc = move(loc, it)
                        // DEBUG
                        ++turn
                        writer.writeLine("turn: $turn Move: $it")
                        writer.writeLine(printGrid(grid))
                        writer.writeLine(" ")
                    }
                }

                var result = 0L
                for (y in grid.indices) {
                    for (x in grid[y].indices) {
                        if (grid[y][x] == '[') result += (100 * y + x)
                    }
                }

                writer.writeLine(result)
            }
        }
    }

    private fun printGrid(grid: List<CharArray>): String {
        return grid.joinToString("\n") { row -> row.joinToString("") }
    }

    private fun BufferedWriter.writeLine(v: Any) {
        this.write("$v")
        this.newLine()
    }
}
