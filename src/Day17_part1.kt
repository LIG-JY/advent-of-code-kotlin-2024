import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day17_part1 {
    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val input = reader.readLines()

                val registers = mutableMapOf(
                    'A' to input[0].split(" ").last().toLong(),
                    'B' to input[1].split(" ").last().toLong(),
                    'C' to input[2].split(" ").last().toLong(),
                )

                val instructions = input[4].split(" ").last().split(",").map { it.toLong() }
                var pointer = 0

                fun getComboOperand(operand: Long): Long {
                    assert(operand in 0 until 7)
                    when (operand) {
                        0L -> return 0L
                        1L -> return 1L
                        2L -> return 2L
                        3L -> return 3L
                        4L -> return registers['A']!!
                        5L -> return registers['B']!!
                        6L -> return registers['C']!!
                    }
                    error("invalid operand: $operand")
                }

                val outputs = mutableListOf<Long>()

                fun run(pointer: Int, opcode: Long, operand: Long): Int {
                    assert(opcode in 0 until 8)
                    when (opcode) {
                        0L -> {
                            val numerator = registers['A']!!
                            var denominator = 1L
                            repeat(getComboOperand(operand).toInt()) {
                                denominator *= 2
                            }
                            registers['A'] = numerator / denominator
                        }

                        1L -> {
                            registers['B'] = operand.xor(registers['B']!!)
                        }

                        2L -> {
                            registers['B'] = getComboOperand(operand) % 8
                        }

                        3L -> {
                            if (registers['A']!! != 0L) {
                                return operand.toInt()
                            }
                        }

                        4L -> {
                            registers['B'] = registers['B']!!.xor(registers['C']!!)
                        }

                        5L -> {
                            outputs.add(getComboOperand(operand) % 8)
                        }

                        6L -> {
                            val numerator = registers['A']!!
                            var denominator = 1L
                            repeat(getComboOperand(operand).toInt()) {
                                denominator *= 2
                            }
                            registers['B'] = numerator / denominator
                        }

                        7L -> {
                            val numerator = registers['A']!!
                            var denominator = 1L
                            repeat(getComboOperand(operand).toInt()) {
                                denominator *= 2
                            }
                            registers['C'] = numerator / denominator
                        }
                    }
                    return pointer + 2
                }

                while (pointer < instructions.size) {
                    pointer = run(pointer, instructions[pointer], instructions[pointer + 1])
                }

                writer.writeLine(outputs.joinToString(","))
            }
        }
    }

    private fun BufferedWriter.writeLine(v: Any) {
        this.write("$v")
        this.newLine()
    }
}
