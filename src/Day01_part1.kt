import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import kotlin.math.abs

class Day01_part1 {
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

                firstList.sort()
                secondList.sort()

                var sum = 0
                for (i in 0 until INPUT_LINE_COUNT) {
                    sum += abs(firstList[i] - secondList[i])
                }

                writer.writeLine(sum)
            }
        }
    }

    private fun BufferedWriter.writeLine(v: Int) {
        this.write("$v\n")
    }
}
