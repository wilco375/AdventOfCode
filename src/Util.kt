fun readInputFile(filename: String): String {
    return object {}.javaClass.getResource("input/${filename}").readText().trim()
}

fun readIntcode(filename: String): List<Long> {
    return readInputFile(filename)
        .split(",")
        .map { it.toLong() } // Convert to list of integers
}
