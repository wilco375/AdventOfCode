fun main() {
    val program = readInputFile("Day5.txt")
        .split(",")
        .map { it.toLong() } // Convert to list of integers

    val computer1 = Computer(program, listOf(1))
    computer1.run()
    val output1 = computer1.getOutput().last()

    println("5.1:")
    println(output1)

    val computer2 = Computer(program, listOf(5))
    computer2.run()
    val output2 = computer2.getOutput().last()

    println("5.2:")
    println(output2)
}
