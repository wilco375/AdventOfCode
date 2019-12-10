import com.marcinmoskala.math.pow
import kotlin.math.*

fun main() {
    val asteroids = ArrayList<Pair<Int, Int>>()
    readInputFile("Day10.txt")
        .split("\n")
        .forEachIndexed { y, it ->
            it.forEachIndexed { x, asteroid ->
                if (asteroid == '#') {
                    asteroids.add(Pair(x, y))
                }
            }
        }

    var maxDetected = 0
    var base: Pair<Int, Int>? = null // Asteroid with Instant Monitoring Station
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

    println("10.1:")
    println(maxDetected)

    var vaporized = 0 // Number of vaporized asteroids
    val anglesToAsteroids = HashMap<Double, MutableList<Pair<Int, Int>>>()
    asteroids.forEach {
        if (it != base) {
            val radius = radius(base!!, it)
            anglesToAsteroids.putIfAbsent(radius, ArrayList())
            anglesToAsteroids[radius]!!.add(it)
        }
    }
    var twoHundredthAsteroid: Pair<Int, Int>? = null
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
    println("10.2:")
    println(twoHundredthAsteroid!!.first * 100 + twoHundredthAsteroid.second)
}

fun radius(from: Pair<Int, Int>, to: Pair<Int, Int>): Double {
    val x = to.first - from.first
    val y = to.second - from.second
    var angle = atan2(y.toDouble(), x.toDouble())
    angle += 0.5 * PI // Starting at north
    return if (angle >= 0) angle else angle + 2 * PI
}

fun distance(from: Pair<Int, Int>, to: Pair<Int, Int>): Double {
    val deltaX = (from.first - to.first).toDouble()
    val deltaY = (from.second - to.second).toDouble()
    return abs(sqrt(deltaX.pow(2) + deltaY.pow(2)))
}