fun main() {
    val input = readInputFile("Day1.txt")
        .split("\n")
        .map { it.toInt() } // Convert to list of integers

    val output1 = input
        .map { calculateFuel(it) }
        .sum() // Get the sum of the list

    println("1.1:")
    println(output1)

    val output2 = input
        .map { calculateFuelRec(it) }
        .sum() // Get the sum of the list

    println("1.2:")
    println(output2)
}

fun calculateFuel(mass: Int): Int {
    return (mass / 3) - 2 // Divide by 3 and round down (integer division always rounds down) and subtract 2
}

fun calculateFuelRec(mass: Int): Int {
    val requiredFuel = (mass / 3) - 2 // Divide by 3 and round down (integer division always rounds down) and subtract 2
    return if (requiredFuel <= 0) {
        // If the required fuel is zero or negative, stop calculating
        0
    } else {
        // Otherwise return the required fuel plus the required fuel for that fuel
        requiredFuel + calculateFuelRec(requiredFuel)
    }
}