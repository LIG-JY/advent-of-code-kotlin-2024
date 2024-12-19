import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day06_part1 {
    val dy = listOf(-1, 0, 1, 0)
    val dx = listOf(0, 1, 0, -1)

    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val readOnlyGrid = mutableListOf<String>()
                while (true) {
                    val line = reader.readLine() ?: break
                    readOnlyGrid.add(line)
                }

                val start = findStart(readOnlyGrid)!!
                val res = traverse(readOnlyGrid, start)
                writer.writeLine(res)
            }
        }
    }

    private fun findStart(readOnlyGrid: MutableList<String>): Pair<Int, Int>? {
        for (y in readOnlyGrid.indices) {
            for (x in readOnlyGrid[y].indices) {
                if (readOnlyGrid[y][x] == '^') {
                    return y to x
                }
            }
        }

        return null // unreachable
    }

    private fun traverse(readOnlyGrid: MutableList<String>, start: Pair<Int, Int>): Int {
        var d = 0
        var res = 1
        val visited = Array(readOnlyGrid.size) { BooleanArray(readOnlyGrid[0].length) { false } }
        val dq = ArrayDeque<Pair<Int, Int>>()

        visited[start.first][start.second] = true
        dq.addLast(start)
        while (dq.isNotEmpty()) {
            val current = dq.removeFirst()
            val ny = current.first + dy[d]
            val nx = current.second + dx[d]
            if (ny >= readOnlyGrid.size || ny < 0 || nx >= readOnlyGrid[0].length || nx < 0) {
                // According to the input conditions, the guard is guaranteed to exit the map
                return res
            }
            if (readOnlyGrid[ny][nx] == '#') {
                d = (d + 1) % 4
                dq.addFirst(current)
                continue
            }
            if (!visited[ny][nx]) {
                ++res
                visited[ny][nx] = true
            }
            dq.addLast(Pair(ny, nx))
        }

        return res
    }

    private fun BufferedWriter.writeLine(v: Int) {
        this.write("$v")
        this.newLine()
    }
}