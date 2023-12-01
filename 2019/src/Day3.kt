import kotlin.math.absoluteValue

class Day3 : AbstractDay() {
    override fun run() = sequence<Any> {
        val matrix = HashMap<Coord, Pair<Int, Int>>() // Coords mapped to number of lines and distance

        readInputFile("Day3.txt")
            .split("\n")
            .filter { it.isNotBlank() }
            .forEach { path ->
                var x = 0
                var y = 0
                var steps = 0
                val pathCoords = HashSet<Coord>()
                path.split(",").forEach { instr ->
                    val direction = instr.substring(0, 1)
                    val length = instr.substring(1).toInt()
                    (1..length).forEach { _ ->
                        steps += 1
                        when (direction) {
                            "L" -> x -= 1
                            "R" -> x += 1
                            "U" -> y += 1
                            "D" -> y -= 1
                        }
                        val coord = Coord(x, y)
                        if (matrix.containsKey(coord) && !pathCoords.contains(coord)) {
                            val value = matrix[coord]!!
                            matrix[coord] = Pair(value.first + 1, value.second + steps)
                            pathCoords.add(coord)
                        } else {
                            matrix[coord] = Pair(1, steps)
                            pathCoords.add(coord)
                        }
                    }
                }
            }

        val closestIntersection = matrix
            .filterValues { it.first > 1 } // Get intersections
            .map { it.key.x.absoluteValue + it.key.y.absoluteValue } // Calculate manhattan distances from (0, 0)
            .min() // Get the closest intersection
        yield(closestIntersection!!) // Part 1

        val leastStepsIntersection = matrix
            .filterValues { it.first > 1 } // Get intersections
            .map { it.value.second } // Get number  of steps to intersection
            .min() // Get the intersection with the least steps
        yield(leastStepsIntersection!!) // Part 2
    }
}