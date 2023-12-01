class Day9 : AbstractDay() {
    override fun run() = sequence<Any> {
        val program = readIntcode("Day9.txt")

        val output1 = Computer(program, listOf(1)).runForOutput()
        yield(output1) // Part 1

        val output2 = Computer(program, listOf(2)).runForOutput()
        yield(output2) // Part 2
    }
}