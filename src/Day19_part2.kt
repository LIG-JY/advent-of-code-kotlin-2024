import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day19_part2 {
    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val towels = reader.readLine().split(", ").toSet()
                reader.readLine()
                val patterns = reader.readLines()

                fun countComb(design: String): Long {
                    val dp = LongArray(design.length + 1)
                    dp[0] = 1

                    for (i in 1..design.length) {
                        for (towel in towels) {
                            if (i >= towel.length && dp[i - towel.length] > 0
                                && design.substring(startIndex = i - towel.length, endIndex = i) == towel
                            ) {
                                dp[i] += dp[i - towel.length]
                            }
                        }
                    }

                    return dp[design.length]
                }

                writer.writeLine(patterns.sumOf { countComb(it) })
            }
        }
    }

    private fun BufferedWriter.writeLine(v: Any) {
        this.write("$v")
        this.newLine()
    }
}
