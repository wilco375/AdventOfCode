import java.lang.RuntimeException

fun main() {
    val input = readInputFile("Day2.txt")
        .split(",")
        .map { it.toInt() } // Convert to list of integers
        .toMutableList()

    input[1] = 12
    input[2] = 2

    val output = runProgram(input, 0)

    println("2.1:")
    println(output[0])

    val wantedOutput = 19690720
    // Since the number of possibilities are relatively small, just brute force:
    (0..99).forEach outer@ { i ->
        (0..99).forEach { j ->
            try {
                input[1] = i
                input[2] = j
                val output = runProgram(input, 0)
                if (output[0] == wantedOutput) {
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

fun runProgram(mem: List<Int>, index: Int): List<Int> {
    var newMem = mem.toMutableList()
    when (mem[index]) {
        99 -> {
            // Program is finished
            return mem
        }
        1 -> {
            // Add
            newMem[mem[index + 3]] = mem[mem[index + 1]] + mem[mem[index + 2]]
            newMem = runProgram(newMem, index + 4).toMutableList()
        }
        2 -> {
            // Multiply
            newMem[mem[index + 3]] = mem[mem[index + 1]] * mem[mem[index + 2]]
            newMem = runProgram(newMem, index + 4).toMutableList()
        }
        else -> {
            throw RuntimeException("Unknown opcode ${mem[index]}")
        }
    }
    return newMem
}
