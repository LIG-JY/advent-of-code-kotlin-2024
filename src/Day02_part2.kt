import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import kotlin.math.abs

class Day02_part2 {

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
                    if (solve(input)) {
                        ++count
                    }
                }
                writer.writeLine(count)
            }
        }
    }

    private fun solve(list: List<Int>): Boolean {
        assert(list.size > 1) { "Input list must contain at least two elements" }
        var prevState = Direction.INIT

        for (i in 1 until list.size) {
            val diff = list[i] - list[i - 1]
            if (!isStepValid(diff)) {
                return checkWithSkip(list, i)
            }

            when {
                diff > 0 -> {
                    if (prevState == Direction.DECREASE) {
                        return checkWithSkip(list, i)
                    }
                    prevState = Direction.INCREASE
                }

                diff < 0 -> {
                    if (prevState == Direction.INCREASE) {
                        return checkWithSkip(list, i)
                    }
                    prevState = Direction.DECREASE
                }
            }
        }
        return true
    }

    private fun checkWithSkip(list: List<Int>, skipIndex: Int): Boolean {
        return check(list, skipIndex) || check(list, skipIndex - 1)
    }

    private fun check(list: List<Int>, skipIndex: Int = -1): Boolean {
        var prevState = Direction.INIT
        var previousValue: Int? = null

        for (i in list.indices) {
            if (i == skipIndex) continue
            val currentValue = list[i]

            if (previousValue != null) {
                val diff = currentValue - previousValue
                if (!isStepValid(diff)) return false

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
            previousValue = currentValue
        }
        return true
    }

    private fun isStepValid(diff: Int): Boolean = abs(diff) in LOWER_BOUND..UPPER_BOUND

    private fun BufferedWriter.writeLine(v: Int) {
        this.write("$v")
        this.newLine()
    }
}
