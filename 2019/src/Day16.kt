import kotlin.system.measureTimeMillis

class Day16 : AbstractDay() {
    override fun run() = sequence<Any> {
        val input = "03036732577212944063491565474664"//readInputFile("Day16.txt")

        var output = input
        repeat(100) {
            output = applyPhase(output)
        }

        yield(output.substring(0 until 8)) // Part 1
    }

    private fun applyPhase(input: String): String {
        var output = ""
        for (i in input.indices) {
            val pattern = generatePattern(i + 1).take(input.length).toList()
            output += input.toList().zip(pattern) { char, patternVal ->
                Character.getNumericValue(char) * patternVal
            }.sum().toString().last()
        }
        return output
    }

    private fun generatePattern(element: Int) = sequence {
        while (true) {
            repeat(element) {
                yield(0)
            }
            repeat(element) {
                yield(1)
            }
            repeat(element) {
                yield(0)
            }
            repeat(element) {
                yield(-1)
            }
        }
    }.drop(1)
}