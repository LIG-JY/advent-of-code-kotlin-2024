import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day04_part2 {
    private val directions = listOf(
        listOf(-1 to -1, 1 to 1),
        listOf(1 to -1, -1 to 1)
    )

    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val grid = reader.readText().split(System.lineSeparator())
                val maxY = grid.size
                val maxX = grid[0].length
                var count = 0

                for (y in 0 until maxY) {
                    for (x in 0 until maxX) {
                        if (grid[y][x] == 'A') {
                            if (isXMasPattern(grid, y, x, maxY, maxX)) count++
                        }
                    }
                }
                writer.writeLine(count)
            }
        }
    }

    private fun isXMasPattern(grid: List<String>, centerY: Int, centerX: Int, maxY: Int, maxX: Int): Boolean {
        val results = BooleanArray(2) { false }
        for (i in directions.indices) {
            val dir = directions[i]
            val (from, to) = dir

            val (fromDX, fromDY) = from
            val (toDX, toDY) = to

            val nYFrom = centerY + fromDY
            val nXFrom = centerX + fromDX
            if (!isValid(grid, nYFrom, nXFrom, maxY, maxX)) {
                return false
            }

            val nYTo = centerY + toDY
            val nXTo = centerX + toDX
            if (!isValid(grid, nYTo, nXTo, maxY, maxX)) {
                return false
            }

            if (grid[nYFrom][nXFrom] == 'M' && grid[nYTo][nXTo] == 'S') {
                results[i] = true
                continue
            }

            if (grid[nYFrom][nXFrom] == 'S' && grid[nYTo][nXTo] == 'M') {
                results[i] = true
                continue
            }
        }
        return results.all { it == true }
    }

    private fun isValid(grid: List<String>, y: Int, x: Int, maxY: Int, maxX: Int): Boolean {
        return y in 0 until maxY && x in 0 until maxX
    }

    private fun BufferedWriter.writeLine(v: Int) {
        this.write("$v")
        this.newLine()
    }
}