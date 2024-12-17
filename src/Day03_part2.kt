import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day03_part2 {
    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val input = reader.readText()
                val regex = Regex("""do\(\)|don't\(\)|mul\(([0-9]{1,3}),([0-9]{1,3})\)""")
                var sum = 0
                var isEnabled = true
                regex.findAll(input).forEach {
                    when {
                        it.value == "do()" -> isEnabled = true  // Enable future mul
                        it.value == "don't()" -> isEnabled = false  // Disable future mul
                        it.groups.size == 3 -> {  // Valid mul(X,Y) with two captured groups
                            if (isEnabled) {
                                val (x, y) = it.destructured
                                sum += x.toInt() * y.toInt()
                            }
                        }
                    }
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
