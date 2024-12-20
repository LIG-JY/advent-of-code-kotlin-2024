import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day07_part1 {
    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                var sum = 0L
                while (true) {
                    val input = reader.readLine() ?: break
                    val (answer, operandsStr) = input.split(":")
                    val operands = operandsStr.trim().split(" ").map { it.toLong() }
                    if (canOperate(answer.toLong(), operands)) {
                        sum += answer.toLong()
                    }
                }
                writer.writeLine(sum)
            }
        }
    }

    private fun canOperate(answer: Long, operands: List<Long>): Boolean {
        return findAnswerRecursive(answer, operands.first(), operands.drop(1))
    }

    private fun findAnswerRecursive(answer: Long, intermediateResult: Long, remainingOperands: List<Long>): Boolean {
        if (remainingOperands.isEmpty()) {
            // intermediate Result == final Result
            return intermediateResult == answer
        }

        val nextOperand = remainingOperands.first()
        val restOperands = remainingOperands.drop(1)

        return findAnswerRecursive(answer, intermediateResult + nextOperand, restOperands)
                || findAnswerRecursive(answer, intermediateResult * nextOperand, restOperands)
    }

    private fun BufferedWriter.writeLine(v: Long) {
        this.write("$v")
        this.newLine()
    }
}
