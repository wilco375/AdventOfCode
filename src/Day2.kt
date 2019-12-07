import java.lang.RuntimeException

fun main() {
    val input = readInputFile("Day2.txt")
        .split(",")
        .map { it.toInt() } // Convert to list of integers
        .toMutableList()

    input[1] = 12
    input[2] = 2

    val computer = Computer(input, listOf()).run()

    println("2.1:")
    println(computer.getMemory()[0])

    val wantedOutput = 19690720
    // Since the number of possibilities are relatively small, just brute force:
    (0..99).forEach outer@ { i ->
        (0..99).forEach { j ->
            try {
                input[1] = i
                input[2] = j
                val computer = Computer(input, listOf()).run()
                if (computer.getMemory()[0] == wantedOutput) {
                    println("2.2:")
                    println(100 * i + j)
                    return@outer
                }
            } catch (e: RuntimeException) {
                // Program ran into an unknown opcode, just try the next input combination
            }
        }
    }
}
