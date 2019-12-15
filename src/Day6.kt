class Day6 : AbstractDay() {
    override fun run() = sequence<Any> {
        val input = readInputFile("Day6.txt")
            .split("\n")
            .filter { it.isNotBlank() }

        val isOrbitting = input.associate { Pair(it.split(")")[1], it.split(")")[0]) }
        var orbits = 0
        isOrbitting.forEach { (k, _) ->
            var key = k
            while (isOrbitting.containsKey(key)) {
                orbits += 1
                key = isOrbitting[key]!!
            }
        }
        yield(orbits) // Part 1

        // Make a map of neighbors
        val neighbors = HashMap<String, ArrayList<String>>()
        input.forEach {
            val a = it.split(")")[0]
            val b = it.split(")")[1]
            if (!neighbors.containsKey(a)) {
                neighbors[a] = ArrayList()
            }
            if (!neighbors.containsKey(b)) {
                neighbors[b] = ArrayList()
            }
            neighbors[a]!!.add(b)
            neighbors[b]!!.add(a)
        }
        val vertices = neighbors.keys.toMutableSet()
        val distances = HashMap<String, Int>()

        vertices.forEach {
            distances[it] = Int.MAX_VALUE
        }
        distances["YOU"] = 0

        // Run Dijkstra's algorithm
        while (vertices.isNotEmpty()) {
            val closest = distances.filter { (k, _) -> vertices.contains(k) }.minBy { (_, v) -> v }!!.key
            vertices.remove(closest)

            neighbors[closest]!!.filter { vertices.contains(it) }.forEach {
                val dist = distances[closest]!! + 1
                if (dist < distances[it]!!) {
                    distances[it] = dist
                }
            }
        }

        yield(distances["SAN"]!! - 2) // Part 2
    }
}