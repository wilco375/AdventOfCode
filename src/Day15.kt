class Day15 : AbstractDay() {
    companion object {
        const val NORTH = 1
        const val SOUTH = 2
        const val WEST = 3
        const val EAST = 4

        const val UNKNOWN = -1L
        const val WALL = 0L
        const val AIR = 1L
        const val OXYGEN = 2L
    }

    private val map = HashMap<Coord, Long>()

    override fun run() = sequence<Any> {
        val program = readIntcode("Day15.txt")
        val computer = Computer(program)

        // Recursively explore the entire map
        explore(computer, Coord(0, 0))

        // Map should be explored now so we can start finding paths
        yield(pathfind()) // Part 1

        yield(oxygenFill()) // Part 2
    }

    /**
     * Explores the map as far as it can
     */
    private fun explore(state: Computer, stateCoord: Coord) {
        for (direction in (1..4)) {
            val coord = stateCoord.move(direction)
            if (map.getOrDefault(coord, UNKNOWN) == UNKNOWN) {
                // Try to go north
                val computer = state.clone()
                computer.addInput(direction.toLong())
                val block = computer.runForOutput()
                map[coord] = block
                if (block == AIR) {
                    explore(computer, coord)
                }
            }
        }
    }

    /**
     * Finds a path to the oxygen in a filled map using Dijksta's algorithm
     *
     * @return distance to oxygen from start
     */
    private fun pathfind(): Int {
        val oxygen = map.filterValues { it == OXYGEN }.keys.first()
        val vertices = map.filterValues { it == AIR || it == OXYGEN }.keys.toMutableList()
        val distances = HashMap<Coord, Int>()
        distances[Coord(0, 0)] = 0
        loop@ while (vertices.isNotEmpty()) {
            val vertex = vertices.minBy { distances.getOrDefault(it, Integer.MAX_VALUE) }!!
            vertices.remove(vertex)

            for (direction in (1..4)) {
                val neighbor = vertex.move(direction)

                if (neighbor in vertices) {
                    val dist = distances[vertex]!! + 1

                    if (dist < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                        distances[neighbor] = dist
                    }

                    if (neighbor == oxygen) {
                        break@loop
                    }
                }
            }
        }

        return distances[oxygen]!!
    }

    /**
     * Use Dijkstra another time to find the longest distance from the oxygen block
     *
     * @return longest distance from the oxygen block
     */
    private fun oxygenFill(): Int {
        val oxygen = map.filterValues { it == OXYGEN }.keys.first()
        val vertices = map.filterValues { it == AIR || it == OXYGEN }.keys.toMutableList()
        val distances = HashMap<Coord, Int>()
        distances[oxygen] = 0
        loop@ while (vertices.isNotEmpty()) {
            val vertex = vertices.minBy { distances.getOrDefault(it, Integer.MAX_VALUE) }!!
            vertices.remove(vertex)

            for (direction in (1..4)) {
                val neighbor = vertex.move(direction)

                if (neighbor in vertices) {
                    val dist = distances[vertex]!! + 1

                    if (dist < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                        distances[neighbor] = dist
                    }
                }
            }
        }

        return distances.filterValues { it != Integer.MAX_VALUE }.values.max()!!
    }

    private fun Coord.move(direction: Int): Coord {
        var x = this.x
        var y = this.y
        when (direction) {
            NORTH -> y += 1
            SOUTH -> y -= 1
            EAST -> x += 1
            WEST -> x -= 1
        }
        return Coord(x, y)
    }
}