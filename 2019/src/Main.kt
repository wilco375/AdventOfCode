fun main(args: Array<String>) {
    fun runDay(day: Int) {
        println("============ Day ${day.toString().padStart(2)} ============")
        try {
            val dayClass = Class.forName("Day$day")
            val dayObj = dayClass.getConstructor().newInstance() as AbstractDay
            dayObj.run().forEachIndexed { part, result ->
                println("Part ${part + 1}:")
                println(result)
                println()
            }
        } catch (e: ClassNotFoundException) {
            println("Not implemented")
            println()
        }
    }

    if (args.isEmpty()) {
        for (i in 1..25) {
            runDay(i)
        }
    } else {
        runDay(args[0].toInt())
    }
}

