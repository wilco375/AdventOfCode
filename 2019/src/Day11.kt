class Day11 : AbstractDay() {
    override fun run() = sequence<Any> {
        val output1 = run(0).keys.size
        yield(output1) // Part 1

        val output2 = run(1)
        var formattedOutput = ""
        val ys = output2.keys.map { it.y }
        for (y in (ys.max()!! downTo ys.min()!!)) {
            val xs = output2.keys.map { it.x }
            for (x in (xs.min()!!..xs.max()!!)) {
                formattedOutput += (if (output2.getOrDefault(Coord(x, y), 0L) == 0L) " " else "█")
            }
            formattedOutput += "\n"
        }
        yield(formattedOutput) // Part 2
    }

    private fun run(initialColor: Long): HashMap<Coord, Long> {
        val program = readIntcode("Day11.txt")

        val computer1 = Computer(program)

        val grid = HashMap<Coord, Long>() // Maps coordinates to a color
        var currentCoord = Coord(0, 0)
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
                    0 -> currentCoord = Coord(currentCoord.x, currentCoord.y + 1)
                    90 -> currentCoord = Coord(currentCoord.x + 1, currentCoord.y)
                    180 -> currentCoord = Coord(currentCoord.x, currentCoord.y - 1)
                    270 -> currentCoord = Coord(currentCoord.x - 1, currentCoord.y)
                }
            }
        } catch (e: IllegalStateException) {
            // Program is finished
            return grid
        }
    }
}
