class Day2 : AbstractDay() {
    override fun run() = sequence<Any> {
        val input = readIntcode("Day2.txt")
            .toMutableList()

        input[1] = 12
        input[2] = 2

        val computer = Computer(input, listOf()).run()

        yield(computer.getMemory()[0]) // Part 1

        val wantedOutput = 19690720L
        // Since the number of possibilities are relatively small, just brute force:
        (0L..99).forEach outer@{ i ->
            (0L..99).forEach { j ->
                try {
                    input[1] = i
                    input[2] = j
                    val computer = Computer(input, listOf()).run()
                    if (computer.getMemory()[0] == wantedOutput) {
                        yield(100 * i + j) // Part 2
                        return@outer
                    }
                } catch (e: RuntimeException) {
                    // Program ran into an unknown opcode, just try the next input combination
                }
            }
        }
    }
}
