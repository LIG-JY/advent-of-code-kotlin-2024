import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day05_part2_A {
    private data class Node(
        val value: Int, // unique identifier
        val nextNodeValues: MutableList<Int> = mutableListOf(),     // successors
        var inDegree: Int = 0   //  prev predecessors count
    )

    fun main() {
        BufferedReader(InputStreamReader(System.`in`)).use { reader ->
            BufferedWriter(OutputStreamWriter(System.out)).use { writer ->
                val allValues = mutableSetOf<Int>()
                val orderMap = mutableMapOf<Int, MutableSet<Int>>()
                while (true) {
                    val input = reader.readLine()

                    if (input.isEmpty()) {
                        break
                    }

                    val (k, v) = input.split("|").map { it.toInt() }
                    orderMap.getOrPut(k) { mutableSetOf() }.add(v)
                    allValues.add(k)
                    allValues.add(v)
                }

                val readOnlyOrderMap = orderMap.toMap()
                var result = 0
                while (true) {
                    val input = reader.readLine()

                    if (input.isNullOrBlank()) {
                        break
                    }

                    val list = input.split(",").map { it.toInt() }
                    if (!isCorrectOrder(list, readOnlyOrderMap)) {
                        result += solve(list, readOnlyOrderMap)
                    }
                }

                writer.writeLine(result)
            }
        }
    }

    private fun isCorrectOrder(list: List<Int>, readOnlyOrderMap: Map<Int, MutableSet<Int>>): Boolean {
        for (i in list.indices) {
            val current = list[i]
            for (j in i + 1 until list.size) {
                val orderList = readOnlyOrderMap[list[j]]
                if (orderList != null) {
                    if (orderList.contains(current)) {
                        return false
                    }
                }
            }
        }
        return true
    }

    private fun solve(list: List<Int>, readOnlyOrderMap: Map<Int, MutableSet<Int>>): Int {
        val nodeMap = list.associateWith { Node(it) }.toMutableMap()
        makeGraph(writableNodeMap = nodeMap, readOnlyOrderMap = readOnlyOrderMap)
        val result = topologicalSort(nodeMap = nodeMap)!!    // must be able to topologySearch

        return result[result.size / 2]
    }

    private fun makeGraph(writableNodeMap: MutableMap<Int, Node>, readOnlyOrderMap: Map<Int, MutableSet<Int>>) {
        // writableNodeMap == graph
        for ((nodeValue, node) in writableNodeMap) {
            readOnlyOrderMap[nodeValue]?.let { successors ->
                successors.forEach { sucval ->
                    if (writableNodeMap.containsKey(sucval)) {
                        node.nextNodeValues.add(sucval)
                        writableNodeMap[sucval]!!.inDegree++
                    }
                }
            }
        }
    }

    private fun topologicalSort(nodeMap: Map<Int, Node>): List<Int>? {
        val dq = ArrayDeque<Int>()
        val result = mutableListOf<Int>()

        for (node in nodeMap.values) {
            if (node.inDegree == 0) {
                result.add(node.value)
                dq.addLast(node.value)
            }
        }

        while (dq.isNotEmpty()) {
            val currentNode = nodeMap[dq.removeFirst()]!!
            for (nextValue in currentNode.nextNodeValues) {
                val nextNode = nodeMap[nextValue]!!
                nextNode.inDegree--
                if (nextNode.inDegree == 0) {
                    result.add(nextValue)
                    dq.addLast(nextValue)
                }
            }
        }

        return if (result.size == nodeMap.size) {
            result
        } else {
            null    // topology sort disable
        }
    }

    private fun BufferedWriter.writeLine(v: Int) {
        this.write("$v")
        this.newLine()
    }
}
