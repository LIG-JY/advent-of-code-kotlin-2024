import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day19_part1 {
    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val towels = reader.readLine().split(", ").toSet()
                reader.readLine()
                val patterns = reader.readLines()

                fun isAvailable(design: String): Boolean {
                    val dp = BooleanArray(design.length + 1)
                    dp[0] = true

                    for (i in 1..design.length) {
                        for (towel in towels) {
                            if (i >= towel.length && dp[i - towel.length]
                                && design.substring(startIndex = i - towel.length, endIndex = i) == towel
                            ) {
                                dp[i] = true
                                break
                            }
                        }
                    }

                    return dp[design.length]
                }

                var count = 0
                for (pattern in patterns) {
                    if (isAvailable(pattern)) {
                        writer.writeLine(pattern)
                        ++count
                    }
                }
                writer.writeLine(count)
            }
        }
    }

    private fun BufferedWriter.writeLine(v: Any) {
        this.write("$v")
        this.newLine()
    }
}
