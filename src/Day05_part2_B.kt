import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class Day05_part2_B {
    data class Node(
        val value: Int, // unique identifier
        val nextNodeValues: MutableList<Int> = mutableListOf(),     // successors
    )

    enum class State { UNVISITED, VISITING, VISITED }

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
        val result = topologicalSortDfs(nodeMap = nodeMap)!!    // always can topological sort

        return result[result.size / 2]
    }

    private fun makeGraph(writableNodeMap: MutableMap<Int, Node>, readOnlyOrderMap: Map<Int, MutableSet<Int>>) {
        // writableNodeMap == graph
        for ((nodeValue, node) in writableNodeMap) {
            readOnlyOrderMap[nodeValue]?.let { successors ->
                successors.forEach { sucval ->
                    if (writableNodeMap.containsKey(sucval)) {
                        node.nextNodeValues.add(sucval)
                    }
                }
            }
        }
    }

    private fun topologicalSortDfs(nodeMap: Map<Int, Node>): List<Int>? {
        val reversedResult = ArrayDeque<Int>()
        val visitStates = nodeMap.keys.associateWith { State.UNVISITED }.toMutableMap()

        for ((key, node) in nodeMap) {
            if (visitStates[key] == State.UNVISITED) {
                if (!dfs(current = key, nodeMap = nodeMap, visitStates = visitStates, stack = reversedResult)) {
                    return null // cylce
                }
            }
        }

        return reversedResult
    }

    private fun dfs(
        current: Int,
        nodeMap: Map<Int, Node>,
        visitStates: MutableMap<Int, State>,
        stack: ArrayDeque<Int>
    ): Boolean {
        visitStates[current] = State.VISITING

        val currentNode = nodeMap[current]!!

        for (nextValue in currentNode.nextNodeValues) {
            when (visitStates[nextValue]!!) {
                State.VISITED -> {} // do nothing

                State.UNVISITED -> {
                    if (!dfs(nextValue, nodeMap, visitStates, stack)) {
                        return false
                    }
                }

                State.VISITING -> {
                    return false    // cycle
                }
            }
        }

        visitStates[current] = State.VISITED
        stack.addLast(current)
        return true
    }

    private fun BufferedWriter.writeLine(v: Int) {
        this.write("$v")
        this.newLine()
    }
}
