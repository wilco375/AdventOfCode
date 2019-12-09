import java.lang.RuntimeException
import java.util.*

class Computer(initialMemory: List<Long>, input: List<Long>) {
    private val input: MutableList<Long> = input.toMutableList()
    private var output = LinkedList<Long>()
    private var mem = initialMemory.toMutableList() // Memory
    private var pc = 0 // Program counter
    private var relBase = 0 // Relative base

    fun run(): Computer {
        run(false)
        return this
    }

    fun runForOutput(): Long {
        return run(true)
    }

    private fun run(returnOutput: Boolean): Long {
        val instr = mem[pc].toString().padStart(5, '0')
        val opCode = instr.substring(3, 5).toInt()
        val mode = listOf(
            instr.substring(2, 3).toInt(),
            instr.substring(1, 2).toInt(),
            instr.substring(0, 1).toInt()
        )

        fun getParamIndex(i: Int): Int {
            val index =  when (mode[i - 1]) {
                0 -> mem[pc + i].toInt() // Position mode
                1 -> pc + i // Immediate mode
                2 -> relBase + mem[pc + i].toInt() // Relative mode
                else -> throw UnsupportedOperationException("Unsupported parameter mode ${mode[i - 1]}")
            }
            while (index > mem.size - 1) {
                mem.add(0)
            }
            return index
        }

        fun getParam(i: Int): Long {
            return mem[getParamIndex(i)]
        }

        fun setParam(i: Int, value: Long) {
            mem[getParamIndex(i)] = value
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
                setParam(3, getParam(1) + getParam(2))
                pc += 4
            }
            2 -> {
                // Multiply
                setParam(3, getParam(1) * getParam(2))
                pc += 4
            }
            3 -> {
                // Input
                setParam(1, input.removeAt(0))
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
                if (getParam(1) != 0L) {
                    pc = getParam(2).toInt()
                } else {
                    pc += 3
                }
            }
            6 -> {
                // Jump-if-false
                if (getParam(1) == 0L) {
                    pc = getParam(2).toInt()
                } else {
                    pc += 3
                }
            }
            7 -> {
                // Less than
                if (getParam(1) < getParam(2)) {
                    setParam(3, 1)
                } else {
                    setParam(3, 0)
                }
                pc += 4
            }
            8 -> {
                // Equals
                if (getParam(1) == getParam(2)) {
                    setParam(3, 1)
                } else {
                    setParam(3, 0)
                }
                pc += 4
            }
            9 -> {
                // Adjust relative base
                relBase += getParam(1).toInt()
                pc += 2
            }
            else -> {
                throw RuntimeException("Unknown opcode ${mem[pc]}")
            }
        }
        return run(returnOutput)
    }

    fun addInput(newInput: Long) {
        input.add(newInput)
    }

    fun getMemory(): List<Long> {
        return mem
    }

    fun getOutput(): List<Long> {
        return output
    }
}