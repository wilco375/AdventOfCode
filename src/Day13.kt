fun main() {
    val program1 = readInputFile("Day13.txt")
        .split(",")
        .map { it.toLong() } // Convert to list of integers

    val computer1 = Computer(program1).run()
    val output1 = computer1.getOutput().filterIndexed { i, v -> i % 3 == 2 && v == 2L }.size

    println("13.1:")
    println(output1)

    val program2 = program1.toMutableList()
    program2[0] = 2

    val computer2 = Computer(program2)
    val screen = HashMap<Pair<Long, Long>, Long>()

    fun updateScreen() {
        val output = computer2.getOutput()
        for (i in output.indices step 3) {
            screen[Pair(output[i], output[i + 1])] = output[i + 2]
        }
        computer2.clearOutput()
    }

    fun printScreen() {
        for (y in (0L..(screen.keys.map { it.second }.max() ?: 0L))) {
            for (x in (0L..(screen.keys.map { it.first }.max() ?: 0L))) {
                val tile = screen.getOrDefault(Pair(x, y), 0)
                when (tile) {
                    0L -> print(" ")
                    1L -> print("█")
                    2L -> print("▒")
                    3L -> print("▁")
                    4L -> print("●")
                }
            }
            println()
        }
    }

    try {
        while (true) {
            computer2.runForInput()
            //printScreen()

            updateScreen()

            val paddle = screen.filterValues { it == 3L }.keys.first()
            val ball = screen.filterValues { it == 4L }.keys.first()
            when {
                paddle.first > ball.first -> {
                    // Move paddle left to catch ball
                    computer2.addInput(-1)
                }
                paddle.first < ball.first -> {
                    // Move paddle right to catch ball
                    computer2.addInput(1)
                }
                else -> {
                    // Ball directly above paddle
                    computer2.addInput(0)
                }
            }

            computer2.clearOutput()
        }
    } catch (e: IllegalStateException) {
        // Game is finished
        updateScreen()

        println("13.2:")
        println(screen[Pair(-1L, 0L)])
    }
}
