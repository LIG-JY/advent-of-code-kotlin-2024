import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.math.BigInteger

class Day13_part2 {
    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val input = reader.readText()

                data class Point(val x: BigInteger, val y: BigInteger)

                var a = Point(BigInteger.ZERO, BigInteger.ZERO)
                var b = Point(BigInteger.ZERO, BigInteger.ZERO)
                var prize = Point(BigInteger.ZERO, BigInteger.ZERO)

                fun parseButtonInput(input: String): Point {
                    val (x, y) = input.split("X+", "Y+", ",")
                        .filter { it.trim().toBigIntegerOrNull() != null }.map { it.toBigInteger() }

                    return Point(x, y)
                }

                fun parsePrizeInput(input: String): Point {
                    val (x, y) = input.split("X=", "Y=", ",")
                        .filter { it.trim().toBigIntegerOrNull() != null }
                        .map { it.toBigInteger() + BigInteger("10000000000000") }

                    return Point(x, y)
                }

                fun solveOrNull(a: Point, b: Point, prize: Point): BigInteger? {
                    var det = a.x * b.y - a.y * b.x
                    val detSign = det.signum().toBigInteger()
                    det *= detSign  // ensure det > 0

                    val na = (prize.x * b.y - prize.y * b.x) * detSign  // numerator
                    val nb = (a.x * prize.y - a.y * prize.x) * detSign  // numerator
                    if (na.mod(det) != BigInteger.ZERO) return null // solution must be integer
                    if (nb.mod(det) != BigInteger.ZERO) return null // solution must be integer
                    if (na > BigInteger.ZERO && nb > BigInteger.ZERO) { // solution must be positive
                        val sa = (na.div(det))
                        val sb = (nb.div(det))

                        return BigInteger("3").multiply(sa).add(sb)
                    }
                    return null
                }

                var sum = BigInteger.ZERO
                input.split(System.lineSeparator()).forEachIndexed { index, s ->
                    val mod = index % 4
                    when (mod) {
                        0 -> {
                            a = parseButtonInput(s)
                        }

                        1 -> {
                            b = parseButtonInput(s)
                        }

                        2 -> {
                            prize = parsePrizeInput(s)
                        }

                        3 -> {
                            solveOrNull(a, b, prize)?.let {
                                sum += it
                            }
                        }
                    }
                }
                writer.writeLine(sum)
            }
        }
    }

    private fun BufferedWriter.writeLine(v: Any) {
        this.write("$v")
        this.newLine()
    }
}
