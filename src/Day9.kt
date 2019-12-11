fun main() {
    val program = readInputFile("Day9.txt")
        .split(",")
        .map { it.toLong() } // Convert to list of integers

    val output1 = Computer(program, listOf(1)).runForOutput()
    println("9.1:")
    println(output1)

    val output2 = Computer(program, listOf(2)).runForOutput()
    println("9.2:")
    println(output2)
}