import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import kotlin.math.abs

fun main() {
    BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
        val input = readInput("Day01")

        val firstList = mutableListOf<Int>()
        val secondList = mutableListOf<Int>()

        input.forEach {
            val (first, second) = it.split("\\s+".toRegex()).map { it.toInt() }
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

private fun BufferedWriter.writeLine(v: Int) {
    this.write("$v\n")
}
