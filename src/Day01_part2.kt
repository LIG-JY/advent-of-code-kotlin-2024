import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day01_part2 {
    companion object {
        const val INPUT_LINE_COUNT = 1000
    }

    fun solve() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val firstList = mutableListOf<Int>()
                val secondList = mutableListOf<Int>()

                repeat(INPUT_LINE_COUNT) {
                    val (first, second) = reader.readLine().split("\\s+".toRegex()).map { it.toInt() }
                    firstList.add(first)
                    secondList.add(second)
                }

                // Create a frequency map for secondList
                val countMap = secondList.groupingBy { it }.eachCount()

                // Calculate the result by summing up the weighted values
                val result = firstList.sumOf { countMap.getOrDefault(it, 0) * it }
                writer.writeLine(result)
            }
        }
    }

    private fun BufferedWriter.writeLine(v: Int) {
        this.write("$v\n")
    }
}
