fun main() {
    fun run(initialColor: Long): HashMap<Pair<Int, Int>, Long> {
        val program = readIntcode("Day11.txt")

        val computer1 = Computer(program)

        val grid = HashMap<Pair<Int, Int>, Long>() // Maps coordinates to a color
        var currentCoord = Pair(0, 0)
        var currentDirection = 0
        var initial = true

        try {
            while (true) {
                // Get the color of the current panel or black by default
                val currentColor = grid.getOrDefault(currentCoord, if (initial) initialColor else 0L)
                computer1.addInput(currentColor)
                initial = false

                val colorToPaint = computer1.runForOutput()
                val direction = computer1.runForOutput()

                grid[currentCoord] = colorToPaint
                currentDirection += when (direction) {
                    0L -> 270
                    1L -> 90
                    else -> 0
                }
                currentDirection %= 360
                when (currentDirection) {
                    0 -> currentCoord = Pair(currentCoord.first, currentCoord.second + 1)
                    90 -> currentCoord = Pair(currentCoord.first + 1, currentCoord.second)
                    180 -> currentCoord = Pair(currentCoord.first, currentCoord.second - 1)
                    270 -> currentCoord = Pair(currentCoord.first - 1, currentCoord.second)
                }
            }
        } catch (e: IllegalStateException) {
            // Program is finished
            return grid
        }
    }

    println("11.1:")
    val output1 = run(0).keys.size
    println(output1)

    println("11.2:")
    val output2 = run(1)
    val ys = output2.keys.map { it.second }
    for (y in (ys.max()!! downTo ys.min()!!)) {
        val xs = output2.keys.map { it.first }
        for (x in (xs.min()!! .. xs.max()!!)) {
            print(if (output2.getOrDefault(Pair(x, y), 0L) == 0L) " " else "█")
        }
        println()
    }
}
