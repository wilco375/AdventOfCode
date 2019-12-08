fun main() {
    val layerWidth = 25
    val layerHeight = 6

    val input = readInputFile("Day8.txt")
        .chunked(layerWidth * layerHeight) // Split into layers

    val fewestZeroLayer = input
        .minBy { it.count { it == '0' } }!! // Count 0 digits per layer and get layer with least 0 digits

    val output1 = fewestZeroLayer.count { it == '1' } * fewestZeroLayer.count { it == '2' }

    println("8.1:")
    println(output1)

    val output2 = input
        .reduce { layerA, layerB ->

            layerA.zip(layerB) { a, b ->
                if (a == '2') b else a // If a pixel is transparent show the next layer
            }.joinToString("")
        }

    val formattedOutput2 = output2
        .chunked(layerWidth)
        .joinToString("\n")
        .replace("1", "█")
        .replace("0", " ")

    println("8.2:")
    println(formattedOutput2)
}