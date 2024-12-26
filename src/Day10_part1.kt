import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day10_part1 {
    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val input = reader.readText().split(System.lineSeparator())
                val r = input.size
                val c = input[0].length

                data class Node(val y: Int, val x: Int, val id: Int)

                val dq = ArrayDeque<Node>()
                val visited = Array(r) { Array(c) { mutableSetOf<Node>() } }
                var count = 0
                for (y in input.indices) {
                    for (x in input[0].indices) {
                        if (input[y][x] == '0') {
                            val node = Node(y, x, count)
                            visited[y][x].add(node)
                            dq.addLast(node)
                            ++count
                        }
                    }
                }

                val dy = intArrayOf(0, 0, -1, 1)
                val dx = intArrayOf(-1, 1, 0, 0)
                var res = 0
                while (dq.isNotEmpty()) {
                    val cur = dq.removeFirst()
                    val currentHeight = input[cur.y][cur.x]

                    if (currentHeight == '9') {
                        ++res
                    }

                    for (i in 0 until 4) {
                        val ny = cur.y + dy[i]
                        val nx = cur.x + dx[i]
                        if (ny in 0 until r && nx in 0 until c) {
                            if (input[ny][nx] != currentHeight + 1) continue
                            val newNode = Node(ny, nx, cur.id)
                            if (visited[ny][nx].contains(newNode)) continue
                            visited[ny][nx].add(newNode)
                            dq.addLast(newNode)
                        }
                    }
                }
                writer.writeLine(res)
            }
        }
    }

    private fun BufferedWriter.writeLine(v: Any) {
        this.write("$v")
        this.newLine()
    }
}