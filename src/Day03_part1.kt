import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day03_part1 {

    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val input = reader.readText()
                val regex = Regex("""mul\(([0-9]{1,3}),([0-9]{1,3})\)""")
                var sum = 0
                regex.findAll(input).forEach {
                    val (first, second) = it.destructured
                    sum += first.toInt() * second.toInt()
                }
                writer.writeLine(sum)
            }
        }
    }

    private fun BufferedWriter.writeLine(v: Int) {
        this.write("$v")
        this.newLine()
    }
}
