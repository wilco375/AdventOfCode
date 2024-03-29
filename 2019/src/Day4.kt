class Day4 : AbstractDay() {
    override fun run() = sequence<Any> {
        val input = (240920..789857)
        val output1 = input
            .filter { it in 100000..999999 }
            .filter { it.hasSameAdjacentDigits() }
            .filter { it.hasIncreasingDigits() }
            .size
        yield(output1) // Part 1

        val output2 = input
            .filter { it in 100000..999999 }
            .filter { it.hasTwoAdjacentDigits() }
            .filter { it.hasIncreasingDigits() }
            .size
        yield(output2) // Part 2
    }

    private fun Int.hasSameAdjacentDigits(): Boolean {
        val string = this.toString()
        for (i in (1 until string.length)) {
            if (string[i - 1] == string[i]) {
                return true
            }
        }
        return false
    }

    private fun Int.hasTwoAdjacentDigits(): Boolean {
        val string = this.toString()
        for (i in (1 until string.length)) {
            if (string[i - 1] == string[i]) {
                // Two of the same
                if ((i - 2 < 0 || string[i - 2] != string[i]) && (i + 1 >= string.length || string[i + 1] != string[i])) {
                    // Not three or more of the same
                    return true
                }
            }
        }
        return false
    }


    private fun Int.hasIncreasingDigits(): Boolean {
        val string = this.toString()
        var last = string[0].toInt()
        for (i in (1 until string.length)) {
            if (string[i].toInt() < last) {
                return false
            }
            last = string[i].toInt()
        }
        return true
    }
}
