import java.lang.RuntimeException
import java.util.*

class Computer(initialMemory: List<Int>, input: List<Int>) {
    private val input: MutableList<Int> = input.toMutableList()
    private var output = LinkedList<Int>()
    private var mem = initialMemory.toMutableList() // Memory
    private var pc = 0 // Program counter

    fun run(): Computer {
        run(false)
        return this
    }

    fun runForOutput(): Int {
        return run(true)
    }

    private fun run(returnOutput: Boolean): Int {
        val instr = mem[pc].toString().padStart(5, '0')
        val opCode = instr.substring(3, 5).toInt()
        val mode = listOf(
            instr.substring(2, 3).toInt(),
            instr.substring(1, 2).toInt(),
            instr.substring(0, 1).toInt()
        )

        fun getParam(i: Int): Int {
            return if (mode[i - 1] == 0) mem[mem[pc + i]] else mem[pc + i]
        }

        when (opCode) {
            99 -> {
                // Program is finished
                if (returnOutput) {
                    throw IllegalStateException("No output")
                } else {
                    return 0
                }
            }
            1 -> {
                // Add
                mem[mem[pc + 3]] = getParam(1) + getParam(2)
                pc += 4
            }
            2 -> {
                // Multiply
                mem[mem[pc + 3]] = getParam(1) * getParam(2)
                pc += 4
            }
            3 -> {
                // Input
                mem[mem[pc + 1]] = input.removeAt(0)
                pc += 2
            }
            4 -> {
                // Output
                output.add(getParam(1))
                pc += 2
                if (returnOutput) {
                    return output.last
                }
            }
            5 -> {
                // Jump-if-true
                if (getParam(1) != 0) {
                    pc = getParam(2)
                } else {
                    pc += 3
                }
            }
            6 -> {
                // Jump-if-false
                if (getParam(1) == 0) {
                    pc = getParam(2)
                } else {
                    pc += 3
                }
            }
            7 -> {
                // Less than
                if (getParam(1) < getParam(2)) {
                    mem[mem[pc + 3]] = 1
                } else {
                    mem[mem[pc + 3]] = 0
                }
                pc += 4
            }
            8 -> {
                // Equals
                if (getParam(1) == getParam(2)) {
                    mem[mem[pc + 3]] = 1
                } else {
                    mem[mem[pc + 3]] = 0
                }
                pc += 4
            }
            else -> {
                throw RuntimeException("Unknown opcode ${mem[pc]}")
            }
        }
        return run(returnOutput)
    }

    fun addInput(newInput: Int) {
        input.add(newInput)
    }

    fun getMemory(): List<Int> {
        return mem
    }

    fun getOutput(): List<Int> {
        return output
    }
}