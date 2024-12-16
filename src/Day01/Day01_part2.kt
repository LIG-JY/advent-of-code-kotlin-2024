import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

const val INPUT_LINE_COUNT = 1000

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

        // Create a frequency map for secondList
        val countMap = secondList.groupingBy { it }.eachCount()

        // Calculate the result by summing up the weighted values
        val result = firstList.sumOf { countMap.getOrDefault(it, 0) * it }
        writer.writeLine(result)
    }
}

private fun BufferedWriter.writeLine(v: Int) {
    this.write("$v\n")
}
