import java.lang.RuntimeException
import java.util.*

fun main() {
    val program = readInputFile("Day5.txt")
        .split(",")
        .map { it.toInt() } // Convert to list of integers

    val computer1 = Computer(program, 1)
    computer1.run()
    val output1 = computer1.getOutput().last()

    println("5.1:")
    println(output1)

    val computer2 = Computer(program, 5)
    computer2.run()
    val output2 = computer2.getOutput().last()

    println("5.2:")
    println(output2)
}

class Computer(initialMemory: List<Int>, private val input: Int) {
    private var output = LinkedList<Int>()
    private var mem = initialMemory.toMutableList() // Memory
    private var pc = 0 // Program counter
    
    fun run() {
        val instr = mem[pc].toString().padStart(5, '0')
        val opCode = instr.substring(3, 5).toInt()
        val mode1 = instr.substring(2, 3).toInt()
        val mode2 = instr.substring(1, 2).toInt()
        val mode3 = instr.substring(0, 1).toInt()

        when (opCode) {
            99 -> {
                // Program is finished
                return
            }
            1 -> {
                // Add
                val param1 = if (mode1 == 0) mem[mem[pc + 1]] else mem[pc + 1]
                val param2 = if (mode2 == 0) mem[mem[pc + 2]] else mem[pc + 2]
                mem[mem[pc + 3]] = param1 + param2
                pc += 4
            }
            2 -> {
                // Multiply
                val param1 = if (mode1 == 0) mem[mem[pc + 1]] else mem[pc + 1]
                val param2 = if (mode2 == 0) mem[mem[pc + 2]] else mem[pc + 2]
                mem[mem[pc + 3]] = param1 * param2
                pc += 4
            }
            3 -> {
                // Input
                mem[mem[pc + 1]] = input
                pc += 2
            }
            4 -> {
                // Output
                output.add(if (mode1 == 0) mem[mem[pc + 1]] else mem[pc + 1])
                pc += 2
            }
            5 -> {
                // Jump-if-true
                val param1 = if (mode1 == 0) mem[mem[pc + 1]] else mem[pc + 1]
                if (param1 != 0) {
                    val param2 = if (mode2 == 0) mem[mem[pc + 2]] else mem[pc + 2]
                    pc = param2
                } else {
                    pc += 3
                }
            }
            6 -> {
                // Jump-if-false
                val param1 = if (mode1 == 0) mem[mem[pc + 1]] else mem[pc + 1]
                if (param1 == 0) {
                    val param2 = if (mode2 == 0) mem[mem[pc + 2]] else mem[pc + 2]
                    pc = param2
                } else {
                    pc += 3
                }
            }
            7 -> {
                // Less than
                val param1 = if (mode1 == 0) mem[mem[pc + 1]] else mem[pc + 1]
                val param2 = if (mode2 == 0) mem[mem[pc + 2]] else mem[pc + 2]
                if (param1 < param2) {
                    mem[mem[pc + 3]] = 1
                } else {
                    mem[mem[pc + 3]] = 0
                }
                pc += 4
            }
            8 -> {
                // Equals
                val param1 = if (mode1 == 0) mem[mem[pc + 1]] else mem[pc + 1]
                val param2 = if (mode2 == 0) mem[mem[pc + 2]] else mem[pc + 2]
                if (param1 == param2) {
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
        run()
    }
    
    fun getMemory(): List<Int> {
        return mem
    }

    fun getOutput(): List<Int> {
        return output
    }
}