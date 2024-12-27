import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day11_part1 {
    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val input = reader.readLine().split(" ")

                fun String.removePrefixZero(): String {
                    val transformed = this.dropWhile { it == '0' }
                    return transformed.ifEmpty { "0" }
                }

                fun solveRecursive(depth: Int, l: List<String>): List<String> {
                    if (depth == 25) return l

                    val nl = mutableListOf<String>()
                    for (e in l) {
                        if (e == "0") {
                            nl.add("1")
                        } else if (e.length % 2 == 0) {
                            nl.add(e.substring(0, e.length / 2))
                            val right = e.substring(e.length / 2)
                            if (right.startsWith("0")) {
                                nl.add(right.removePrefixZero())
                            } else {
                                nl.add(right)
                            }
                        } else {
                            val num = e.toLong()
                            nl.add((num * 2024).toString())
                        }
                    }
                    return solveRecursive(depth + 1, nl)
                }

                fun solve(l: List<String>): List<String> {
                    return solveRecursive(0, l)
                }

                val res = solve(input)
                writer.writeLine(res.size)
            }
        }
    }

    private fun BufferedWriter.writeLine(v: Any) {
        this.write("$v")
        this.newLine()
    }
}