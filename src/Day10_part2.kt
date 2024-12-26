import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day10_part2 {
    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val input = reader.readText().split(System.lineSeparator())
                val r = input.size
                val c = input[0].length

                data class Node(val y: Int, val x: Int, val routeId: Int)

                val dq = ArrayDeque<Node>()
                var count = 0
                for (y in input.indices) {
                    for (x in input[0].indices) {
                        if (input[y][x] == '0') {
                            val node = Node(y, x, count)
                            dq.addLast(node)
                            ++count
                        }
                    }
                }

                val dy = intArrayOf(0, 0, -1, 1)
                val dx = intArrayOf(-1, 1, 0, 0)

                data class Point(val y: Int, val x: Int)

                fun findBranches(y: Int, x: Int, currentHeight: Char): LinkedHashMap<Int, Point> {
                    val res = LinkedHashMap<Int, Point>()
                    var key = 0
                    for (i in 0 until 4) {
                        val ny = y + dy[i]
                        val nx = x + dx[i]
                        if (ny in 0 until r && nx in 0 until c) {
                            if (input[ny][nx] != currentHeight + 1) continue
                            res[key++] = Point(ny, nx)
                        }
                    }

                    return res
                }

                var nextRouteId = count + 1
                var res = 0
                while (dq.isNotEmpty()) {
                    val cur = dq.removeFirst()
                    val currentHeight = input[cur.y][cur.x]

                    if (currentHeight == '9') {
                        ++res
                    }

                    val branches = findBranches(cur.y, cur.x, currentHeight)
                    for (k in branches.keys) {
                        val branch = branches[k]!!
                        val newNode = if (k == 0) {
                            Node(branch.y, branch.x, cur.routeId)
                        } else {
                            Node(branch.y, branch.x, nextRouteId++)
                        }
                        dq.add(newNode)
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