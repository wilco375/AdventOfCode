import kotlin.math.absoluteValue

class Day12 : AbstractDay() {
    override fun run() = sequence<Any> {
        val planets = readInputFile("Day12.txt")
            .lines()
            .map { Planet.fromText(it) }

        repeat(1000) {
            simulateStep(planets)
        }

        yield(planets.sumBy(Planet::getEnergy)) // Part 1
    }

    private fun simulateStep(planets: List<Planet>) {
        // Calculate gravity
        for (planet in planets) {
            for (otherPlanet in (planets - planet)) {
                planet.velX += calcVelChange(planet.x, otherPlanet.x)
                planet.velY += calcVelChange(planet.y, otherPlanet.y)
                planet.velZ += calcVelChange(planet.z, otherPlanet.z)
            }
        }

        // Apply velocity
        for (planet in planets) {
            planet.x += planet.velX
            planet.y += planet.velY
            planet.z += planet.velZ
        }
    }

    /**
     * Calculate the velocity change on one axis between two planets
     *
     * @param thisPlanet coordinate value of the planet we are calculating the velocity for
     * @param otherPlanet coordinate value of another planet
     * @return velocity change
     */
    private fun calcVelChange(thisPlanet: Int, otherPlanet: Int): Int {
        return when {
            otherPlanet > thisPlanet -> 1
            otherPlanet < thisPlanet -> -1
            else -> 0
        }
    }

    class Planet(var x: Int, var y: Int, var z: Int) {
        var velX = 0
        var velY = 0
        var velZ = 0

        fun getEnergy(): Int {
            return (x.absoluteValue + y.absoluteValue + z.absoluteValue) * (velX.absoluteValue + velY.absoluteValue + velZ.absoluteValue)
        }

        companion object {
            fun fromText(text: String): Planet {
                val matches = "<x=(-?[0-9]+), y=(-?[0-9]+), z=(-?[0-9]+)>"
                    .toRegex()
                    .find(text)!!
                    .groupValues
                    .drop(1)
                    .map { it.toInt() }

                return Planet(matches[0], matches[1], matches[2])
            }
        }
    }
}