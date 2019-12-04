import kotlin.math.absoluteValue

fun main() {
    val matrix = HashMap<Pair<Int, Int>, Pair<Int, Int>>() // Coords mapped to number of lines and distance

    readInputFile("Day3.txt")
        .split("\n")
        .filter { it.isNotBlank() }
        .forEach { path ->
            var x = 0
            var y = 0
            var steps = 0
            val pathCoords = HashSet<Pair<Int, Int>>()
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
                    val coord = Pair(x, y)
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
        .map { it.key.first.absoluteValue + it.key.second.absoluteValue } // Calculate manhattan distances from (0, 0)
        .min() // Get the closest intersection
    println("3.1:")
    println(closestIntersection)

    val leastStepsIntersection = matrix
        .filterValues { it.first > 1 } // Get intersections
        .map { it.value.second } // Get number  of steps to intersection
        .min() // Get the intersection with the least steps
    println("3.2:")
    println(leastStepsIntersection)
}
