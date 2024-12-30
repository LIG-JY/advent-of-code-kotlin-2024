import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.*

class Day13_part1 {
    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val input = reader.readText()

                data class Point(val x: Int, val y: Int) {
                    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
                }

                var a = Point(Int.MAX_VALUE, Int.MAX_VALUE)
                var b = Point(Int.MAX_VALUE, Int.MAX_VALUE)
                var prize = Point(Int.MAX_VALUE, Int.MAX_VALUE)

                fun parseButtonInput(input: String): Point {
                    val (x, y) = input.split("X+", "Y+", ",")
                        .filter { it.trim().toIntOrNull() != null }.map { it.toInt() }

                    return Point(x, y)
                }

                fun parsePrizeInput(input: String): Point {
                    val (x, y) = input.split("X=", "Y=", ",")
                        .filter { it.trim().toIntOrNull() != null }.map { it.toInt() }

                    return Point(x, y)
                }


                data class Node(val p: Point, val cost: Int, val counts: Pair<Int, Int>)

                fun solveOrNull(a: Point, b: Point, prize: Point): Int? {
                    val start = Node(Point(0, 0), 0, 0 to 0)
                    val A = Node(a, 3, 0 to 0)
                    val B = Node(b, 1, 0 to 0)


                    val pq = PriorityQueue<Node>(compareBy { it.cost })
                    val visited = mutableMapOf<Point, Int>()
                    pq.add(start)

                    while (pq.isNotEmpty()) {
                        val cur = pq.poll()

                        if (cur.p == prize) return cur.cost

                        if (cur.counts.first > 100 || cur.counts.second > 100) {
                            continue
                        }

                        if (visited[cur.p]?.let { it <= cur.cost } == true) {
                            continue
                        }

                        visited[cur.p] = cur.cost
                        pq.add(Node(cur.p + A.p, cur.cost + A.cost, cur.counts.first + 1 to cur.counts.second))
                        pq.add(Node(cur.p + B.p, cur.cost + B.cost, cur.counts.first to cur.counts.second + 1))
                    }

                    return null
                }

                var sum = 0
                input.split(System.lineSeparator()).forEachIndexed { index, s ->
                    val mod = index % 4
                    when (mod) {
                        0 -> {
                            a = parseButtonInput(s)
                        }

                        1 -> {
                            b = parseButtonInput(s)
                        }

                        2 -> {
                            prize = parsePrizeInput(s)
                        }

                        3 -> {
                            solveOrNull(a, b, prize)?.let {
                                sum += it
                            }
                        }
                    }
                }
                writer.writeLine(sum)
            }
        }
    }

    private fun BufferedWriter.writeLine(v: Any) {
        this.write("$v")
        this.newLine()
    }
}
