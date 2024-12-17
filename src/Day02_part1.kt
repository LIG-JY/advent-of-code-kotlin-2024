import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import kotlin.math.abs

class Day02_part1 {
    companion object {
        const val INPUT_LINE_COUNT = 1000
        const val LOWER_BOUND = 1
        const val UPPER_BOUND = 3
    }
    
    enum class Direction {
        INCREASE,
        DECREASE,
        INIT
    }

    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                var count = 0
                repeat(INPUT_LINE_COUNT) {
                    val input = reader.readLine().split("\\s+".toRegex()).map { it.toInt() }
                    if (check(input)) {
                        ++count
                    }
                }
                writer.writeLine(count)
            }
        }
    }

    private fun check(list: List<Int>): Boolean {
        assert(list.size > 1)
        var prevState = Direction.INIT
        for (i in 1 until list.size) {
            val diff = list[i] - list[i - 1]
            if (abs(diff) > UPPER_BOUND || abs(diff) < LOWER_BOUND) {
                return false
            }
            when {
                diff > 0 -> {
                    if (prevState == Direction.DECREASE) return false
                    prevState = Direction.INCREASE
                }

                diff < 0 -> {
                    if (prevState == Direction.INCREASE) return false
                    prevState = Direction.DECREASE
                }
            }
        }
        return true
    }

    private fun BufferedWriter.writeLine(v: Int) {
        this.write("$v")
        this.newLine()
    }
}
