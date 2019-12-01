fun readInputFile(filename: String): String {
    return object {}.javaClass.getResource("input/${filename}").readText().trim()
}