class Day5 : AbstractDay() {
    override fun run() = sequence<Any> {
        val program = readIntcode("Day5.txt")

        val computer1 = Computer(program, listOf(1)).run()
        val output1 = computer1.getOutput().last()

        yield(output1) // Part 1

        val computer2 = Computer(program, listOf(5)).run()
        val output2 = computer2.getOutput().last()

        yield(output2) // Part 2
    }
}
