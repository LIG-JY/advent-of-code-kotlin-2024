import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import kotlin.math.pow

class Day07_par2 {
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
        val placeValues = operands.map { op ->
            (10.0.pow(op.toString().length.toDouble())).toLong()
        }.toLongArray()

        return findAnswerRecursive(
            answer = answer,
            intermediateResult = operands.first(),
            i = 1,
            operands = operands,
            placeValues = placeValues
        )
    }

    private fun findAnswerRecursive(
        answer: Long,
        intermediateResult: Long,
        i: Int, // next index
        operands: List<Long>,
        placeValues: LongArray
    ): Boolean {
        assert(operands.size == placeValues.size)
        if (i >= operands.size) {
            // intermediate Result == final Result
            return intermediateResult == answer
        }

        if (findAnswerRecursive(answer, intermediateResult + operands[i], i + 1, operands, placeValues)) {
            return true
        }
        if (findAnswerRecursive(answer, intermediateResult * operands[i], i + 1, operands, placeValues)) {
            return true
        }
        if (findAnswerRecursive(answer, intermediateResult * placeValues[i] + operands[i], i + 1, operands, placeValues)
        ) {
            return true
        }
        return false
    }

    private fun BufferedWriter.writeLine(v: Long) {
        this.write("$v")
        this.newLine()
    }
}
