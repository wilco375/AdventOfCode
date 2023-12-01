import kotlin.math.*

class Day10 : AbstractDay() {    
    override fun run() = sequence<Any> {
        val asteroids = ArrayList<Coord>()
        readInputFile("Day10.txt")
            .split("\n")
            .forEachIndexed { y, it ->
                it.forEachIndexed { x, asteroid ->
                    if (asteroid == '#') {
                        asteroids.add(Coord(x, y))
                    }
                }
            }

        var maxDetected = 0
        var base: Coord? = null // Asteroid with Instant Monitoring Station
        asteroids.forEach { a ->
            val angles = HashSet<Double>()
            asteroids.forEach { b ->
                if (a != b) {
                    angles.add(radius(a, b))
                }
            }
            if (angles.size > maxDetected) {
                maxDetected = angles.size
                base = a
            }
        }

        yield(maxDetected) // Part 1

        var vaporized = 0 // Number of vaporized asteroids
        val anglesToAsteroids = HashMap<Double, MutableList<Coord>>()
        asteroids.forEach {
            if (it != base) {
                val radius = radius(base!!, it)
                anglesToAsteroids.putIfAbsent(radius, ArrayList())
                anglesToAsteroids[radius]!!.add(it)
            }
        }
        var twoHundredthAsteroid: Coord? = null
        while (vaporized <= 200) {
            for (angle in anglesToAsteroids.keys.sorted()) {
                val it = anglesToAsteroids[angle]!!
                val closest = it.minBy { distance(base!!, it) }
                if (closest != null) {
                    vaporized += 1
                    it.remove(closest)
                    if (vaporized == 200) {
                        twoHundredthAsteroid = closest
                        break
                    }
                }
            }
        }
        yield(twoHundredthAsteroid!!.x * 100 + twoHundredthAsteroid.y) // Part 2
    }

    private fun radius(from: Coord, to: Coord): Double {
        val x = to.x - from.x
        val y = to.y - from.y
        var angle = atan2(y.toDouble(), x.toDouble())
        angle += 0.5 * PI // Starting at north
        return if (angle >= 0) angle else angle + 2 * PI
    }

    private fun distance(from: Coord, to: Coord): Double {
        val deltaX = (from.x - to.x).toDouble()
        val deltaY = (from.y - to.y).toDouble()
        return abs(sqrt(deltaX.pow(2) + deltaY.pow(2)))
    }
}