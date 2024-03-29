import com.marcinmoskala.math.permutations
import java.lang.Long.max

class Day7 : AbstractDay() {
    override fun run() = sequence<Any> {
        val program = readIntcode("Day7.txt")

        var maxOutput1 = 0L
        (0L..4).toList().permutations().forEach { (a, b, c, d, e) ->
            val computerA = Computer(program, listOf(a, 0)).run()
            val computerB = Computer(program, listOf(b, computerA.getOutput().last())).run()
            val computerC = Computer(program, listOf(c, computerB.getOutput().last())).run()
            val computerD = Computer(program, listOf(d, computerC.getOutput().last())).run()
            val computerE = Computer(program, listOf(e, computerD.getOutput().last())).run()
            val output = computerE.getOutput().last()
            maxOutput1 = max(output, maxOutput1)
        }

        yield(maxOutput1) // Part 1

        var maxOutput2 = 0L
        (5L..9).toList().permutations().forEach { (a, b, c, d, e) ->
            val computerA = Computer(program, listOf(a, 0))
            val computerB = Computer(program, listOf(b))
            val computerC = Computer(program, listOf(c))
            val computerD = Computer(program, listOf(d))
            val computerE = Computer(program, listOf(e))
            var lastOutput = 0L
            try {
                while (true) {
                    val outA = computerA.runForOutput()
                    computerB.addInput(outA)
                    val outB = computerB.runForOutput()
                    computerC.addInput(outB)
                    val outC = computerC.runForOutput()
                    computerD.addInput(outC)
                    val outD = computerD.runForOutput()
                    computerE.addInput(outD)
                    val outE = computerE.runForOutput()
                    computerA.addInput(outE)
                    lastOutput = outE
                }
            } catch (e: IllegalStateException) {
                // Program is finished
                maxOutput2 = max(lastOutput, maxOutput2)
            }
        }
        yield(maxOutput2) // Part 2
    }
}