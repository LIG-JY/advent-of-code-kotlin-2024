import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day04_part1 {
    val dy = listOf(0, 1, 1, 1, 0, -1, -1, -1)
    val dx = listOf(1, 1, 0, -1, -1, -1, 0, 1)
    val chars = listOf('M', 'A', 'S')

    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val inputs = reader.readText().split(System.lineSeparator())
                val maxY = inputs.size - 1
                val maxX = inputs[0].length - 1
                var count = 0

                for (y in 0..maxY) {
                    for (x in 0..maxX) {
                        val c = inputs[y][x]
                        if (c == 'X') {
                            for (i in dy.indices) {
                                var hasSuccess = true
                                for (f in 1..chars.size) {
                                    val ny = y + f * dy[i]
                                    val nx = x + f * dx[i]
                                    if (ny > maxY || ny < 0 || nx > maxX || nx < 0) {
                                        hasSuccess = false
                                        break
                                    }
                                    if (chars[f - 1] != inputs[ny][nx]) {
                                        hasSuccess = false
                                        break
                                    }
                                }
                                if (hasSuccess) {
                                    ++count
                                }
                            }
                        }
                    }
                }
                writer.writeLine(count)
            }
        }
    }

    private fun BufferedWriter.writeLine(v: Int) {
        this.write("$v")
        this.newLine()
    }
}
